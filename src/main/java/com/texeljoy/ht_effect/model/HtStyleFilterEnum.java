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
public enum HtStyleFilterEnum {
  // 原图
  NONE(R.string.filter_none, "", R.drawable.ic_filter_yuantu),
  // 自然系
  N1(R.string.beauty_filter_ziran1, "ziran1", R.drawable.ic_filter_ziran1),
  N2(R.string.beauty_filter_ziran2, "ziran2", R.drawable.ic_filter_ziran2),
  N3(R.string.beauty_filter_ziran3, "ziran3", R.drawable.ic_filter_ziran3),
  N4(R.string.beauty_filter_ziran4, "ziran4", R.drawable.ic_filter_ziran4),
  N5(R.string.beauty_filter_ziran5, "ziran5", R.drawable.ic_filter_ziran5),
  N6(R.string.beauty_filter_ziran6, "ziran6", R.drawable.ic_filter_ziran6),
  // 月色
  YUE_SE(R.string.beauty_filter_yuese, "yuese", R.drawable.ic_filter_yuese),
  // 月辉
  YUE_HUI(R.string.beauty_filter_yuehui, "yuehui", R.drawable.ic_filter_yuehui),
  // 日光
  RI_GUANG(R.string.beauty_filter_riguang, "riguang", R.drawable.ic_filter_riguang),
  // 落霞
  LUO_XIA(R.string.beauty_filter_luoxia, "luoxia", R.drawable.ic_filter_luoxia),
  // 质感系
  S1(R.string.beauty_filter_zhigan1, "zhigan1", R.drawable.ic_filter_zhigan1),
  S2(R.string.beauty_filter_zhigan2, "zhigan2", R.drawable.ic_filter_zhigan2),
  S3(R.string.beauty_filter_zhigan3, "zhigan3", R.drawable.ic_filter_zhigan3),
  S4(R.string.beauty_filter_zhigan4, "zhigan4", R.drawable.ic_filter_zhigan4),
  S5(R.string.beauty_filter_zhigan5, "zhigan5", R.drawable.ic_filter_zhigan5),
  // shadow
  SHADOW1(R.string.beauty_filter_shadow1, "shadow1", R.drawable.ic_filter_shadow1),
  SHADOW2(R.string.beauty_filter_shadow2, "shadow2", R.drawable.ic_filter_shadow2),
  SHADOW3(R.string.beauty_filter_shadow3, "shadow3", R.drawable.ic_filter_shadow3),
  SHADOW4(R.string.beauty_filter_shadow4, "shadow4", R.drawable.ic_filter_shadow4),
  //月夜
  YUE_YE(R.string.beauty_filter_yueye, "yueye", R.drawable.ic_filter_yueye),
  //米茶
  MI_CHA(R.string.beauty_filter_micha, "micha", R.drawable.ic_filter_micha),
  //静谧
  JING_MI(R.string.beauty_filter_jingmi, "jingmi", R.drawable.ic_filter_jingmi),
  //柔光
  ROU_GUANG(R.string.beauty_filter_rouguang, "rouguang", R.drawable.ic_filter_rouguang),
  //奶杏
  NAI_XING(R.string.beauty_filter_naixing, "naixing", R.drawable.ic_filter_naixing),
  //烟色
  YAN_SE(R.string.beauty_filter_yanse, "yanse", R.drawable.ic_filter_yanse),
  //青雾
  QING_WU(R.string.beauty_filter_qingwu, "qingwu", R.drawable.ic_filter_qingwu),
  //青曝
  YAN_BAO(R.string.beauty_filter_qingbao, "yanse", R.drawable.ic_filter_qingbao),
  //幻紫
  HUAN_ZI(R.string.beauty_filter_huanzi, "huanzi", R.drawable.ic_filter_huanzi),
  //幻色
  HUAN_SE(R.string.beauty_filter_huanse, "huanse", R.drawable.ic_filter_huanse),
  //旧照
  JIU_ZHAO(R.string.beauty_filter_jiuzhao, "jiuzhao", R.drawable.ic_filter_jiuzhao),
  //迷幻
  MI_HUAN(R.string.beauty_filter_mihuan, "mihuan", R.drawable.ic_filter_mihuan),
  //异彩
  YI_CAI(R.string.beauty_filter_yicai, "yicai", R.drawable.ic_filter_yicai),
  //春晖
  CHUN_HUI(R.string.beauty_filter_chunhui,"chunhui", R.drawable.ic_filter_chunhui),
  //光晕
  GUANG_YUN(R.string.beauty_filter_guangyun, "guangyun", R.drawable.ic_filter_guangyun),
  //柔雾
  ROU_WU(R.string.beauty_filter_rouwu, "rouwu", R.drawable.ic_filter_rouwu),
  //直射灯
  ZHI_SHE_DENG(R.string.beauty_filter_zhishedeng, "zhishedeng", R.drawable.ic_filter_zhishedeng),
  //初春
  CHUN_CHUN(R.string.beauty_filter_chuchun, "chuchun", R.drawable.ic_filter_chuchun),
  //初恋
  CHU_LIAN(R.string.beauty_filter_chulian, "chulian", R.drawable.ic_filter_chulian),
  //初雪
  CHU_XUE(R.string.beauty_filter_chuxue, "chuxue", R.drawable.ic_filter_chuxue),
  //粉瓷
  FEN_CI(R.string.beauty_filter_fenci, "fenci", R.drawable.ic_filter_fenci),
  //冷冬
  LENG_DONG(R.string.beauty_filter_lengdong, "lengdong", R.drawable.ic_filter_lengdong),
  //梦境
  MENG_JING(R.string.beauty_filter_mengjing, "mengjing", R.drawable.ic_filter_mengjing),
  //清透
  QING_TOU(R.string.beauty_filter_qingtou, "qingtou", R.drawable.ic_filter_qingtou),
  //新叶
  XIN_YE(R.string.beauty_filter_xinye, "xinye", R.drawable.ic_filter_xinye),
  //冰茶
  BING_CHA(R.string.beauty_filter_bingcha, "bingcha", R.drawable.ic_filter_bingcha),
  //浪漫
  LANG_MAN(R.string.beauty_filter_langman, "langman", R.drawable.ic_filter_langman),
  //迷雾
  MI_WU(R.string.beauty_filter_miwu, "miwu", R.drawable.ic_filter_miwu),
  //青木
  QING_MU(R.string.beauty_filter_qingmu, "qingmu", R.drawable.ic_filter_qingmu),
  //轻沙
  QING_SHA(R.string.beauty_filter_qingsha, "qingsha", R.drawable.ic_filter_qingsha),
  //情绪
  QING_XU(R.string.beauty_filter_qingxu, "qingxu", R.drawable.ic_filter_qingxu),
  //森雾
  SEN_WU(R.string.beauty_filter_senwu, "senwu", R.drawable.ic_filter_senwu),
  //沙漫
  SHA_MAN(R.string.beauty_filter_shaman, "shaman", R.drawable.ic_filter_shaman),
  //温柔
  WEN_ROU(R.string.beauty_filter_wenrou, "wenrou", R.drawable.ic_filter_wenrou),
  //庙会
  MIAO_HUI(R.string.beauty_filter_miaohui, "miaohui", R.drawable.ic_filter_miaohui),
  //晴空
  QING_KONG(R.string.beauty_filter_qingkong, "qingkong", R.drawable.ic_filter_qingkong),
  //山间
  SHAN_JIAN(R.string.beauty_filter_shanjian, "shanjian", R.drawable.ic_filter_shanjian),
  //香松
  XIANG_SONG(R.string.beauty_filter_xiangsong, "xiangsong", R.drawable.ic_filter_xiangsong),
  // 标准
  BIAO_ZHUN(R.string.beauty_filter_biaozhun, "biaozhun", R.drawable.ic_filter_biaozhun),
  // 水光
  SHUI_GUANG(R.string.beauty_filter_shuiguang, "shuiguang", R.drawable.ic_filter_shuiguang),
  // 水雾
  SHUI_WU(R.string.beauty_filter_shuiwu, "shuiwu", R.drawable.ic_filter_shuiwu),
  // 冷淡
  LENG_DAN(R.string.beauty_filter_lengdan, "lengdan", R.drawable.ic_filter_lengdan),
  // 白兰
  BAI_LAN(R.string.beauty_filter_bailan, "bailan", R.drawable.ic_filter_bailan),
  // 纯真
  CHUN_ZHEN(R.string.beauty_filter_chunzhen, "chunzhen", R.drawable.ic_filter_chunzhen),
  // 超脱
  CHAO_TUO(R.string.beauty_filter_chaotuo, "chaotuo", R.drawable.ic_filter_chaotuo),
  // 森系
  SEN_XI(R.string.beauty_filter_senxi, "senxi", R.drawable.ic_filter_senxi),
  // 暖阳
  NUAN_YANG(R.string.beauty_filter_nuanyang, "nuanyang", R.drawable.ic_filter_nuanyang),
  //夏日
  XIA_RI(R.string.beauty_filter_xiari, "xiari", R.drawable.ic_filter_xiari),
  // 蜜桃乌龙
  MI_TAO_WU_LONG(R.string.beauty_filter_mitaowulong, "mitaowulong", R.drawable.ic_filter_mitaowulong),
  // 少女
  SHAO_NV(R.string.beauty_filter_shaonv, "shaonv", R.drawable.ic_filter_shaonv),
  // 元气
  YUAN_QI(R.string.beauty_filter_yuanqi, "yuanqi", R.drawable.ic_filter_yuanqi),
  // 绯樱
  FEI_YING(R.string.beauty_filter_feiying, "feiying", R.drawable.ic_filter_feiying),
  // 清新
  QING_XIN(R.string.beauty_filter_qingxin, "qingxin", R.drawable.ic_filter_qingxin),
  // 日系
  RI_XI(R.string.beauty_filter_rixi, "rixi", R.drawable.ic_filter_rixi),
  // 反差色
  FAN_CHA_SE(R.string.beauty_filter_fanchase, "fanchase", R.drawable.ic_filter_fanchase),
  // 复古
  FU_GU(R.string.beauty_filter_fugu, "fugu", R.drawable.ic_filter_fugu),
  // 回忆
  HUI_YI(R.string.beauty_filter_huiyi, "huiyi", R.drawable.ic_filter_huiyi),
  // 怀旧
  HUAI_JIU(R.string.beauty_filter_huaijiu, "huaijiu", R.drawable.ic_filter_huaijiu),
  ;
  //名字
  private final int stringRes;

  //对应的资源名
  private final String keyName;

  //图标地址
  private final int iconRes;



  HtStyleFilterEnum(@StringRes int stringRes, String keyName, @DrawableRes int iconRes) {
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
  public int getIconRes(){
    return iconRes;
  }

}
