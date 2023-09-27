package com.texeljoy.ht_effect.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import com.texeljoy.ht_effect.R;
import com.texeljoy.hteffect.model.HTStyleEnum;

/**
 * 妆容推荐枚举
 */
public enum HtMakeupStyle {
  //原图
  NONE(R.string.makeup_none, R.drawable.ic_makeup_none, HTStyleEnum.HTStyleTypeNone),
  //清纯白花
  QCBH(R.string.makeup_qcbh, R.drawable.ic_makeup_01, HTStyleEnum.HTStyleTypeOne),
  //狐系美人
  HXMR(R.string.makeup_hxmr, R.drawable.ic_makeup_02, HTStyleEnum.HTStyleTypeTwo),
  //清甜妆
  QTZ(R.string.makeup_qtz, R.drawable.ic_makeup_03, HTStyleEnum.HTStyleTypeThree),
  //白露
  BL(R.string.makeup_bl, R.drawable.ic_makeup_04, HTStyleEnum.HTStyleTypeFour),
  //冷调
  LD(R.string.makeup_ld, R.drawable.ic_makeup_05, HTStyleEnum.HTStyleTypeFive),
  //元气少女
  YQSN(R.string.makeup_yqsn, R.drawable.ic_makeup_06, HTStyleEnum.HTStyleTypeSix),
  //女团
  NT(R.string.makeup_nt, R.drawable.ic_makeup_07, HTStyleEnum.HTStyleTypeSeven),
  //纯欲妆
  CYZ(R.string.makeup_cyz, R.drawable.ic_makeup_08, HTStyleEnum.HTStyleTypeEight);

  private final int stringRes;

  private final int iconRes;

  private final HTStyleEnum lightMakeup;

    HtMakeupStyle(int stringRes, int iconRes, HTStyleEnum lightMakeup) {
    this.stringRes = stringRes;
    this.iconRes = iconRes;
    this.lightMakeup = lightMakeup;
  }

  public String getName(Context context) {
    return context.getString(stringRes);
  }

  public Drawable getIcon(Context context) {
    return ContextCompat.getDrawable(context, iconRes);
  }

  public HTStyleEnum getLightMakeup() {
    return lightMakeup;
  }
}
