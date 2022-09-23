package com.texeljoy.ht_effect.model;

import com.google.gson.Gson;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import com.texeljoy.hteffect.HTEffect;
import java.io.File;
import java.util.List;

/**
 * 水印配置参数
 */
public class HtWatermarkConfig {
  /**
   * watermarks
   */
  private List<HtWatermark> ht_watermark;

  public List<HtWatermark> getWatermarks() { return ht_watermark;}

  public void setWatermarks(List<HtWatermark> htWatermarks) { this.ht_watermark = htWatermarks;}

  public static class HtWatermark {

    public static final HtWatermark NO_WATERMARK =
        new HtWatermark(0, 0, 0, "", "", HTDownloadState.COMPLETE_DOWNLOAD
        );

    public HtWatermark(int x, int y, int ratio, String name, String icon, int download) {
      this.x = x;
      this.y = y;
      this.ratio = ratio;
      this.name = name;
      this.icon = icon;
      this.download = download;
    }

    /**
     * x
     */
    private int x;
    /**
     * y
     */
    private int y;
    /**
     * ratio
     */
    private int ratio;
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

    public int getX() { return x;}

    public void setX(int x) { this.x = x;}

    public int getY() { return y;}

    public void setY(int y) { this.y = y;}

    public int getRatio() { return ratio;}

    public void setRatio(int ratio) { this.ratio = ratio;}

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public String getIcon() {
      return HTEffect.shareInstance().getWatermarkPath() + File.separator + this.name + File.separator + this.name + ".png";
    }

    public void setIcon(String icon) { this.icon = icon;}

    public int isDownloaded() { return download;}

    public void setDownloaded(int download) { this.download = download;}

    public String getUrl() {
      return HTEffect.shareInstance().getWatermarkPath() + File.separator + this.name + File.separator + this.name + ".png";

    }

    /**
     * 下载完更新缓存
     */
    public void downloaded() {
      HtWatermarkConfig watermarkList = HtConfigTools.getInstance().getWatermarkList();

      for (HtWatermark watermark : watermarkList.ht_watermark) {
        if (watermark.name == this.name) {
          watermark.setDownloaded(HTDownloadState.COMPLETE_DOWNLOAD);
        }
      }

      HtConfigTools.getInstance().watermarkDownload(new Gson().toJson(watermarkList));

    }

  }

}
