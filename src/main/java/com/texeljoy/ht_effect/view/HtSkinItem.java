package com.texeljoy.ht_effect.view;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
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
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.model.HtBeauty;
import com.texeljoy.ht_effect.model.HtBeautyKey;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;

/**
 * 美颜——美肤——美肤的Item
 */
@SuppressWarnings("unused")
public class HtSkinItem extends LinearLayout {

  public final @NonNull AppCompatTextView text;

  public final @NonNull AppCompatImageView image;

  public final @NonNull View point;

  private HtBeauty htBeauty;

  private int initialValue = 0;

  public void init(HtBeauty htBeauty) {
    this.htBeauty = htBeauty;
    if (htBeauty == HtBeauty.brightness) {
      initialValue = 50;
    }
    updateData();
    changedPoint("");
  }

  public HtSkinItem(Context context) {
    super(context);
  }

  public HtSkinItem(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public HtSkinItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  {
    LayoutInflater.from(getContext()).inflate(R.layout.item_drawable_top_button, this);
    RxBus.get().register(this);
    text = findViewById(R.id.htTextTV);
    image = findViewById(R.id.htImageIV);
    point = findViewById(R.id.point);

    //同步滑动条
    RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");

    setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {

        if (v.isSelected()) {
          return;
        } else {
          HtState.setCurrentBeautySkin(htBeauty.getHtBeautyKey());
          v.setSelected(true);
        }

        RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");

      }
    });

  }

  /**
   * 将匹配上的美肤Item设为选中状态
   * @param o
   */
  @Subscribe(thread = EventThread.MAIN_THREAD,
             tags = { @Tag(HTEventAction.ACTION_SYNC_PROGRESS) })
  public void syncState(Object o) {
    setSelected(htBeauty
        .getHtBeautyKey().name()
        .equals(HtState.getCurrentBeautySkin().name()));
  }

  /**
   * 根据主题修改UI
   */
  public void updateData() {
    if (HtState.isDark) {
      text.setText((htBeauty.getName(getContext())));
      image.setImageDrawable(ContextCompat
          .getDrawable(getContext(),
              htBeauty.getDrawableRes_white()));
      text.setTextColor(
          ContextCompat.getColorStateList(getContext(),
              R.color.color_selector_tab_dark));
    } else {
      text.setText((htBeauty.getName(getContext())));
      image.setImageDrawable(ContextCompat
          .getDrawable(getContext(),
              htBeauty.getDrawableRes_black()));
      text.setTextColor(
          ContextCompat.getColorStateList(getContext(),
              R.color.color_selector_tab_light));
    }
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
   * 根据参数判断当前是否显示圆点
   * @param o
   */
  @Subscribe(thread = EventThread.MAIN_THREAD,
             tags = { @Tag(HTEventAction.ACTION_SYNC_ITEM_CHANGED) })
  public void changedPoint(Object o) {
    point.setVisibility((HtUICacheUtils
        .beautySkinValue(HtBeautyKey.valueOf(htBeauty.name())) != initialValue) ?
                        View.VISIBLE : View.INVISIBLE);
  }

}
