package com.texeljoy.ht_effect.model;

import java.util.List;

/**
 * 美妆睫毛列表配置
 */

public class HtEyelashConfig {

  /**
   * stickers
   */
  private List<HtMakeup> ht_makeup_eyelash;

  @Override public String toString() {
    return "HtMakeupConfig{" +
        "htEyelashes=" + ht_makeup_eyelash.size() +
        "个}";

  }

  public List<HtMakeup> getEyelashes() {
    return ht_makeup_eyelash;

  }

  public HtEyelashConfig(List<HtMakeup> eyelashes) {
    this.ht_makeup_eyelash = eyelashes;
  }

  public void setEyelashs(List<HtMakeup> htEyelashes) {
    this.ht_makeup_eyelash = htEyelashes;
  }

}

