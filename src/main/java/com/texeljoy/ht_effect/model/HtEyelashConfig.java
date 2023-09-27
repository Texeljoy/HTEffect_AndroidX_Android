package com.texeljoy.ht_effect.model;

import java.util.List;

/**
 * 贴纸列表配置
 */

public class HtEyelashConfig {

  /**
   * stickers
   */
  private List<HtMakeup> ht_makeup_eyelash;

  @Override public String toString() {
    return "HtMakeupConfig{" +
        "htMakeups=" + ht_makeup_eyelash.size() +
        "个}";

  }

  public List<HtMakeup> getMakeups() {
    return ht_makeup_eyelash;

  }

  public HtEyelashConfig(List<HtMakeup> makeups) {
    this.ht_makeup_eyelash = makeups;
  }

  public void setMakeups(List<HtMakeup> htMakeups) {
    this.ht_makeup_eyelash = htMakeups;
  }

}

