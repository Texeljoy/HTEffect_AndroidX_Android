package com.texeljoy.ht_effect.model;

import java.util.List;

/**
 * 贴纸列表配置
 */

public class HtEyeshadowConfig {

  /**
   * stickers
   */
  private List<HtMakeup> ht_makeup_eyeshadow;

  @Override public String toString() {
    return "HtMakeupConfig{" +
        "htMakeups=" + ht_makeup_eyeshadow.size() +
        "个}";

  }

  public List<HtMakeup> getMakeups() {
    return ht_makeup_eyeshadow;

  }

  public HtEyeshadowConfig(List<HtMakeup> makeups) {
    this.ht_makeup_eyeshadow = makeups;
  }

  public void setMakeups(List<HtMakeup> htMakeups) {
    this.ht_makeup_eyeshadow = htMakeups;
  }

}

