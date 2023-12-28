package com.texeljoy.ht_effect.model;

import com.google.gson.Gson;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import java.util.List;

/**
 * 3d配置参数
 */

@SuppressWarnings("unused")
public class HtThreedConfig {
  /**
   * portraits
   */
  private List<HtThreed> ht_3d_effect;

  public List<HtThreed> getThreeds() { return ht_3d_effect;}

  public void setThreeds(List<HtThreed> threeds) { this.ht_3d_effect = threeds;}

  public static class HtThreed {

    public static final HtThreed NO_Threed = new HtThreed("", "", "", HTDownloadState.COMPLETE_DOWNLOAD);

    public HtThreed(String name, String category, String icon, int download) {
      this.name = name;
      this.category = category;
      this.icon = icon;
      this.download = download;
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
    private int download;

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public String getCategory() { return category;}

    public void setCategory(String category) { this.category = category;}

    public String getIcon() {
      // return HTEffectAR.shareInstance().getResourcePath()  + "/hteffect/3d_effect" + "/ht_3d_effect_icon/" + this.icon;
      return "";
    }

    public void setIcon(String icon) { this.icon = icon;}

    public int isDownloaded() { return download;}

    public String getUrl() {
      return null;

    }

    public void downloaded() {
      HtThreedConfig threedList = HtConfigTools.getInstance().getThreedList();

      for (HtThreed threed : threedList.getThreeds()) {
        if (threed.name.equals(this.name) && threed.icon.equals(this.icon)) {
          threed.setDownloaded(HTDownloadState.COMPLETE_DOWNLOAD);
        }
      }

      HtConfigTools.getInstance().threedDownload(new Gson().toJson(threedList));

    }

    public void setDownloaded(int download) {
      this.download = download;
    }
  }
}
