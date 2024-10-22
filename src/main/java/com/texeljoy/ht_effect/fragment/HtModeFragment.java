package com.texeljoy.ht_effect.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.appcompat.widget.AppCompatTextView;
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
  private AppCompatTextView btnGesture;
  private AppCompatTextView btnPortrait;
  private AppCompatTextView btnFilter;
  private AppCompatTextView btnBeautyMakeup;
  private AppCompatTextView btnHair;
  private AppCompatTextView btnBody;
  private View container;

  /**
   * 根据系统主题改变功能列表样式
   * @param o
   */
  @Subscribe(thread = EventThread.MAIN_THREAD,
             tags = { @Tag(HTEventAction.ACTION_CHANGE_THEME) })
  public void changeTheme(Object o) {
    Drawable beautyDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.icon_func_beauty);
    Drawable arpropsDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.icon_func_arprops);
    Drawable threeDDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.icon_func_filter);
    Drawable gestureDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.icon_func_gesture);
    Drawable portraitDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.icon_func_aiportrait);
    Drawable beautymakeupDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.icon_func_beautymakeup);
    Drawable hairDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.icon_func_hair);
    Drawable bodyDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.icon_func_body);

    if (HtState.isDark) {

      DrawableCompat.setTint(beautyDrawable, ContextCompat.getColor(getContext(), android.R.color.white));
      DrawableCompat.setTint(arpropsDrawable, ContextCompat.getColor(getContext(), android.R.color.white));
      DrawableCompat.setTint(threeDDrawable, ContextCompat.getColor(getContext(), android.R.color.white));
      DrawableCompat.setTint(gestureDrawable, ContextCompat.getColor(getContext(), android.R.color.white));
      DrawableCompat.setTint(portraitDrawable, ContextCompat.getColor(getContext(), android.R.color.white));
      DrawableCompat.setTint(beautymakeupDrawable, ContextCompat.getColor(getContext(), android.R.color.white));
      DrawableCompat.setTint(hairDrawable, ContextCompat.getColor(getContext(), android.R.color.white));
      DrawableCompat.setTint(bodyDrawable, ContextCompat.getColor(getContext(), android.R.color.white));

      container.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.dark_background));
      btnBeauty.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
      btnARprops.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
      btnFilter.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
      btnGesture.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
      btnPortrait.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
      btnBeautyMakeup.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
      btnHair.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
      btnBody.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
    } else {

      DrawableCompat.setTint(beautyDrawable, ContextCompat.getColor(getContext(), R.color.dark_background));
      DrawableCompat.setTint(arpropsDrawable, ContextCompat.getColor(getContext(), R.color.dark_background));
      DrawableCompat.setTint(threeDDrawable, ContextCompat.getColor(getContext(), R.color.dark_background));
      DrawableCompat.setTint(gestureDrawable, ContextCompat.getColor(getContext(), R.color.dark_background));
      DrawableCompat.setTint(portraitDrawable, ContextCompat.getColor(getContext(), R.color.dark_background));
      DrawableCompat.setTint(beautymakeupDrawable, ContextCompat.getColor(getContext(), R.color.dark_background));
      DrawableCompat.setTint(hairDrawable, ContextCompat.getColor(getContext(), R.color.dark_background));
      DrawableCompat.setTint(bodyDrawable, ContextCompat.getColor(getContext(), R.color.dark_background));

      container.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_background));
      btnBeauty.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_background));
      btnARprops.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_background));
      btnFilter.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_background));
      btnGesture.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_background));
      btnPortrait.setTextColor(ContextCompat.getColor(getContext(), R.color.dark_background));
      btnBeautyMakeup.setTextColor(ContextCompat.getColor(getContext(),R.color.dark_background));
      btnHair.setTextColor(ContextCompat.getColor(getContext(),R.color.dark_background));
      btnBody.setTextColor(ContextCompat.getColor(getContext(),R.color.dark_background));
    }
    btnBeauty.setCompoundDrawablesWithIntrinsicBounds(null, beautyDrawable, null, null);
    btnARprops.setCompoundDrawablesWithIntrinsicBounds(null, arpropsDrawable, null, null);
    btnFilter.setCompoundDrawablesWithIntrinsicBounds(null, threeDDrawable, null, null);
    btnGesture.setCompoundDrawablesWithIntrinsicBounds(null, gestureDrawable, null, null);
    btnPortrait.setCompoundDrawablesWithIntrinsicBounds(null, portraitDrawable, null, null);
    btnBeautyMakeup.setCompoundDrawablesWithIntrinsicBounds(null, beautymakeupDrawable, null, null);
    btnHair.setCompoundDrawablesWithIntrinsicBounds(null, hairDrawable, null, null);
    btnBody.setCompoundDrawablesWithIntrinsicBounds(null, bodyDrawable, null, null);
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
    btnFilter = view.findViewById(R.id.btn_filter);
    btnGesture = view.findViewById(R.id.btn_gesture);
    btnPortrait = view.findViewById(R.id.btn_portrait);
    btnBeautyMakeup = view.findViewById(R.id.btn_beautymakeup);
    btnHair = view.findViewById(R.id.btn_hair);
    btnBody = view.findViewById(R.id.btn_body);
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

    //点击滤镜,进入滤镜面板
    btnFilter.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        RxBus.get().post(HTEventAction.ACTION_CHANGE_PANEL, HTViewState.FILTER);
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
    //点击美妆，进入美妆面板
    btnBeautyMakeup.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        RxBus.get().post(HTEventAction.ACTION_CHANGE_PANEL, HTViewState.BEAUTYMAKEUP);
      }
    });
    //点击美发，进入美发面板
    btnHair.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        RxBus.get().post(HTEventAction.ACTION_CHANGE_PANEL, HTViewState.HAIR);
      }
    });
    //点击美体，进入美体面板
    btnBody.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        RxBus.get().post(HTEventAction.ACTION_CHANGE_PANEL, HTViewState.BODY);
      }
    });
  }
}
