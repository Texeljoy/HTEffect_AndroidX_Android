package com.texeljoy.ht_effect.model;

import com.google.gson.Gson;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import com.texeljoy.hteffect.HTEffect;
import com.texeljoy.hteffect.model.HTItemEnum;
import java.util.List;

/**
 * 水印配置参数
 */
public class HtGiftConfig {
  /**
   * watermarks
   */
  private List<HtGift> ht_gift;

  public List<HtGift> getGifts() { return ht_gift;}

  public void setGifts(List<HtGift> htGifts) { this.ht_gift = htGifts;}

  public static class HtGift {

    public static final HtGift NO_Gift =
        new HtGift("", "", HTDownloadState.COMPLETE_DOWNLOAD
        );
    public static final HtGift NEW_Gift =
        new HtGift("", "", HTDownloadState.COMPLETE_DOWNLOAD
        );

    public HtGift(String name, String icon, int download) {

      this.name = name;
      this.icon = icon;
      this.download = download;
    }

    /**
     * name
     */
    private String name;
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

    public String getIcon() {
      return HTEffect.shareInstance().getARItemUrlBy(HTItemEnum.HTItemGift.getValue()) + icon;
    }

    public void setIcon(String icon) { this.icon = icon;}

    public int isDownloaded() { return download;}

    public void setDownloaded(int download) { this.download = download;}

    public String getUrl() {
      return HTEffect.shareInstance().getARItemUrlBy(HTItemEnum.HTItemGift.getValue()) + name + ".zip";

    }

    /**
     * 下载完更新缓存
     */
    public void downloaded() {
      HtGiftConfig giftList = HtConfigTools.getInstance().getGiftList();

      for (HtGift gift : giftList.ht_gift) {
        if (gift.name == this.name) {
          gift.setDownloaded(HTDownloadState.COMPLETE_DOWNLOAD);
        }
      }

      HtConfigTools.getInstance().giftDownload(new Gson().toJson(giftList));

    }

  }

}
