package com.texeljoy.ht_effect.model;

import com.google.gson.Gson;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import com.texeljoy.hteffect.HTEffect;
import java.util.List;

/**
 * 人像抠图配置参数
 */

@SuppressWarnings("unused")
public class HtAISegmentationConfig {
  /**
   * portraits
   */
  private List<HtAISegmentation> ht_aiseg_effect;

  public List<HtAISegmentation> getSegmentations() { return ht_aiseg_effect;}

  public void setPortraits(List<HtAISegmentation> segmentations) { this.ht_aiseg_effect = segmentations;}

  public static class HtAISegmentation {

    public static final HtAISegmentation NO_Portrait = new HtAISegmentation("", "", "", HTDownloadState.COMPLETE_DOWNLOAD);

    public HtAISegmentation(String name, String category, String icon, int downloaded) {
      this.name = name;
      this.category = category;
      this.icon = icon;
      this.downloaded = downloaded;
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
     * thumb
     */
    private String icon;
    /**
     * downloaded
     */
    private int downloaded;

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public String getCategory() { return category;}

    public void setCategory(String category) { this.category = category;}

    public String getIcon() {
      return HTEffect.shareInstance().getAISegEffectUrl() + icon;
    }

    public void setIcon(String icon) { this.icon = icon;}

    public int isDownloaded() { return downloaded;}

    public String getUrl() {
      return HTEffect.shareInstance().getAISegEffectUrl() + this.name + ".zip";

    }

    public void downloaded() {
      HtAISegmentationConfig segmentationList = HtConfigTools.getInstance().getAISegmentationList();

      for (HtAISegmentation portrait : segmentationList.getSegmentations()) {
        if (portrait.name.equals(this.name) && portrait.icon.equals(this.icon)) {
          portrait.setDownloaded(HTDownloadState.COMPLETE_DOWNLOAD);
        }
      }

      HtConfigTools.getInstance().segmentationDownload(new Gson().toJson(segmentationList));

    }

    public void setDownloaded(int downloaded) {
      this.downloaded = downloaded;
    }
  }
}
