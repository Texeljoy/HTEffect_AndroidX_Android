package com.texeljoy.ht_effect.model;

import java.util.List;

/**
 * 美妆腮红列表配置
 */

public class HtBlushConfig {

  /**
   * stickers
   */
  private List<HtMakeup> ht_makeup_blush;

  @Override public String toString() {
    return "HtMakeupConfig{" +
        "htBlushes=" + ht_makeup_blush.size() +
        "个}";

  }

  public List<HtMakeup> getBlushes() {
    return ht_makeup_blush;

  }

  public HtBlushConfig(List<HtMakeup> blushes) {
    this.ht_makeup_blush = blushes;
  }

  public void setBlushes(List<HtMakeup> htBlushes) {
    this.ht_makeup_blush = htBlushes;
  }

}

