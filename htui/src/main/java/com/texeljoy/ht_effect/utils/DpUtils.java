package com.texeljoy.ht_effect.utils;

import android.content.res.Resources;

/**
 * Dp相关工具类
 */
@SuppressWarnings("unused")
public class DpUtils {
  public static int dip2px( float dpValue) {
    final float scale = Resources.getSystem().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }

  /**
   * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
   */
  public static int px2dip( float pxValue) {
    final float scale = Resources.getSystem().getDisplayMetrics().density;
    return (int) (pxValue / scale + 0.5f);
  }
}
