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
import com.texeljoy.ht_effect.model.HtBlushConfig;
import com.texeljoy.ht_effect.model.HtEffectFilterConfig;
import com.texeljoy.ht_effect.model.HtEyebrowConfig;
import com.texeljoy.ht_effect.model.HtEyelashConfig;
import com.texeljoy.ht_effect.model.HtEyelineConfig;
import com.texeljoy.ht_effect.model.HtEyeshadowConfig;
import com.texeljoy.ht_effect.model.HtGestureConfig;
import com.texeljoy.ht_effect.model.HtGiftConfig;
import com.texeljoy.ht_effect.model.HtGreenScreenConfig;
import com.texeljoy.ht_effect.model.HtGreenScreenConfig.HtGreenScreen;
import com.texeljoy.ht_effect.model.HtHaHaFilterConfig;
import com.texeljoy.ht_effect.model.HtHairConfig;
import com.texeljoy.ht_effect.model.HtLipstickConfig;
import com.texeljoy.ht_effect.model.HtMakeup;
import com.texeljoy.ht_effect.model.HtMakeupConfig;
import com.texeljoy.ht_effect.model.HtMakeupStyleConfig;
import com.texeljoy.ht_effect.model.HtMaskConfig;
import com.texeljoy.ht_effect.model.HtPupilsConfig;
import com.texeljoy.ht_effect.model.HtStickerConfig;
import com.texeljoy.ht_effect.model.HtStyleFilterConfig;
import com.texeljoy.ht_effect.model.HtThreedConfig;
import com.texeljoy.ht_effect.model.HtWatermarkConfig;
import com.texeljoy.ht_effect.model.HtWatermarkConfig.HtWatermark;
import com.texeljoy.hteffect.HTEffect;
import com.texeljoy.hteffect.model.HTItemEnum;
import com.texeljoy.hteffect.model.HTMakeupEnum;
import com.texeljoy.hteffectar.HTEffectAR;
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

  private int makeupType;

  //贴纸配置的文件路径
  private String PATH_STICKER;
  //面具配置的文件路径
  private String PATH_MASK;
  //礼物配置的文件路径
  private String PATH_GIFT;
  //AI抠图配置文件
  private String PATH_AISEGMENTATION;
  //3d配置文件
  private String PATH_THREED;
  //绿幕配置文件
  private String PATH_GREEN_SCREEN;
  //水印配置文件
  private String PATH_WATER_MARK;
  //手势配置文件
  private String PATH_GESTURE;
  //妆容推荐配置文件
  private String PATH_MAKEUPSTYLE;
  //风格滤镜配置文件
  private String PATH_STYLE_FILTER;
  //特效滤镜配置文件
  private String PATH_EFFECT_FILTER;
  //哈哈镜配置文件
  private String PATH_HAHA_FILTER;
  //美发配置文件
  private String PATH_HAIR;
  //美妆配置文件
  private String PATH_MAKEUP;
  //口红配置文件
  private String PATH_LIPSTICK;
  //眉毛配置文件
  private String PATH_EYEBROW;
  //腮红配置文件
  private String PATH_BLUSH;
  //眼影配置文件
  private String PATH_EYESHADOW;
  //眼线配置文件
  private String PATH_EYELINE;
  //睫毛配置文件
  private String PATH_EYELASH;
  //美瞳配置文件
  private String PATH_PUPILS;


  private HtStickerConfig stickerList;
  private HtMaskConfig maskList;
  private HtGiftConfig giftList;
  private HtAISegmentationConfig segmentationList;
  private HtThreedConfig threedList;
  private HtGreenScreenConfig greenScreenList;
  private HtWatermarkConfig watermarkList;
  private HtGestureConfig gestureList;
  private HtMakeupStyleConfig makeupStyleList;
  private HtStyleFilterConfig styleFilterList;
  private HtEffectFilterConfig effectFilterList;
  private HtHaHaFilterConfig hahaFilterList;
  private HtMakeupConfig makeupList;
  private HtLipstickConfig lipstickList;
  private HtEyebrowConfig eyebrowList;
  private HtBlushConfig blushList;
  private HtEyeshadowConfig eyeshadowList;
  private HtEyelineConfig eyelineList;
  private HtEyelashConfig eyelashList;
  private HtPupilsConfig pupilsList;
  private HtHairConfig hairList;


  private final ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

  final Handler uiHandler = new Handler(Looper.getMainLooper());

  @SuppressLint("StaticFieldLeak")
  private static HtConfigTools instance;

  public void initHtConfigTools(Context context) {
    this.context = context;
    instance = this;

    //贴纸配置的文件路径
    PATH_STICKER = HTEffect.shareInstance().getARItemPathBy(HTItemEnum.HTItemSticker.getValue()) + File.separator + "ht_sticker_config.json";
    //面具配置的文件路径
    PATH_MASK = HTEffect.shareInstance().getARItemPathBy(HTItemEnum.HTItemMask.getValue()) + File.separator + "ht_mask_config.json";
    //礼物配置的文件路径
    PATH_GIFT = HTEffect.shareInstance().getARItemPathBy(HTItemEnum.HTItemGift.getValue()) + File.separator + "ht_gift_config.json";
    //AI抠图配置文件
    PATH_AISEGMENTATION = HTEffect.shareInstance().getAISegEffectPath() + File.separator + "ht_aiseg_effect_config.json";
    //3D配置文件
    //todo 等待接口
    // PATH_THREED =  HTEffectAR.shareInstance().getResourcePath() + File.separator + "hteffect/3d_effect/ht_3d_config.json";
    //绿幕配置文件
    PATH_GREEN_SCREEN = HTEffect.shareInstance().getChromaKeyingPath() + File.separator + "ht_gsseg_effect_config.json";
    //水印配置文件
    PATH_WATER_MARK = HTEffect.shareInstance().getARItemPathBy(HTItemEnum.HTItemWatermark.getValue()) + File.separator + "ht_watermark_config.json";
    //手势配置文件
    PATH_GESTURE = HTEffect.shareInstance().getGestureEffectPath() + File.separator + "ht_gesture_effect_config.json";
    //美妆配置文件
    //PATH_MAKEUP = HTEffect.shareInstance().getMakeupPath() + File.separator + "ht_makeupconfig.json";
    //滤镜配置文件
    PATH_STYLE_FILTER = HTEffect.shareInstance().getFilterPath() + File.separator + "ht_style_filter_config.json";
    PATH_EFFECT_FILTER = HTEffect.shareInstance().getFilterPath() + File.separator + "ht_effect_filter_config.json";
    PATH_HAHA_FILTER = HTEffect.shareInstance().getFilterPath() + File.separator + "ht_haha_filter_config.json";
    //美发配置文件
    PATH_HAIR = context.getFilesDir().getAbsolutePath()+"/hteffect/hair/ht_hair_config.json";

    //美妆配置文件
    // PATH_LIPSTICK = context.getFilesDir().getAbsolutePath()+"/hteffect/makeup/lipstick/ht_makeup_lipstick_config.json";
    PATH_LIPSTICK = HTEffect.shareInstance().getMakeupPath(HTMakeupEnum.HTMakeupLipstick.getValue()) + File.separator + "ht_makeup_lipstick_config.json";
    PATH_EYEBROW = HTEffect.shareInstance().getMakeupPath(HTMakeupEnum.HTMakeupEyebrow.getValue()) + File.separator + "ht_makeup_eyebrow_config.json";
    PATH_BLUSH = HTEffect.shareInstance().getMakeupPath(HTMakeupEnum.HTMakeupBlush.getValue()) + File.separator + "ht_makeup_blush_config.json";
    PATH_EYESHADOW = HTEffect.shareInstance().getMakeupPath(HTMakeupEnum.HTMakeupEyeshadow.getValue()) + File.separator + "ht_makeup_eyeshadow_config.json";
    PATH_EYELINE = HTEffect.shareInstance().getMakeupPath(HTMakeupEnum.HTMakeupEyeline.getValue()) + File.separator + "ht_makeup_eyeline_config.json";
    PATH_EYELASH = HTEffect.shareInstance().getMakeupPath(HTMakeupEnum.HTMakeupEyelash.getValue()) + File.separator + "ht_makeup_eyelash_config.json";
    PATH_PUPILS = HTEffect.shareInstance().getMakeupPath(HTMakeupEnum.HTMakeupPupils.getValue()) + File.separator + "ht_makeup_pupils_config.json";


  }

  public static HtConfigTools getInstance() {
    if (instance == null) instance = new HtConfigTools();
    return instance;
  }

  public HtMakeupStyleConfig getMakeupStyleList() {
    return makeupStyleList;
  }

  public HtAISegmentationConfig getAISegmentationList() {
    if (segmentationList == null) return null;
    return segmentationList;
  }
  public HtThreedConfig getThreedList() {
    if (threedList == null) return null;
    return threedList;
  }

  public HtGreenScreenConfig getGreenScreenList() {
    if (greenScreenList == null) return null;
    return greenScreenList;
  }



  public HtGestureConfig getGestureList() {
    if (gestureList == null) return null;
    return gestureList;
  }

  public HtStickerConfig getStickerList() {
    if (stickerList == null) return null;
    return stickerList;
  }

  public HtMaskConfig getMaskList() {
    if (maskList == null) return null;
    return maskList;
  }

  public HtGiftConfig getGiftList() {
    if (giftList == null) return null;
    return giftList;
  }

  public HtWatermarkConfig getWatermarkList() {
    if (watermarkList == null) return null;
    return watermarkList;
  }

  public HtStyleFilterConfig getStyleFilterConfig() {
    if (styleFilterList == null) return null;
    return styleFilterList;
  }
  public HtEffectFilterConfig getEffectFilterConfig() {
    if (effectFilterList == null) return null;
    return effectFilterList;
  }
  public HtHaHaFilterConfig getHaHaFilterConfig() {
    if (hahaFilterList == null) return null;
    return hahaFilterList;
  }

  public HtHairConfig getHairConfig() {
    if (hairList == null) return null;
    return hairList;
  }

  public HtMakeupConfig getMakeupList() {
    if (makeupList == null) return null;
    return makeupList;
  }

  public HtLipstickConfig getLipstickList() {
    if (lipstickList == null) return null;
    return lipstickList;
  }

  public HtEyebrowConfig getEyebrowList() {
    if (eyebrowList == null) return null;
    return eyebrowList;
  }

  public HtBlushConfig getBlushList() {
    if (blushList == null) return null;
    return blushList;
  }

    public HtEyeshadowConfig getEyeshadowList() {
    if (eyeshadowList == null) return null;
    return eyeshadowList;
  }

  public HtEyelineConfig getEyelineList() {
    if (eyelineList == null) return null;
    return eyelineList;
  }

  public HtEyelashConfig getEyelashList() {
    if (eyelashList == null) return null;
    return eyelashList;
  }

  public HtPupilsConfig getPupilsList() {
    if (pupilsList == null) return null;
    return pupilsList;
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
   * 获取缓存文件中面具配置
   */
  public void getMasksConfig(HtConfigCallBack<List<HtMaskConfig.HtMask>> callBack) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        try {
          String res = getFileString(PATH_MASK);
          if (TextUtils.isEmpty(res)) {
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(new ArrayList<>());
              }
            });
          } else {
            maskList = new Gson().fromJson(res, new TypeToken<HtMaskConfig>() {}.getType());
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(maskList.getMasks());
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

  /**
   * 获取缓存文件中礼物配置
   */
  public void getGiftsConfig(HtConfigCallBack<List<HtGiftConfig.HtGift>> callBack) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        try {
          String res = getFileString(PATH_GIFT);
          if (TextUtils.isEmpty(res)) {
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(new ArrayList<>());
              }
            });
          } else {
            giftList = new Gson().fromJson(res, new TypeToken<HtGiftConfig>() {}.getType());
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(giftList.getGifts());
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
   * 更新gift文件
   *
   * @param content json 内容
   */
  public void giftDownload(final String content) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        modifyFile(content, PATH_GIFT);
      }
    });
  }

  /**
   * 获取缓存文件中风格滤镜配置
   */
  public void getStyleFiltersConfig(HtConfigCallBack<List<HtStyleFilterConfig.HtStyleFilter>> callBack) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        try {
          String res = getFileString(PATH_STYLE_FILTER);
          if (TextUtils.isEmpty(res)) {
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(new ArrayList<>());
              }
            });
          } else {
            styleFilterList = new Gson().fromJson(res, new TypeToken<HtStyleFilterConfig>() {}.getType());
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(styleFilterList.getFilters());
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
   * 更新风格滤镜文件
   */
  public void styleFilterDownload(String content) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        modifyFile(content, PATH_STYLE_FILTER);
      }
    });
  }

  /**
   * 获取缓存文件中特效滤镜配置
   */
  public void getEffectFiltersConfig(HtConfigCallBack<List<HtEffectFilterConfig.HtEffectFilter>> callBack) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        try {
          String res = getFileString(PATH_EFFECT_FILTER);
          if (TextUtils.isEmpty(res)) {
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(new ArrayList<>());
              }
            });
          } else {
            effectFilterList = new Gson().fromJson(res, new TypeToken<HtEffectFilterConfig>() {}.getType());
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(effectFilterList.getFilters());
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
   * 更新特效滤镜文件
   */
  public void effectFilterDownload(String content) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        modifyFile(content, PATH_EFFECT_FILTER);
      }
    });
  }

  /**
   * 获取缓存文件中哈哈镜配置
   */
  public void getHaHaFiltersConfig(HtConfigCallBack<List<HtHaHaFilterConfig.HtHaHaFilter>> callBack) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        try {
          String res = getFileString(PATH_HAHA_FILTER);
          if (TextUtils.isEmpty(res)) {
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(new ArrayList<>());
              }
            });
          } else {
            hahaFilterList = new Gson().fromJson(res, new TypeToken<HtHaHaFilterConfig>() {}.getType());
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(hahaFilterList.getFilters());
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
   * 更新哈哈镜文件
   */
  public void hahaFilterDownload(String content) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        modifyFile(content, PATH_HAHA_FILTER);
      }
    });
  }

  /**
   * 更新美发文件
   */
  public void hairDownload(String content) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        modifyFile(content, PATH_HAIR);
      }
    });
  }


  /**
   * 获取缓存文件中美发配置
   */
  public void getHairsConfig(HtConfigCallBack<List<HtHairConfig.HtHair>> callBack) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        try {
          String res = getFileString(PATH_HAIR);
          if (TextUtils.isEmpty(res)) {
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(new ArrayList<>());
              }
            });
          } else {
            hairList = new Gson().fromJson(res, new TypeToken<HtHairConfig>() {}.getType());
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(hairList.getHairs());
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
   * 更新3d缓存文件
   */
  public void threedDownload(String content) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        modifyFile(content, PATH_THREED);
      }
    });
  }

  /**
   * 从缓存文件中获取3d配置文件
   */
  public void getThreedsConfig(HtConfigCallBack<List<HtThreedConfig.HtThreed>> callBack) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        try {
          String result = getFileString(PATH_THREED);

          if (TextUtils.isEmpty(result)) {
            Log.i("读取3d配置文件：", "内容为空");
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(new ArrayList<>());
              }
            });

          } else {
            threedList = new Gson().fromJson(result, new TypeToken<HtThreedConfig>() {}.getType());
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(threedList.getThreeds());
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
   * 从缓存文件中获取水印配置文件
   */
  public void getWatermarksConfig(HtConfigCallBack<List<HtWatermark>> callBack) {
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

  //todo

  /**
   * 更新美妆文件
   */
  public void makeupDownload(int type, String content) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        String res = "";
        switch (type){
          case 0:
            res = PATH_LIPSTICK;
            break;
          case 1:
            res = PATH_EYEBROW;
            break;
          case 2:
            res = PATH_BLUSH;
            break;
        }
        modifyFile(content, res);
      }
    });
  }


  /**
   * 获取缓存文件中口红配置
   */
  public void getLipsticksConfig(HtConfigCallBack<List<HtMakeup>> callBack) {

    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        String res;
        try {
              res = getFileString(PATH_LIPSTICK);
          if (TextUtils.isEmpty(res)) {
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(new ArrayList<>());
              }
            });
          } else {
            lipstickList = new Gson().fromJson(res, new TypeToken<HtLipstickConfig>() {}.getType());
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(lipstickList.getMakeups());
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
   * 获取缓存文件中眉毛配置
   */
  public void getEyebrowsConfig(HtConfigCallBack<List<HtMakeup>> callBack) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        String res;
        try {
          res = getFileString(PATH_EYEBROW);

          if (TextUtils.isEmpty(res)) {
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(new ArrayList<>());
              }
            });
          } else {
            eyebrowList = new Gson().fromJson(res, new TypeToken<HtEyebrowConfig>() {}.getType());
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(eyebrowList.getMakeups());
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
   * 获取缓存文件中腮红配置
   */
  public void getBlushsConfig(HtConfigCallBack<List<HtMakeup>> callBack) {

    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        String res;
        try {
          res = getFileString(PATH_BLUSH);
          if (TextUtils.isEmpty(res)) {
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(new ArrayList<>());
              }
            });
          } else {
            blushList = new Gson().fromJson(res, new TypeToken<HtBlushConfig>() {}.getType());
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(blushList.getMakeups());
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
   * 获取缓存文件中眼影配置
   */
  public void getEyeshadowsConfig(HtConfigCallBack<List<HtMakeup>> callBack) {

    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        String res;
        try {
          res = getFileString(PATH_EYESHADOW);
          if (TextUtils.isEmpty(res)) {
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(new ArrayList<>());
              }
            });
          } else {
            eyeshadowList = new Gson().fromJson(res, new TypeToken<HtEyeshadowConfig>() {}.getType());
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(eyeshadowList.getMakeups());
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
   * 获取缓存文件中眼线配置
   */
  public void getEyelinesConfig(HtConfigCallBack<List<HtMakeup>> callBack) {

    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        String res;
        try {
          res = getFileString(PATH_EYELINE);
          if (TextUtils.isEmpty(res)) {
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(new ArrayList<>());
              }
            });
          } else {
            eyelineList = new Gson().fromJson(res, new TypeToken<HtEyelineConfig>() {}.getType());
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(eyelineList.getMakeups());
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
   * 获取缓存文件中睫毛配置
   */
  public void getEyelashsConfig(HtConfigCallBack<List<HtMakeup>> callBack) {

    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        String res;
        try {
          res = getFileString(PATH_EYELASH);
          if (TextUtils.isEmpty(res)) {
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(new ArrayList<>());
              }
            });
          } else {
            eyelashList = new Gson().fromJson(res, new TypeToken<HtEyelashConfig>() {}.getType());
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(eyelashList.getMakeups());
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
   * 获取缓存文件中美瞳配置
   */
  public void getPupilsConfig(HtConfigCallBack<List<HtMakeup>> callBack) {

    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        String res;
        try {
          res = getFileString(PATH_PUPILS);
          if (TextUtils.isEmpty(res)) {
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(new ArrayList<>());
              }
            });
          } else {
            pupilsList = new Gson().fromJson(res, new TypeToken<HtPupilsConfig>() {}.getType());
            uiHandler.post(new Runnable() {
              @Override public void run() {
                callBack.success(pupilsList.getMakeups());
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
   * 获取特定的Type的美妆配置文件
   */
  public List<HtMakeupStyleConfig.HtMakeupStyle> getMakeupsWithType(String type) {
    ArrayList<HtMakeupStyleConfig.HtMakeupStyle> items = new ArrayList<>();
    if (makeupStyleList == null) {
      try {
        String res = getJsonString(context, "makeup/makeups.json");
        makeupStyleList = new Gson().fromJson(res, new TypeToken<HtMakeupStyleConfig>() {}.getType());
      } catch (IOException e) {
        e.printStackTrace();
        return null;
      }
    }
    for (HtMakeupStyleConfig.HtMakeupStyle makeup : makeupStyleList.getMakeups()) {
      if (makeup.getType().equals(type)) {
        items.add(makeup);
      }
    }
    return items;
  }

  /**
   * 美妆的配置文件改写
   */
  public void makeupStyleDownLoad(String content) {
    cachedThreadPool.execute(new Runnable() {
      @Override public void run() {
        modifyFile(content, PATH_MAKEUPSTYLE);
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

        String newThreed;
        try {
          //todo 等待
          newThreed = getJsonString(context, "aisegmentation/aisegmentations.json");
          modifyFile(newThreed, PATH_THREED);
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
          makeupList = new Gson().fromJson(newMakeups, new TypeToken<HtMakeupStyleConfig>() {}.getType());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
  }

}

