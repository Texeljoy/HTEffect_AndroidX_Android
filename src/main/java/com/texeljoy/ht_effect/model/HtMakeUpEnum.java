package com.texeljoy.ht_effect.model;

import android.content.Context;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import com.texeljoy.ht_effect.R;

/**
 * 美妆枚举类
 */
public enum HtMakeUpEnum {

  //口红
  LIPSTICK(R.string.lipstick,
      R.drawable.ic_makeup_lipstick_white,
      R.drawable.ic_makeup_lipstick_black
      ,0),
  //眉毛
  EYEBROW(R.string.eyebrow,
      R.drawable.ic_makeup_eyebrow_white,
      R.drawable.ic_makeup_eyebrow_black,1),

  //腮红
  BLUSH(R.string.blush,
      R.drawable.ic_makeup_blush_white,
      R.drawable.ic_makeup_blush_black,2),

  //眼影
  EYESHADOW(R.string.eyeshadow,
      R.drawable.ic_makeup_eyeshadow_white,
      R.drawable.ic_makeup_eyeshadow_black,3),
  //眼线
  EYELINE(R.string.eyeline,
      R.drawable.ic_makeup_eyeline_white,
      R.drawable.ic_makeup_eyeline_black,4),

  //睫毛
  EYELASH(R.string.eyelash,
      R.drawable.ic_makeup_eyelash_white,
      R.drawable.ic_makeup_eyelash_black,5),
  //美瞳
  BEAUTYPUPILS(R.string.beautypupils,
      R.drawable.ic_makeup_pupils_white,
      R.drawable.ic_makeup_pupils_black,6
  );

  //名称
  private final int name;
  //黑色图标
  private final int drawableRes_black;
  //白色图标
  private final int drawableRes_white;

  private final int type;

  public String getName(Context context) {
    return context.getString(name);
  }

  public int getDrawableRes_black() {
    return drawableRes_black;
  }

  public int getDrawableRes_white() {
    return drawableRes_white;
  }

  public int getType(){
    return type;
  }

  HtMakeUpEnum(@StringRes int name, @DrawableRes int drawableResWhite,
               @DrawableRes int drawableResBlack, int type) {
    this.name = name;
    this.drawableRes_white = drawableResWhite;
    this.drawableRes_black = drawableResBlack;
    this.type = type;
  }

}
