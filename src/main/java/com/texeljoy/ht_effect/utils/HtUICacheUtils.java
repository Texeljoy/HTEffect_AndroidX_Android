package com.texeljoy.ht_effect.utils;

import android.content.Context;
import android.util.Log;
import com.texeljoy.ht_effect.model.HtBeautyKey;
import com.texeljoy.ht_effect.model.HtBeautyParam;
import com.texeljoy.ht_effect.model.HtFaceTrim;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.model.HtStyleFilterEnum;
import com.texeljoy.ht_effect.model.HtUICacheKey;
import com.texeljoy.hteffect.HTEffect;
import com.texeljoy.hteffect.model.HTFilterEnum;

/**
 * ui缓存工具类
 */
public class HtUICacheUtils {

  /**
   * 载入缓存参数
   */
  public static void initCache(boolean isFirstInit) {

    HTEffect.shareInstance().setRenderEnable(true);

    if (isFirstInit) {
      HtState.release();

    }


    // HtStyleFilterEnum currentFilter = HtStyleFilterEnum.values()[beautyFilterPosition()];
    //设置滤镜
    HTEffect.shareInstance().setFilter(HTFilterEnum.HTFilterBeauty.getValue(), beautyFilterName());

    //美肤系
    HTEffect.shareInstance().setBeauty(HtBeautyParam.HTBeautyClearSmoothing,beautySkinValue(HtBeautyKey.blurriness));
    // HTEffect.shareInstance().setBeauty(HtBeautyParam.HTBeautyClearSmoothing,beautySkinValue(HtBeautyKey.precise_blurriness));
    HTEffect.shareInstance().setBeauty(HtBeautyParam.HTBeautySkinRosiness,beautySkinValue(HtBeautyKey.rosiness));
    HTEffect.shareInstance().setBeauty(HtBeautyParam.HTBeautySkinWhitening,beautySkinValue(HtBeautyKey.whiteness));
    HTEffect.shareInstance().setBeauty(HtBeautyParam.HTBeautyImageBrightness,beautySkinValue(HtBeautyKey.brightness) - 50);
    HTEffect.shareInstance().setBeauty(HtBeautyParam.HTBeautyImageSharpness,beautySkinValue(HtBeautyKey.clearness));
    HTEffect.shareInstance().setBeauty(HtBeautyParam.HTBeautyDarkCircleLessening,beautySkinValue(HtBeautyKey.undereye_circles));
    HTEffect.shareInstance().setBeauty(HtBeautyParam.HTBeautyNasolabialLessening,beautySkinValue(HtBeautyKey.nasolabial));
    //美型系
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeEyeEnlarging,beautyFaceTrimValue(HtFaceTrim.EYE_ENLARGING));
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeEyeRounding,beautyFaceTrimValue(HtFaceTrim.EYE_ROUNDING));
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeCheekThinning,beautyFaceTrimValue(HtFaceTrim.CHEEK_THINNING));
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeCheekVShaping,beautyFaceTrimValue(HtFaceTrim.CHEEK_V_SHAPING));
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeCheekNarrowing,beautyFaceTrimValue(HtFaceTrim.CHEEK_NARROWING));
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeCheekboneThinning,beautyFaceTrimValue(HtFaceTrim.CHEEK_BONE_THINNING));
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeJawboneThinning,beautyFaceTrimValue(HtFaceTrim.JAW_BONE_THINNING));
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeChinTrimming,beautyFaceTrimValue(HtFaceTrim.CHIN_TRIMMING) - 50);
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeTempleEnlarging,beautyFaceTrimValue(HtFaceTrim.TEMPLE_ENLARGING));
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeHeadLessening,beautyFaceTrimValue(HtFaceTrim.head_lessening));
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeFaceLessening,beautyFaceTrimValue(HtFaceTrim.FACE_LESSENING));
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeForeheadTrimming,beautyFaceTrimValue(HtFaceTrim.FOREHEAD_TRIM) - 50);
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeNoseThinning,beautyFaceTrimValue(HtFaceTrim.NOSE_THINNING));
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeEyeCornerEnlarging,beautyFaceTrimValue(HtFaceTrim.EYE_CORNER_ENLARGING));
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeEyeSpaceTrimming,beautyFaceTrimValue(HtFaceTrim.EYE_SAPCING) - 50);
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeEyeCornerTrimming,beautyFaceTrimValue(HtFaceTrim.EYE_CORNER_TRIMMING) - 50);
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeNoseEnlarging,beautyFaceTrimValue(HtFaceTrim.NOSE_ENLARGING) - 50);
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapePhiltrumTrimming,beautyFaceTrimValue(HtFaceTrim.PHILTRUM_TRIMMING) - 50);
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeNoseApexLessening,beautyFaceTrimValue(HtFaceTrim.NOSE_APEX_LESSENING));
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeNoseRootEnlarging,beautyFaceTrimValue(HtFaceTrim.NOSE_ROOT_ENLARGING));
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeMouthTrimming,beautyFaceTrimValue(HtFaceTrim.MOUTH_TRIMMING) - 50);
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeMouthSmiling,beautyFaceTrimValue(HtFaceTrim.MOUTH_SMILING));
    // int a = beautySimilarityValue();
    HTEffect.shareInstance().setGsSegEffectSimilarity(50);
    HTEffect.shareInstance().setGsSegEffectSmoothness(0);
    HTEffect.shareInstance().setGsSegEffectTransparency(0);


  }

  //---------美肤选中了哪个-------------------
  public static int beautySkinPosition() {
        return SharedPreferencesUtil
            .get(HtUICacheKey.BEAUTY_SKIN_SELECT_POSITION.name(),
        HtUICacheKey.BEAUTY_SKIN_SELECT_POSITION.getDefaultInt());
  }

  public static void beautySkinPosition(int position) {
    Log.e("beautySkinPosition",position+"");
    SharedPreferencesUtil.put(HtUICacheKey.BEAUTY_SKIN_SELECT_POSITION.name(),
        position);
  }

  //-------------------------------------------------

  //---------------美型----------------------------------
  public static int beautyFaceTrimPosition() {
    return SharedPreferencesUtil
        .get(HtUICacheKey.BEAUTY_FACE_TRIM_SELECT_POSITION.name(),
            HtUICacheKey.BEAUTY_FACE_TRIM_SELECT_POSITION.getDefaultInt());
  }

  public static void beautyFaceTrimPosition(int position) {
    SharedPreferencesUtil.put(HtUICacheKey.BEAUTY_FACE_TRIM_SELECT_POSITION.name(),
        position);
  }

  //-------------------------------------------------

  //---------------风格滤镜----------------------------------

  public static int beautyFilterPosition() {

    return SharedPreferencesUtil.get(HtUICacheKey.FILTER_SELECT_POSITION.name(),
        HtUICacheKey.FILTER_SELECT_POSITION.getDefaultInt());
  }

  public static void beautyFilterPosition(int position) {
    SharedPreferencesUtil.put(HtUICacheKey.FILTER_SELECT_POSITION.name(), position);
  }

  public static String beautyFilterName() {

    return SharedPreferencesUtil.get(HtUICacheKey.FILTER_SELECT_NAME.name(),
        HtUICacheKey.FILTER_SELECT_NAME.getDefaultStr());
  }

  public static void beautyFilterName(String name) {
    SharedPreferencesUtil.put(HtUICacheKey.FILTER_SELECT_NAME.name(), name);
  }

  //-------------------------------------------------

  //--------------特效滤镜----------------------------------

  public static int beautyEffectFilterPosition() {

    return SharedPreferencesUtil.get(HtUICacheKey.EFFECT_FILTER_SELECT_POSITION.name(),
        HtUICacheKey.EFFECT_FILTER_SELECT_POSITION.getDefaultInt());
  }

  public static void beautyEffectFilterPosition(int position) {
    SharedPreferencesUtil.put(HtUICacheKey.EFFECT_FILTER_SELECT_POSITION.name(), position);
  }

  public static String beautyEffectFilterName() {

    return SharedPreferencesUtil.get(HtUICacheKey.EFFECT_FILTER_SELECT_NAME.name(),
        HtUICacheKey.EFFECT_FILTER_SELECT_NAME.getDefaultStr());
  }

  public static void beautyEffectFilterName(String name) {
    SharedPreferencesUtil.put(HtUICacheKey.EFFECT_FILTER_SELECT_NAME.name(), name);
  }

  //-------------------------------------------------

  //---------------趣味滤镜----------------------------------

  public static int beautyFunnyFilterPosition() {

    return SharedPreferencesUtil.get(HtUICacheKey.FUNNY_FILTER_SELECT_POSITION.name(),
        HtUICacheKey.FUNNY_FILTER_SELECT_POSITION.getDefaultInt());
  }

  public static void beautyFunnyFilterPosition(int position) {
    SharedPreferencesUtil.put(HtUICacheKey.FUNNY_FILTER_SELECT_POSITION.name(), position);
  }

  public static String beautyFunnyFilterName() {

    return SharedPreferencesUtil.get(HtUICacheKey.FUNNY_FILTER_SELECT_NAME.name(),
        HtUICacheKey.FUNNY_FILTER_SELECT_NAME.getDefaultStr());
  }

  public static void beautyFunnyFilterName(String name) {
    SharedPreferencesUtil.put(HtUICacheKey.FUNNY_FILTER_SELECT_NAME.name(), name);
  }
  //-------------------------------------------------

  //---------------美发----------------------------------

  public static int beautyHairPosition() {

    return SharedPreferencesUtil.get(HtUICacheKey.HAIR_SELECT_POSITION.name(),
        HtUICacheKey.HAIR_SELECT_POSITION.getDefaultInt());
  }

  public static void beautyHairPosition(int position) {
    SharedPreferencesUtil.put(HtUICacheKey.HAIR_SELECT_POSITION.name(), position);
  }

  public static String beautyHairName() {

    return SharedPreferencesUtil.get(HtUICacheKey.HAIR_SELECT_NAME.name(),
        HtUICacheKey.HAIR_SELECT_NAME.getDefaultStr());
  }

  public static void beautyHairName(String name) {
    SharedPreferencesUtil.put(HtUICacheKey.HAIR_SELECT_NAME.name(), name);
  }

  public static int beautyHairValue(String hairName) {
    return SharedPreferencesUtil.get("hair_" + hairName,100);
  }

  public static void beautyHairValue(String hairName, int value) {
    SharedPreferencesUtil.put("hair_" + hairName, value);
  }

  //-------------------------------------------------

  //---------------相似度----------------------------------

  public static int beautyEditPosition() {

    return SharedPreferencesUtil.get(HtUICacheKey.GREENSCREEN_EDIT_POSITION.name(),
        HtUICacheKey.GREENSCREEN_EDIT_POSITION.getDefaultInt());
  }

  public static void beautyEditPosition(int position) {
    SharedPreferencesUtil.put(HtUICacheKey.GREENSCREEN_EDIT_POSITION.name(), position);
  }

  public static int beautySimilarityValue() {
    return SharedPreferencesUtil.get("similarity",100);
  }

  public static void beautySimilarityValue( int value) {
    SharedPreferencesUtil.put("similarity", value);
  }
  //-------------------------------------------------

  //---------------平滑度----------------------------------

  public static int beautySmoothnessValue() {
    return SharedPreferencesUtil.get("smoothness",100);
  }

  public static void beautySmoothnessValue( int value) {
    SharedPreferencesUtil.put("smoothness", value);
  }
  //-------------------------------------------------

  //---------------透明度----------------------------------

  public static int beautyAlphaValue() {
    return SharedPreferencesUtil.get("alpha",100);
  }

  public static void beautyAlphaValue( int value) {
    SharedPreferencesUtil.put("alpha", value);
  }

  //-------------------------------------------------

  //---------------风格----------------------------------

  public static int beautyStylePosition() {
    return SharedPreferencesUtil.get(HtUICacheKey.BEAUTY_STYLE_SELECT_POSITION.name(),
        HtUICacheKey.BEAUTY_STYLE_SELECT_POSITION.getDefaultInt());
  }

  public static void beautyStylePosition(int position) {
    SharedPreferencesUtil.put(HtUICacheKey.BEAUTY_STYLE_SELECT_POSITION.name(), position);
  }

  public static int previewInitialWidth() {

    return SharedPreferencesUtil.get("previewInitialWidth",
        0);
  }

  public static void previewInitialWidth(int width) {
    SharedPreferencesUtil.put("previewInitialWidth", width);
  }
  public static int previewInitialHeight() {

    return SharedPreferencesUtil.get("previewInitialHeight",
        0);
  }

  public static void previewInitialHeight(int height) {
    SharedPreferencesUtil.put("previewInitialHeight", height);
  }

  //-------------------------------------------------

  //---------------轻彩妆----------------------------------

  /**
   * 当前选中的轻彩妆的位置
   * @return
   */
  public static int beautyMakeupPosition() {
    return SharedPreferencesUtil.get(HtUICacheKey.MAKEUP_SELECT_POSITION.name(),
        HtUICacheKey.BEAUTY_STYLE_SELECT_POSITION.getDefaultInt());
  }

  public static void beautyMakeupPosition(int position) {
    SharedPreferencesUtil.put(HtUICacheKey.MAKEUP_SELECT_POSITION.name(), position);
  }

  public static int beautyMakeupValue(String key) {
    return SharedPreferencesUtil.get("make_up_" + key, 0);
  }

  public static void beautyMakeupValue(String key, int value) {
    SharedPreferencesUtil.put("make_up_" + key, value);
  }

  /**
   * 获取美肤默认参数
   * @param key
   * @return int
   */
  public static int beautySkinValue(HtBeautyKey key) {

    int defaultValue = 0;

    switch (key) {
      case whiteness:
        defaultValue = 50;
        break;
      // case vague_blurriness:
      //   defaultValue = 0;
      //   break;
      case blurriness:
        defaultValue = 60;
        break;
      case rosiness:
        defaultValue = 10;
        break;
      case clearness:
        defaultValue = 60;
        break;
      case brightness:
        defaultValue = 50;
        break;
      case undereye_circles:
        defaultValue = 70;
        break;
      case nasolabial:
      case NONE:
        break;
    }

    return SharedPreferencesUtil.get("beauty_skin_" + key.name(),
        defaultValue);

  }

  public static void beautySkinValue(HtBeautyKey key, int progress) {
    SharedPreferencesUtil
        .put("beauty_skin_" + key.name(),
            progress);
  }
  //-------------------------------------------------

  //---------------美型子功能参数----------------------------------
  public static int beautyFaceTrimValue(HtFaceTrim key) {

    int defaultValue = 0;

    switch (key) {
      case EYE_ENLARGING:
        defaultValue = 60;
        break;
      case EYE_CORNER_ENLARGING:
        break;
      case CHEEK_THINNING:
        defaultValue = 30;
        break;
      case NOSE_APEX_LESSENING:
      case NOSE_ROOT_ENLARGING:
      case MOUTH_SMILING:
      case FACE_LESSENING:
        break;
      case TEMPLE_ENLARGING:
        defaultValue = 50;
        break;
      case CHEEK_BONE_THINNING:
      case CHEEK_NARROWING:
      case JAW_BONE_THINNING:

      case CHEEK_SHORTENING:
        break;
      case EYE_ROUNDING:
        break;
      case CHEEK_V_SHAPING:
        defaultValue = 60;
        break;
      case CHIN_TRIMMING:
        defaultValue = 50;
        break;
      case PHILTRUM_TRIMMING:
        defaultValue = 50;
        break;
      case FOREHEAD_TRIM:
        defaultValue = 50;
        break;
      case EYE_SAPCING:
        defaultValue = 50;
        break;
      case EYE_CORNER_TRIMMING:
        defaultValue = 50;
        break;
      case NOSE_ENLARGING:
        defaultValue = 50;
        break;
      case NOSE_THINNING:
        defaultValue = 40;
        break;
      case MOUTH_TRIMMING:
        defaultValue = 50;
        break;
      case head_lessening:
        defaultValue = 0;
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + key);
    }

    return SharedPreferencesUtil.get("beauty_face_trim_" + key.name(),
        defaultValue);

  }

  public static void beautyFaceTrimValue(HtFaceTrim key, int progress) {
    SharedPreferencesUtil
        .put("beauty_face_trim_" + key.name(),
            progress);
  }
  //-------------------------------------------------

  //----------

  //--------

  //----------------------是否可用重置---------------------------

  public static void beautyFaceTrimResetEnable(boolean enable) {
    SharedPreferencesUtil.put("face_trim_enable", enable);
  }

  public static void beautySkinResetEnable(boolean enable) {
    SharedPreferencesUtil.put("skin_enable", enable);
  }
  public static void greenscreenResetEnable(boolean enable) {
    SharedPreferencesUtil.put("greenscreen_enable", enable);
  }

  public static boolean beautyFaceTrimResetEnable() {
    return SharedPreferencesUtil.get("face_trim_enable", false);
  }

  public static boolean beautySkinResetEnable() {
    return SharedPreferencesUtil.get("skin_enable", false);
  }

  public static boolean greenscreenResetEnable() {
    return SharedPreferencesUtil.get("greenscreen_enable", false);
  }

  //--------------------------------------------------

  //------------------重置相关---------------------------

  public static void resetFaceTrimValue(Context context) {
    HtFaceTrim[] items = HtFaceTrim.values();
    for (HtFaceTrim item : items) {
      SharedPreferencesUtil.remove(context, "beauty_face_trim_" + item.name());
    }
    beautyFaceTrimPosition(-1);
    initCache(false);
    HtState.currentFaceTrim = HtFaceTrim.EYE_ENLARGING;
  }

  public static void resetSkinValue(Context context) {
    HtBeautyKey[] items = HtBeautyKey.values();
    for (HtBeautyKey item : items) {
      SharedPreferencesUtil.remove(context, "beauty_skin_" + item.name());
    }
    beautySkinPosition(-1);
    initCache(false);
    HtState.setCurrentBeautySkin(HtBeautyKey.NONE);
  }

  public static void resetGreencreenValue(Context context) {

    HtSelectedPosition.VALUE_SIMILARITY = 50;
    HtSelectedPosition.VALUE_SMOOTHNESS = 0;
    HtSelectedPosition.VALUE_ALPHA = 0;
    // beautySimilarityValue(0);
    // beautySmoothnessValue(0);
    // beautyAlphaValue(0);
    initCache(false);
  }

  //----------------------------------------------------------------

  //---------------------------------应用风格参数------------------------------

  public static void applyHtCache() {
    //美白
    HTEffect.shareInstance().setBeauty(HtBeautyParam.HTBeautySkinWhitening,HtUICacheUtils.beautySkinValue(HtBeautyKey.whiteness));
    //磨皮
    // HTEffect.shareInstance().setBeauty(HtBeautyParam.HTBeautyBlurrySmoothing,HtUICacheUtils.beautySkinValue(HtBeautyKey.vague_blurriness));

    HTEffect.shareInstance().setBeauty(HtBeautyParam.HTBeautyClearSmoothing,HtUICacheUtils.beautySkinValue(HtBeautyKey.blurriness));
    //红润
    HTEffect.shareInstance().setBeauty(HtBeautyParam.HTBeautySkinRosiness,HtUICacheUtils.beautySkinValue(HtBeautyKey.rosiness));
    //清晰
    HTEffect.shareInstance().setBeauty(HtBeautyParam.HTBeautyImageSharpness,HtUICacheUtils.beautySkinValue(HtBeautyKey.clearness));
    //亮度
    HTEffect.shareInstance().setBeauty(HtBeautyParam.HTBeautyImageBrightness,HtUICacheUtils.beautySkinValue(HtBeautyKey.brightness));
    //大眼
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeEyeEnlarging,HtUICacheUtils.beautyFaceTrimValue(HtFaceTrim.EYE_ENLARGING));
    //圆形眼
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeEyeRounding,HtUICacheUtils.beautyFaceTrimValue(HtFaceTrim.EYE_ROUNDING));
    //瘦脸
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeCheekThinning,HtUICacheUtils.beautyFaceTrimValue(HtFaceTrim.CHEEK_BONE_THINNING));
    //V脸
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeCheekVShaping,HtUICacheUtils.beautyFaceTrimValue(HtFaceTrim.CHEEK_V_SHAPING));
    //窄脸
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeCheekNarrowing,HtUICacheUtils.beautyFaceTrimValue(HtFaceTrim.CHEEK_NARROWING));
    //瘦颧骨
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeCheekboneThinning,HtUICacheUtils.beautyFaceTrimValue(HtFaceTrim.CHEEK_BONE_THINNING));

    //瘦下颌骨
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeJawboneThinning,HtUICacheUtils.beautyFaceTrimValue(HtFaceTrim.JAW_BONE_THINNING));

    //丰太阳穴
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeTempleEnlarging,HtUICacheUtils.beautyFaceTrimValue(HtFaceTrim.TEMPLE_ENLARGING));
    //小头
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeHeadLessening,HtUICacheUtils.beautyFaceTrimValue(HtFaceTrim.head_lessening));

    //小脸
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeFaceLessening,HtUICacheUtils.beautyFaceTrimValue(HtFaceTrim.FACE_LESSENING));
    //短脸
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeCheekShortening,HtUICacheUtils.beautyFaceTrimValue(HtFaceTrim.CHEEK_SHORTENING));

    //下巴
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeChinTrimming,HtUICacheUtils.beautyFaceTrimValue(HtFaceTrim.CHIN_TRIMMING));

    //缩人中
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapePhiltrumTrimming,HtUICacheUtils.beautyFaceTrimValue(HtFaceTrim.PHILTRUM_TRIMMING));

    //发际线
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeForeheadTrimming,HtUICacheUtils.beautyFaceTrimValue(HtFaceTrim.FOREHEAD_TRIM));

    //眼间距
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeEyeSpaceTrimming,HtUICacheUtils.beautyFaceTrimValue(HtFaceTrim.EYE_SAPCING));

    //倾斜
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeEyeCornerTrimming,HtUICacheUtils.beautyFaceTrimValue(HtFaceTrim.EYE_CORNER_TRIMMING));

    //开眼角
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeEyeCornerEnlarging,HtUICacheUtils.beautyFaceTrimValue(HtFaceTrim.EYE_CORNER_ENLARGING));
    //长鼻
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeNoseEnlarging,HtUICacheUtils.beautyFaceTrimValue(HtFaceTrim.NOSE_ENLARGING));

    //瘦鼻
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeNoseThinning,HtUICacheUtils.beautyFaceTrimValue(HtFaceTrim.NOSE_THINNING));

    //鼻头
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeNoseApexLessening,HtUICacheUtils.beautyFaceTrimValue(HtFaceTrim.NOSE_APEX_LESSENING));

    //山根
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeNoseRootEnlarging,HtUICacheUtils.beautyFaceTrimValue(HtFaceTrim.NOSE_ROOT_ENLARGING));

    //嘴型
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeMouthTrimming,HtUICacheUtils.beautyFaceTrimValue(HtFaceTrim.MOUTH_TRIMMING));

    //微笑嘴角
    HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeMouthSmiling,HtUICacheUtils.beautyFaceTrimValue(HtFaceTrim.MOUTH_SMILING));

  }

}
