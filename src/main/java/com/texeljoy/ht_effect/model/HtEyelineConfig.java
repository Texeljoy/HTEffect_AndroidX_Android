package com.texeljoy.ht_effect.model;

import java.util.List;

/**
 * 美妆眼线列表配置
 */

public class HtEyelineConfig {

  /**
   * stickers
   */
  private List<HtMakeup> ht_makeup_eyeline;

  @Override public String toString() {
    return "HtMakeupConfig{" +
        "htEyeliners=" + ht_makeup_eyeline.size() +
        "个}";

  }

  public List<HtMakeup> getEyeliners() {
    return ht_makeup_eyeline;

  }

  public HtEyelineConfig(List<HtMakeup> eyeliners) {
    this.ht_makeup_eyeline = eyeliners;
  }

  public void setEyeliners(List<HtMakeup> htEyeliners) {
    this.ht_makeup_eyeline = htEyeliners;
  }

}

