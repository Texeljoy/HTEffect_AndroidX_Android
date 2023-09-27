package com.texeljoy.ht_effect.model;

import java.util.List;

/**
 * 贴纸列表配置
 */

public class HtPupilsConfig {

  /**
   * stickers
   */
  private List<HtMakeup> ht_makeup_pupils;

  @Override public String toString() {
    return "HtMakeupConfig{" +
        "htMakeups=" + ht_makeup_pupils.size() +
        "个}";

  }

  public List<HtMakeup> getMakeups() {
    return ht_makeup_pupils;

  }

  public HtPupilsConfig(List<HtMakeup> makeups) {
    this.ht_makeup_pupils = makeups;
  }

  public void setMakeups(List<HtMakeup> htMakeups) {
    this.ht_makeup_pupils = htMakeups;
  }

}

