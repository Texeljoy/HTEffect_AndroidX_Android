package com.texeljoy.ht_effect.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HTViewState;
import com.texeljoy.ht_effect.model.HtBeautyKey;
import com.texeljoy.ht_effect.model.HtBeautyParam;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.utils.DpUtils;
import com.texeljoy.ht_effect.utils.HtSelectedPosition;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;
import com.texeljoy.hteffect.HTEffect;
import com.texeljoy.hteffect.model.HTBodyBeautyEnum;
import com.texeljoy.hteffect.model.HTMakeupEnum;

/**
 * 复用的Slider
 */

public class HtBarView extends LinearLayout implements SeekBar.OnSeekBarChangeListener {

  private TextView htNumberTV;
  private TextView htBubbleTV;
  private SeekBar htSeekBar;
  private View htProgressV;
  private View htMiddleV;
  private ImageView htRenderEnableIV;

  private int htSeekBarWidth = 0;

  public HtBarView(Context context) {
    super(context);
    init();
  }

  public HtBarView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public HtBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  public HtBarView init() {
    LayoutInflater.from(getContext()).inflate(R.layout.layout_bar, this);
    RxBus.get().register(this);
    initView();
    return this;
  }

  /**
   * 初始化View
   */
  @SuppressLint("ClickableViewAccessibility")
  private void initView() {

    htNumberTV = findViewById(R.id.htNumberTV);
    htBubbleTV = findViewById(R.id.htBubbleTV);
    htSeekBar = findViewById(R.id.htSeekBar);
    htProgressV = findViewById(R.id.htProgressV);
    htMiddleV = findViewById(R.id.htMiddleV);
    htRenderEnableIV = findViewById(R.id.htRenderEnableIV);

    changeTheme(null);
    htSeekBar.setOnSeekBarChangeListener(this);

    htRenderEnableIV.setOnTouchListener(new OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
          case MotionEvent.ACTION_DOWN:
            HTEffect.shareInstance().setRenderEnable(false);
            return true;
          case MotionEvent.ACTION_UP:
            HTEffect.shareInstance().setRenderEnable(true);
            return true;
        }
        return false;
      }
    });







  }

  /**
   * 销毁View时候解绑
   */
  @Override
  protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    RxBus.get().unregister(this);
  }

  /**
   * 同步滑动条参数
   */
  @Subscribe(thread = EventThread.MAIN_THREAD,
             tags = { @Tag(HTEventAction.ACTION_SYNC_PROGRESS) })
  public void syncProgress(Object o) {

    Log.e("面板1", HtState.currentViewState.name());
    Log.e("面板2", HtState.currentSecondViewState.name());
    Log.e("面板3", HtState.currentThirdViewState.name());

    //美颜——美颜
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_SKIN){

      //美颜效果未选中，隐藏滑动条
      if (HtState.getCurrentBeautySkin() == HtBeautyKey.NONE) {
        setVisibility(INVISIBLE);
        return;
      } else {
        setVisibility(VISIBLE);

      }


      int progress = HtUICacheUtils.beautySkinValue(HtState.getCurrentBeautySkin());
      Log.e("当前模块:", HtState.getCurrentBeautySkin().name());
      Log.e("美颜滑动参数同步:", progress + "");
      switch (HtState.getCurrentBeautySkin()) {
        // case vague_blurriness:
        // case precise_blurriness:
        case blurriness:
        case whiteness:
        case rosiness:
        case clearness:
        case undereye_circles:
        case nasolabial:
          styleNormal(progress);
          htSeekBar.setProgress(progress);
          break;
        case brightness:
          styleTransform(progress);
          htSeekBar.setProgress(progress);
          break;
        case NONE:
          setVisibility(INVISIBLE);
          break;
      }
      return;

    }

    //美颜——美型
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_FACE_TRIM) {

      //美型效果未选中，隐藏滑动条
      if (HtUICacheUtils.beautyFaceTrimPosition() == -1) {
        setVisibility(INVISIBLE);
        return;
      } else {
        setVisibility(VISIBLE);
      }

      int progress = HtUICacheUtils
          .beautyFaceTrimValue(HtState.currentFaceTrim);
      Log.e("当前模块:", HtState.currentFaceTrim.name());
      Log.e("美型滑动参数同步:", progress + "");
      htSeekBar.setProgress(progress);

      //根据参数 选中哪种滑动条
      switch (HtState.currentFaceTrim) {
        case EYE_ENLARGING: //大眼
        case NOSE_ROOT_ENLARGING:  // 山根
        case NOSE_APEX_LESSENING:  //鼻头 *
        case NOSE_THINNING:  //瘦鼻
        case MOUTH_SMILING:  //微笑嘴唇
        case EYE_CORNER_ENLARGING:  // 开眼角
        case CHEEK_SHORTENING:  //短脸
        case TEMPLE_ENLARGING:  //丰太阳穴 *
        case EYE_ROUNDING:  //圆眼
        case CHEEK_THINNING:  //瘦脸
        case CHEEK_V_SHAPING:  //V脸
        case CHEEK_NARROWING: //窄脸
        case CHEEK_BONE_THINNING:  //瘦颧骨
        case JAW_BONE_THINNING:  //瘦下颌骨
        case head_lessening:  //小头
        case FACE_LESSENING:  //小脸
          styleNormal(progress);
          break;
        case CHIN_TRIMMING:  //下巴
        case MOUTH_TRIMMING:// 嘴型
        case NOSE_ENLARGING:  //长鼻
        case PHILTRUM_TRIMMING: //缩人中
        case FOREHEAD_TRIM:  //发际线
        case EYE_SAPCING:  //眼间距
        case EYE_CORNER_TRIMMING:  //眼角角度
          styleTransform(progress);
          break;
      }

      return;
    }



    //美颜——美发
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_HAIR) {

      //美发效果未选中，隐藏滑动条
      if (HtUICacheUtils.beautyHairPosition() == 0) {
        setVisibility(INVISIBLE);
        return;
      } else {
        setVisibility(VISIBLE);
      }

      int progress = HtUICacheUtils
          .beautyHairValue(HtState.currentHair.getName());
      Log.e("当前模块:", HtState.currentHair.getName());
      Log.e("美发滑动参数同步:", progress + "");
      htSeekBar.setProgress(progress);
      styleNormal(HtUICacheUtils.beautyHairValue(HtState.currentHair.getName()));
      return;
    }

    //美颜——美妆
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_MAKE_UP
        && HtState.currentThirdViewState == HTViewState.MAKEUP_OUT) {
        setVisibility(INVISIBLE);
        return;
    }

    //妆容推荐
    // if (HtState.currentViewState == HTViewState.BEAUTY
    //     && HtState.currentSecondViewState == HTViewState.BEAUTY_MAKE_UP_STYLE) {
    //   setVisibility(HtState.currentMakeUpStyle == HtMakeupStyle.NONE ?
    //                 View.INVISIBLE : View.VISIBLE);
    //   styleNormal(HtUICacheUtils.beautyMakeupStyleValue(HtState.currentMakeUpStyle.getName(getContext())));
    //   htSeekBar.setProgress(HtUICacheUtils.beautyMakeupStyleValue(HtState.currentMakeUpStyle.getName(getContext())));
    // }

    //美颜——美妆——口红
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_MAKE_UP
        && HtState.currentThirdViewState == HTViewState.MAKEUP_LIPSTICK) {

      //美妆效果未选中，隐藏滑动条
      if (HtUICacheUtils.beautyLipstickPosition(HTMakeupEnum.HTMakeupLipstick.getValue()) == 0) {
        setVisibility(INVISIBLE);
        return;
      } else {
        setVisibility(VISIBLE);
      }

      String currentName = HtUICacheUtils.beautyLipstickName(HTMakeupEnum.HTMakeupLipstick.getValue());
      int progress = HtUICacheUtils
          .beautyLipstickValue(HTMakeupEnum.HTMakeupLipstick.getValue(), currentName);
      Log.e("当前模块:", currentName);
      Log.e("口红滑动参数同步:", progress + "");
      htSeekBar.setProgress(progress);
      styleNormal(HtUICacheUtils.beautyLipstickValue(HTMakeupEnum.HTMakeupLipstick.getValue(), currentName));
      return;
    }

    //美颜——美妆——眉毛
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_MAKE_UP
        && HtState.currentThirdViewState == HTViewState.MAKEUP_EYEBROW) {

      //美妆效果未选中，隐藏滑动条
      if (HtUICacheUtils.beautyLipstickPosition(HTMakeupEnum.HTMakeupEyebrow.getValue()) == 0) {
        setVisibility(INVISIBLE);
        return;
      } else {
        setVisibility(VISIBLE);
      }

      String currentName = HtUICacheUtils.beautyLipstickName(HTMakeupEnum.HTMakeupEyebrow.getValue());
      int progress = HtUICacheUtils
          .beautyLipstickValue(HTMakeupEnum.HTMakeupEyebrow.getValue(),currentName);
      Log.e("当前模块:", currentName);
      Log.e("眉毛滑动参数同步:", progress + "");
      htSeekBar.setProgress(progress);
      styleNormal(HtUICacheUtils.beautyLipstickValue(HTMakeupEnum.HTMakeupEyebrow.getValue(), currentName));
      return;
    }

    //美颜——美妆——腮红
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_MAKE_UP
        && HtState.currentThirdViewState == HTViewState.MAKEUP_BLUSH) {

      //美妆效果未选中，隐藏滑动条
      if (HtUICacheUtils.beautyLipstickPosition(HTMakeupEnum.HTMakeupBlush.getValue()) == 0) {
        setVisibility(INVISIBLE);
        return;
      } else {
        setVisibility(VISIBLE);
      }

      String currentName = HtUICacheUtils.beautyLipstickName(HTMakeupEnum.HTMakeupBlush.getValue());
      int progress = HtUICacheUtils
          .beautyLipstickValue(HTMakeupEnum.HTMakeupBlush.getValue(), currentName);
      Log.e("当前模块:", currentName);
      Log.e("腮红滑动参数同步:", progress + "");
      htSeekBar.setProgress(progress);
      styleNormal(HtUICacheUtils.beautyLipstickValue(HTMakeupEnum.HTMakeupBlush.getValue(), currentName));
      return;
    }

    //美颜——美妆——眼影
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_MAKE_UP
        && HtState.currentThirdViewState == HTViewState.MAKEUP_EYESHADOW) {

      //美妆效果未选中，隐藏滑动条
      if (HtUICacheUtils.beautyLipstickPosition(HTMakeupEnum.HTMakeupEyeshadow.getValue()) == 0) {
        setVisibility(INVISIBLE);
        return;
      } else {
        setVisibility(VISIBLE);
      }

      String currentName = HtUICacheUtils.beautyLipstickName(HTMakeupEnum.HTMakeupEyebrow.getValue());
      int progress = HtUICacheUtils
          .beautyLipstickValue(HTMakeupEnum.HTMakeupEyeshadow.getValue(), currentName);
      Log.e("当前模块:", currentName);
      Log.e("眼影滑动参数同步:", progress + "");
      htSeekBar.setProgress(progress);
      styleNormal(HtUICacheUtils.beautyLipstickValue(HTMakeupEnum.HTMakeupEyeshadow.getValue(), currentName));
      return;
    }

    //美颜——美妆——眼线
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_MAKE_UP
        && HtState.currentThirdViewState == HTViewState.MAKEUP_EYELINE) {

      //美妆效果未选中，隐藏滑动条
      if (HtUICacheUtils.beautyLipstickPosition(HTMakeupEnum.HTMakeupEyeline.getValue()) == 0) {
        setVisibility(INVISIBLE);
        return;
      } else {
        setVisibility(VISIBLE);
      }

      String currentName = HtUICacheUtils.beautyLipstickName(HTMakeupEnum.HTMakeupEyeline.getValue());
      int progress = HtUICacheUtils
          .beautyLipstickValue(HTMakeupEnum.HTMakeupEyeline.getValue(), currentName);
      Log.e("当前模块:", currentName);
      Log.e("眼线滑动参数同步:", progress + "");
      htSeekBar.setProgress(progress);
      styleNormal(HtUICacheUtils.beautyLipstickValue(HTMakeupEnum.HTMakeupEyeline.getValue(), currentName));
      return;
    }

    //美颜——美妆——睫毛
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_MAKE_UP
        && HtState.currentThirdViewState == HTViewState.MAKEUP_EYELASH) {

      //美妆效果未选中，隐藏滑动条
      if (HtUICacheUtils.beautyLipstickPosition(HTMakeupEnum.HTMakeupEyelash.getValue()) == 0) {
        setVisibility(INVISIBLE);
        return;
      } else {
        setVisibility(VISIBLE);
      }

      String currentName = HtUICacheUtils.beautyLipstickName(HTMakeupEnum.HTMakeupEyelash.getValue());
      int progress = HtUICacheUtils
          .beautyLipstickValue(HTMakeupEnum.HTMakeupEyelash.getValue(), currentName);
      Log.e("当前模块:", currentName);
      Log.e("睫毛滑动参数同步:", progress + "");
      htSeekBar.setProgress(progress);
      styleNormal(HtUICacheUtils.beautyLipstickValue(HTMakeupEnum.HTMakeupEyelash.getValue(), currentName));
      return;
    }

    //美颜——美妆——美瞳
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_MAKE_UP
        && HtState.currentThirdViewState == HTViewState.MAKEUP_BEAUTYPUPILS) {

      //美妆效果未选中，隐藏滑动条
      if (HtUICacheUtils.beautyLipstickPosition(HTMakeupEnum.HTMakeupPupils.getValue()) == 0) {
        setVisibility(INVISIBLE);
        return;
      } else {
        setVisibility(VISIBLE);
      }

      String currentName = HtUICacheUtils.beautyLipstickName(HTMakeupEnum.HTMakeupPupils.getValue());
      int progress = HtUICacheUtils
          .beautyLipstickValue(HTMakeupEnum.HTMakeupPupils.getValue(), currentName);
      Log.e("当前模块:", currentName);
      Log.e("美瞳滑动参数同步:", progress + "");
      htSeekBar.setProgress(progress);
      styleNormal(HtUICacheUtils.beautyLipstickValue(HTMakeupEnum.HTMakeupPupils.getValue(), currentName));
      return;
    }

    //美颜——美体
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_BODY) {

      //美型效果未选中，隐藏滑动条
      if (HtUICacheUtils.beautyBodyPosition() == -1) {
        setVisibility(INVISIBLE);
        return;
      } else {
        setVisibility(VISIBLE);
      }

      int progress = HtUICacheUtils
          .beautyBodyValue(HtState.currentBody);
      Log.e("当前模块:", HtState.currentBody.name());
      Log.e("美体滑动参数同步:", progress + "");
      htSeekBar.setProgress(progress);
      styleNormal(progress);

      return;
    }



    //AR道具——水印
    if (HtState.currentViewState == HTViewState.AR
        && HtState.currentSecondViewState == HTViewState.AR_WATERMARK) {

      // //水印效果未选中，隐藏滑动条
      // Log.d("lu123456", "syncProgress: "+HtSelectedPosition.POSITION_WATERMARK);
      // if (HtSelectedPosition.POSITION_WATERMARK < 1) {
      //   setVisibility(INVISIBLE);
      //   return;
      // } else {
      //   setVisibility(VISIBLE);
      // }
      //
      // int progress = HtSelectedPosition.VALUE_WATERMARK_ALPHA;
      // Log.e("当前模块:", "水印");
      // Log.e("水印滑动参数同步:", progress + "");
      // htSeekBar.setProgress(progress);
      // styleNormal(HtSelectedPosition.VALUE_WATERMARK_ALPHA);
      return;
    }

    //人像抠图——绿幕抠图——相似度
    if (HtState.currentViewState == HTViewState.PORTRAIT
        && HtState.currentSecondViewState == HTViewState.GREENSCREEN_SIMILARITY) {
      setVisibility(VISIBLE);

      int progress = HtSelectedPosition.VALUE_SIMILARITY;
      Log.e("当前模块:", "相似度");
      Log.e("相似度滑动参数同步:", progress + "");
      htSeekBar.setProgress(progress);
      styleNormal(HtSelectedPosition.VALUE_SIMILARITY);
      return;
    }

    //人像抠图——绿幕抠图——平滑度
    if (HtState.currentViewState == HTViewState.PORTRAIT
        && HtState.currentSecondViewState == HTViewState.GREENSCREEN_SMOOTHNESS) {
      setVisibility(VISIBLE);

      int progress = HtSelectedPosition.VALUE_SMOOTHNESS;
      Log.e("当前模块:", "平滑度");
      Log.e("平滑度滑动参数同步:", progress + "");
      htSeekBar.setProgress(progress);
      styleNormal(HtSelectedPosition.VALUE_SMOOTHNESS);
      return;
    }

    //人像抠图——绿幕抠图——透明度
    if (HtState.currentViewState == HTViewState.PORTRAIT
        && HtState.currentSecondViewState == HTViewState.GREENSCREEN_ALPHA) {
      setVisibility(VISIBLE);

      int progress = HtSelectedPosition.VALUE_ALPHA;
      Log.e("当前模块:", "透明度");
      Log.e("透明度滑动参数同步:", progress + "");
      htSeekBar.setProgress(progress);
      styleNormal(HtSelectedPosition.VALUE_ALPHA);
      return;
    }

    //人像抠图——绿幕抠图——祛色度
    if (HtState.currentViewState == HTViewState.PORTRAIT
            && HtState.currentSecondViewState == HTViewState.GREENSCREEN_DECOLOR) {
      setVisibility(VISIBLE);

      int progress = HtSelectedPosition.VALUE_DECOLOR;
      Log.e("当前模块:", "祛色度");
      Log.e("祛色度滑动参数同步:", progress + "");
      htSeekBar.setProgress(progress);
      styleNormal(HtSelectedPosition.VALUE_DECOLOR);
      return;
    }

    //美颜——滤镜
    if (HtState.currentViewState == HTViewState.FILTER) {

      setVisibility(INVISIBLE);
      // styleNormal(HtUICacheUtils.beautyFilterValue(HtState.currentStyleFilter.getName()));
      // htSeekBar.setProgress(HtUICacheUtils.beautyFilterValue(HtState.currentStyleFilter.getName()));
      return;
    }

    //美颜——风格推荐
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_STYLE) {
      setVisibility(INVISIBLE);
      return;
    }

    //美颜——妆容推荐
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_MAKE_UP_STYLE) {
      setVisibility(INVISIBLE);
      return;
    }


    //AR道具
    if (HtState.currentViewState == HTViewState.AR) {
      setVisibility(INVISIBLE);
    }



    //人像抠图
    if (HtState.currentViewState == HTViewState.PORTRAIT) {
      setVisibility(INVISIBLE);
    }

    //人像抠图——绿幕抠图——背景
    if (HtState.currentSecondViewState == HTViewState.GREENSCREEN_BACKGROUND) {
      setVisibility(INVISIBLE);
    }

  }



  /**
   * 根据系统主题切换面板
   * @param o
   */
  @Subscribe(thread = EventThread.MAIN_THREAD,
             tags = { @Tag(HTEventAction.ACTION_CHANGE_THEME) })
  public void changeTheme(Object o) {
    Drawable bgThumb = ContextCompat.getDrawable(getContext(), R.drawable.bg_ht_seekbar_thumb);
    Drawable bgMiddle = ContextCompat.getDrawable(getContext(), R.drawable.bg_middle);
    Drawable bgProgress = ContextCompat.getDrawable(getContext(), R.drawable.bg_ht_bar_progress);

    if (HtState.isDark) {
      DrawableCompat.setTint(bgMiddle, ContextCompat.getColor(getContext(),
          R.color.white));
      DrawableCompat.setTint(bgProgress, ContextCompat.getColor(getContext(),
          R.color.theme_color));
      DrawableCompat.setTint(bgThumb, ContextCompat.getColor(getContext(),
          R.color.theme_color));

      htBubbleTV.setTextColor(ContextCompat.getColor(getContext(),
          R.color.seekbar_background));

      htRenderEnableIV.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.ic_render_white_enable));
    } else {

      DrawableCompat.setTint(bgMiddle, ContextCompat.getColor(getContext(),
          R.color.dark_black));
      DrawableCompat.setTint(bgProgress, ContextCompat.getColor(getContext(),
          R.color.theme_color));
      DrawableCompat.setTint(bgThumb, ContextCompat.getColor(getContext(),
          R.color.theme_color));
      htBubbleTV.setTextColor(ContextCompat.getColor(getContext(),
          R.color.seekbar_background));

      htRenderEnableIV.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.ic_render_black_enable));
    }
    htProgressV.setBackground(bgProgress);
    htMiddleV.setBackground(bgMiddle);
    htSeekBar.setThumb(bgThumb);
  }

  @SuppressLint("LongLogTag")
  @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    if (!fromUser) {
      return;
    }

    //美颜——美肤——美肤
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_SKIN) {

      //滑动条变化时，将重置按钮设为可选
      if (!HtUICacheUtils.beautySkinResetEnable()) {
        HtUICacheUtils.beautySkinResetEnable(true);
        RxBus.get().post(HTEventAction.ACTION_SYNC_RESET, "");
      }


      switch (HtState.getCurrentBeautySkin()) {
        // case vague_blurriness:
        //   styleNormal(progress);
        //   HTEffect.shareInstance().setBeauty(HtBeautyParam.HTBeautyBlurrySmoothing,progress);
        //   break;
        // case precise_blurriness:
        //   styleNormal(progress);
        //   HTEffect.shareInstance().setBeauty(HtBeautyParam.HTBeautyClearSmoothing,progress);
        //   break;
        case blurriness:
          styleNormal(progress);
          HTEffect.shareInstance().setBeauty(HtBeautyParam.HTBeautyClearSmoothing,progress);
          break;
        case whiteness:
          styleNormal(progress);
          HTEffect.shareInstance().setBeauty(HtBeautyParam.HTBeautySkinWhitening,progress);
          break;
        case rosiness:
          styleNormal(progress);
          HTEffect.shareInstance().setBeauty(HtBeautyParam.HTBeautySkinRosiness,progress);
          break;
        case clearness:
          styleNormal(progress);
          HTEffect.shareInstance().setBeauty(HtBeautyParam.HTBeautyImageSharpness,progress);
          break;
        case brightness:
          styleTransform(progress);
          HTEffect.shareInstance().setBeauty(HtBeautyParam.HTBeautyImageBrightness,progress - 50);
          break;
        case undereye_circles:
          styleNormal(progress);
          HTEffect.shareInstance().setBeauty(HtBeautyParam.HTBeautyDarkCircleLessening,progress);
          break;
        case nasolabial:
          styleNormal(progress);
          HTEffect.shareInstance().setBeauty(HtBeautyParam.HTBeautyNasolabialLessening,progress);
          break;
        case NONE:
          break;
      }

      //精细磨皮和朦胧磨皮冲突，二者选其一
      // if (HtState.getCurrentBeautySkin() == HtBeautyKey.precise_blurriness) {
      //   HtUICacheUtils.beautySkinValue(HtBeautyKey.vague_blurriness, 0);
      // }
      // if (HtState.getCurrentBeautySkin() == HtBeautyKey.vague_blurriness) {
      //   HtUICacheUtils.beautySkinValue(HtBeautyKey.precise_blurriness, 0);
      // }

      Log.e("美颜" + HtState.getCurrentBeautySkin(), progress + "");
      HtUICacheUtils.beautySkinValue(HtState.getCurrentBeautySkin(), progress);

      return;
    }

    //美颜——美肤——美型
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_FACE_TRIM) {

      //滑动条变化时，将重置按钮设为可选
      if (!HtUICacheUtils.beautyFaceTrimResetEnable()) {
        HtUICacheUtils.beautyFaceTrimResetEnable(true);
        RxBus.get().post(HTEventAction.ACTION_SYNC_RESET, "");
      }

      switch (HtState.currentFaceTrim) {
        case EYE_ENLARGING: //大眼
          HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeEyeEnlarging,progress);
          styleNormal(progress);
          break;
        case EYE_ROUNDING:  //圆眼
          HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeEyeRounding,progress);
          styleNormal(progress);
          break;
        case CHEEK_THINNING:  //瘦脸
          HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeCheekThinning,progress);
          styleNormal(progress);
          break;
        case CHEEK_V_SHAPING:  //V脸
          HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeCheekVShaping,progress);
          styleNormal(progress);
          break;
        case CHEEK_NARROWING: //窄脸
          HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeCheekNarrowing,progress);
          styleNormal(progress);
          break;
        case CHEEK_BONE_THINNING:  //瘦颧骨
          HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeCheekboneThinning,progress);
          styleNormal(progress);
          break;
        case JAW_BONE_THINNING:  //瘦下颌骨
          HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeJawboneThinning,progress);
          styleNormal(progress);
          break;
        case TEMPLE_ENLARGING:  //丰太阳穴 *
          HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeTempleEnlarging,progress);
          styleNormal(progress);
          break;
        case head_lessening:  //小头
          HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeHeadLessening,progress);
          styleNormal(progress);
          break;
        case FACE_LESSENING:  //小脸
          HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeFaceLessening,progress);
          styleNormal(progress);
          break;
        case CHEEK_SHORTENING:  //短脸
          HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeCheekShortening,progress);
          styleNormal(progress);
          break;
        case CHIN_TRIMMING:  //下巴
          HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeChinTrimming,progress - 50);
          styleTransform(progress);
          break;
        case PHILTRUM_TRIMMING: //缩人中
          HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapePhiltrumTrimming,progress - 50);
          styleTransform(progress);
          break;
        case FOREHEAD_TRIM:  //发际线
          HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeForeheadTrimming,progress - 50);
          styleTransform(progress);
          break;
        case EYE_SAPCING:  //眼间距
          HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeEyeSpaceTrimming,progress - 50);
          styleTransform(progress);
          break;
        case EYE_CORNER_TRIMMING:  //眼角角度
          HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeEyeCornerTrimming,progress - 50);
          styleTransform(progress);
          break;
        case EYE_CORNER_ENLARGING:  // 开眼角
          HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeEyeCornerEnlarging,progress);
          styleNormal(progress);
          break;
        case NOSE_ENLARGING:  //长鼻
          HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeNoseEnlarging,progress - 50);
          styleTransform(progress);
          break;
        case NOSE_THINNING:  //瘦鼻
          HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeNoseThinning,progress);
          styleNormal(progress);
          break;
        case NOSE_APEX_LESSENING:  //鼻头 *
          HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeNoseApexLessening,progress);
          styleNormal(progress);
          break;
        case NOSE_ROOT_ENLARGING:  // 山根
          HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeNoseRootEnlarging,progress);
          styleNormal(progress);
          break;
        case MOUTH_TRIMMING:// 嘴型
          HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeMouthTrimming,progress - 50);
          styleTransform(progress);
          break;
        case MOUTH_SMILING:  //微笑嘴唇
          styleNormal(progress);
          HTEffect.shareInstance().setReshape(HtBeautyParam.HTReshapeMouthSmiling,progress);
          break;
      }

      Log.e("美型" + HtState.currentFaceTrim, progress + "");
      HtUICacheUtils.beautyFaceTrimValue(HtState.currentFaceTrim, progress);

      return;
    }

    //美颜——美发
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_HAIR) {

      styleNormal(progress);
      Log.e("美发" + HtState.currentHair.getName(), progress + "%");
      HtUICacheUtils.beautyHairValue(HtState.currentHair.getName(), progress);

      HTEffect.shareInstance().setHairStyling( HtState.currentHair.getId(),progress);
      return;
    }

    //美颜——美妆——口红
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_MAKE_UP
        && HtState.currentThirdViewState == HTViewState.MAKEUP_LIPSTICK) {

      //滑动条变化时，将重置按钮设为可选
      if (!HtUICacheUtils.beautyMakeUpResetEnable()) {
        HtUICacheUtils.beautyMakeUpResetEnable(true);
        RxBus.get().post(HTEventAction.ACTION_SYNC_RESET, "");
      }

      styleNormal(progress);
      Log.e("美妆——口红" + HtState.currentLipstick.getName(), progress + "%");
      HtUICacheUtils.beautyLipstickValue(HTMakeupEnum.HTMakeupLipstick.getValue(), HtUICacheUtils.beautyLipstickName(HTMakeupEnum.HTMakeupLipstick.getValue()), progress);
      HTEffect.shareInstance().setMakeup(HTMakeupEnum.HTMakeupLipstick.getValue(), HtUICacheUtils.beautyLipstickName(HTMakeupEnum.HTMakeupLipstick.getValue()), progress);

      return;
    }

    //美颜——美妆——眉毛
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_MAKE_UP
        && HtState.currentThirdViewState == HTViewState.MAKEUP_EYEBROW) {

      //滑动条变化时，将重置按钮设为可选
      if (!HtUICacheUtils.beautyMakeUpResetEnable()) {
        HtUICacheUtils.beautyMakeUpResetEnable(true);
        RxBus.get().post(HTEventAction.ACTION_SYNC_RESET, "");
      }

      styleNormal(progress);
      Log.e("美妆" + HtState.currentEyebrow.getName(), progress + "%");
      HtUICacheUtils.beautyLipstickValue(HTMakeupEnum.HTMakeupEyebrow.getValue(),HtUICacheUtils.beautyLipstickName(HTMakeupEnum.HTMakeupEyebrow.getValue()), progress);
      HTEffect.shareInstance().setMakeup(HTMakeupEnum.HTMakeupEyebrow.getValue(), HtUICacheUtils.beautyLipstickName(HTMakeupEnum.HTMakeupEyebrow.getValue()), progress);

      return;
    }

    //美颜——美妆——腮红
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_MAKE_UP
        && HtState.currentThirdViewState == HTViewState.MAKEUP_BLUSH) {

      //滑动条变化时，将重置按钮设为可选
      if (!HtUICacheUtils.beautyMakeUpResetEnable()) {
        HtUICacheUtils.beautyMakeUpResetEnable(true);
        RxBus.get().post(HTEventAction.ACTION_SYNC_RESET, "");
      }

      styleNormal(progress);
      Log.e("美妆" + HtState.currentBlush.getName(), progress + "%");
      HtUICacheUtils.beautyLipstickValue(HTMakeupEnum.HTMakeupBlush.getValue(),HtUICacheUtils.beautyLipstickName(HTMakeupEnum.HTMakeupBlush.getValue()), progress);
      HTEffect.shareInstance().setMakeup(HTMakeupEnum.HTMakeupBlush.getValue(), HtUICacheUtils.beautyLipstickName(HTMakeupEnum.HTMakeupBlush.getValue()), progress);

      return;
    }

    //美颜——美妆——眼影
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_MAKE_UP
        && HtState.currentThirdViewState == HTViewState.MAKEUP_EYESHADOW) {

      //滑动条变化时，将重置按钮设为可选
      if (!HtUICacheUtils.beautyMakeUpResetEnable()) {
        HtUICacheUtils.beautyMakeUpResetEnable(true);
        RxBus.get().post(HTEventAction.ACTION_SYNC_RESET, "");
      }

      styleNormal(progress);
      Log.e("美妆" + HtState.currentEyeshadow.getName(), progress + "%");
      HtUICacheUtils.beautyLipstickValue(HTMakeupEnum.HTMakeupEyeshadow.getValue(),HtUICacheUtils.beautyLipstickName(HTMakeupEnum.HTMakeupEyeshadow.getValue()), progress);
      HTEffect.shareInstance().setMakeup(HTMakeupEnum.HTMakeupEyeshadow.getValue(), HtUICacheUtils.beautyLipstickName(HTMakeupEnum.HTMakeupEyeshadow.getValue()), progress);

      return;
    }

    //美颜——美妆——眼线
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_MAKE_UP
        && HtState.currentThirdViewState == HTViewState.MAKEUP_EYELINE) {

      //滑动条变化时，将重置按钮设为可选
      if (!HtUICacheUtils.beautyMakeUpResetEnable()) {
        HtUICacheUtils.beautyMakeUpResetEnable(true);
        RxBus.get().post(HTEventAction.ACTION_SYNC_RESET, "");
      }

      styleNormal(progress);
      Log.e("美妆" + HtState.currentEyeline.getName(), progress + "%");
      HtUICacheUtils.beautyLipstickValue(HTMakeupEnum.HTMakeupEyeline.getValue(),HtUICacheUtils.beautyLipstickName(HTMakeupEnum.HTMakeupEyeline.getValue()), progress);
      HTEffect.shareInstance().setMakeup(HTMakeupEnum.HTMakeupEyeline.getValue(), HtUICacheUtils.beautyLipstickName(HTMakeupEnum.HTMakeupEyeline.getValue()), progress);

      return;
    }

    //美颜——美妆——睫毛
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_MAKE_UP
        && HtState.currentThirdViewState == HTViewState.MAKEUP_EYELASH) {
      //滑动条变化时，将重置按钮设为可选
      if (!HtUICacheUtils.beautyMakeUpResetEnable()) {
        HtUICacheUtils.beautyMakeUpResetEnable(true);
        RxBus.get().post(HTEventAction.ACTION_SYNC_RESET, "");
      }

      styleNormal(progress);
      Log.e("美妆" + HtState.currentEyelash.getName(), progress + "%");
      HtUICacheUtils.beautyLipstickValue(HTMakeupEnum.HTMakeupEyelash.getValue(),HtUICacheUtils.beautyLipstickName(HTMakeupEnum.HTMakeupEyelash.getValue()), progress);
      HTEffect.shareInstance().setMakeup(HTMakeupEnum.HTMakeupEyelash.getValue(), HtUICacheUtils.beautyLipstickName(HTMakeupEnum.HTMakeupEyelash.getValue()), progress);

      return;
    }

    //美颜——美妆——美瞳
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_MAKE_UP
        && HtState.currentThirdViewState == HTViewState.MAKEUP_BEAUTYPUPILS) {

      //滑动条变化时，将重置按钮设为可选
      if (!HtUICacheUtils.beautyMakeUpResetEnable()) {
        HtUICacheUtils.beautyMakeUpResetEnable(true);
        RxBus.get().post(HTEventAction.ACTION_SYNC_RESET, "");
      }

      styleNormal(progress);
      Log.e("美妆" + HtState.currentPupils.getName(), progress + "%");
      HtUICacheUtils.beautyLipstickValue(HTMakeupEnum.HTMakeupPupils.getValue(),HtUICacheUtils.beautyLipstickName(HTMakeupEnum.HTMakeupPupils.getValue()), progress);
      HTEffect.shareInstance().setMakeup(HTMakeupEnum.HTMakeupPupils.getValue(), HtUICacheUtils.beautyLipstickName(HTMakeupEnum.HTMakeupPupils.getValue()), progress);

      return;
    }




    //美颜——美体
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_BODY) {

      //滑动条变化时，将重置按钮设为可选
      if (!HtUICacheUtils.beautyBodyResetEnable()) {
        HtUICacheUtils.beautyBodyResetEnable(true);
        RxBus.get().post(HTEventAction.ACTION_SYNC_RESET, "");
      }

      switch (HtState.currentBody) {
        case LONG_LEG: //长腿
          HTEffect.shareInstance().setBodyBeauty(HTBodyBeautyEnum.HTBodyBeautyLegSlimming.getValue(),progress);
          styleNormal(progress);
          break;
        case THIN:  //瘦身
          HTEffect.shareInstance().setBodyBeauty(HTBodyBeautyEnum.HTBodyBeautyBodyThinning.getValue(),progress);
          styleNormal(progress);
          break;
      }

      Log.e("美体" + HtState.currentBody, progress + "");
      HtUICacheUtils.beautyBodyValue(HtState.currentBody, progress);

      return;
    }

    //AR抠图——水印
    if (HtState.currentViewState == HTViewState.AR
        && HtState.currentSecondViewState == HTViewState.AR_WATERMARK) {

      // styleNormal(progress);
      // Log.e("水印透明度",  progress + "%");
      // HtSelectedPosition.VALUE_WATERMARK_ALPHA = progress;
      //todo 设置水印透明度的接口
      return;
    }

    //人像抠图——绿幕抠图——相似度
    if (HtState.currentViewState == HTViewState.PORTRAIT
        && HtState.currentSecondViewState == HTViewState.GREENSCREEN_SIMILARITY) {

      //滑动条变化时，将重置按钮设为可选
      if (!HtUICacheUtils.greenscreenResetEnable()) {
        HtUICacheUtils.greenscreenResetEnable(true);
        RxBus.get().post(HTEventAction.ACTION_SYNC_RESET, "");
      }
      styleNormal(progress);
      Log.e("绿幕抠图——相似度", progress + "%");
      HtSelectedPosition.VALUE_SIMILARITY = progress;
      // HtUICacheUtils.beautySimilarityValue(progress);

      HTEffect.shareInstance().setChromaKeyingParams(0, progress);
      return;
    }

    //人像抠图——绿幕抠图——平滑度
    if (HtState.currentViewState == HTViewState.PORTRAIT
        && HtState.currentSecondViewState == HTViewState.GREENSCREEN_SMOOTHNESS) {
      //滑动条变化时，将重置按钮设为可选
      if (!HtUICacheUtils.greenscreenResetEnable()) {
        HtUICacheUtils.greenscreenResetEnable(true);
        RxBus.get().post(HTEventAction.ACTION_SYNC_RESET, "");
      }
      styleNormal(progress);
      Log.e("绿幕抠图——平滑度", progress + "%");
      // HtUICacheUtils.beautySmoothnessValue(progress);
      HtSelectedPosition.VALUE_SMOOTHNESS = progress;

      HTEffect.shareInstance().setChromaKeyingParams(1, progress);
      return;
    }

    //人像抠图——绿幕抠图——祛色度
    if (HtState.currentViewState == HTViewState.PORTRAIT
        && HtState.currentSecondViewState == HTViewState.GREENSCREEN_DECOLOR) {
      //滑动条变化时，将重置按钮设为可选
      if (!HtUICacheUtils.greenscreenResetEnable()) {
        HtUICacheUtils.greenscreenResetEnable(true);
        RxBus.get().post(HTEventAction.ACTION_SYNC_RESET, "");
      }
      styleNormal(progress);
      Log.e("绿幕抠图——祛色度", progress + "%");
      // HtUICacheUtils.beautyAlphaValue(progress);
      HtSelectedPosition.VALUE_DECOLOR = progress;
      HTEffect.shareInstance().setChromaKeyingParams(2, progress);
      return;
    }

    //人像抠图——绿幕抠图——透明度
    if (HtState.currentViewState == HTViewState.PORTRAIT
            && HtState.currentSecondViewState == HTViewState.GREENSCREEN_ALPHA) {
      //滑动条变化时，将重置按钮设为可选
      if (!HtUICacheUtils.greenscreenResetEnable()) {
        HtUICacheUtils.greenscreenResetEnable(true);
        RxBus.get().post(HTEventAction.ACTION_SYNC_RESET, "");
      }
      styleNormal(progress);
      Log.e("绿幕抠图——透明度", progress + "%");
      // HtUICacheUtils.beautyAlphaValue(progress);
      HtSelectedPosition.VALUE_ALPHA = progress;
      HTEffect.shareInstance().setChromaKeyingParams(3, progress);
      return;
    }

    //美颜——滤镜
    // if (HtState.currentViewState == HTViewState.BEAUTY
    //     && HtState.currentSecondViewState == HTViewState.FILTER) {
    //
    //   styleNormal(progress);
    //   Log.e("滤镜" + HtState.currentStyleFilter.getName(), progress + "%");
    //   HtUICacheUtils.beautyFilterValue(HtState.currentStyleFilter.getName(), progress);
    //
    //   HTEffect.shareInstance().setFilter(HTFilterEnum.HTFilterBeauty.getValue(), HtState.currentStyleFilter.getName());
    //   return;
    // }

    //妆容推荐
    // if (HtState.currentViewState == HTViewState.MAKE_UP) {
    //   styleNormal(progress);
    //   Log.e("妆容推荐" + HtState.currentMakeup.name(), progress + "%");
    //   HtUICacheUtils.beautyMakeupValue(HtState.currentMakeup.getName(getContext()), progress);
    //   HTEffect.shareInstance().setMakeup(HtState.currentMakeup.getLightMakeup(),progress);
    //   return;
    // }

  }

  /**
   * 开始拖动时显示数字
   * @param seekBar
   */
  @Override public void onStartTrackingTouch(SeekBar seekBar) {
    htBubbleTV.setVisibility(View.VISIBLE);

  }

  /**
   * 停止拖动时隐藏数字
   * @param seekBar
   */
  @Override public void onStopTrackingTouch(SeekBar seekBar) {
    htBubbleTV.setVisibility(View.GONE);
    RxBus.get().post(HTEventAction.ACTION_SYNC_ITEM_CHANGED, "");
    //RxBus.get().post(HTEventAction.ACTION_STYLE_SELECTED,"");
  }

  /**
   * 滑动到该参数,参数区域 0~100
   * @param progress
   */
  private void styleNormal(final int progress) {
    htMiddleV.setVisibility(GONE);

    final CharSequence percent = new StringBuilder().append(progress).append("");

    htNumberTV.setText(percent);

    //防止第一次获取不到mtSeekBar的宽度

    if (htSeekBarWidth <= 0) {
      htSeekBar.post(new Runnable() {
        @Override
        public void run() {
          htSeekBarWidth = htSeekBar.getWidth();

          float width = htSeekBar.getWidth() - (DpUtils.dip2px(34) + 0.5f);

          htBubbleTV.setText(percent);
          htBubbleTV.setX(width / 100 * progress + (DpUtils.dip2px(1) + 0.5f));

          htProgressV.setVisibility(VISIBLE);
          ViewGroup.LayoutParams layoutParams = htProgressV.getLayoutParams();
          layoutParams.width = (int) (width / 100 * progress);
          htProgressV.setLayoutParams(layoutParams);
        }
      });
    } else {
      float width = htSeekBarWidth - (DpUtils.dip2px(34) + 0.5f);

      htBubbleTV.setText(percent);
      htBubbleTV.setX(width / 100 * progress + (DpUtils.dip2px(1) + 0.5f));

      htProgressV.setVisibility(VISIBLE);
      ViewGroup.LayoutParams layoutParams = htProgressV.getLayoutParams();
      htProgressV.setX(getContext().getResources().getDisplayMetrics().density * 16f + 0.5f);
      layoutParams.width = (int) (width / 100 * progress);
      htProgressV.setLayoutParams(layoutParams);
    }

  }

  /**
   * 滑动到该参数,参数区域 -50~50
   * @param progress
   */
  private void styleTransform(int progress) {
    htMiddleV.setVisibility((progress > 48 && progress < 52) ? GONE : VISIBLE);

    CharSequence percent = new StringBuilder().append(progress - 50);

    htNumberTV.setText(percent);

    float width = htSeekBar.getWidth() - (DpUtils.dip2px(34));

    htBubbleTV.setText(percent);
    htBubbleTV.setX(width / 100 * progress + (DpUtils.dip2px(1)));

    htProgressV.setVisibility(VISIBLE);
    ViewGroup.LayoutParams layoutParams = htProgressV.getLayoutParams();

    if (progress < 51) {
      htProgressV.setX(width / 100 * progress + (DpUtils.dip2px(16)));
      layoutParams.width = (int) (width * (50 - progress) / 100);
    } else {
      htProgressV.setX(width / 2 + (DpUtils.dip2px(16)));
      layoutParams.width = (int) (width * (progress - 50) / 100);
    }

    htProgressV.setLayoutParams(layoutParams);
  }

}




