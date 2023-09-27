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
  THIN(R.string.thin,
      R.drawable.ic_thin_white,
      R.drawable.ic_thin_black)

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
