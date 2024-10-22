package com.texeljoy.ht_effect.model;

import com.texeljoy.hteffect.HTEffect;
import java.io.File;
import java.util.List;

/**
 * 风格滤镜列表配置
 */
@SuppressWarnings("unused")
public class HtBeautyFilterConfig {

  /**
   * stickers
   */
  private List<HtBeautyFilter> ht_style_filter;

  @Override public String toString() {
    return "HtStyleFilterConfig{" +
        "htFilters=" + ht_style_filter.size() +
        "个}";
  }

  public List<HtBeautyFilter> getFilters() {
    return ht_style_filter;
  }

  public HtBeautyFilterConfig(List<HtBeautyFilter> filters) {
    this.ht_style_filter = filters;
  }

  public void setFilters(List<HtBeautyFilter> htFilters) { this.ht_style_filter = htFilters;}

  public static class HtBeautyFilter {

    public static final HtBeautyFilter NO_FILTER = new HtBeautyFilter("","","", "", "", 2);

    public HtBeautyFilter(String title,String titleEn, String name, String category, String icon, int download) {
      this.title = title;
      this.title_en = titleEn;
      this.name = name;
      this.category = category;
      this.icon = icon;
      this.download = download;
    }

    @Override public String toString() {
      return "HtStyleFilter{" +
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

    public String getUrl() {

      return HTEffect.shareInstance().getFilterUrl() + name + ".zip";

    }

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public String getCategory() { return category;}

    public void setCategory(String category) { this.category = category;}

    public String getIcon() {
      return HTEffect.shareInstance().getFilterPath() + File.separator + this.name + ".png";
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
     * 下载完成更新缓存数据
     */
    // public void downloaded() {
    //   HtStyleFilterConfig htFilterConfig = HtConfigTools.getInstance().getStyleFilterConfig();
    //
    //   for (HtFilter filter : htFilterConfig.getFilters()) {
    //     if (this.name.equals(filter.name) && filter.icon.equals(this.icon)) {
    //       filter.setDownloaded(2);
    //     }
    //   }
    //   HtConfigTools.getInstance().styleFilterDownload(new Gson().toJson(htFilterConfig));
    // }

  }
}

