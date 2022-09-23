package com.texeljoy.ht_effect.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
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
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HTViewState;
import com.texeljoy.ht_effect.model.HtBeautyKey;
import com.texeljoy.ht_effect.model.HtBeautyParam;
import com.texeljoy.ht_effect.model.HtFilterConfig.HtFilter;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.utils.DpUtils;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;
import com.texeljoy.hteffect.HTEffect;

/**
 * 复用的Slider
 */
@SuppressWarnings("unused")
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

    //美颜——美肤——美肤
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_SKIN){

      //美颜效果未选中，隐藏滑动条
      if (HtState.getCurrentBeautySkin() == HtBeautyKey.NONE) {
        setVisibility(INVISIBLE);
        return;
      } else {
        setVisibility(VISIBLE);
        // HTEffect.shareInstance().setBeautyEnable(HtState.currentStyle.equals(HtStyle.YUAN_TU));
        // htSeekBar.setEnabled(HtState.currentStyle.equals(HtStyle.YUAN_TU));
        // if(HtState.currentStyle != HtStyle.YUAN_TU){
        //   RxBus.get().post(HTEventAction.ACTION_STYLE_SELECTED,"请先取消“风格推荐”效果");
        // }else{
        //   RxBus.get().post(HTEventAction.ACTION_STYLE_SELECTED,"");
        // }
      }


      int progress = HtUICacheUtils.beautySkinValue(HtState.getCurrentBeautySkin());
      Log.e("当前模块:", HtState.getCurrentBeautySkin().name());
      Log.e("美颜滑动参数同步:", progress + "");
      switch (HtState.getCurrentBeautySkin()) {
        case vague_blurriness:
        case precise_blurriness:
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

    //美颜——美肤——美型
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

    //美颜——滤镜
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_FILTER) {

      setVisibility(HtState.currentFilter == HtFilter.NO_FILTER ?
                    View.INVISIBLE : View.VISIBLE);
      styleNormal(HtUICacheUtils.beautyFilterValue(HtState.currentFilter.getName()));
      htSeekBar.setProgress(HtUICacheUtils.beautyFilterValue(HtState.currentFilter.getName()));
      return;
    }

    //美颜——风格推荐
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_STYLE) {
      setVisibility(INVISIBLE);
      return;
    }


    //AR道具
    if (HtState.currentViewState == HTViewState.AR) {
      setVisibility(INVISIBLE);
    }

    //轻彩妆
    // if (HtState.currentViewState == HTViewState.MAKE_UP) {
    //   setVisibility(HtState.currentMakeup == HtMakeup.NONE ?
    //                 View.INVISIBLE : View.VISIBLE);
    //   setVisibility(INVISIBLE);
    //   styleNormal(HtUICacheUtils.beautyMakeupValue(HtState.currentMakeup.getName(getContext())));
    //   htSeekBar.setProgress(HtUICacheUtils.beautyMakeupValue(HtState.currentMakeup.getName(getContext())));
    // }

    //人像抠图
    if (HtState.currentViewState == HTViewState.PORTRAIT) {
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
          R.color.white));
      DrawableCompat.setTint(bgThumb, ContextCompat.getColor(getContext(),
          R.color.white));

      htBubbleTV.setTextColor(ContextCompat.getColor(getContext(),
          R.color.light_background));

      htRenderEnableIV.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.ic_render_white_enable));
    } else {

      DrawableCompat.setTint(bgMiddle, ContextCompat.getColor(getContext(),
          R.color.dark_black));
      DrawableCompat.setTint(bgProgress, ContextCompat.getColor(getContext(),
          R.color.dark_black));
      DrawableCompat.setTint(bgThumb, ContextCompat.getColor(getContext(),
          R.color.dark_black));
      htBubbleTV.setTextColor(ContextCompat.getColor(getContext(),
          R.color.dark_black));

      htRenderEnableIV.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.ic_render_black_enable));
    }
    htMiddleV.setBackground(bgMiddle);
    htProgressV.setBackground(bgProgress);
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
        case vague_blurriness:
          styleNormal(progress);
          HTEffect.shareInstance().setBeauty(HtBeautyParam.HTBeautyBlurrySmoothing,progress);
          break;
        case precise_blurriness:
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

      //精细磨皮和精准磨皮冲突，二者选其一
      if (HtState.getCurrentBeautySkin() == HtBeautyKey.precise_blurriness) {
        HtUICacheUtils.beautySkinValue(HtBeautyKey.vague_blurriness, 0);
      }
      if (HtState.getCurrentBeautySkin() == HtBeautyKey.vague_blurriness) {
        HtUICacheUtils.beautySkinValue(HtBeautyKey.precise_blurriness, 0);
      }

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
    //美颜——滤镜
    if (HtState.currentViewState == HTViewState.BEAUTY
        && HtState.currentSecondViewState == HTViewState.BEAUTY_FILTER) {

      styleNormal(progress);
      Log.e("滤镜" + HtState.currentFilter.getName(), progress + "%");
      HtUICacheUtils.beautyFilterValue(HtState.currentFilter.getName(), progress);

      HTEffect.shareInstance().setFilter(HtState.currentFilter.getName(),progress);
      return;
    }

    //轻彩妆
    // if (HtState.currentViewState == HTViewState.MAKE_UP) {
    //   styleNormal(progress);
    //   Log.e("轻彩妆" + HtState.currentMakeup.name(), progress + "%");
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
    RxBus.get().post(HTEventAction.ACTION_STYLE_SELECTED,"");
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




