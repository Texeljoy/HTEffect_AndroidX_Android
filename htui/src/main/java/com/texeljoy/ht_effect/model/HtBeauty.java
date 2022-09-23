package com.texeljoy.ht_effect.model;

import android.content.Context;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import com.texeljoy.ht_effect.R;

/**
 * 美肤枚举类
 */
public enum HtBeauty {
  whiteness(R.string.whiteness, R.drawable.ic_whiteness_black, R.drawable.ic_whiteness_white, HtBeautyKey.whiteness),
  blurriness(R.string.blurriness, R.drawable.ic_blurriness_black, R.drawable.ic_blurriness_white, HtBeautyKey.vague_blurriness),
  rosiness(R.string.rosiness, R.drawable.ic_rosiness_black, R.drawable.ic_rosiness_white, HtBeautyKey.rosiness),
  clearness(R.string.clearness, R.drawable.ic_clearness_black, R.drawable.ic_clearness_white, HtBeautyKey.clearness),
  brightness(R.string.brightness, R.drawable.ic_brightness_black, R.drawable.ic_brightness_white, HtBeautyKey.brightness),
  undereye_circles(R.string.undereye_circles, R.drawable.ic_dark_circle_black, R.drawable.ic_dark_circle_white, HtBeautyKey.undereye_circles),
  nasolabial(R.string.nasolabial_fold, R.drawable.ic_nasolabial_black, R.drawable.ic_nasolabial_white, HtBeautyKey.nasolabial);

  //名称
  private final int name;
  //黑色图标
  private final int drawableRes_black;
  //白色图标
  private final int drawableRes_white;
  //对应的key
  private final HtBeautyKey htBeautyKey;


  public HtBeautyKey getHtBeautyKey() {
    return htBeautyKey;
  }

  public String getName(@NonNull Context context) {
    return context.getString(name);
  }

  public int getDrawableRes_black() {
    return drawableRes_black;
  }

  public int getDrawableRes_white() {
    return drawableRes_white;
  }

  HtBeauty(@StringRes int name, @DrawableRes int drawableRes_black, @DrawableRes int drawableResWhite, HtBeautyKey htBeautyKey) {
    this.name = name;
    this.drawableRes_white = drawableResWhite;
    this.drawableRes_black = drawableRes_black;
    this.htBeautyKey = htBeautyKey;
  }
}
