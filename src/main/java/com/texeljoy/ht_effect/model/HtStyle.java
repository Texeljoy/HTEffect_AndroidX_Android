package com.texeljoy.ht_effect.model;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import com.texeljoy.ht_effect.R;

/**
 * 风格推荐枚举
 */
public enum HtStyle {
  //原图
  YUAN_TU(R.string.none,R.drawable.ic_style_none,0,"#666666"),
  //经典
  JING_DIAN(R.string.style_jingdian, R.drawable.ic_style_jingdian, 1, "#B77F52"),
  //网红
  WANG_HONG(R.string.style_wanghong, R.drawable.ic_style_wanghong, 2, "#8B897A"),
  //女神
  NV_SHENG(R.string.style_nvsheng, R.drawable.ic_style_nvsheng, 3, "#C49E97"),
  //复古
  FU_GU(R.string.style_fugu, R.drawable.ic_style_fugu, 4, "#BC917F"),
  //日杂
  RI_ZA(R.string.style_riza, R.drawable.ic_style_riza, 5, "#7F6990"),
  //初恋
  CHU_LIAN(R.string.style_chulian, R.drawable.ic_style_chulian, 6, "#97A5BF"),
  //质感
  ZHI_GAN(R.string.style_zhigan, R.drawable.ic_style_zhigan, 7, "#70564E"),
  //伪素颜
  WEI_SU_YAN(R.string.style_weisuyan, R.drawable.ic_style_weisuyan, 8, "#817D7A"),
  //清冷
  QING_LENG(R.string.style_qing_leng, R.drawable.ic_style_qingleng, 9, "#BBA488"),
  //甜心
  TIAN_XIN(R.string.style_tianxin, R.drawable.ic_style_tianxin, 10, "#CA9FA7"),
  //低端机适配
  //LOW_END(R.string.style_low_end, R.drawable.ic_style_low_end, HtStyleParam.LOW_END, "#9A745C"),
  ;

  private final int stringId;

  private final int iconId;

  private final int param;

  private final String fillColor;

  HtStyle(@StringRes int stringId, @DrawableRes int iconId, int htStyleParam, String fillColor) {
    this.stringId = stringId;
    this.iconId = iconId;
    this.param = htStyleParam;
    this.fillColor = fillColor;
  }

  public int getFillColor() {
    return Color.parseColor(fillColor);
  }

  public int getParam() {
    return param;
  }

  public String getString(Context context) {
    return context.getString(stringId);
  }

  public Drawable getIcon(Context context) {
    return ContextCompat.getDrawable(context, iconId);
  }

}
