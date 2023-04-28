package com.texeljoy.ht_effect.model;

import com.google.gson.Gson;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import com.texeljoy.hteffect.HTEffect;
import com.texeljoy.hteffect.model.HTItemEnum;
import java.util.List;

/**
 * 贴纸列表配置
 */
@SuppressWarnings("unused")
public class HtMaskConfig {

  /**
   * stickers
   */
  private List<HtMask> ht_mask;

  @Override public String toString() {
    return "HtMaskConfig{" +
        "htMasks=" + ht_mask.size() +
        "个}";
  }

  public List<HtMask> getMasks() {
    return ht_mask;
  }

  public HtMaskConfig(List<HtMask> masks) {
    this.ht_mask = masks;
  }

  public void setMasks(List<HtMask> tiMasks) { this.ht_mask = tiMasks;}

  public static class HtMask {

    public static final HtMask NO_MASK = new HtMask("", "", "", HTDownloadState.COMPLETE_DOWNLOAD);
    public static final HtMask NEW_MASK = new HtMask("", "", "", HTDownloadState.COMPLETE_DOWNLOAD);

    /**
     * name
     */
    private String name;


    public HtMask(String name, String category, String icon, int downloaded) {
      this.name = name;
      this.category = category;
      this.icon = icon;
      this.downloaded = downloaded;
    }

    @Override public String toString() {
      return "HtMask{" +
          "name='" + name + '\'' +
          ", category='" + category + '\'' +
          ", icon='" + icon + '\'' +
          ", downloaded=" + downloaded +
          '}';
    }

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
    private int downloaded;

    public String getUrl() {

      return HTEffect.shareInstance().getARItemUrlBy(HTItemEnum.HTItemMask.getValue()) + name + ".zip";

    }

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public String getCategory() { return category;}

    public void setCategory(String category) { this.category = category;}

    public String getIcon() {
      //todo 等待接口
      return HTEffect.shareInstance().getARItemUrlBy(HTItemEnum.HTItemMask.getValue()) + icon;


    }

    public void setThumb(String icon) { this.icon = icon;}

    public int isDownloaded() { return downloaded;}

    public void setDownloaded(int downloaded) {
      this.downloaded = downloaded;
    }

    /**
     * 下载完成更新缓存数据
     */
    public void downloaded() {
      HtMaskConfig tiMaskConfig = HtConfigTools.getInstance().getMaskList();

      for (HtMask mask : tiMaskConfig.getMasks()) {
        if (this.name.equals(mask.name) && mask.icon.equals(this.icon)) {
          mask.setDownloaded(HTDownloadState.COMPLETE_DOWNLOAD);
        }
      }
      HtConfigTools.getInstance().maskDownload(new Gson().toJson(tiMaskConfig));
    }

  }

}
