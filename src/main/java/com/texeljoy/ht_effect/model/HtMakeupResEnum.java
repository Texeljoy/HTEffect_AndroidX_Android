package com.texeljoy.ht_effect.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import com.texeljoy.ht_effect.R;

public enum HtMakeupResEnum {

    LIPSTICK_FANQIE(R.drawable.ht_makeup_lipstick_fanqie_icon),
    LIPSTICK_NVTUAN(R.drawable.ht_makeup_lipstick_nvtuan_icon),
    LIPSTICK_PINGGUO(R.drawable.ht_makeup_lipstick_pingguo_icon),
    LIPSTICK_ROUGUI(R.drawable.ht_makeup_lipstick_rougui_icon),
    LIPSTICK_ZHANNAN(R.drawable.ht_makeup_lipstick_zhannan_icon),
    LIPSTICK_ZHENGGONG(R.drawable.ht_makeup_lipstick_zhenggong_icon),
    EYEBROW_BIAOZHUNMEI(R.drawable.ht_makeup_eyebrow_biaozhunmei_icon),
    EYEBROW_JIANMEI(R.drawable.ht_makeup_eyebrow_jianmei_icon),
    EYEBROW_LIUXINGMEI(R.drawable.ht_makeup_eyebrow_liuxingmei_icon),
    EYEBROW_LIUYEMEI(R.drawable.ht_makeup_eyebrow_liuyemei_icon),
    EYEBROW_OUSHIMEI(R.drawable.ht_makeup_eyebrow_oushimei_icon),
    EYEBROW_PINGZHIMEI(R.drawable.ht_makeup_eyebrow_pingzhimei_icon),
    BLUSH_QINGSE(R.drawable.ht_makeup_blush_qingse_icon),
    BLUSH_RIZA(R.drawable.ht_makeup_blush_riza_icon),
    BLUSH_TIANCHENG(R.drawable.ht_makeup_blush_tiancheng_icon),
    BLUSH_WEIXUN(R.drawable.ht_makeup_blush_weixun_icon),
    BLUSH_XINDONG(R.drawable.ht_makeup_blush_xindong_icon),
    BLUSH_YOUYA(R.drawable.ht_makeup_blush_youya_icon),
    EYESHADOW_DADI(R.drawable.ht_makeup_eyeshadow_dadi_icon),
    EYESHADOW_NVTUAN(R.drawable.ht_makeup_eyeshadow_nvtuan_icon),
    EYESHADOW_SUMMER(R.drawable.ht_makeup_eyeshadow_summer_icon),
    EYESHADOW_TAOHUA(R.drawable.ht_makeup_eyeshadow_taohua_icon),
    EYESHADOW_YANXUNZHUANG(R.drawable.ht_makeup_eyeshadow_yanxunzhuang_icon),
    EYESHADOW_YUANQI(R.drawable.ht_makeup_eyeshadow_yuanqi_icon),
    EYELINE_GEXING(R.drawable.ht_makeup_eyeline_gexing_icon),
    EYELINE_MEIHEI(R.drawable.ht_makeup_eyeline_meihei_icon),
    EYELINE_ROUHUA(R.drawable.ht_makeup_eyeline_rouhua_icon),
    EYELINE_SHENSUI(R.drawable.ht_makeup_eyeline_shensui_icon),
    EYELINE_SUYAN(R.drawable.ht_makeup_eyeline_suyan_icon),
    EYELINE_WUGU(R.drawable.ht_makeup_eyeline_wugu_icon),
    EYELASH_BABI(R.drawable.ht_makeup_eyelash_babi_icon),
    EYELASH_HUXI(R.drawable.ht_makeup_eyelash_huxi_icon),
    EYELASH_NONGMI(R.drawable.ht_makeup_eyelash_nongmi_icon),
    EYELASH_NUSHEN(R.drawable.ht_makeup_eyelash_nushen_icon),
    EYELASH_ROUHE(R.drawable.ht_makeup_eyelash_rouhe_icon),
    EYELASH_ZIRAN(R.drawable.ht_makeup_eyelash_ziran_icon),
    PUPILS_HONGYUE(R.drawable.ht_makeup_pupils_hongyue_icon),
    PUPILS_JINZONG(R.drawable.ht_makeup_pupils_jinzong_icon),
    PUPILS_LENGYAN(R.drawable.ht_makeup_pupils_lengyan_icon),
    PUPILS_LVYE(R.drawable.ht_makeup_pupils_lvye_icon),
    PUPILS_QIAOKELI(R.drawable.ht_makeup_pupils_qiaokeli_icon),
    PUPILS_XINGKONG(R.drawable.ht_makeup_pupils_xingkong_icon);



    HtMakeupResEnum(int iconRes) {this.iconRes = iconRes;}

    //图标地址
    private final int iconRes;

    public Drawable getIcon(Context context) {
        return ContextCompat.getDrawable(context, iconRes);
    }
}
