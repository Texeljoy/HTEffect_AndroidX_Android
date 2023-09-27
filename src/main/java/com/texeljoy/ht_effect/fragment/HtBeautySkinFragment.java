package com.texeljoy.ht_effect.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.core.content.ContextCompat;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.base.HtBaseLazyFragment;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HTViewState;
import com.texeljoy.ht_effect.model.HtBeauty;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;
import com.texeljoy.ht_effect.view.HtResetDialog;
import com.texeljoy.ht_effect.view.HtSkinItem;
import java.util.ArrayList;

/**
 * 美颜——美肤——美肤
 */
@SuppressWarnings("unused")
public class HtBeautySkinFragment extends HtBaseLazyFragment {

  private LinearLayout btnReset;
  private LinearLayout container;
  private android.widget.ImageView ivReset;
  private android.widget.TextView tvReset;

  private final HtResetDialog resetDialog = new HtResetDialog();

  private View line;

  private HtSkinItem btnWhite;
  private HtSkinItem btnBlur;
  private HtSkinItem btnRosiness;
  private HtSkinItem btnClearness;
  private HtSkinItem btnBrightness;
  private HtSkinItem btnUnderEyes;
  private HtSkinItem btnNasolabial;

  private ArrayList<HtSkinItem> itemViews = new ArrayList<>();

  @Override protected int getLayoutId() {
    return R.layout.fragment_beauty_skin;
  }

  @Override protected void initView(
      View view,
      Bundle savedInstanceState) {

    btnReset = findViewById(R.id.btn_reset);
    ivReset = findViewById(R.id.iv_reset);
    tvReset = findViewById(R.id.tv_reset);

    container = findViewById(R.id.container);

    line = findViewById(R.id.line);

    btnWhite = findViewById(R.id.btn_white);
    btnWhite.init(HtBeauty.whiteness);
    btnBlur = findViewById(R.id.btn_blur);
    btnBlur.init(HtBeauty.blurriness);
    btnRosiness = findViewById(R.id.btn_rosiness);
    btnRosiness.init(HtBeauty.rosiness);
    btnClearness = findViewById(R.id.btn_clearness);
    btnClearness.init(HtBeauty.clearness);
    btnBrightness = findViewById(R.id.btn_brightness);
    btnBrightness.init(HtBeauty.brightness);
    btnUnderEyes = findViewById(R.id.btn_undereye_circles);
    btnUnderEyes.init(HtBeauty.undereye_circles);

    btnNasolabial = findViewById(R.id.btn_nasolabial);
    btnNasolabial.init(HtBeauty.nasolabial);

    itemViews.add(btnWhite);
    itemViews.add(btnRosiness);
    itemViews.add(btnClearness);
    itemViews.add(btnBrightness);
    itemViews.add(btnUnderEyes);
    itemViews.add(btnNasolabial);

    btnReset.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        resetDialog.show(getChildFragmentManager(), "skin");
        //RxBus.get().post(HTEventAction.ACTION_SYNC_RESET, "");
      }
    });

    changeTheme("");

  }


  @SuppressLint("NotifyDataSetChanged")
  @Override protected void onFragmentStartLazy() {
    super.onFragmentStartLazy();
    //更新ui状态
    HtState.currentSecondViewState = HTViewState.BEAUTY_SKIN;
    //同步滑动条
    RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");
    syncReset("");
  }

  /**
   * 同步重置按钮状态
   *
   * @param message support版本Rxbus
   * 传入boolean类型会导致接收不到参数
   */
  @SuppressLint("NotifyDataSetChanged")
  @Subscribe(thread = EventThread.MAIN_THREAD,
             tags = { @Tag(HTEventAction.ACTION_SYNC_RESET) })
  public void syncReset(String message) {
    btnReset.setEnabled(HtUICacheUtils.beautySkinResetEnable());
    ivReset.setEnabled(HtUICacheUtils.beautySkinResetEnable());
    tvReset.setEnabled(HtUICacheUtils.beautySkinResetEnable());

    if (message.equals("true")) {
      for (HtSkinItem item : itemViews) {
        item.updateData();
      }
    }

    // btnBlur.update();
    RxBus.get().post(HTEventAction.ACTION_SYNC_ITEM_CHANGED, "");


  }

  /**
   * 切换主题
   *
   * @param o
   */
  @Subscribe(thread = EventThread.MAIN_THREAD,
             tags = { @Tag(HTEventAction.ACTION_CHANGE_THEME) })
  public void changeTheme(Object o) {
    if (HtState.isDark) {
      container.setBackground(ContextCompat.getDrawable(getContext(), R.color.dark_background));
      line.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.divide_line));
      ivReset.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_reset_white));
      tvReset.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.color_reset_text_white));
    } else {
      container.setBackground(ContextCompat.getDrawable(getContext(), R.color.light_background));
      line.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_gray_line));
      ivReset.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_reset_black));
      tvReset.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.color_reset_text_black));
    }
  }



}
