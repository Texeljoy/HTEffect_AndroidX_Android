package com.texeljoy.ht_effect.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.adapter.HtFunnyFilterItemViewBinder;
import com.texeljoy.ht_effect.base.HtBaseLazyFragment;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HTViewState;
import com.texeljoy.ht_effect.model.HtFunnyFilterConfig;
import com.texeljoy.ht_effect.model.HtFunnyFilterConfig.HtFunnyFilter;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.utils.HtConfigCallBack;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;
import com.texeljoy.ht_effect.utils.MyItemDecoration;
import java.util.ArrayList;
import java.util.List;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * 哈哈镜
 */
public class HtFunnyFilterFragment extends HtBaseLazyFragment {

  private View container;

  private RecyclerView rvFilter;

  private final List<HtFunnyFilter> items = new ArrayList<>();

  @Override protected int getLayoutId() {
    return R.layout.fragment_filter;
  }

  private final MultiTypeAdapter listAdapter = new MultiTypeAdapter();

  @Override protected void initView(View view, Bundle savedInstanceState) {

    container = findViewById(R.id.container);
    rvFilter = findViewById(R.id.rv_filter);

    rvFilter.addItemDecoration(new
        MyItemDecoration(5)
    );

    changeTheme(null);
    items.clear();

    HtFunnyFilterConfig filterList = HtConfigTools.getInstance().getFunnyFilterConfig();

    if (filterList == null) {
      HtConfigTools.getInstance().getFunnyFiltersConfig(new HtConfigCallBack<List<HtFunnyFilter>>() {
        @Override public void success(List<HtFunnyFilter> list) {
          items.addAll(list);
          initRecyclerView();
        }

        @Override public void fail(Exception error) {
          error.printStackTrace();
          Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
        }
      });
    } else {
      items.addAll(filterList.getFilters());
      initRecyclerView();
    }

  }

  public void initRecyclerView() {
    listAdapter.register(HtFunnyFilter.class, new HtFunnyFilterItemViewBinder());
    listAdapter.setItems(items);
    rvFilter.setAdapter(listAdapter);
    rvFilter.smoothScrollToPosition(HtUICacheUtils.getFunnyFilterPosition());
    //sync progress
    RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");
  }

  /**
   * 更换主题
   */
  @Subscribe(thread = EventThread.MAIN_THREAD,
             tags = { @Tag(HTEventAction.ACTION_CHANGE_THEME) })
  public void changeTheme(@Nullable Object o) {
    Log.e("切换主题:", HtState.isDark ? "黑色" : "白色");
    if (HtState.isDark) {
      // container.setBackground(ContextCompat.getDrawable(getContext(),
      //     R.color.dark_background));
    } else {
      // container.setBackground(ContextCompat.getDrawable(getContext(),
      //     R.color.light_background));
    }
  }

  @Override protected void onFragmentStartLazy() {
    super.onFragmentStartLazy();
    //更新ui状态
    HtState.currentSecondViewState = HTViewState.FILTER;
    HtState.currentSliderViewState = HTViewState.FILTER_HAHA;
    //同步滑动条
    RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");
    listAdapter.notifyDataSetChanged();
  }

}
