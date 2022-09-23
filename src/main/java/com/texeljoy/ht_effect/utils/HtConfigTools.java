package com.texeljoy.ht_effect.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.texeljoy.ht_effect.model.HtAISegmentationConfig;
import com.texeljoy.ht_effect.model.HtFilterConfig;
import com.texeljoy.ht_effect.model.HtGestureConfig;
import com.texeljoy.ht_effect.model.HtGreenScreenConfig;
import com.texeljoy.ht_effect.model.HtGreenScreenConfig.HtGreenScreen;
import com.texeljoy.ht_effect.model.HtMakeupConfig;
import com.texeljoy.ht_effect.model.HtStickerConfig;
import com.texeljoy.ht_effect.model.HtWatermarkConfig;
import com.texeljoy.ht_effect.model.HtWatermarkConfig.HtWatermark;
import com.texeljoy.hteffect.HTEffect;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName TiConfigTools
 * @Description 配置文件管理
 * @Author Spica2 7
 * @Date 2021/7/23 15:31
 */
@SuppressWarnings("unused")
public class HtConfigTools {

  private Context context;

  //贴纸配置的文件路径
  private String PATH_STICKER;
  //面具配置的文件路径
  private String PATH_MASK;
  //AI抠图配置文件
  private String PATH_AISEGMENTATION;
  //绿幕配置文件
  private String PATH_GREEN_SCREEN;
  //水印配置文件
  private String PATH_WATER_MARK;
  //手势配置文件
  private String PATH_GESTURE;
  //美妆配置文件
  private String PATH_MAKEUP;
  //滤镜配置文件
  private String PATH_FILTER;


  private HtStickerConfig stickerList;
  private HtAISegmentationConfig segmentationList;
  private HtGreenScreenConfig greenScreenList;
  private HtWatermarkConfig watermarkList;
  private HtGestureConfig gestureList;
  private HtMakeupConfig makeupList;
  private HtFilterConfig filterList;

  private final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

  final Handler uiHandler = new Handler(Looper.getMainLooper());

  @SuppressLint("StaticFieldLeak")
  private static HtConfigTools instance;

  public void initHtConfigTools(Context context) {
    this.context = context;
    instance = this;

    //贴纸配置的文件路径
    PATH_STICKER = HTEffect.shareInstance().getStickerPath() + File.separator + "ht_sticker_config.json";
    //AI抠图配置文件
    PATH_AISEGMENTATION = HTEffect.shareInstance().getAISegEffectPath() + File.separator + "ht_aiseg_effect_config.json";
    //绿幕配置文件
    PATH_GREEN_SCREEN = HTEffect.shareInstance().getGSSegEffectPath() + File.separator + "ht_gsseg_effect_config.json";
    //水印配置文件
    PATH_WATER_MARK = HTEffect.shareInstance().getWatermarkPath() + File.separator + "ht_watermark_config.json";
    //手势配置文件
    PATH_GESTURE = HTEffect.shareInstance().getGestureEffectPath() + File.separator + "ht_gesture_effect_config.json";
    //美妆配置文件
    //PATH_MAKEUP = HTEffect.shareInstance().getMakeupPath() + File.separator + "ht_makeupconfig.json";
    //滤镜配置文件
    PATH_FILTER = HTEffect.shareInstance().getFilterPath() + File.separator + "ht_filter_config.json";

  }

  public HtMakeupConfig getMakeupList() {
    return makeupList;
  }

  public HtAISegmentationConfig getAISegmentationList() {
    if (segmentationList == null) return null;
    return segmentationList;
  }

  public HtGreenScreenConfig getGreenScreenList() {
    if (greenScreenList == null) return null;
    return greenScreenList;
  }

  public HtWatermarkConfig getWatermarkList() {
    if (watermarkList == null) return null;
    return watermarkList;
  }

  public HtGestureConfig getGestureList() {
    if (gestureList == null) return null;
    return gestureList;
  }

  public static HtConfigTools getInstance() {
    if (instance == null) instance = new HtConfigTools();
    return instance;
  }

  /**
   * 更新mask文件
   *
   * @param content json 内容
   */
  public void maskDownload(final String content) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        modifyFile(content, PATH_MASK);
      }
    });
  }


  public HtStickerConfig getStickerConfig() {
    if (stickerList == null) return null;
    return stickerList;
  }

  public HtFilterConfig getFilterConfig() {
    if (filterList == null) return null;
    return filterList;
  }

  /**
   * 获取缓存文件中贴纸配置
   */
  public void getStickersConfig(HtConfigCallBack<List<HtStickerConfig.HtSticker>> callBack) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        try {
          String res = getFileString(PATH_STICKER);
          if (TextUtils.isEmpty(res)) {
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(new ArrayList<>());
              }
            });
          } else {
            stickerList = new Gson().fromJson(res, new TypeToken<HtStickerConfig>() {}.getType());
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(stickerList.getStickers());
              }
            });
          }

        } catch (Exception e) {
          uiHandler.post(new Runnable() {
            @Override public void run() {
              callBack.fail(e);
            }
          });
        }
      }
    });
  }

  /**
   * 更新贴纸文件
   */
  public void stickerDownload(String content) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        modifyFile(content, PATH_STICKER);
      }
    });
  }

  /**
   * 获取缓存文件中滤镜配置
   */
  public void getFiltersConfig(HtConfigCallBack<List<HtFilterConfig.HtFilter>> callBack) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        try {
          String res = getFileString(PATH_FILTER);
          if (TextUtils.isEmpty(res)) {
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(new ArrayList<>());
              }
            });
          } else {
            filterList = new Gson().fromJson(res, new TypeToken<HtFilterConfig>() {}.getType());
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(filterList.getFilters());
              }
            });
          }

        } catch (Exception e) {
          uiHandler.post(new Runnable() {
            @Override public void run() {
              callBack.fail(e);
            }
          });
        }
      }
    });
  }

  /**
   * 更新滤镜文件
   */
  public void filterDownload(String content) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        modifyFile(content, PATH_FILTER);
      }
    });
  }

  /**
   * 从缓存文件中获取绿幕配置文件
   */
  public void getGreenScreenConfig(HtConfigCallBack<List<HtGreenScreen>> callBack) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {

        try {
          String result = getFileString(PATH_GREEN_SCREEN);

          if (TextUtils.isEmpty(result)) {
            Log.i("读取绿幕配置文件：", "内容为空");
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(new ArrayList<>());
              }
            });

          } else {
            greenScreenList = new Gson().fromJson(result, HtGreenScreenConfig.class);
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(greenScreenList.getGreenScreens());
              }
            });
          }

        } catch (IOException e) {
          e.printStackTrace();
          callBack.fail(e);
        }


      }
    });
  }

  /**
   * 更新绿幕缓存文件
   */
  public void greenScreenDownload(String content) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        modifyFile(content, PATH_GREEN_SCREEN);
      }
    });
  }

  /**
   * 从缓存文件中获取AI抠图配置文件
   */
  public void getAISegmentationConfig(HtConfigCallBack<List<HtAISegmentationConfig.HtAISegmentation>> callBack) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        try {

          String result = getFileString(PATH_AISEGMENTATION);

          if (TextUtils.isEmpty(result)) {
            Log.i("读取抠图配置文件：", "内容为空");
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(new ArrayList<>());
              }
            });

          } else {
            segmentationList = new Gson().fromJson(result, new TypeToken<HtAISegmentationConfig>() {}.getType());
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(segmentationList.getSegmentations());
              }
            });
          }

        } catch (IOException e) {
          e.printStackTrace();
          callBack.fail(e);
        }
      }
    });
  }

  /**
   * 更新AI抠图缓存文件
   */
  public void segmentationDownload(String content) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        modifyFile(content, PATH_AISEGMENTATION);
      }
    });
  }

  /**
   * 从缓存文件中获取水印配置文件
   */
  public void getWaterMarkConfig(HtConfigCallBack<List<HtWatermark>> callBack) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        try {
          String result = getFileString(PATH_WATER_MARK);

          if (TextUtils.isEmpty(result)) {
            Log.i("读取绿幕配置文件：", "内容为空");
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(new ArrayList<>());
              }
            });

          } else {
            watermarkList = new Gson().fromJson(result, new TypeToken<HtWatermarkConfig>() {}.getType());
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(watermarkList.getWatermarks());
              }
            });
          }

        } catch (IOException e) {
          e.printStackTrace();
          callBack.fail(e);
        }
      }
    });
  }

  /**
   * 更新水印缓存文件
   */
  public void watermarkDownload(String content) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        modifyFile(content, PATH_WATER_MARK);
      }
    });
  }

  /**
   * 从缓存文件中获取手势配置文件
   */
  public void getGestureConfig(HtConfigCallBack<List<HtGestureConfig.HtGesture>> callBack) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        try {
          String result = getFileString(PATH_GESTURE);

          if (TextUtils.isEmpty(result)) {
            Log.i("读取手势配置文件：", "内容为空");
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(new ArrayList<>());
              }
            });

          } else {
            gestureList = new Gson().fromJson(result, new TypeToken<HtGestureConfig>() {}.getType());
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(gestureList.getGestures());
              }
            });
          }

        } catch (IOException e) {
          e.printStackTrace();
          callBack.fail(e);
        }
      }
    });
  }
  /**
   * 更新手势缓存文件
   */
  public void gestureDownload(String content) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        modifyFile(content, PATH_GESTURE);
      }
    });
  }

  /**
   * 获取特定的Type的美妆配置文件
   */
  public List<HtMakeupConfig.HtMakeup> getMakeupsWithType(String type) {
    ArrayList<HtMakeupConfig.HtMakeup> items = new ArrayList<>();
    if (makeupList == null) {
      try {
        String res = getJsonString(context, "makeup/makeups.json");
        makeupList = new Gson().fromJson(res, new TypeToken<HtMakeupConfig>() {}.getType());
      } catch (IOException e) {
        e.printStackTrace();
        return null;
      }
    }
    for (HtMakeupConfig.HtMakeup makeup : makeupList.getMakeups()) {
      if (makeup.getType().equals(type)) {
        items.add(makeup);
      }
    }
    return items;
  }

  /**
   * 美妆的配置文件改写
   */
  public void makeupDownLoad(String content) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        modifyFile(content, PATH_MAKEUP);
      }
    });
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

  public void release() {
    this.context = null;
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
   * 重置配置文件
   */
  public void resetConfigFile() {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        try {
          String newSticker = getJsonString(context, "sticker/ht_sticker_config.json");
          modifyFile(newSticker, PATH_STICKER);
        } catch (IOException e) {
          e.printStackTrace();
        }

        String newMask;
        try {
          newMask = getJsonString(context, "mask/masks.json");
          modifyFile(newMask, PATH_MASK);
        } catch (IOException e) {
          e.printStackTrace();
        }

        String newWatermark;
        try {
          newWatermark = getJsonString(context, "watermark/watermarks.json");
          modifyFile(newWatermark, PATH_WATER_MARK);
        } catch (IOException e) {
          e.printStackTrace();
        }

        String newAISegmentation;
        try {
          newAISegmentation = getJsonString(context, "aisegmentation/aisegmentations.json");
          modifyFile(newAISegmentation, PATH_AISEGMENTATION);
        } catch (IOException e) {
          e.printStackTrace();
        }

        String newGreenScreen;
        try {
          newGreenScreen = getJsonString(context, "greenscreen/greenscreens.json");
          modifyFile(newGreenScreen, PATH_GREEN_SCREEN);
        } catch (IOException e) {
          e.printStackTrace();
        }

        try {
          String newInteractions = getJsonString(context, "gesture/gestures.json");
          modifyFile(newInteractions, PATH_GESTURE);
        } catch (IOException e) {
          e.printStackTrace();
        }

        try {
          String newMakeups = getJsonString(context, "makeup/makeups.json");
          modifyFile(newMakeups, PATH_MAKEUP);
          makeupList = new Gson().fromJson(newMakeups, new TypeToken<HtMakeupConfig>() {}.getType());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
  }

}

