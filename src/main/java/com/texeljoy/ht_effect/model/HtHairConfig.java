package com.texeljoy.ht_effect.model;

import com.texeljoy.hteffect.HTEffect;
import java.io.File;
import java.util.List;

/**
 * 贴纸列表配置
 */
@SuppressWarnings("unused")
public class HtHairConfig {

  /**
   * stickers
   */
  private List<HtHair> ht_hair;

  @Override public String toString() {
    return "HtHairConfig{" +
        "htHairs=" + ht_hair.size() +
        "个}";
  }

  public List<HtHair> getHairs() {
    return ht_hair;
  }

  public HtHairConfig(List<HtHair> hairs) {
    this.ht_hair = hairs;
  }

  public void setHairs(List<HtHair> htHairs) { this.ht_hair = htHairs;}

  public static class HtHair {

    public static final HtHair NO_HAIR = new HtHair("","", 0,"", "", 2);

    public HtHair(String title, String name, int id, String category, String icon, int download) {
      this.title = title;
      this.name = name;
      this.id = id;
      this.category = category;
      this.icon = icon;
      this.download = download;
    }

    @Override public String toString() {
      return "HtHair{" +
          "name='" + name + '\'' +
          ", category='" + category + '\'' +
          ", id='" + id + '\'' +
          ", icon='" + icon + '\'' +
          ", download=" + download +
          '}';
    }
    /**
     * title
     */
    private String title;
    /**
     * id
     */
    private int id;
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

      return HTEffect.shareInstance().getFilterUrl() + name + ".zip";

    }

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public String getCategory() { return category;}

    public void setCategory(String category) { this.category = category;}

    public String getIcon() {
      return HTEffect.shareInstance().getARItemPathBy(5) + File.separator + this.name + ".png";
    }

    public void setThumb(String icon) { this.icon = icon;}

    public int isDownloaded() { return download;}

    public void setDownloaded(int download) {
      this.download = download;
    }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }
    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }




    /**
     * 下载完成更新缓存数据
     */
    // public void downloaded() {
    //   //todo 等待接口
    //   HtHairConfig htHairConfig = HtConfigTools.getInstance().getFilterConfig();
    //
    //   for (HtHair hair : htHairConfig.getHairs()) {
    //     if (this.name.equals(hair.name) && hair.icon.equals(this.icon)) {
    //       hair.setDownloaded(2);
    //     }
    //   }
    //   HtConfigTools.getInstance().filterDownload(new Gson().toJson(htHairConfig));
    // }

  }
}

