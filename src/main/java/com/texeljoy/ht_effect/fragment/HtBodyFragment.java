package com.texeljoy.ht_effect.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.adapter.HtBodyItemViewBinder;
import com.texeljoy.ht_effect.base.HtBaseLazyFragment;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HTViewState;
import com.texeljoy.ht_effect.model.HtBody;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;
import com.texeljoy.ht_effect.utils.MyItemDecoration;
import com.texeljoy.ht_effect.view.HtResetDialog;
import java.util.Arrays;
import java.util.List;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * 美颜——美肤——美型
 */
@SuppressWarnings("unused")
public class HtBodyFragment extends HtBaseLazyFragment {

  private final MultiTypeAdapter adapter = new MultiTypeAdapter();

  private final List<HtBody> mData;

  private android.widget.ImageView ivReset;
  private android.widget.TextView tvReset;
  private LinearLayout btnReset;
  private LinearLayout container;

  private View line;

  private final HtResetDialog resetDialog = new HtResetDialog();

  {
    mData = Arrays.asList(HtBody.values());
  }

  @Override protected int getLayoutId() {
    return R.layout.fragment_beauty_face_trim;
  }

  @Override protected void initView(
      View view,
      Bundle savedInstanceState) {

    btnReset = findViewById(R.id.btn_reset);
    container = findViewById(R.id.container);
    RecyclerView rvFaceTrim = findViewById(R.id.rv_skin);
    rvFaceTrim.setHasFixedSize(true);
    ivReset = findViewById(R.id.iv_reset);
    tvReset = findViewById(R.id.tv_reset);
    line = findViewById(R.id.line);

    adapter.setItems(mData);

    adapter.register(HtBody.class, new HtBodyItemViewBinder());

    rvFaceTrim.addItemDecoration(new
        MyItemDecoration(5)
    );

    rvFaceTrim.setAdapter(adapter);

    btnReset.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        resetDialog.show(getChildFragmentManager(), "face_trim");
      }
    });

    changeTheme("");


  }


  @Subscribe(thread = EventThread.MAIN_THREAD,
             tags = { @Tag(HTEventAction.ACTION_SYNC_ITEM_CHANGED) })
  public void changedPoint(Object o) {
    adapter.notifyItemChanged(HtUICacheUtils.beautyBodyPosition());
  }

  /**
   * 当前页面显示时的回调
   */
  @SuppressLint("NotifyDataSetChanged")
  @Override protected void onFragmentStartLazy() {
    super.onFragmentStartLazy();
    //更新ui状态
    HtState.currentSecondViewState = HTViewState.BEAUTY_BODY;
    //同步滑动条
    RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");
    adapter.notifyDataSetChanged();
    syncReset("");
  }

  /**
   * 同步重置按钮状态
   * @param message
   * support版本Rxbus
   * 传入boolean类型会导致接收不到参数
   */
  @SuppressLint("NotifyDataSetChanged")
  @Subscribe(thread = EventThread.MAIN_THREAD,
             tags = { @Tag(HTEventAction.ACTION_SYNC_RESET) })
  public void syncReset(String message) {

    btnReset.setEnabled(HtUICacheUtils.beautyBodyResetEnable());
    ivReset.setEnabled(HtUICacheUtils.beautyBodyResetEnable());
    tvReset.setEnabled(HtUICacheUtils.beautyBodyResetEnable());

    if (message.equals("true")) {
      adapter.notifyDataSetChanged();
    }
  }

  /**
   * 切换主题的通知
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
    adapter.notifyDataSetChanged();
  }
}