package com.texeljoy.ht_effect.model;

import android.util.Log;
import com.texeljoy.ht_effect.model.HtFilterConfig.HtFilter;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;

/**
 * UI状态容器
 */
public class HtState {

  //一级面板是哪个
  public static HTViewState currentViewState = HTViewState.HIDE;

  //二级面板是哪个
  public static HTViewState currentSecondViewState = HTViewState.BEAUTY_SKIN;

  // 当前选中了哪个美颜参数
  private static HtBeautyKey currentBeautySkin = HtBeautyKey.NONE;

  // 当前选中了哪个美型参数
  public static HtFaceTrim currentFaceTrim = HtFaceTrim.EYE_ENLARGING;

  // 当前选中了哪个滤镜
  public static HtFilter currentFilter = HtFilter.NO_FILTER;

  // 选中哪个风格
  public static HtStyle currentStyle = HtStyle.YUAN_TU;

  // 当前选中哪个AR道具
  public static HTViewState currentAR = HTViewState.AR_PROP;

  // 当前选中哪个轻彩妆
  //public static HtMakeup currentMakeup = HtMakeup.NONE;



  //释放黑色主题
  public static boolean isDark = true;

  public static HtBeautyKey getCurrentBeautySkin() {
    return currentBeautySkin;
  }

  public static void setCurrentBeautySkin(HtBeautyKey currentBeautySkin) {
    Log.e("设置当前的美肤",currentBeautySkin.name());
    HtState.currentBeautySkin = currentBeautySkin;
  }

  //释放
  public static void release() {
    currentViewState = HTViewState.HIDE;
    currentSecondViewState = HTViewState.BEAUTY_SKIN;
    currentBeautySkin = HtBeautyKey.NONE;
    currentFaceTrim = HtFaceTrim.EYE_ENLARGING;
    currentStyle = HtStyle.YUAN_TU;

    HtUICacheUtils.beautyFaceTrimPosition(-1);
    HtUICacheUtils.beautySkinPosition(-1);
    HtUICacheUtils.beautyStylePosition(0);
  }
}
