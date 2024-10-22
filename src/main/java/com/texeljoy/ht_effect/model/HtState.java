package com.texeljoy.ht_effect.model;

import android.util.Log;
import com.texeljoy.ht_effect.model.HtBeautyFilterConfig.HtBeautyFilter;
import com.texeljoy.ht_effect.model.HtEffectFilterConfig.HtEffectFilter;
import com.texeljoy.ht_effect.model.HtFunnyFilterConfig.HtFunnyFilter;
import com.texeljoy.ht_effect.model.HtHairConfig.HtHair;
import com.texeljoy.ht_effect.model.HtMakeupStyleConfig.HtMakeupStyle;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;

/**
 * UI状态容器
 */
public class HtState {

  //一级面板是哪个
  public static HTViewState currentViewState = HTViewState.HIDE;

  //二级面板是哪个
  public static HTViewState currentSecondViewState = HTViewState.BEAUTY_SKIN;

  public static HTViewState currentSliderViewState = HTViewState.BEAUTY_SKIN;

  //是否处于第三面板
  public static HTViewState currentThirdViewState = HTViewState.MAKEUP_OUT;

  // 当前选中了哪个美颜参数
  private static HtBeautyKey currentBeautySkin = HtBeautyKey.NONE;

  // 当前选中了哪个美型参数
  public static HtFaceTrim currentFaceTrim = HtFaceTrim.EYE_ENLARGING;

  // 当前选中了哪个美妆参数
  public static HtMakeUpEnum currentMakeUp = HtMakeUpEnum.LIPSTICK;

  public static HtMakeup currentLipstick = HtMakeup.NO_MAKEUP;
  public static HtMakeup currentEyebrow = HtMakeup.NO_MAKEUP;
  public static HtMakeup currentBlush = HtMakeup.NO_MAKEUP;
  public static HtMakeup currentEyeshadow = HtMakeup.NO_MAKEUP;
  public static HtMakeup currentEyeline = HtMakeup.NO_MAKEUP;
  public static HtMakeup currentEyelash = HtMakeup.NO_MAKEUP;
  public static HtMakeup currentPupils = HtMakeup.NO_MAKEUP;



  // 当前选中了哪个妆容推荐参数
  public static HtMakeupStyle currentMakeUpStyle = HtMakeupStyle.NO_STYLE;
  // public static HtMakeupStyleConfig currentMakeUpStyle = HtMakeupStyleConfig.NONE;

  // 当前选中了哪个美体参数
  public static HtBody currentBody = HtBody.LONG_LEG;

  // 当前选中了哪个滤镜
  public static HtBeautyFilter currentStyleFilter = HtBeautyFilter.NO_FILTER;
  public static HtEffectFilter currentEffectFilter = HtEffectFilter.NO_FILTER;
  public static HtFunnyFilter currentFunnyFilter = HtFunnyFilter.NO_FILTER;

  // 当前选中了哪个美发
  public static HtHair currentHair = HtHair.NO_HAIR;

  // 选中哪个风格
  // public static HtStyle currentStyle = HtStyle.YUAN_TU;
  public static HtMakeupStyle currentStyle = HtMakeupStyle.NO_STYLE;

  // 当前选中哪个AR道具
  public static HTViewState currentAR = HTViewState.AR_PROP;

  // 当前选中哪个妆容推荐
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
    currentStyle = HtMakeupStyle.NO_STYLE;
    // currentStyle = HtStyle.YUAN_TU;

    HtUICacheUtils.beautyFaceTrimPosition(-1);
    HtUICacheUtils.beautySkinPosition(-1);
    HtUICacheUtils.beautyStylePosition(0);
    HtUICacheUtils.setBeautyMakeUpStylePosition(0);
    HtUICacheUtils.beautyBodyPosition(0);
  }
}
