package com.texeljoy.ht_effect.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import com.texeljoy.ht_effect.R;

/**
 * 滤镜枚举类
 */
public enum HtFilterEnum {
  // 原图
  NONE(R.string.filter_none, "", R.drawable.ic_filter_none),
  // 自然系
  N1(R.string.filter_ziran1, "ziran1", R.drawable.ic_filter_ziran1),
  N2(R.string.filter_ziran2, "ziran2", R.drawable.ic_filter_ziran2),
  N3(R.string.filter_ziran3, "ziran3", R.drawable.ic_filter_ziran3),
  N4(R.string.filter_ziran4, "ziran4", R.drawable.ic_filter_ziran4),
  N5(R.string.filter_ziran5, "ziran5", R.drawable.ic_filter_ziran5),
  N6(R.string.filter_ziran6, "ziran6", R.drawable.ic_filter_ziran6),
  // 质感系
  S1(R.string.filter_zhigan1, "zhigan1", R.drawable.ic_filter_zhigan1),
  S2(R.string.filter_zhigan2, "zhigan2", R.drawable.ic_filter_zhigan2),
  S3(R.string.filter_zhigan3, "zhigan3", R.drawable.ic_filter_zhigan3),
  S4(R.string.filter_zhigan4, "zhigan4", R.drawable.ic_filter_zhigan4),
  S5(R.string.filter_zhigan5, "zhigan5", R.drawable.ic_filter_zhigan_5),
  // 标准
  BIAO_ZHUN(R.string.filter_biaozhun, "biaozhun", R.drawable.ic_filter_biaozhun),
  // 水光
  SHUI_GUANG(R.string.filter_shuiguang, "shuiguang", R.drawable.ic_filter_shuiguang),
  // 水雾
  SHUI_WU(R.string.filter_shuiwu, "shuiwu", R.drawable.ic_filter_shuiwu),
  // 冷淡
  LENG_DAN(R.string.filter_lengdan, "lengdan", R.drawable.ic_filter_lengdan),
  // 白兰
  BAI_LAN(R.string.bailan, "bailan", R.drawable.ic_filter_bailan),
  // 纯真
  CHUN_ZHEN(R.string.chunzhen, "chunzhen", R.drawable.ic_filter_chunzhen),
  // 超脱
  CHAO_TUO(R.string.chaotuo, "chaotuo", R.drawable.ic_filter_chaotuo),
  // 森系
  SEN_XI(R.string.filter_senxi, "senxi", R.drawable.ic_filter_sengxi),
  // 暖阳
  NUAN_YANG(R.string.filter_nuanyang, "nuanyang", R.drawable.ic_filter_nuanyang),
  //夏日
  XIA_RI(R.string.filter_xiari, "xiari", R.drawable.ic_filter_xiari),
  // 蜜桃乌龙
  MI_TAO_WU_LONG(R.string.filter_mitaowulong, "mitaowulong", R.drawable.ic_filter_mitaowulong),
  // 少女
  SHAO_NV(R.string.filter_shaonv, "shaonv", R.drawable.ic_filter_shaonv),
  // 元气
  YUAN_QI(R.string.filter_yuanqi, "yuanqi", R.drawable.ic_filter_yuanqi),
  // 绯樱
  FEI_YING(R.string.filter_feiying, "feiying", R.drawable.ic_filter_feiying),
  // 清新
  QING_XIN(R.string.qingxin, "qingxin", R.drawable.ic_filter_qingxin),
  // 日系
  RI_XI(R.string.rixi, "rixi", R.drawable.ic_filter_rixi),
  // 反差色
  FAN_CHA_SE(R.string.filter_fanchase, "fanchase", R.drawable.ic_filter_fanchase),
  // 复古
  FU_GU(R.string.filter_fugu, "fugu", R.drawable.ic_filter_fugu),
  // 回忆
  HUI_YI(R.string.filter_huiyi, "huiyi", R.drawable.ic_filter_huiyi),
  // 怀旧
  HUAI_JIU(R.string.huaijiu, "huaijiu", R.drawable.ic_filter_huaijiu),
  ;
  //名字
  private final int stringRes;

  //对应的资源名
  private final String keyName;

  //图标地址
  private final int iconRes;



  HtFilterEnum(@StringRes int stringRes, String keyName, @DrawableRes int iconRes) {
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
