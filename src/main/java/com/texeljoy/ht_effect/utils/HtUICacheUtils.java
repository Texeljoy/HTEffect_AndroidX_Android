package com.texeljoy.ht_effect.utils;

import android.content.Context;
import android.util.Log;
import com.texeljoy.ht_effect.model.HtBeautyKey;
import com.texeljoy.ht_effect.model.HtBeautyParam;
import com.texeljoy.ht_effect.model.HtBlushConfig;
import com.texeljoy.ht_effect.model.HtBody;
import com.texeljoy.ht_effect.model.HtEyebrowConfig;
import com.texeljoy.ht_effect.model.HtEyelashConfig;
import com.texeljoy.ht_effect.model.HtEyelineConfig;
import com.texeljoy.ht_effect.model.HtEyeshadowConfig;
import com.texeljoy.ht_effect.model.HtFaceTrim;
import com.texeljoy.ht_effect.model.HtLipstickConfig;
import com.texeljoy.ht_effect.model.HtMakeUpEnum;
import com.texeljoy.ht_effect.model.HtMakeup;
import com.texeljoy.ht_effect.model.HtPupilsConfig;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.model.HtUICacheKey;
import com.texeljoy.hteffect.HTEffect;
import com.texeljoy.hteffect.model.HTBodyBeautyEnum;
import com.texeljoy.hteffect.model.HTFilterEnum;
import java.util.ArrayList;
import java.util.List;

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
    for(int i = 0; i < 7 ; i++){
      HTEffect.shareInstance().setMakeup(i, beautyLipstickName(i), beautyLipstickValue(i,beautyLipstickName(i)));
    }

    HTEffect.shareInstance().setChromaKeyingParams(0, 0);
    HTEffect.shareInstance().setChromaKeyingParams(1, 0);
    HTEffect.shareInstance().setChromaKeyingParams(2, 0);
    HTEffect.shareInstance().setChromaKeyingParams(3, 0);

    HTEffect.shareInstance().setBodyBeauty(HTBodyBeautyEnum.HTBodyBeautyLegSlimming.getValue(), beautyBodyValue(HtBody.LONG_LEG));
    HTEffect.shareInstance().setBodyBeauty(HTBodyBeautyEnum.HTBodyBeautyBodyThinning.getValue(), beautyBodyValue(HtBody.THIN));
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

  //---------------美妆----------------------------------
  public static int beautyMakeUpPosition() {
    return SharedPreferencesUtil
        .get(HtUICacheKey.BEAUTY_MAKE_UP_SELECT_POSITION.name(),
            HtUICacheKey.BEAUTY_MAKE_UP_SELECT_POSITION.getDefaultInt());
  }

  public static void beautyMakeUpPosition(int position) {
    SharedPreferencesUtil.put(HtUICacheKey.BEAUTY_MAKE_UP_SELECT_POSITION.name(),
        position);
  }

  //-------------------------------------------------

  //---------------妆容推荐----------------------------------
  public static int beautyMakeUpStylePosition() {
    return SharedPreferencesUtil
        .get(HtUICacheKey.BEAUTY_MAKE_UP_STYLE_SELECT_POSITION.name(),
            HtUICacheKey.BEAUTY_MAKE_UP_STYLE_SELECT_POSITION.getDefaultInt());
  }

  public static void beautyMakeUpStylePosition(int position) {
    SharedPreferencesUtil.put(HtUICacheKey.BEAUTY_MAKE_UP_STYLE_SELECT_POSITION.name(),
        position);
  }

  //-------------------------------------------------

  //---------------美体----------------------------------
  public static int beautyBodyPosition() {
    return SharedPreferencesUtil
        .get(HtUICacheKey.BEAUTY_BODY_SELECT_POSITION.name(),
            HtUICacheKey.BEAUTY_BODY_SELECT_POSITION.getDefaultInt());
  }

  public static void beautyBodyPosition(int position) {
    SharedPreferencesUtil.put(HtUICacheKey.BEAUTY_BODY_SELECT_POSITION.name(),
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

  //---------------妆容推荐----------------------------------

  /**
   * 当前选中的口红的位置
   * @return
   */
  public static int beautyLipstickPosition(int type) {
    switch (type){
      case 0:
        return SharedPreferencesUtil.get(HtUICacheKey.LIPSTICK_SELECT_POSITION.name(),
            HtUICacheKey.LIPSTICK_SELECT_POSITION.getDefaultInt());
      case 1:
        return SharedPreferencesUtil.get(HtUICacheKey.EYEBROW_SELECT_POSITION.name(),
            HtUICacheKey.EYEBROW_SELECT_POSITION.getDefaultInt());
      case 2:
        return SharedPreferencesUtil.get(HtUICacheKey.BLUSH_SELECT_POSITION.name(),
            HtUICacheKey.BLUSH_SELECT_POSITION.getDefaultInt());
      case 3:
        return SharedPreferencesUtil.get(HtUICacheKey.EYESHADOW_SELECT_POSITION.name(),
            HtUICacheKey.EYESHADOW_SELECT_POSITION.getDefaultInt());
      case 4:
        return SharedPreferencesUtil.get(HtUICacheKey.EYELINE_SELECT_POSITION.name(),
            HtUICacheKey.EYELINE_SELECT_POSITION.getDefaultInt());
      case 5:
        return SharedPreferencesUtil.get(HtUICacheKey.EYELASH_SELECT_POSITION.name(),
            HtUICacheKey.EYELASH_SELECT_POSITION.getDefaultInt());
      case 6:
        return SharedPreferencesUtil.get(HtUICacheKey.PUPILS_SELECT_POSITION.name(),
            HtUICacheKey.PUPILS_SELECT_POSITION.getDefaultInt());
      default:
        return 0;
    }

  }

  public static void beautyLipstickPosition(int type, int position) {
    switch (type){
      case 0:
        SharedPreferencesUtil.put(HtUICacheKey.LIPSTICK_SELECT_POSITION.name(), position);
        break;
      case 1:
        SharedPreferencesUtil.put(HtUICacheKey.EYEBROW_SELECT_POSITION.name(), position);
        break;
      case 2:
        SharedPreferencesUtil.put(HtUICacheKey.BLUSH_SELECT_POSITION.name(), position);
        break;
      case 3:
        SharedPreferencesUtil.put(HtUICacheKey.EYESHADOW_SELECT_POSITION.name(), position);
        break;
      case 4:
        SharedPreferencesUtil.put(HtUICacheKey.EYELINE_SELECT_POSITION.name(), position);
        break;
      case 5:
        SharedPreferencesUtil.put(HtUICacheKey.EYELASH_SELECT_POSITION.name(), position);
        break;
      case 6:
        SharedPreferencesUtil.put(HtUICacheKey.PUPILS_SELECT_POSITION.name(), position);
        break;
    }

  }

  /**
   * 当前选中的口红的名称
   * @return
   */
  public static String beautyLipstickName(int type) {
    switch (type){
      case 0:
        return SharedPreferencesUtil.get(HtUICacheKey.LIPSTICK_SELECT_NAME.name(),
            HtUICacheKey.LIPSTICK_SELECT_NAME.getDefaultStr());
      case 1:
        return SharedPreferencesUtil.get(HtUICacheKey.EYEBROW_SELECT_NAME.name(),
            HtUICacheKey.EYEBROW_SELECT_NAME.getDefaultStr());
      case 2:
        return SharedPreferencesUtil.get(HtUICacheKey.BLUSH_SELECT_NAME.name(),
            HtUICacheKey.BLUSH_SELECT_NAME.getDefaultStr());
      case 3:
        return SharedPreferencesUtil.get(HtUICacheKey.EYESHADOW_SELECT_NAME.name(),
            HtUICacheKey.EYESHADOW_SELECT_NAME.getDefaultStr());
      case 4:
        return SharedPreferencesUtil.get(HtUICacheKey.EYELINE_SELECT_NAME.name(),
            HtUICacheKey.EYELINE_SELECT_NAME.getDefaultStr());
      case 5:
        return SharedPreferencesUtil.get(HtUICacheKey.EYELASH_SELECT_NAME.name(),
            HtUICacheKey.EYELASH_SELECT_NAME.getDefaultStr());
      case 6:
        return SharedPreferencesUtil.get(HtUICacheKey.PUPILS_SELECT_NAME.name(),
            HtUICacheKey.PUPILS_SELECT_NAME.getDefaultStr());
      default:
        return "";
    }

  }

  public static void beautyLipstickName(int type, String name) {
    switch (type){
      case 0:
        SharedPreferencesUtil.put(HtUICacheKey.LIPSTICK_SELECT_NAME.name(), name);
        break;
      case 1:
        SharedPreferencesUtil.put(HtUICacheKey.EYEBROW_SELECT_NAME.name(), name);
        break;
      case 2:
        SharedPreferencesUtil.put(HtUICacheKey.BLUSH_SELECT_NAME.name(), name);
        break;
      case 3:
        SharedPreferencesUtil.put(HtUICacheKey.EYESHADOW_SELECT_NAME.name(), name);
        break;
      case 4:
        SharedPreferencesUtil.put(HtUICacheKey.EYELINE_SELECT_NAME.name(), name);
        break;
      case 5:
        SharedPreferencesUtil.put(HtUICacheKey.EYELASH_SELECT_NAME.name(), name);
        break;
      case 6:
        SharedPreferencesUtil.put(HtUICacheKey.PUPILS_SELECT_NAME.name(), name);
        break;
    }

  }

  public static int beautyLipstickValue(int type, String key) {
    switch (type){
      case 0:
        return SharedPreferencesUtil.get("makeup_lipstick_" + key, 0);
      case 1:
        return SharedPreferencesUtil.get("makeup_eyebrow_" + key, 0);
      case 2:
        return SharedPreferencesUtil.get("makeup_blush_" + key, 0);
      case 3:
        return SharedPreferencesUtil.get("makeup_eyeshadow_" + key, 0);
      case 4:
        return SharedPreferencesUtil.get("makeup_eyeline_" + key, 0);
      case 5:
        return SharedPreferencesUtil.get("makeup_eyelash_" + key, 0);
      case 6:
        return SharedPreferencesUtil.get("makeup_pupils_" + key, 0);
      default:
        return 0;
    }

  }

  public static void beautyLipstickValue(int type, String key, int value) {
    switch (type){
      case 0:
        SharedPreferencesUtil.put("makeup_lipstick_" + key, value);
        break;
      case 1:
        SharedPreferencesUtil.put("makeup_eyebrow_" + key, value);
        break;
      case 2:
        SharedPreferencesUtil.put("makeup_blush_" + key, value);
        break;
      case 3:
        SharedPreferencesUtil.put("makeup_eyeshadow_" + key, value);
        break;
      case 4:
        SharedPreferencesUtil.put("makeup_eyeline_" + key, value);
        break;
      case 5:
        SharedPreferencesUtil.put("makeup_eyelash_" + key, value);
        break;
      case 6:
        SharedPreferencesUtil.put("makeup_pupils_" + key, value);
        break;
    }

  }

  //-------------------------------------------------

  //---------------眉毛----------------------------------

  public static int beautyEyebrowPosition() {
    return SharedPreferencesUtil.get(HtUICacheKey.EYEBROW_SELECT_POSITION.name(),
        HtUICacheKey.EYEBROW_SELECT_POSITION.getDefaultInt());
  }

  public static void beautyEyebrowPosition(int position) {
    SharedPreferencesUtil.put(HtUICacheKey.EYEBROW_SELECT_POSITION.name(), position);
  }

  public static int beautyEyebrowValue(String key) {
    return SharedPreferencesUtil.get("makeup_eyebrow_" + key, 0);
  }

  public static void beautyEyebrowValue(String key, int value) {
    SharedPreferencesUtil.put("makeup_eyebrow_" + key, value);
  }



  public static int beautyBodyValue(HtBody key) {
    // int defaultValue = 0;
    return SharedPreferencesUtil.get("beauty_body_" + key.name(), 0);
  }

  public static void beautyBodyValue(HtBody key, int value) {
    SharedPreferencesUtil.put("beauty_body_" + key.name(), value);
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
        defaultValue = 80;
        break;
      // case vague_blurriness:
      //   defaultValue = 0;
      //   break;
      case blurriness:
        defaultValue = 100;
        break;
      case rosiness:
        defaultValue = 10;
        break;
      case clearness:
        defaultValue = 5;
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
        defaultValue = 50;
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

  //---------------美妆子功能参数----------------------------------
  public static int beautyMakeUpValue(HtMakeUpEnum key) {

    int defaultValue = 0;

    switch (key) {
      case LIPSTICK:
      case EYEBROW:
      case BLUSH:
      case EYESHADOW:
      case EYELINE:
      case EYELASH:
      case BEAUTYPUPILS:
        defaultValue = 0;
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + key);
    }

    return SharedPreferencesUtil.get("beauty_make_up_" + key.name(),
        defaultValue);

  }

  public static void beautyMakeUpValue(HtMakeUpEnum key, int progress) {
    SharedPreferencesUtil
        .put("beauty_make_up_" + key.name(),
            progress);
  }
  //-------------------------------------------------

  //----------

  //--------

  //----------------------是否可用重置---------------------------

  public static void beautySkinResetEnable(boolean enable) {
    SharedPreferencesUtil.put("skin_enable", enable);
  }

  public static boolean beautySkinResetEnable() {
    return SharedPreferencesUtil.get("skin_enable", false);
  }


  public static void beautyFaceTrimResetEnable(boolean enable) {
    SharedPreferencesUtil.put("face_trim_enable", enable);
  }

  public static boolean beautyFaceTrimResetEnable() {
    return SharedPreferencesUtil.get("face_trim_enable", false);
  }

  public static void beautyBodyResetEnable(boolean enable) {
    SharedPreferencesUtil.put("body_enable", enable);
  }

    public static boolean beautyBodyResetEnable() {
    return SharedPreferencesUtil.get("body_enable", false);
  }

  public static void beautyMakeUpResetEnable(boolean enable) {
    SharedPreferencesUtil.put("make_up_enable", enable);
  }

  public static boolean beautyMakeUpResetEnable() {
    return SharedPreferencesUtil.get("make_up_enable", false);
  }


  public static void greenscreenResetEnable(boolean enable) {
    SharedPreferencesUtil.put("greenscreen_enable", enable);
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

  public static void resetBodyValue(Context context) {
    HtBody[] items = HtBody.values();
    for (HtBody item : items) {
      SharedPreferencesUtil.remove(context, "beauty_body_" + item.name());
    }
    beautyBodyPosition(-1);
    initCache(false);
    HtState.currentBody = HtBody.LONG_LEG;
  }

  public static void resetMakeUpValue(Context context, int type) {
    final List<HtMakeup> items = new ArrayList<>();
    items.clear();
    switch (type){
      case 0:
        HtLipstickConfig lipstickList = HtConfigTools.getInstance().getLipstickList();
        if (lipstickList == null) {
          HtConfigTools.getInstance().getLipsticksConfig(new HtConfigCallBack<List<HtMakeup>>() {
            @Override public void success(List<HtMakeup> list) {
              items.addAll(list);
            }

            @Override public void fail(Exception error) {
              error.printStackTrace();

            }
          });
        } else {
          items.addAll(lipstickList.getMakeups());

        }
        for (HtMakeup item : items) {
          SharedPreferencesUtil.remove(context, "makeup_lipstick_" + item.getName());
        }

        break;
      case 1:

        HtEyebrowConfig eyebrowList = HtConfigTools.getInstance().getEyebrowList();
        if (eyebrowList == null) {
          HtConfigTools.getInstance().getEyebrowsConfig(new HtConfigCallBack<List<HtMakeup>>() {
            @Override public void success(List<HtMakeup> list) {
              items.addAll(list);

            }

            @Override public void fail(Exception error) {
              error.printStackTrace();

            }
          });
        } else {
          items.addAll(eyebrowList.getMakeups());

        }
        for (HtMakeup item : items) {
          SharedPreferencesUtil.remove(context, "makeup_eyebrow_" + item.getName());
        }
        break;
      case 2:
        HtBlushConfig blushList = HtConfigTools.getInstance().getBlushList();
        if (blushList == null) {
          HtConfigTools.getInstance().getBlushsConfig(new HtConfigCallBack<List<HtMakeup>>() {
            @Override public void success(List<HtMakeup> list) {
              items.addAll(list);

            }

            @Override public void fail(Exception error) {
              error.printStackTrace();

            }
          });
        } else {
          items.addAll(blushList.getMakeups());

        }
        for (HtMakeup item : items) {
          SharedPreferencesUtil.remove(context, "makeup_blush_" + item.getName());
        }
        break;
      case 3:
        HtEyeshadowConfig shadowList = HtConfigTools.getInstance().getEyeshadowList();
        if (shadowList == null) {
          HtConfigTools.getInstance().getEyeshadowsConfig(new HtConfigCallBack<List<HtMakeup>>() {
            @Override public void success(List<HtMakeup> list) {
              items.addAll(list);

            }

            @Override public void fail(Exception error) {
              error.printStackTrace();

            }
          });
        } else {
          items.addAll(shadowList.getMakeups());

        }
        for (HtMakeup item : items) {
          SharedPreferencesUtil.remove(context, "makeup_eyeshadow_" + item.getName());
        }
        break;
      case 4:
        HtEyelineConfig eyelineList = HtConfigTools.getInstance().getEyelineList();
        if (eyelineList == null) {
          HtConfigTools.getInstance().getEyelinesConfig(new HtConfigCallBack<List<HtMakeup>>() {
            @Override public void success(List<HtMakeup> list) {
              items.addAll(list);

            }

            @Override public void fail(Exception error) {
              error.printStackTrace();

            }
          });
        } else {
          items.addAll(eyelineList.getMakeups());

        }
        for (HtMakeup item : items) {
          SharedPreferencesUtil.remove(context, "makeup_eyeline_" + item.getName());
        }
        break;
      case 5:
        HtEyelashConfig eyelashList = HtConfigTools.getInstance().getEyelashList();
        if (eyelashList == null) {
          HtConfigTools.getInstance().getEyelashsConfig(new HtConfigCallBack<List<HtMakeup>>() {
            @Override public void success(List<HtMakeup> list) {
              items.addAll(list);

            }

            @Override public void fail(Exception error) {
              error.printStackTrace();

            }
          });
        } else {
          items.addAll(eyelashList.getMakeups());

        }
        for (HtMakeup item : items) {
          SharedPreferencesUtil.remove(context, "makeup_eyelash_" + item.getName());
        }
        break;
      case 6:
        HtPupilsConfig pupilsList = HtConfigTools.getInstance().getPupilsList();
        if (pupilsList == null) {
          HtConfigTools.getInstance().getPupilsConfig(new HtConfigCallBack<List<HtMakeup>>() {
            @Override public void success(List<HtMakeup> list) {
              items.addAll(list);

            }

            @Override public void fail(Exception error) {
              error.printStackTrace();

            }
          });
        } else {
          items.addAll(pupilsList.getMakeups());

        }
        for (HtMakeup item : items) {
          SharedPreferencesUtil.remove(context, "makeup_pupils_" + item.getName());
        }
        break;


    }
    beautyLipstickPosition(type,-1);

    beautyMakeUpPosition(-1);

    initCache(false);
    HtState.currentMakeUp = HtMakeUpEnum.LIPSTICK;
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
