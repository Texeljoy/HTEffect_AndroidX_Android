package com.texeljoy.ht_effect.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import android.view.View;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.base.HtBaseFragment;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HTViewState;
import com.texeljoy.ht_effect.model.HtState;

/**
 * 功能选择
 */
public class HtModeFragment extends HtBaseFragment {

  private AppCompatTextView btnBeauty;
  private AppCompatTextView btnARprops;
  private AppCompatTextView btnThreeD;
  private AppCompatTextView btnGesture;
  private AppCompatTextView btnPortrait;
  private View container;

  /**
   * 根据系统主题改变功能列表样式
   * @param o
   */
  @Subscribe(thread = EventThread.MAIN_THREAD,
             tags = { @Tag(HTEventAction.ACTION_CHANGE_THEME) })
  public void changeTheme(Object o) {
    Drawable beautyDrawable = ContextCompat.getDrawable(requireContext(), R.mipmap.icon_func_beauty);
    Drawable arpropsDrawable = ContextCompat.getDrawable(requireContext(), R.mipmap.icon_func_arprops);
    Drawable threeDDrawable = ContextCompat.getDrawable(requireContext(), R.mipmap.icon_func_makeup);
    Drawable gestureDrawable = ContextCompat.getDrawable(requireContext(), R.mipmap.icon_func_gesture);
    Drawable portraitDrawable = ContextCompat.getDrawable(requireContext(), R.mipmap.icon_func_portrait);

    if (HtState.isDark) {

      DrawableCompat.setTint(beautyDrawable, ContextCompat.getColor(getContext(), android.R.color.white));
      DrawableCompat.setTint(arpropsDrawable, ContextCompat.getColor(getContext(), android.R.color.white));
      DrawableCompat.setTint(threeDDrawable, ContextCompat.getColor(getContext(), android.R.color.white));
      DrawableCompat.setTint(gestureDrawable, ContextCompat.getColor(getContext(), android.R.color.white));
      DrawableCompat.setTint(portraitDrawable, ContextCompat.getColor(getContext(), android.R.color.white));

      container.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.dark_background));
      btnBeauty.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
      btnARprops.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
      btnThreeD.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
      btnGesture.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
      btnPortrait.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));

    } else {

      DrawableCompat.setTint(beautyDrawable, ContextCompat.getColor(getContext(), R.color.dark_background));
      DrawableCompat.setTint(arpropsDrawable, ContextCompat.getColor(getContext(), R.color.dark_background));
      DrawableCompat.setTint(threeDDrawable, ContextCompat.getColor(getContext(), R.color.dark_background));
      DrawableCompat.setTint(gestureDrawable, ContextCompat.getColor(getContext(), R.color.dark_background));
      DrawableCompat.setTint(portraitDrawable, ContextCompat.getColor(getContext(), R.color.dark_background));

      container.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_background));
      btnBeauty.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_background));
      btnARprops.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_background));
      btnThreeD.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_background));
      btnGesture.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_background));
      btnPortrait.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_background));
    }
    btnBeauty.setCompoundDrawablesWithIntrinsicBounds(null, beautyDrawable, null, null);
    btnARprops.setCompoundDrawablesWithIntrinsicBounds(null, arpropsDrawable, null, null);
    btnThreeD.setCompoundDrawablesWithIntrinsicBounds(null, threeDDrawable, null, null);
    btnGesture.setCompoundDrawablesWithIntrinsicBounds(null, gestureDrawable, null, null);
    btnPortrait.setCompoundDrawablesWithIntrinsicBounds(null, portraitDrawable, null, null);
  }

  @Override protected int getLayoutId() {
    return R.layout.layout_mode;
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    changeTheme(null);
  }

  @Override protected void initView(View view, Bundle savedInstanceState) {
    btnBeauty = view.findViewById(R.id.btn_beauty);
    btnARprops = view.findViewById(R.id.btn_arprops);
    btnThreeD = view.findViewById(R.id.btn_makeup);
    btnGesture = view.findViewById(R.id.btn_gesture);
    btnPortrait = view.findViewById(R.id.btn_portrait);
    container = view.findViewById(R.id.container);

    //点击美颜,进入美颜面板
    btnBeauty.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        RxBus.get().post(HTEventAction.ACTION_CHANGE_PANEL, HTViewState.BEAUTY);

      }
    });

    //点击AR道具,进入AR道具面板
    btnARprops.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        RxBus.get().post(HTEventAction.ACTION_CHANGE_PANEL, HTViewState.AR);

      }
    });

    //点击轻彩妆,进入轻彩妆面板
    btnThreeD.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        RxBus.get().post(HTEventAction.ACTION_CHANGE_PANEL, HTViewState.ThreeD);
      }
    });

    //点击手势识别,进入手势识别面板
    btnGesture.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        RxBus.get().post(HTEventAction.ACTION_CHANGE_PANEL, HTViewState.GESTURE);
      }
    });

    //点击人像抠图,进入人像抠图面板
    btnPortrait.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        RxBus.get().post(HTEventAction.ACTION_CHANGE_PANEL, HTViewState.PORTRAIT);
      }
    });
  }
}
