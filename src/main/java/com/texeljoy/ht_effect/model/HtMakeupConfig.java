package com.texeljoy.ht_effect.model;

import com.google.gson.Gson;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import com.texeljoy.hteffect.HTEffect;
import java.util.List;

/**
 * 贴纸列表配置
 */

public class HtMakeupConfig {

  /**
   * stickers
   */
  private List<HtMakeup> ht_makeup_blush;
  private List<HtMakeup> ht_makeup_eyebrow;
  private List<HtMakeup> ht_makeup_eyelash;
  private List<HtMakeup> ht_makeup_eyeline;
  private List<HtMakeup> ht_makeup_eyeshadow;

  /**
   * id
   */
  private static int type;

  @Override public String toString() {
    switch (type){
      case 1:
        return "HtMakeupConfig{" +
            "htMakeups=" + ht_makeup_eyebrow.size() +
            "个}";
      case 2:
        return "HtMakeupConfig{" +
            "htMakeups=" + ht_makeup_blush.size() +
            "个}";
      case 3:
        return "HtMakeupConfig{" +
            "htMakeups=" + ht_makeup_eyeshadow.size() +
            "个}";
      case 4:
        return "HtMakeupConfig{" +
            "htMakeups=" + ht_makeup_eyeline.size() +
            "个}";
      case 5:
        return "HtMakeupConfig{" +
            "htMakeups=" + ht_makeup_eyelash.size() +
            "个}";
      default:
        return "HtMakeupConfig{" +
            "htMakeups=" + ht_makeup_eyebrow.size() +
            "个}";
    }

  }

  public List<HtMakeup> getMakeups() {
    switch (type){
      case 1:
        return ht_makeup_eyebrow;
      case 2:
        return ht_makeup_blush;
      case 3:
        return ht_makeup_eyeshadow;
      case 4:
        return ht_makeup_eyeline;
      case 5:
        return ht_makeup_eyelash;
      default:
        return ht_makeup_eyebrow;

    }
  }

  public void setType(int type){

  }

  public HtMakeupConfig(List<HtMakeup> makeups) {
    this.ht_makeup_eyebrow = makeups;
    this.ht_makeup_blush = makeups;
    this.ht_makeup_eyeshadow = makeups;
    this.ht_makeup_eyeline = makeups;
    this.ht_makeup_eyelash = makeups;
  }

  public void setMakeups(List<HtMakeup> htMakeups) {
    this.ht_makeup_eyebrow = htMakeups;
    this.ht_makeup_blush = htMakeups;
    this.ht_makeup_eyeshadow = htMakeups;
    this.ht_makeup_eyeline = htMakeups;
    this.ht_makeup_eyelash = htMakeups;
  }

  public static class HtMakeup {

    public static final HtMakeup NO_MAKEUP = new HtMakeup("", "", "", 0);

    public HtMakeup( String name,String category, String icon, int download) {

      this.name = name;
      this.category = category;
      this.icon = icon;
      this.download = download;
    }

    @Override public String toString() {
      return "HtMakeup{" +
          "name='" + name + '\'' +
          ", category='" + category + '\'' +
          ", icon='" + icon + '\'' +
          ", download=" + download +
          '}';
    }


    /**
     * name
     */
    private String name;
    /**
     * category
     */
    private String category;
    /**
     * icon
     */
    private String icon;
    /**
     * downloaded
     */
    private int download;

    public String getUrl() {

      return HTEffect.shareInstance().getMakeupUrl(type) + name + ".zip";
    }

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public String getCategory() { return category;}

    public void setCategory(String category) { this.category = category;}

    public String getIcon() {
      return HTEffect.shareInstance().getMakeupUrl(type) + this.icon;
    }

    public void setThumb(String icon) { this.icon = icon;}

    public int isDownloaded() { return download;}

    public void setDownloaded(int download) {
      this.download = download;
    }




    /**
     * 下载完成更新缓存数据
     */
    public void downloaded() {
      //todo 等待接口
      HtMakeupConfig htMakeupConfig = HtConfigTools.getInstance().getMakeupList();

      for (HtMakeup makeup : htMakeupConfig.getMakeups()) {
        if (this.name.equals(makeup.name) && makeup.icon.equals(this.icon)) {
          makeup.setDownloaded(2);
        }
      }
      HtConfigTools.getInstance().makeupDownload(type,new Gson().toJson(htMakeupConfig));
    }

  }
}

