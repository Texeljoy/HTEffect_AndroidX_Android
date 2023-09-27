package com.texeljoy.ht_effect.model;

import java.util.List;

/**
 * 贴纸列表配置
 */

public class HtEyelineConfig {

  /**
   * stickers
   */
  private List<HtMakeup> ht_makeup_eyeline;

  @Override public String toString() {
    return "HtMakeupConfig{" +
        "htMakeups=" + ht_makeup_eyeline.size() +
        "个}";

  }

  public List<HtMakeup> getMakeups() {
    return ht_makeup_eyeline;

  }

  public HtEyelineConfig(List<HtMakeup> makeups) {
    this.ht_makeup_eyeline = makeups;
  }

  public void setMakeups(List<HtMakeup> htMakeups) {
    this.ht_makeup_eyeline = htMakeups;
  }

}

