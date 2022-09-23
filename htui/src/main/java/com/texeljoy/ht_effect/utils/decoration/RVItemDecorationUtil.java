package com.texeljoy.ht_effect.utils.decoration;

import java.util.regex.Pattern;

final class RVItemDecorationUtil {

  /**
   * check is a color string like #xxxxxx or #xxxxxxxx
   *
   * @param colorStr
   * @return
   */
  public static boolean isColorString(String colorStr) {
    return Pattern.matches("^#([0-9a-fA-F]{6}||[0-9a-fA-F]{8})$", colorStr);
  }

  /**
   * check is a color string like #xxxxxx
   *
   * @param colorStr
   * @return
   */
  public static boolean isColorStringWithoutAlpha(String colorStr) {
    return Pattern.matches("^#[0-9a-fA-F]{6}$", colorStr);
  }
}