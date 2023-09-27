package com.texeljoy.ht_effect.model;

import java.util.List;

/**
 * 贴纸列表配置
 */

public class HtLipstickConfig {

  /**
   * stickers
   */
  private List<HtMakeup> ht_makeup_lipstick;



  @Override public String toString() {
    return "HtMakeupConfig{" +
        "htMakeups=" + ht_makeup_lipstick.size() +
        "个}";

  }

  public List<HtMakeup> getMakeups() {
    return ht_makeup_lipstick;
  }

  public HtLipstickConfig(List<HtMakeup> makeups) {
    this.ht_makeup_lipstick = makeups;
  }

  public void setMakeups(List<HtMakeup> htMakeups) {
    this.ht_makeup_lipstick = htMakeups;
  }





  }


