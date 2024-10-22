package com.texeljoy.ht_effect.model;

import java.util.List;

/**
 * 美妆口红列表配置
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

  public List<HtMakeup> getLipsticks() {
    return ht_makeup_lipstick;
  }

  public HtLipstickConfig(List<HtMakeup> lipsticks) {
    this.ht_makeup_lipstick = lipsticks;
  }

  public void setLipsticks(List<HtMakeup> htLipsticks) {
    this.ht_makeup_lipstick = htLipsticks;
  }





  }


