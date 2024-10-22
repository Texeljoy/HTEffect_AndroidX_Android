package com.texeljoy.ht_effect.view;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.constraintlayout.motion.widget.MotionLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HtBeauty;
import com.texeljoy.ht_effect.model.HtBeautyKey;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;

/**
 * 美颜——美肤——美肤的磨皮相关Item
 */
@SuppressWarnings("unused")
public class HtSkinBlurItem extends MotionLayout {

  private final int initialValue = 0;

  public MotionLayout motionContainer;
  private LinearLayout widget;
  private View btnBlur;
  private AppCompatImageView htImageIV;
  private AppCompatTextView htTextTV;
  private View point;
  private LinearLayout btnVagueBlurriness;
  private AppCompatImageView ivVagueBlurriness;
  private AppCompatTextView tvVagueBlurriness;
  private View pointVagueBlurriness;
  private LinearLayout btnPreciseBlurriness;
  private AppCompatImageView ivPreciseBlurriness;
  private AppCompatTextView tvPreciseBlurriness;
  private View pointPreciseBlurriness;

  public static HtBeautyKey blurType = HtBeautyKey.blurriness;

  public HtSkinBlurItem(@NonNull Context context) {
    super(context);
  }

  public HtSkinBlurItem(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public HtSkinBlurItem(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  {

    LayoutInflater.from(getContext()).inflate(R.layout.item_blurriness, this);
    RxBus.get().register(this);
    initView();

    btnBlur.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        if (v.isSelected()) {
          v.setSelected(false);
          motionContainer.transitionToStart();
          HtState.setCurrentBeautySkin(HtBeautyKey.NONE);
        } else {
          v.setSelected(true);
          motionContainer.transitionToEnd();
          HtState.setCurrentBeautySkin(blurType);
          update();

        }

        RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");

      }
    });

    btnPreciseBlurriness.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        blurType = HtBeautyKey.blurriness;
        if (v.isSelected()) {
          return;
        }
        v.setSelected(true);

        btnVagueBlurriness.setSelected(false);

        HtState.setCurrentBeautySkin(HtBeautyKey.blurriness);

        RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");

      }
    });

    btnVagueBlurriness.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        blurType = HtBeautyKey.blurriness;
        if (v.isSelected()) {
          return;
        }
        btnPreciseBlurriness.setSelected(false);
        v.setSelected(true);

        HtState.setCurrentBeautySkin(HtBeautyKey.blurriness);

        RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");

      }
    });

  }

  public void init() {
    update();
    changeTheme("");
    changedPoint("");
  }

  /**
   * 磨皮更新Item选中状态
   */
  public void update() {

    if (HtState.getCurrentBeautySkin() == HtBeautyKey.blurriness) {
      btnPreciseBlurriness.setSelected(true);
      btnVagueBlurriness.setSelected(false);
    } else {
      btnPreciseBlurriness.setSelected(false);
      btnVagueBlurriness.setSelected(true);
    }
  }

  @Subscribe(thread = EventThread.MAIN_THREAD,
             tags = { @Tag(HTEventAction.ACTION_SYNC_PROGRESS) })
  public void syncState(Object o) {

    if (HtState.getCurrentBeautySkin() == HtBeautyKey.blurriness ||
        HtState.getCurrentBeautySkin() == HtBeautyKey.blurriness
    ) {
      btnBlur.setSelected(true);
      update();
      motionContainer.transitionToEnd();

    } else {
      btnBlur.setSelected(false);
      update();
      motionContainer.transitionToStart();
    }

    changedPoint("");

  }

  @Subscribe(thread = EventThread.MAIN_THREAD,
             tags = { @Tag(HTEventAction.ACTION_CHANGE_THEME) })
  public void changeTheme(Object o) {

    if (HtState.isDark) {

      htImageIV.setImageDrawable(ContextCompat
          .getDrawable(getContext(),
              HtBeauty.blurriness.getDrawableRes_white()));

      htTextTV.setTextColor(ContextCompat.getColor(getContext(),
          R.color.light_background));

      ivPreciseBlurriness
          .setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_blurrness_precise_white));

      ivVagueBlurriness
          .setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_blurrness_vague_white));

      tvPreciseBlurriness.setTextColor(ContextCompat
          .getColorStateList(getContext(),
              R.color.color_selector_tab_dark
          ));

      tvVagueBlurriness.setTextColor(ContextCompat
          .getColorStateList(getContext(),
              R.color.color_selector_tab_dark
          ));

    } else {

      ivPreciseBlurriness
          .setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_blurrness_precise_black));

      ivVagueBlurriness
          .setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_blurriness_vague_black));

      htImageIV.setImageDrawable(ContextCompat
          .getDrawable(getContext(),
              HtBeauty.blurriness.getDrawableRes_black()));

      htTextTV.setTextColor(ContextCompat.getColor(getContext(),
          R.color.dark_black));

      tvVagueBlurriness.setTextColor(ContextCompat
          .getColorStateList(getContext(),
              R.color.color_selector_tab_light));

      tvPreciseBlurriness.setTextColor(ContextCompat
          .getColorStateList(getContext(),
              R.color.color_selector_tab_light));

    }

  }

  private void initView() {
    motionContainer = findViewById(R.id.motionContainer);
    widget = findViewById(R.id.widget);
    btnBlur = findViewById(R.id.btn_blur);
    htImageIV = findViewById(R.id.htImageIV);
    htTextTV = findViewById(R.id.tv_label);
    point = findViewById(R.id.point);
    btnVagueBlurriness = findViewById(R.id.btn_vague_blurriness);
    ivVagueBlurriness = findViewById(R.id.iv_vague_blurriness);
    tvVagueBlurriness = findViewById(R.id.tv_vague_blurriness);
    pointVagueBlurriness = findViewById(R.id.point_vague_blurriness);
    btnPreciseBlurriness = findViewById(R.id.btn_precise_blurriness);
    ivPreciseBlurriness = findViewById(R.id.iv_precise_blurriness);
    tvPreciseBlurriness = findViewById(R.id.tv_precise_blurriness);
    pointPreciseBlurriness = findViewById(R.id.point_precise_blurriness);
  }

  /**
   * 销毁View时候解绑
   */
  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    RxBus.get().unregister(this);
  }

  /**
   * 判断Item下方圆点是否显示
   * @param o
   */
  @Subscribe(thread = EventThread.MAIN_THREAD,
             tags = { @Tag(HTEventAction.ACTION_SYNC_ITEM_CHANGED) })
  public void changedPoint(Object o) {

    pointPreciseBlurriness
        .setVisibility((HtUICacheUtils.beautySkinValue(HtBeautyKey.blurriness) != 0) ?
                       View.VISIBLE : View.INVISIBLE);

    // pointVagueBlurriness
    //     .setVisibility((HtUICacheUtils.beautySkinValue(HtBeautyKey.vague_blurriness) != 0) ?
    //                    View.VISIBLE : View.INVISIBLE);

    point.setVisibility(
        (
            HtUICacheUtils.beautySkinValue(HtBeautyKey.blurriness) != 0
            // || ((HtUICacheUtils.beautySkinValue(HtBeautyKey.vague_blurriness) != 0)
            ) && (!btnBlur.isSelected())

        ?
        View.VISIBLE : View.INVISIBLE
    );

  }

}
