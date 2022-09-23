package com.texeljoy.ht_effect.model;


import com.google.gson.Gson;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import java.util.List;

/**
 * 轻彩妆配置
 */

public class HtMakeupConfig {

  /**
   * makeups
   */
  private List<HtMakeup> makeups;

  public List<HtMakeup> getMakeups() { return makeups;}

  public void setMakeups(List<HtMakeup> makeups) { this.makeups = makeups;}

  public static class HtMakeup {

    public static final HtMakeup NO_MAKEUP = new HtMakeup("", "", "", false);

    public HtMakeup(String name, String thumb, String type, boolean downloaded) {
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
      HtMakeupConfig makeupList = HtConfigTools.getInstance().getMakeupList();
      for (HtMakeup makeup : makeupList.getMakeups()) {
        if (makeup.name.equals(this.name) && makeup.type.equals(this.type)) {
          makeup.setDownloaded(true);
        }
      }
      HtConfigTools.getInstance().makeupDownLoad(new Gson().toJson(makeupList));
    }

    public void setType(String type) { this.type = type;}

    public boolean isDownloaded() { return downloaded;}

    public void setDownloaded(boolean downloaded) { this.downloaded = downloaded;}
  }

}
