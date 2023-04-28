package com.texeljoy.ht_effect.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import com.texeljoy.ht_effect.R;

/**
 * 特效滤镜枚举类
 */
public enum HtEffectFilterEnum {
  NONE(R.string.effect_none, "", R.drawable.icon_effect_yuantu),
  LHCQ(R.string.effect_lhcq, "1", R.drawable.icon_effect_lhcq),
  HBDY(R.string.effect_hbdy, "2", R.drawable.icon_effect_hbdy),
  MFJM(R.string.effect_mfjm, "3", R.drawable.icon_effect_mfjm),
  XCDD(R.string.effect_xcdd, "4", R.drawable.icon_effect_xcdd),
  TYMX(R.string.effect_tymx, "5", R.drawable.icon_effect_tymx),
  DGFP(R.string.effect_dgfp, "6", R.drawable.icon_effect_dgfp),
  SGG(R.string.effect_sfp, "7", R.drawable.icon_effect_sfp),
  SPJX(R.string.effect_spjx, "8", R.drawable.icon_effect_spjx),
  MBL(R.string.effect_mbl, "9", R.drawable.icon_effect_maoboli),
  BJMH(R.string.effect_bjmh, "10", R.drawable.icon_effect_bkmh),
  MHFP(R.string.effect_mhfp, "11", R.drawable.icon_effect_mhfp),
  SJSH(R.string.effect_sjsh, "12", R.drawable.icon_effect_sjsh),
  QCDD(R.string.effect_qcdd, "13", R.drawable.icon_effect_qcdd),
  NHD(R.string.effect_nhd, "14", R.drawable.icon_effect_nhd),
  JGG(R.string.effect_jgg, "15", R.drawable.icon_effect_jgg),
  FGJ(R.string.effect_fgj, "16", R.drawable.icon_effect_fgj),
  XNJX(R.string.effect_xnjx, "17", R.drawable.icon_effect_xnjx),
  HJCY(R.string.effect_hjcy, "18", R.drawable.icon_effect_hjcy);
  //名字
  private final int stringRes;

  //对应的资源名
  private final String keyName;

  //图标地址
  private final int iconRes;



  HtEffectFilterEnum(@StringRes int stringRes, String keyName, @DrawableRes int iconRes) {
    this.stringRes = stringRes;
    this.keyName = keyName;
    this.iconRes = iconRes;
  }

  public String getName(Context context) {
    return context.getString(stringRes);
  }

  public String getKeyName() {
    return keyName;
  }

  public Drawable getIcon(Context context) {
    return ContextCompat.getDrawable(context, iconRes);
  }

}
