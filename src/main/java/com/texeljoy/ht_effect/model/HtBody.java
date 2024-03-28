package com.texeljoy.ht_effect.model;

import android.content.Context;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import com.texeljoy.ht_effect.R;

/**
 * 美型枚举类
 */
public enum HtBody {

  //长腿
  LONG_LEG(R.string.longleg,
      R.drawable.ic_long_leg_white,
      R.drawable.ic_long_leg_black),
  //瘦身
  BODY_THIN(R.string.bodythin,
      R.drawable.ic_thin_white,
      R.drawable.ic_thin_black),
  //细腰
  WAIST_SLIM(R.string.waistslim,
      R.drawable.ic_waistslim_white,
      R.drawable.ic_waistslim_black),
  //美肩
  SHOULDER_SLIM(R.string.shoulderslim,
      R.drawable.ic_shoulderslim_white,
      R.drawable.ic_shoulderslim_black),
  //修胯
  HIP_TRIM(R.string.hiptrim,
      R.drawable.ic_hiptrim_white,
      R.drawable.ic_hiptrim_black),
  //瘦大腿
  THIGH_THIN(R.string.thighthin,
      R.drawable.ic_thighthin_white,
      R.drawable.ic_thighthin_black),
  //天鹅颈
  NECK_SLIM(R.string.neckslim,
      R.drawable.ic_neckslim_white,
      R.drawable.ic_neckslim_black),
  //丰胸
  CHEST_ENLARGE(R.string.chestenlarge,
      R.drawable.ic_chestenlarge_white,
      R.drawable.ic_chestenlarge_black),
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

  HtBody(@StringRes int name, @DrawableRes int drawableResWhite,
         @DrawableRes int drawableResBlack) {
    this.name = name;
    this.drawableRes_white = drawableResWhite;
    this.drawableRes_black = drawableResBlack;
  }

}
