package com.texeljoy.ht_effect.model;

import com.google.gson.Gson;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import com.texeljoy.hteffect.HTEffect;
import java.io.File;
import java.util.List;

/**
 * 贴纸列表配置
 */
@SuppressWarnings("unused")
public class HtFilterConfig {

  /**
   * stickers
   */
  private List<HtFilter> ht_filter;

  @Override public String toString() {
    return "HtFilterConfig{" +
        "htFilters=" + ht_filter.size() +
        "个}";
  }

  public List<HtFilter> getFilters() {
    return ht_filter;
  }

  public HtFilterConfig(List<HtFilter> filters) {
    this.ht_filter = filters;
  }

  public void setFilters(List<HtFilter> htFilters) { this.ht_filter = htFilters;}

  public static class HtFilter {

    public static final HtFilter NO_FILTER = new HtFilter("","", "", "", 2);

    public HtFilter(String title, String name, String category, String icon, int download) {
      this.title = title;
      this.name = name;
      this.category = category;
      this.icon = icon;
      this.download = download;
    }

    @Override public String toString() {
      return "HtFilter{" +
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




    /**
     * 下载完成更新缓存数据
     */
    public void downloaded() {
      HtFilterConfig htFilterConfig = HtConfigTools.getInstance().getFilterConfig();

      for (HtFilter filter : htFilterConfig.getFilters()) {
        if (this.name.equals(filter.name) && filter.icon.equals(this.icon)) {
          filter.setDownloaded(2);
        }
      }
      HtConfigTools.getInstance().filterDownload(new Gson().toJson(htFilterConfig));
    }

  }
}

