package com.texeljoy.ht_effect.model;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.texeljoy.ht_effect.utils.FileUtils;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import com.texeljoy.hteffect.HTEffect;
import com.texeljoy.hteffect.model.HTItemEnum;
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
        new HtWatermark("", "", HTDownloadState.COMPLETE_DOWNLOAD,""
        );

    public HtWatermark(String name, String icon, int download, String dir) {

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
     * icon
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
      return HTEffect.shareInstance().getARItemPathBy(HTItemEnum.HTItemWatermark.getValue()) + File.separator + this.name + File.separator + this.name + ".png";
    }

    public void setIcon(String icon) { this.icon = icon;}

    public int isDownloaded() { return download;}

    public void setDownloaded(int download) { this.download = download;}

    public String getUrl() {
      return HTEffect.shareInstance().getARItemPathBy(HTItemEnum.HTItemWatermark.getValue()) + File.separator + this.name + File.separator + this.name + ".png";

    }

    public String getDir() {
      return dir;
    }


    public void setDir(String dir) {
      this.dir = dir;
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

    public void delete(int position) {
      HtWatermarkConfig watermarkList = HtConfigTools.getInstance().getWatermarkList();
      watermarkList.getWatermarks().remove(position);
      HtConfigTools.getInstance().watermarkDownload(new Gson().toJson(watermarkList));
    }
    private boolean isFromDisk = false;
    public boolean isFromDisk() {
      return isFromDisk;
    }

    public void setFromDisk(boolean fromDisk, Context context, final String sourcePath) {
      isFromDisk = fromDisk;
      if (isFromDisk) {
        HtWatermarkConfig watermarkList = HtConfigTools.getInstance().getWatermarkList();
        if (watermarkList != null) {
          Log.i("添加图片：", sourcePath);
          //根据地址读取源文件
          File sourceFile = new File(sourcePath);
          //获取最后一个.的位置
          int lastIndexOf = sourcePath.lastIndexOf(".");
          //获取文件的后缀名 .jpg
          String suffix = sourcePath.substring(lastIndexOf);
          //拼接出新的文件名
          String newFileName = (System.currentTimeMillis() / 1000) + "";
          //目标位置和文件
          File targetFile = new File(HTEffect.shareInstance().getARItemPathBy(HTItemEnum.HTItemWatermark.getValue())+"/watermark_icon", newFileName + suffix);
          File targetDirectory = new File(HTEffect.shareInstance().getARItemPathBy(HTItemEnum.HTItemWatermark.getValue()) + "/"+newFileName + "/"+newFileName + suffix);
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
          //更新配置文件的名称
          this.name = newFileName;

          //持久化
          watermarkList.getWatermarks().add(this);
          HtConfigTools.getInstance().watermarkDownload(new Gson().toJson(watermarkList));
        }
      }
    }

  }

}
