package com.texeljoy.ht_effect.model;

import com.google.gson.Gson;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import com.texeljoy.hteffect.HTEffect;
import java.util.List;

/**
 * 手势配置参数
 */
@SuppressWarnings("unused")
public class HtGestureConfig {
  /**
   * gestures
   */
  private List<HtGesture> ht_gesture_effect;

  public List<HtGesture> getGestures() { return ht_gesture_effect;}

  public void setGestures(List<HtGesture> gestures) { this.ht_gesture_effect = gestures;}

  public static class HtGesture {

    public static final HtGesture NO_Gesture =
        new HtGesture("",  "", "", HTDownloadState.COMPLETE_DOWNLOAD);

    public HtGesture(String name, String category, String icon, int download) {
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

      return HTEffect.shareInstance().getGestureEffectUrl() + icon;

    }

    public void setThumb(String icon) { this.icon = icon;}

    public int isDownload() { return download;}

    public String getUrl() {
      return HTEffect.shareInstance().getGestureEffectUrl() + this.name + ".zip";

    }

    public void setDownload(int download) {
      this.download = download;
    }

    /**
     * 下载完成更新对应的缓存
     */
    public void downloaded() {
      HtGestureConfig gestureList = HtConfigTools.getInstance().getGestureList();
      for (HtGesture gesture : gestureList.ht_gesture_effect) {
        if (gesture.name.equals(this.name) && gesture.icon.equals(this.icon)) {
          gesture.setDownload(HTDownloadState.COMPLETE_DOWNLOAD);
        }
      }
      HtConfigTools.getInstance().gestureDownload(new Gson().toJson(gestureList));
    }

  }
}
