package com.texeljoy.ht_effect.model;

import java.util.List;

/**
 * 贴纸列表配置
 */

public class HtBlushConfig {

  /**
   * stickers
   */
  private List<HtMakeup> ht_makeup_blush;

  @Override public String toString() {
    return "HtMakeupConfig{" +
        "htMakeups=" + ht_makeup_blush.size() +
        "个}";

  }

  public List<HtMakeup> getMakeups() {
    return ht_makeup_blush;

  }

  public HtBlushConfig(List<HtMakeup> makeups) {
    this.ht_makeup_blush = makeups;
  }

  public void setMakeups(List<HtMakeup> htMakeups) {
    this.ht_makeup_blush = htMakeups;
  }

}

