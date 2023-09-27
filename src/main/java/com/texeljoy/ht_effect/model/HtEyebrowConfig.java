package com.texeljoy.ht_effect.model;

import java.util.List;

/**
 * 贴纸列表配置
 */

public class HtEyebrowConfig {

  /**
   * stickers
   */
  private List<HtMakeup> ht_makeup_eyebrow;

  @Override public String toString() {
    return "HtMakeupConfig{" +
        "htMakeups=" + ht_makeup_eyebrow.size() +
        "个}";

  }

  public List<HtMakeup> getMakeups() {
    return ht_makeup_eyebrow;



  }

  public void setType(int type){

  }

  public HtEyebrowConfig(List<HtMakeup> makeups) {
    this.ht_makeup_eyebrow = makeups;
  }

  public void setMakeups(List<HtMakeup> htMakeups) {
    this.ht_makeup_eyebrow = htMakeups;
  }

}

