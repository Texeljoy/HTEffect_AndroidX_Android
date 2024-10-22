package com.texeljoy.ht_effect.model;

import java.util.List;

/**
 * 美妆眼影列表配置
 */

public class HtEyeshadowConfig {

  /**
   * stickers
   */
  private List<HtMakeup> ht_makeup_eyeshadow;

  @Override public String toString() {
    return "HtMakeupConfig{" +
        "htEyeshadows=" + ht_makeup_eyeshadow.size() +
        "个}";

  }

  public List<HtMakeup> getEyeshadows() {
    return ht_makeup_eyeshadow;

  }

  public HtEyeshadowConfig(List<HtMakeup> eyeshadows) {
    this.ht_makeup_eyeshadow = eyeshadows;
  }

  public void setEyeshadows(List<HtMakeup> htEyeshadows) {
    this.ht_makeup_eyeshadow = htEyeshadows;
  }

}

