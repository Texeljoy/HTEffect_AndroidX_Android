package com.texeljoy.ht_effect.model;

/**
 * 缓存的key值
 */
public enum HtUICacheKey {

  BEAUTY_SKIN_SELECT_POSITION(0,""), //选中了哪个美肤
  BEAUTY_FACE_TRIM_SELECT_POSITION(0,""), //选中了哪个美型
  BEAUTY_STYLE_SELECT_POSITION(0,""), //选中了哪个风格
  FILTER_SELECT_POSITION,//选中了哪个滤镜
  FILTER_SELECT_NAME,//选中了哪个滤镜
  EFFECT_FILTER_SELECT_POSITION,//选中了哪个趣味滤镜
  EFFECT_FILTER_SELECT_NAME,//选中了哪个趣味滤镜
  FUNNY_FILTER_SELECT_POSITION,//选中了哪个趣味滤镜
  FUNNY_FILTER_SELECT_NAME,//选中了哪个趣味滤镜
  HAIR_SELECT_POSITION,//选中了哪个美发
  HAIR_SELECT_NAME,//选中了哪个美发
  MAKEUP_SELECT_POSITION,//选中了哪个轻彩妆
  GREENSCREEN_EDIT_POSITION,
  GREENSCREEN_EDIT
  ;

  int defaultInt;
  String defaultStr;

  public int getDefaultInt() {
    return defaultInt;
  }

  public String getDefaultStr() {
    return defaultStr;
  }

  HtUICacheKey(int defaultInt, String defaultStr) {
    this.defaultInt = defaultInt;
    this.defaultStr = defaultStr;
  }

  HtUICacheKey() {
    defaultStr = "";
    defaultInt = 0;
  }

}
