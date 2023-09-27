package com.texeljoy.ht_effect.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.adapter.HtMakeupStyleItemViewBinder;
import com.texeljoy.ht_effect.base.HtBaseLazyFragment;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HTViewState;
import com.texeljoy.ht_effect.model.HtMakeupStyle;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.utils.MyItemDecoration;
import java.util.Arrays;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * 风格推荐
 */
public class HtMakeUpStyleFragment extends HtBaseLazyFragment {

  private LinearLayout btnReset;
  private LinearLayout container;

  private final MultiTypeAdapter adapter = new MultiTypeAdapter();

  @Override protected int getLayoutId() {
    return R.layout.fragment_beauty_face_trim;
  }

  @Override protected void initView(
      View view,
      Bundle savedInstanceState) {

    btnReset = findViewById(R.id.btn_reset);
    container = findViewById(R.id.container);

    RecyclerView rvStyle = findViewById(R.id.rv_skin);

    rvStyle.setHasFixedSize(true);
    rvStyle.setDrawingCacheEnabled(true);
    rvStyle.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

    rvStyle.addItemDecoration(new
        MyItemDecoration(5)
    );

    findViewById(R.id.line).setVisibility(View.GONE);

    btnReset.setVisibility(View.GONE);

    adapter.setItems(Arrays.asList(HtMakeupStyle.values()));

    adapter.register(HtMakeupStyle.class, new HtMakeupStyleItemViewBinder());

    rvStyle.setAdapter(adapter);

    changeTheme("");

  }

  /**
   * 当前页面显示时 更新状态
   */
  @SuppressLint("NotifyDataSetChanged")
  @Override protected void onFragmentStartLazy() {
    super.onFragmentStartLazy();
    HtState.currentSecondViewState = HTViewState.BEAUTY_MAKE_UP_STYLE;
    RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");
  }

  /**
   * 切换主题时候的通知
   * @param o
   */
  @Subscribe(thread = EventThread.MAIN_THREAD,
             tags = { @Tag(HTEventAction.ACTION_CHANGE_THEME) })
  public void changeTheme(Object o) {
    Log.e("切换主题:", HtState.isDark ? "黑色" : "白色");
    if (HtState.isDark) {
      container.setBackground(ContextCompat.getDrawable(getContext(),
          R.color.dark_background));
    } else {
      container.setBackground(ContextCompat.getDrawable(getContext(),
          R.color.light_background));
    }
    adapter.notifyDataSetChanged();
  }
}
