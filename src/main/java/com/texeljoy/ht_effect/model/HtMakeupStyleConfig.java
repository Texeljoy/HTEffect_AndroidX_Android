package com.texeljoy.ht_effect.model;


import com.google.gson.Gson;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import java.util.List;

/**
 * 妆容推荐配置
 */

public class HtMakeupStyleConfig {

  /**
   * makeups
   */
  private List<HtMakeupStyle> makeups;

  public List<HtMakeupStyle> getMakeups() { return makeups;}

  public void setMakeups(List<HtMakeupStyle> makeups) { this.makeups = makeups;}

  public static class HtMakeupStyle {

    public static final HtMakeupStyle NO_MAKEUP = new HtMakeupStyle("", "", "", false);

    public HtMakeupStyle(String name, String thumb, String type, boolean downloaded) {
      this.name = name;
      this.thumb = thumb;
      this.type = type;
      this.downloaded = downloaded;
    }

    /**
     * name
     */
    private String name;
    /**
     * thumb
     */
    private String thumb;
    /**
     * type
     */
    private String type;
    /**
     * downloaded
     */
    private boolean downloaded;

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    // public String getThumb() {
    //   return HTEffect.shareInstance().getMakeupUrl() + thumb;
    // }

    public void setThumb(String thumb) { this.thumb = thumb;}

    public String getType() { return type;}

    // public String getUrl() {
    //   return HTEffect.shareInstance().getMakeupUrl() + this.name + ".png";
    // }

    /**
     * 下载完成设置缓存文件
     */
    public void downloaded() {
      HtMakeupStyleConfig makeupstyleList = HtConfigTools.getInstance().getMakeupStyleList();
      for (HtMakeupStyle makeup : makeupstyleList.getMakeups()) {
        if (makeup.name.equals(this.name) && makeup.type.equals(this.type)) {
          makeup.setDownloaded(true);
        }
      }
      HtConfigTools.getInstance().makeupStyleDownLoad(new Gson().toJson(makeupstyleList));
    }

    public void setType(String type) { this.type = type;}

    public boolean isDownloaded() { return downloaded;}

    public void setDownloaded(boolean downloaded) { this.downloaded = downloaded;}
  }

}
