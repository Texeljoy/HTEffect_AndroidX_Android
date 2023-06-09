/*
 * Copyright (c) 2015-2018 Shi ChaoHui
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.texeljoy.ht_effect.view;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.animation.LinearInterpolator;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Spica 27 on 2018/8/7.
 * <p>
 * 拍照/录制视频的按钮。
 */
public class HtMagicButton extends AppCompatImageView implements Runnable {

    /**
     * 按钮点击监听。
     */
    public interface OnMagicClickedListener {

        /**
         * 单击。
         */
        void onClicked();

        /**
         * 开始长按。
         */
        void onLongClickStart();

        /**
         * 结束长按。
         */
        void onLongClickStop();
    }

    // 作为计时器时的计时颜色
    private final int COLOR_TIMING = Color.parseColor("#FF4500");

    private long maxLongClickTime = 10 * 1000L;
    private float longClickTime;

    // 本按钮作为计时器
    private boolean isTimer = false;
    private boolean isTiming = false;
    private int time = 0;
    private String timingText = "";
    private Timer mTimer = new Timer();
    private TimerTask mTimerTask;

    private Paint mPaint;

    private float backCircleX, backCircleY, backCircleRadius, backCircleMinRadius, backCircleMaxRadius;
    private float frontCircleX, frontCircleY, frontCircleRadius, frontCircleMinRadius, frontCircleMaxRadius;
    private RectF mBackCircleRectF;
    private Path mTextPath;

    private AnimatorSet zoomAnimSet;
    private ValueAnimator timeAnim;

    private final long animDuration = 250L;

    private boolean isTouching = false;
    private boolean isLongClicking = false;

    private Handler mHandler = new Handler();

    private OnMagicClickedListener mOnMagicClickedListener;

    private String text = "";

    public HtMagicButton(Context context) {
        this(context, null);
    }

    public HtMagicButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HtMagicButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        setClickable(true);
        setLongClickable(true);
    }

    /**
     * 设置最长长按时间。
     *
     * @param time 时间 ms 。
     */
    public void setMaxLongClickTime(long time) {
        maxLongClickTime = time;
    }

    /**
     * 设置点击监听。
     *
     * @param listener 监听器。
     */
    public void setOnMagicClickedListener(OnMagicClickedListener listener) {
        this.mOnMagicClickedListener = listener;
    }

    /**
     * 设置当前按钮是否作为计时器
     */
    public void setTimer(boolean isTimer) {
        this.isTimer = isTimer;
        setLongClickable(!isTimer);
    }

    /**
     * 设置文本。
     *
     * @param text 文本。
     */
    public void setText(String text) {
        this.text = text;
        updateTextPath(getWidth(), getHeight());
        postInvalidate();
    }

    /**
     * 取消本次点击。
     */
    public void cancel() {
        isLongClicking = false;
        isTouching = false;
        startZoomAnim(backCircleMinRadius, frontCircleMaxRadius);
        stopTimeAnim();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouching = true;
                // 启动进入长按事件的任务。
                if (isLongClickable()) {
                    mHandler.postDelayed(this, 150L);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isTouching) {
                    break;
                }
                mHandler.removeCallbacks(this);
                if (isLongClicking) {
                    onLongClickStop();
                } else if (isClickable()) {
                    onClicked();
                }
                isTouching = false;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        backCircleX = frontCircleX = w / 2f;
        backCircleY = frontCircleY = h / 2f;

        backCircleRadius = backCircleMinRadius = Math.min(w, h) / 8f * 3;
        backCircleMaxRadius = Math.min(w, h) / 8f * 4;

        frontCircleMinRadius = Math.min(w, h) / 8f * 1;
        frontCircleRadius = frontCircleMaxRadius = Math.min(w, h) / 8f * 2.5f;

        mPaint.setStrokeWidth(backCircleRadius / 8);
        mPaint.setTextSize((int) (12 * getResources().getDisplayMetrics().scaledDensity + 0.5f));

        mBackCircleRectF = new RectF(backCircleX - backCircleMaxRadius + mPaint.getStrokeWidth() / 2,
                backCircleY - backCircleMaxRadius + mPaint.getStrokeWidth() / 2,
                backCircleX + backCircleMaxRadius - mPaint.getStrokeWidth() / 2,
                backCircleY + backCircleMaxRadius - mPaint.getStrokeWidth() / 2);

        updateTextPath(w, h);
    }

    private void updateTextPath(int w, int h) {
        mTextPath = new Path();
        Rect textBounds = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), textBounds);
        RectF textRectF = new RectF(textBounds.height(), textBounds.height(),
                w - textBounds.height(), h - textBounds.height());
        // angle = arcLength / (2 * PI * radius ) * 360
        float angle = (float) (textBounds.width() / Math.PI / (Math.min(w, h) - textBounds.height() * 2) * 360F);
        mTextPath.addArc(textRectF, 270 - angle / 2, angle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);

        mPaint.setStyle(Paint.Style.FILL);

        if (!isTiming) {
            mPaint.setColor(Color.WHITE);
            canvas.drawTextOnPath(text, mTextPath, 0, 0, mPaint);
        }

        // 绘制背景圆。
        mPaint.setColor(Color.LTGRAY);
        canvas.drawCircle(backCircleX, backCircleY, backCircleRadius, mPaint);

        // 绘制前景圆。
        mPaint.setColor(isTiming ? COLOR_TIMING : Color.WHITE);
        canvas.drawCircle(frontCircleX, frontCircleY, frontCircleRadius, mPaint);

        if (isTiming) {
            // 绘制计时
            mPaint.setColor(Color.WHITE);
            canvas.drawText(
                    timingText,
                    (getWidth() - mPaint.measureText(timingText)) / 2,
                    (getHeight() - mPaint.ascent()) / 2,
                    mPaint
            );
        }

        if (backCircleRadius == backCircleMaxRadius) {
            // 绘制时间。
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.GREEN);
            canvas.drawArc(mBackCircleRectF,
                    270, 360 * longClickTime / maxLongClickTime,
                    false, mPaint);
        }
    }

    @Override
    public void run() {
        onLongClickStart();
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        throw new RuntimeException("请调用 setOnMagicClickedListener()");
    }

    @Override
    public void setOnLongClickListener(@Nullable OnLongClickListener l) {
        throw new RuntimeException("请调用 setOnMagicClickedListener()");
    }

    /**
     * 点击事件。
     */
    private void onClicked() {
        mOnMagicClickedListener.onClicked();
        if (isTimer) {
            if (isTiming) {
                stopTiming();
            } else {
                startTiming();
            }
        }
    }

    /**
     * 长按开始。
     */
    private void onLongClickStart() {
        isLongClicking = true;
        startZoomAnim(backCircleMaxRadius, frontCircleMinRadius);
        startTimeAnim();
        mOnMagicClickedListener.onLongClickStart();
    }

    /**
     * 长按停止。
     */
    private void onLongClickStop() {
        cancel();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mOnMagicClickedListener.onLongClickStop();
            }
        }, animDuration);
    }

    /**
     * 开始计时
     */
    private void startTiming() {
        isTiming=true;
        time = 0;
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                time++;
                timingText = String.format(Locale.getDefault(), "%02d:%02d", time / 60, time % 60);
                postInvalidate();
            }
        };
        mTimer.scheduleAtFixedRate(mTimerTask, 1000, 1000);
    }

    /**
     * 停止计时
     */
    private void stopTiming() {
        mTimerTask.cancel();
        mTimerTask = null;
        isTiming=false;
    }

    /**
     * 开始缩放动画。
     *
     * @param targetBackCircleRadius  背景圆的目标半径。
     * @param targetFrontCircleRadius 前景圆的目标半径。
     */
    private void startZoomAnim(float targetBackCircleRadius, float targetFrontCircleRadius) {
        if (zoomAnimSet != null && zoomAnimSet.isRunning()) {
            zoomAnimSet.cancel();
        }
        zoomAnimSet = new AnimatorSet();
        zoomAnimSet.playTogether(
                createZoomAnim(backCircleRadius, targetBackCircleRadius, new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        backCircleRadius = (float) valueAnimator.getAnimatedValue();
                        postInvalidate();
                    }
                }),
                createZoomAnim(frontCircleRadius, targetFrontCircleRadius, new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        frontCircleRadius = (float) valueAnimator.getAnimatedValue();
                        postInvalidate();
                    }
                })
        );
        zoomAnimSet.start();
    }

    /**
     * 创建动画。
     *
     * @param start    动画开始时的值。
     * @param end      动画结束时的值
     * @param listener 动画监听。
     * @return 动画。
     */
    private ValueAnimator createZoomAnim(float start, float end, ValueAnimator.AnimatorUpdateListener listener) {
        ValueAnimator anim = ValueAnimator.ofFloat(start, end);
        anim.setDuration(animDuration);
        anim.setInterpolator(new LinearInterpolator());
        anim.addUpdateListener(listener);
        return anim;
    }

    /**
     * 开始时间动画。
     */
    private void startTimeAnim() {
        stopTimeAnim();
        timeAnim = ValueAnimator.ofFloat(0, maxLongClickTime);
        timeAnim.setDuration(maxLongClickTime);
        timeAnim.setStartDelay(animDuration);
        timeAnim.setInterpolator(new LinearInterpolator());
        timeAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                longClickTime = (float) valueAnimator.getAnimatedValue();
                postInvalidate();
                if (longClickTime == maxLongClickTime) {
                    onLongClickStop();
                }
            }
        });
        timeAnim.start();
    }

    /**
     * 停止时间动画。
     */
    private void stopTimeAnim() {
        if (timeAnim != null && timeAnim.isRunning()) {
            timeAnim.cancel();
        }
        longClickTime = 0L;
    }

}