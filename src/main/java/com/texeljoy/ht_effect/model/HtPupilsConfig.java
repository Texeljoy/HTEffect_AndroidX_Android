package com.texeljoy.ht_effect.model;

import java.util.List;

/**
 * 美妆美瞳列表配置
 */

public class HtPupilsConfig {

  /**
   * stickers
   */
  private List<HtMakeup> ht_makeup_pupils;

  @Override public String toString() {
    return "HtMakeupConfig{" +
        "htPupils=" + ht_makeup_pupils.size() +
        "个}";

  }

  public List<HtMakeup> getPupils() {
    return ht_makeup_pupils;

  }

  public HtPupilsConfig(List<HtMakeup> pupils) {
    this.ht_makeup_pupils = pupils;
  }

  public void setPupils(List<HtMakeup> htPupils) {
    this.ht_makeup_pupils = htPupils;
  }

}

