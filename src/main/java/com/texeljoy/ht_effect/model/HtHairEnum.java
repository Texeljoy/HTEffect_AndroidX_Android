package com.texeljoy.ht_effect.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import com.texeljoy.ht_effect.R;

public enum HtHairEnum {
    // 原图
    NONE(R.string.hair_none, "", R.drawable.icon_hair_yuantu),
    SHEN_MI_ZI(R.string.hair_shenmizi, "shenmizi", R.drawable.icon_hair_shenmizi),
    QIAO_KE_LI(R.string.hair_qiaokeli, "qiaokeli", R.drawable.icon_hair_qiaokeli),
    QING_MU_ZONG(R.string.hair_qingmuzong, "qingmuzong", R.drawable.icon_hair_qingmuzong),
    JIAO_TANG_ZONG(R.string.hair_jiaotangzong, "jiaotangzong", R.drawable.icon_hair_jiaotangzong),
    // FENG_MI_CHUN_ZONG(R.string.hair_fengmichunzong, "fengmichunzong", R.drawable.icon_hair_fengmizong),
    // QIAN_JIN_ZONG(R.string.hair_qianjinzong, "qianjinzong", R.drawable.icon_hair_qianjinzong),
    // MEI_GUI_JIN(R.string.hair_meiguijin, "jiaotangzong", R.drawable.icon_hair_meiguijin),
    // YA_MA_BAI_JIN(R.string.hair_yamabaijin, "yamabaijin", R.drawable.icon_hair_yamabaijin),
    LUO_RI_JU(R.string.hair_luoriju, "luoriju", R.drawable.icon_hair_luoriju),
    // YA_MA_QIAN_JU(R.string.hair_yamaqianju, "yamaqianju", R.drawable.icon_hair_yamaqianju),
    FU_GU_MEI_GUI(R.string.hair_fugumeigui, "fugumeigui", R.drawable.icon_hair_fugumeigui),
    SHEN_MEI_GUI(R.string.hair_shenmeigui, "shenmeigui", R.drawable.icon_hair_shenmeigui),
    // YAN_HUI_WU_ZI(R.string.hair_yanhuiwuzi, "yanhuiwuzi", R.drawable.icon_hair_yanhuiwuzi),
    // FEN_MEI_TIAN_ZONG(R.string.hair_fenmeitianzong, "fenmeitianzong", R.drawable.icon_hair_fenmeitianzong),
    WU_MAI_XIANG_YU(R.string.hair_wumaixiangyu, "wumaixiangyu", R.drawable.icon_hair_wumaixiangyu),
    KONG_QUE_LAN(R.string.hair_kongquelan, "kongquelan", R.drawable.icon_hair_kongquelan),
    WU_MAI_LAN_HUI(R.string.hair_wumailanhui, "wumailanhui", R.drawable.icon_hair_wumailanhui),
    YA_MA_HUI_ZONG(R.string.hair_yamahuizong, "yamahuizong", R.drawable.icon_hair_yamahuizong),
    YA_MA_QIAN_HUI(R.string.hair_yamaqianhui, "yamaqianhui", R.drawable.icon_hair_yamaqianhui);
    //名字
    private final int stringRes;

    //对应的资源名
    private final String keyName;

    //图标地址
    private final int iconRes;
    HtHairEnum(@StringRes int stringRes, String keyName, @DrawableRes int iconRes) {
        this.stringRes = stringRes;
        this.keyName = keyName;
        this.iconRes = iconRes;
    }

    public String getName(Context context) {
        return context.getString(stringRes);
    }

    public String getKeyName() {
        return keyName;
    }

    public Drawable getIcon(Context context) {
        return ContextCompat.getDrawable(context, iconRes);
    }

    }
