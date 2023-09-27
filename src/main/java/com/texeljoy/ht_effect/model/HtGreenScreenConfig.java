package com.texeljoy.ht_effect.model;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.texeljoy.ht_effect.utils.FileUtils;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import com.texeljoy.hteffect.HTEffect;
import java.io.File;
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

    public static final HtGreenScreen NO_GreenScreen = new HtGreenScreen("", "", HTDownloadState.COMPLETE_DOWNLOAD, "");

    public HtGreenScreen(String name, String icon, int download, String dir) {
      this.name = name;
      this.icon = icon;
      this.download = download;
      this.dir = dir;
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
    private String dir;

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public String getIcon() {
      return HTEffect.shareInstance().getChromaKeyingUrl() + icon;

    }

    public void setIcon(String icon) { this.icon = icon;}

    public int isDownloaded() { return download;}

    public String getUrl() {
       return HTEffect.shareInstance().getChromaKeyingUrl()+ name +  ".zip";

    }public String getDir() {
      return dir;
    }


    public void setDir(String dir) {
      this.dir = dir;
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

    public void delete(int position) {
      HtGreenScreenConfig greenscreenList = HtConfigTools.getInstance().getGreenScreenList();
      greenscreenList.getGreenScreens().remove(position);
      HtConfigTools.getInstance().greenScreenDownload(new Gson().toJson(greenscreenList));
    }
    private boolean isFromDisk = false;
    public boolean isFromDisk() {
      return isFromDisk;
    }

    public void setFromDisk(boolean fromDisk, Context context, final String sourcePath) {
      isFromDisk = fromDisk;
      if (isFromDisk) {
        HtGreenScreenConfig greenscreenList = HtConfigTools.getInstance().getGreenScreenList();
        if (greenscreenList != null) {
          Log.i("添加图片：", sourcePath);
          //根据地址读取源文件
          File sourceFile = new File(sourcePath);
          File sourceJsonFile = new File(HTEffect.shareInstance().getChromaKeyingPath()+"/config.json");
          //获取最后一个.的位置
          int lastIndexOf = sourcePath.lastIndexOf(".");
          //获取文件的后缀名 .jpg
          String suffix = sourcePath.substring(lastIndexOf);
          //拼接出新的文件名
          String newFileName = (System.currentTimeMillis() / 1000) + "";
          //目标位置和文件
          File targetFile = new File(HTEffect.shareInstance().getChromaKeyingPath()+"/gsseg_icon", newFileName + suffix);
          File targetDirectory = new File( HTEffect.shareInstance().getChromaKeyingPath()+ "/"+newFileName + "/P_bg/"+newFileName + suffix);
          File targetJsonDirectory = new File(HTEffect.shareInstance().getChromaKeyingPath() + "/"+newFileName + "/config.json");
          //文件复制

          if (FileUtils.copyFile(sourceFile, targetFile)) {
            Log.i("复制绿幕背景文件：", "成功");
            this.dir = targetFile.getAbsolutePath();
          } else {
            Log.e("复制绿幕背景文件：", "失败");
            return;
          }
          if (FileUtils.copyFile(sourceFile, targetDirectory)) {
            Log.i("复制绿幕背景文件：", "成功");
          } else {
            Log.e("复制绿幕背景文件：", "失败");
            return;
          }
          if (FileUtils.copyFile(sourceJsonFile, targetJsonDirectory)) {
            Log.i("复制绿幕背景json文件：", "成功");
          } else {
            Log.e("复制绿幕背景json文件：", "失败");
            return;
          }
          //更新配置文件的名称
          this.name = newFileName;

          //持久化
          greenscreenList.getGreenScreens().add(this);
          HtConfigTools.getInstance().greenScreenDownload(new Gson().toJson(greenscreenList));
        }
      }
    }
  }

}
