package com.texeljoy.ht_effect.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import androidx.annotation.NonNull;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 配置持久化工具类
 */
@SuppressWarnings("unused")
public class HtConfigUtils {

  //妆容推荐
  private static String PATH_MAKE_UP;

  //滤镜
  private static String PATH_FILTER;

  //风格推荐
  private static String PTAH_STYLE;

  private final static HandlerThread jsonWriterThread = new HandlerThread("jsonWriterThread ");

  //用于写入文件
  private final static Handler writerHandler = new Handler(jsonWriterThread.getLooper());

  //主线程handler
  private final Handler uiHandler = new Handler(Looper.getMainLooper());

  public static void init(@NonNull Context context) {
    jsonWriterThread.start();

    //todo 这个地方原本是maskpath
    // PATH_FILTER = HTEffect.shareInstance().getStickerPath() + File.separator + "filter.json";

    // PATH_MAKE_UP = HTEffect.shareInstance().getStickerPath() + File.separator + "make_up.json";

    // PTAH_STYLE = HTEffect.shareInstance().getStickerPath() + File.separator + "style.json";

  }

  public interface ConfigCallBack<T> {

    void success(T list);

    void fail(Exception error);

  }

  /**
   * async
   * 获取风格的配置
   */
  public void getStyleConfig() {

  }

  /**
   * async
   * 获取滤镜
   */
  public void getFilterConfig() {

  }

  /**
   * async
   * 获取妆容推荐的配置
   */
  public void getMakeupConfig() {

  }

  /**
   * 重置缓存文件
   */
  public static void resetConfigFiles() {

  }

  /**
   * 获取指定目录下的字符内容
   *
   * @param filePath 路径
   * @return 字符内容
   */
  private String getFileString(String filePath) throws IOException {
    BufferedReader br = null;
    StringBuilder sb = new StringBuilder();
    try {
      File file = new File(filePath);
      FileInputStream fis = new FileInputStream(file);
      br = new BufferedReader(new InputStreamReader(fis));
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }
      //            return sb.toString();
    } catch (IOException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return sb.toString();
  }

  /**
   * 读取assets下配置文件
   *
   * @param context 上下文
   * @param fileName 文件名
   * @return 内容
   */
  private String getJsonString(Context context, String fileName)
      throws IOException {
    BufferedReader br = null;
    StringBuilder sb = new StringBuilder();
    try {
      AssetManager manager = context.getAssets();
      br = new BufferedReader(new InputStreamReader(manager.open(fileName)));
      String line;
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return sb.toString();
  }

  /**
   * 写入文件
   *
   * @param content 内容
   * @param filePath 地址
   */
  private void modifyFile(String content, String filePath) {
    try {
      FileWriter fileWriter = new FileWriter(filePath, false);
      BufferedWriter writer = new BufferedWriter(fileWriter);
      writer.append(content);
      writer.flush();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 回收资源
   */
  public static void quit() {
    jsonWriterThread.quitSafely();
  }

}
