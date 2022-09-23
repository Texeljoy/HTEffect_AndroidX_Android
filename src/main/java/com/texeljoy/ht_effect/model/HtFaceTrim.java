package com.texeljoy.ht_effect.model;

import android.content.Context;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import com.texeljoy.ht_effect.R;

/**
 * 美型枚举类
 */
public enum HtFaceTrim {

  //大眼
  EYE_ENLARGING(R.string.eye_enlarging,
      R.drawable.ic_eye_enlarging_white,
      R.drawable.ic_eye_enlarging_black),
  //圆眼
  EYE_ROUNDING(R.string.circle_eye,
      R.drawable.ic_circle_eyes_white,
      R.drawable.ic_circle_eyes_black),

  //瘦脸
  CHEEK_THINNING(R.string.cheek_thinning,
      R.drawable.ic_cheek_thinning_white,
      R.drawable.ic_cheek_thinning_black),

  //V脸
  CHEEK_V_SHAPING(R.string.v_face,
      R.drawable.ic_v_face_white,
      R.drawable.ic_v_face_black),
  //窄脸
  CHEEK_NARROWING(R.string.cheek_narrowing,
      R.drawable.ic_cheek_narrowing_white,
      R.drawable.ic_cheek_narrowing_black),

  //瘦颧骨
  CHEEK_BONE_THINNING(R.string.cheek_bone_thinning,
      R.drawable.ic_cheek_bone_thinning_white,
      R.drawable.ic_cheek_bone_thinning_black),
  //瘦下颌骨
  JAW_BONE_THINNING(R.string.jaw_bone_thinning,
      R.drawable.ic_jaw_bone_thinning_white,
      R.drawable.ic_jaw_bone_thinning_black
  ),

  //丰太阳穴 *
  TEMPLE_ENLARGING(
      R.string.temple_enlarging,
      R.drawable.ic_temple_enlarging_white,
      R.drawable.ic_temple_enlarging_black
  ),
  //小头
  head_lessening(
      R.string.head_lessening,
      R.drawable.ic_head_lessening_white,
      R.drawable.ic_head_lessenin_black
      ),

  //小脸
  FACE_LESSENING(R.string.face_lessening,
      R.drawable.ic_face_lessoning_white,
      R.drawable.ic_face_lessening_black),

  //短脸
  CHEEK_SHORTENING(
      R.string.short_face,
      R.drawable.ic_cheek_shortening_white,
      R.drawable.ic_cheek_shortening_black
  ),
  //下巴
  CHIN_TRIMMING(R.string.chin_trimming,
      R.drawable.ic_chin_trimming_white,
      R.drawable.ic_chin_trimming_black),

  //缩人中
  PHILTRUM_TRIMMING(R.string.philtrum_trimming,
      R.drawable.ic_philtrum_trimming_white,
      R.drawable.ic_philtrum_trimming_black),

  //发际线
  FOREHEAD_TRIM(R.string.forehead_trimming,
      R.drawable.ic_forehead_trim_white,
      R.drawable.ic_forehead_trimming_black),

  //眼间距
  EYE_SAPCING(
      R.string.eye_sapcing,
      R.drawable.ic_eye_spacing_white,
      R.drawable.ic_eye_sapcing_black
  ),

  //眼角角度
  EYE_CORNER_TRIMMING(R.string.eye_corner_trimming,
      R.drawable.ic_eye_corner_trimming_white,
      R.drawable.ic_eye_corner_trimming_black),

  // 开眼角
  EYE_CORNER_ENLARGING(R.string.eye_corner_enlarging,
      R.drawable.ic_eye_corner_enlarging_white,
      R.drawable.ic_eye_corner_enlarging_black),

  //长鼻
  NOSE_ENLARGING(R.string.nose_enlarging,
      R.drawable.icon_nose_enlarging_white,
      R.drawable.ic_nose_enlarging_black),

  //瘦鼻
  NOSE_THINNING(R.string.nose_thinning,
      R.drawable.ic_nose_thin_white,
      R.drawable.ic_nose_thinning_black),

  //鼻头 *
  NOSE_APEX_LESSENING(
      R.string.nose_apex_lessening,
      R.drawable.ic_nose_apex_lessening_white,
      R.drawable.ic_nose_apex_lessening_black
  ),
  //山根 *
  NOSE_ROOT_ENLARGING(
      R.string.nose_root_enlarging,
      R.drawable.ic_nose_root_enlarging_white,
      R.drawable.ic_nose_root_enlarging_black
  ),

  // 嘴型
  MOUTH_TRIMMING(R.string.mouth_trimming,
      R.drawable.ic_mouth_trim_white,
      R.drawable.ic_mouth_trimming_black),

  //微笑嘴唇
  MOUTH_SMILING(R.string.mouth_smiling,
      R.drawable.icon_mouth_smiling_white,
      R.drawable.ic_mouth_smiling_black),
  ;

  //名称
  private final int name;
  //黑色图标
  private final int drawableRes_black;
  //白色图标
  private final int drawableRes_white;

  public String getName(Context context) {
    return context.getString(name);
  }

  public int getDrawableRes_black() {
    return drawableRes_black;
  }

  public int getDrawableRes_white() {
    return drawableRes_white;
  }

  HtFaceTrim(@StringRes int name, @DrawableRes int drawableResWhite,
             @DrawableRes int drawableResBlack) {
    this.name = name;
    this.drawableRes_white = drawableResWhite;
    this.drawableRes_black = drawableResBlack;
  }

}
