package com.texeljoy.ht_effect.model;

import com.google.gson.Gson;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import com.texeljoy.hteffect.HTEffect;
import java.util.List;

/**
 * 贴纸列表配置
 */
@SuppressWarnings("unused")
public class HtStickerConfig {

  /**
   * stickers
   */
  private List<HtSticker> ht_sticker;

  @Override public String toString() {
    return "HtStickerConfig{" +
        "htStickers=" + ht_sticker.size() +
        "个}";
  }

  public List<HtSticker> getStickers() {
    return ht_sticker;
  }

  public HtStickerConfig(List<HtSticker> stickers) {
    this.ht_sticker = stickers;
  }

  public void setStickers(List<HtSticker> tiStickers) { this.ht_sticker = tiStickers;}

  public static class HtSticker {

    public static final HtSticker NO_STICKER = new HtSticker("", "", "", HTDownloadState.COMPLETE_DOWNLOAD);

    /**
     * name
     */
    private String name;


    public HtSticker(String name, String category, String icon, int downloaded) {
      this.name = name;
      this.category = category;
      this.icon = icon;
      this.downloaded = downloaded;
    }

    @Override public String toString() {
      return "HtSticker{" +
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

      return HTEffect.shareInstance().getStickerUrl() + name + ".zip";

    }

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public String getCategory() { return category;}

    public void setCategory(String category) { this.category = category;}

    public String getIcon() {
      return HTEffect.shareInstance().getStickerUrl() + icon;


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
      HtStickerConfig tiStickerConfig = HtConfigTools.getInstance().getStickerConfig();

      for (HtSticker sticker : tiStickerConfig.getStickers()) {
        if (this.name.equals(sticker.name) && sticker.icon.equals(this.icon)) {
          sticker.setDownloaded(HTDownloadState.COMPLETE_DOWNLOAD);
        }
      }
      HtConfigTools.getInstance().stickerDownload(new Gson().toJson(tiStickerConfig));
    }

  }

}
