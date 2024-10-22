package com.texeljoy.ht_effect.model;

import com.texeljoy.hteffect.HTEffect;
import java.io.File;
import java.util.List;

/**
 * 妆容推荐配置
 */

public class HtMakeupStyleConfig {

  /**
   * makeups
   */
  private List<HtMakeupStyle> ht_makeup_style;

  @Override public String toString() {
    return "HtMakeupStyleConfig{" +
        "htStyles=" + ht_makeup_style.size() +
        "个}";
  }

  public List<HtMakeupStyle> getStyles(){
    return ht_makeup_style;
  }

  public HtMakeupStyleConfig(List<HtMakeupStyle> styles) {
    this.ht_makeup_style = styles;
  }

  public void setStyles(List<HtMakeupStyle> htStyles) { this.ht_makeup_style = htStyles;}

  public static class HtMakeupStyle {

    public static final HtMakeupStyle NO_STYLE = new HtMakeupStyle("","","", "", "", 2);

    public HtMakeupStyle(String title,String titleEn, String name, String category, String icon, int download) {
      this.title = title;
      this.title_en = titleEn;
      this.name = name;
      this.category = category;
      this.icon = icon;
      this.download = download;
    }
    @Override public String toString() {
      return "HtMakeupStyle{" +
          "name='" + name + '\'' +
          ", category='" + category + '\'' +
          ", icon='" + icon + '\'' +
          ", download=" + download +
          '}';
    }

    /**
     * title
     */
    private String title;
    private String title_en;
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


    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public String getCategory() { return category;}

    public void setCategory(String category) { this.category = category;}

    public String getIcon() {
      return HTEffect.shareInstance().getStylePath() + File.separator + this.name + ".png";
    }

    public void setThumb(String icon) { this.icon = icon;}

    public int isDownloaded() { return download;}

    public void setDownloaded(int download) {
      this.download = download;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getTitleEn() {
      return title_en;
    }

    public void setTitleEn(String titleEn) {
      this.title_en = titleEn;
    }


    /**
     * 下载完成设置缓存文件
     */
  /*  public void downloaded() {
      HtMakeupStyleConfig makeupstyleList = HtConfigTools.getInstance().getMakeupStyleList();
      for (HtMakeupStyle makeup : makeupstyleList.getStyles()) {
        if (makeup.name.equals(this.name) && makeup.type.equals(this.type)) {
          makeup.setDownloaded(true);
        }
      }
      HtConfigTools.getInstance().makeupStyleDownLoad(new Gson().toJson(makeupstyleList));
    }*/


  }

}
