package com.texeljoy.ht_effect.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import com.texeljoy.ht_effect.R;

public enum HtMakeupResEnum {

    LIPSTICK_JIAOTANGSE(R.drawable.ht_makeup_lipstick_jiaotangse_icon),
    LIPSTICK_JIAOTANGSEZR(R.drawable.ht_makeup_lipstick_jiaotangsezr_icon),
    LIPSTICK_JIAOTANGYC(R.drawable.ht_makeup_lipstick_jiaotangseyc_icon),
    LIPSTICK_DOUSHASE(R.drawable.ht_makeup_lipstick_doushase_icon),
    LIPSTICK_DOUSHASEZR(R.drawable.ht_makeup_lipstick_doushasezr_icon),
    LIPSTICK_DOUSHASEYC(R.drawable.ht_makeup_lipstick_doushaseyc_icon),
    LIPSTICK_FUGUHONG(R.drawable.ht_makeup_lipstick_fuguhong_icon),
    LIPSTICK_FUGUHONGZR(R.drawable.ht_makeup_lipstick_fuguhongzr_icon),
    LIPSTICK_FUGUHONGYC(R.drawable.ht_makeup_lipstick_fuguhongyc_icon),
    LIPSTICK_QIANXINGSE(R.drawable.ht_makeup_lipstick_qianxingse_icon),
    LIPSTICK_QIANXINGSEZR(R.drawable.ht_makeup_lipstick_qianxingsezr_icon),
    LIPSTICK_QIANXINGSEYC(R.drawable.ht_makeup_lipstick_qianxingseyc_icon),
    LIPSTICK_RHFEN(R.drawable.ht_makeup_lipstick_rhfen_icon),
    LIPSTICK_RHFENZR(R.drawable.ht_makeup_lipstick_rhfenzr_icon),
    LIPSTICK_RHFENYC(R.drawable.ht_makeup_lipstick_rhfenyc_icon),
    LIPSTICK_YQJU(R.drawable.ht_makeup_lipstick_yqju_icon),
    LIPSTICK_YQJUZR(R.drawable.ht_makeup_lipstick_yqjuzr_icon),
    LIPSTICK_YQJUYC(R.drawable.ht_makeup_lipstick_yqjuyc_icon),
    EYEBROW_BIAOZHUNMEI(R.drawable.ht_makeup_eyebrow_biaozhunmei_icon),
    EYEBROW_JIANMEI(R.drawable.ht_makeup_eyebrow_jianmei_icon),
    EYEBROW_LIUXINGMEI(R.drawable.ht_makeup_eyebrow_liuxingmei_icon),
    EYEBROW_LIUYEMEI(R.drawable.ht_makeup_eyebrow_liuyemei_icon),
    EYEBROW_OUSHIMEI(R.drawable.ht_makeup_eyebrow_oushimei_icon),
    EYEBROW_PINGZHIMEI(R.drawable.ht_makeup_eyebrow_pingzhimei_icon),
    BLUSH_MITAOWEIXUN(R.drawable.ht_makeup_blush_mitaoweixun_icon),
    BLUSH_MITAOPINGGUOJI(R.drawable.ht_makeup_blush_mitaopingguoji_icon),
    BLUSH_MITAOSHANXING(R.drawable.ht_makeup_blush_mitaoshanxing_icon),
    BLUSH_JIANLINGWEIXUN(R.drawable.ht_makeup_blush_jianlingweixun_icon),
    BLUSH_JIANLINGPINGGUOJI(R.drawable.ht_makeup_blush_jianlingpingguoji_icon),
    BLUSH_JIANLINGSHANXING(R.drawable.ht_makeup_blush_jianlingshanxing_icon),
    BLUSH_RICHANGWEIXUN(R.drawable.ht_makeup_blush_richangweixun_icon),
    BLUSH_RICHANGPINGGUOJI(R.drawable.ht_makeup_blush_richangpingguoji_icon),
    BLUSH_RICHANGSHANXING(R.drawable.ht_makeup_blush_richangshanxing_icon),
    BLUSH_YUANQIWEIXUN(R.drawable.ht_makeup_blush_yuanqiweixun_icon),
    BLUSH_YUANQIPINGGUOJI(R.drawable.ht_makeup_blush_yuanqipingguoji_icon),
    BLUSH_YUANQISHANXING(R.drawable.ht_makeup_blush_yuanqishanxing_icon),
    BLUSH_RIXIWEIXUN(R.drawable.ht_makeup_blush_rixiweixun_icon),
    BLUSH_RIXIPINGGUOJI(R.drawable.ht_makeup_blush_rixipingguoji_icon),
    BLUSH_RIXISHANXING(R.drawable.ht_makeup_blush_rixishanxing_icon),
    BLUSH_FENWEIWEIXUN(R.drawable.ht_makeup_blush_fenweiweixun_icon),
    BLUSH_FENWEIPINGGUOJI(R.drawable.ht_makeup_blush_fenweipingguoji_icon),
    BLUSH_FENWEISHANXING(R.drawable.ht_makeup_blush_fenweishanxing_icon),
    EYESHADOW_DADIZONG(R.drawable.ht_makeup_eyeshadow_dadizong_icon),
    EYESHADOW_NYFENZONG(R.drawable.ht_makeup_eyeshadow_nyfenzong_icon),
    EYESHADOW_NVSHEN(R.drawable.ht_makeup_eyeshadow_nvshen_icon),
    EYESHADOW_FENWEIGAN(R.drawable.ht_makeup_eyeshadow_fenweigan_icon),
    EYESHADOW_SHANYAOFEN(R.drawable.ht_makeup_eyeshadow_shanyaofen_icon),
    EYESHADOW_YANXUN(R.drawable.ht_makeup_eyeshadow_yanxun_icon),
    EYESHADOW_SHAONVGAN(R.drawable.ht_makeup_eyeshadow_shaonvgan_icon),
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
    PUPILS_MITANG(R.drawable.ht_makeup_pupils_mitang_icon),
    PUPILS_HUILV(R.drawable.ht_makeup_pupils_huilv_icon),
    PUPILS_MITAOFEN(R.drawable.ht_makeup_pupils_mitaofen_icon),
    PUPILS_NAICHA(R.drawable.ht_makeup_pupils_naicha_icon),
    PUPILS_XINGKONGLAN(R.drawable.ht_makeup_pupils_xingkonglan_icon),
    PUPILS_ZHENZHUHUI(R.drawable.ht_makeup_pupils_zhenzhuhui_icon);



    HtMakeupResEnum(int iconRes) {this.iconRes = iconRes;}

    //图标地址
    private final int iconRes;

    public Drawable getIcon(Context context) {
        return ContextCompat.getDrawable(context, iconRes);
    }
}
