package com.texeljoy.ht_effect.model;

import com.google.gson.Gson;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import com.texeljoy.hteffect.HTEffect;
import java.util.List;

/**
 * 绿幕配置参数
 */

public class HtGreenScreenConfig {

  /**
   * greenScreens
   */
  private List<HtGreenScreen> ht_gsseg_effect;

  public List<HtGreenScreen> getGreenScreens() { return ht_gsseg_effect;}

  public void setGreenScreens(List<HtGreenScreen> greenScreens) { this.ht_gsseg_effect = greenScreens;}

  public static class HtGreenScreen {

    public static final HtGreenScreen NO_GreenScreen = new HtGreenScreen("", "", HTDownloadState.COMPLETE_DOWNLOAD);

    public HtGreenScreen(String name, String icon, int download) {
      this.name = name;
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

    public String getIcon() {
      return HTEffect.shareInstance().getGSSegEffectUrl() + icon;

    }

    public void setIcon(String icon) { this.icon = icon;}

    public int isDownloaded() { return download;}

    public String getUrl() {

       return HTEffect.shareInstance().getGSSegEffectUrl()+ name +  ".png";

    }

    public void downLoaded() {
      HtGreenScreenConfig greenScreenList = HtConfigTools.getInstance().getGreenScreenList();
      for (HtGreenScreen greenScreen : greenScreenList.ht_gsseg_effect) {
        if (greenScreen.name.equals(this.name) && greenScreen.icon.equals(this.name)) {
          greenScreen.setDownloaded(HTDownloadState.COMPLETE_DOWNLOAD);
        }
      }
      HtConfigTools.getInstance().greenScreenDownload(new Gson().toJson(greenScreenList));
    }

    public void setDownloaded(int download) { this.download = download;}
  }

}
