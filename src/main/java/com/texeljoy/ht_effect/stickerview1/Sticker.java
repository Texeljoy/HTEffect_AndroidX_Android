package com.texeljoy.ht_effect.stickerview1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.view.MotionEvent;

/**
 * 贴纸类（手势与贴纸的关系）
 * Create by: chenWei.li
 * Date: 2019/2/3
 * Time: 8:44 PM
 * Email: lichenwei.me@foxmail.com
 */
public class Sticker extends BaseSticker {

    private PointF mLastTouchedPoint = new PointF();//记录上一次单指触摸屏幕的点坐标
    private PointF mCenterPoint = new PointF();//记录中心点坐标

    private float[] dstPoints;
    private PointF mCurrentTouchedPoint = new PointF();
    private PointF mCurrentPoint = new PointF();

    private PointF mLastDistanceVector = new PointF();//记录上一次双指之间的向量
    private PointF mDistanceVector = new PointF();//记录当前双指之间的向量
    private float mLastDistance;//记录上一次双指之间的距离

    //记录点坐标，减少对象在onTouch中的创建
    private PointF mFirstPoint = new PointF();
    private PointF mSecondPoint = new PointF();

    public Sticker(Context context, Bitmap bitmap) {
        super(context, bitmap);
    }

    /**
     * 重置状态
     */
    public void reset() {

        mLastTouchedPoint.set(0f, 0f);
        mLastDistanceVector.set(0f, 0f);
        mDistanceVector.set(0f, 0f);
        mLastDistance = 0f;
        mMode = MODE_NONE;
    }

    /**
     * 计算两点之间的距离
     */
    public float calculateDistance(PointF firstPointF, PointF secondPointF) {
        float x = firstPointF.x - secondPointF.x;
        float y = firstPointF.y - secondPointF.y;
        return (float) Math.sqrt(x * x + y * y);
    }


    /**
     * 计算旋转角度
     *
     * @param lastVector
     * @param currentVector
     * @return
     */
    public float calculateDegrees(PointF lastVector, PointF currentVector) {
        float lastDegrees = (float) Math.atan2(lastVector.y, lastVector.x);
        float currentDegrees = (float) Math.atan2(currentVector.y, currentVector.x);
        return (float) Math.toDegrees(currentDegrees - lastDegrees);
    }


    /**
     * 处理触摸事件
     *
     * @param event
     */
    public void onTouch(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                dstPoints = getmDstPoints();
                boolean isRotate = StickerManager.getInstance().getRotateButton(event.getX(), event.getY());
                if(isRotate){
                    mMode = Sticker.MODE_MULTIPLE;
                }else{
                    //有触摸到贴纸
                    mMode = Sticker.MODE_SINGLE;
                }
                //记录贴纸中心点的坐标
                mCenterPoint.set(dstPoints[8],dstPoints[9]);
                //记录按下的位置
                mLastTouchedPoint.set(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                if (event.getPointerCount() == 2) {
                    mMode = Sticker.MODE_MULTIPLE;
                    //记录双指的点位置
                    mFirstPoint.set(event.getX(0), event.getY(0));
                    mSecondPoint.set(event.getX(1), event.getY(1));
                    //计算双指之间向量
                    mLastDistanceVector.set(mFirstPoint.x - mSecondPoint.x, mFirstPoint.y - mSecondPoint.y);
                    //计算双指之间距离
                    mLastDistance = calculateDistance(mFirstPoint, mSecondPoint);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                // mCurrentTouchedPoint.set(event.getX() - mLastTouchedPoint.x, event.getY() - mLastTouchedPoint.y);
                // mCurrentPoint.set(event.getX() - mLastTouchedPoint.x, event.getY() - mLastTouchedPoint.y);
                if (mMode == MODE_SINGLE) {
                    //可移动
                    translate(event.getX() - mLastTouchedPoint.x, event.getY() - mLastTouchedPoint.y);
                    mLastTouchedPoint.set(event.getX(), event.getY());
                }
                if (mMode == MODE_MULTIPLE && event.getPointerCount() != 2) {
                    //记录双指的点位置
                    mFirstPoint.set(mCenterPoint);
                    mSecondPoint.set(event.getX(), event.getY());
                    //操作自由缩放
                    float distance = calculateDistance(mFirstPoint, mSecondPoint);
                    //根据双指移动的距离获取缩放因子
                    float scale = distance / mLastDistance;
                    scale(scale, scale);
                    mLastDistance = distance;
                    //操作自由旋转
                    mDistanceVector.set(mFirstPoint.x - mSecondPoint.x, mFirstPoint.y - mSecondPoint.y);
                    rotate(calculateDegrees(mLastDistanceVector, mDistanceVector));
                    mLastDistanceVector.set(mDistanceVector.x, mDistanceVector.y);
                }
                if(mMode == MODE_MULTIPLE && event.getPointerCount() == 2){

                }
                break;
            case MotionEvent.ACTION_UP:
                reset();
                break;
        }
    }

}
