package com.texeljoy.ht_effect.model;

import java.util.List;

/**
 * 美妆眉毛列表配置
 */

public class HtEyebrowConfig {

  /**
   * stickers
   */
  private List<HtMakeup> ht_makeup_eyebrow;

  @Override public String toString() {
    return "HtMakeupConfig{" +
        "htEyebrows=" + ht_makeup_eyebrow.size() +
        "个}";

  }

  public List<HtMakeup> getEyebrows() {
    return ht_makeup_eyebrow;



  }

  public void setType(int type){

  }

  public HtEyebrowConfig(List<HtMakeup> eyebrows) {
    this.ht_makeup_eyebrow = eyebrows;
  }

  public void setEyebrows(List<HtMakeup> htEyebrows) {
    this.ht_makeup_eyebrow = htEyebrows;
  }

}

