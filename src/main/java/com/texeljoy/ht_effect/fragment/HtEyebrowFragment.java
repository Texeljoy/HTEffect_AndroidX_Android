package com.texeljoy.ht_effect.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.adapter.HtEyebrowItemViewBinder;
import com.texeljoy.ht_effect.base.HtBaseLazyFragment;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HTViewState;
import com.texeljoy.ht_effect.model.HtEyebrowConfig;
import com.texeljoy.ht_effect.model.HtMakeup;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.utils.HtConfigCallBack;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;
import com.texeljoy.ht_effect.utils.MyItemDecoration;
import com.texeljoy.hteffect.model.HTMakeupEnum;
import java.util.ArrayList;
import java.util.List;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * 滤镜
 */
public class HtEyebrowFragment extends HtBaseLazyFragment {

  private View container;

  private RecyclerView rvMakeup;
  private AppCompatTextView tvTitle;
  private RelativeLayout rlTitle;
  private View line;

  private final List<HtMakeup> items = new ArrayList<>();


  private int makeType;

  @Override protected int getLayoutId() {
    return R.layout.fragment_lipstick;
  }

  private final MultiTypeAdapter listAdapter = new MultiTypeAdapter();

  @Override protected void initView(View view, Bundle savedInstanceState) {

    container = findViewById(R.id.container);
    rvMakeup = findViewById(R.id.rv_filter);
    tvTitle = findViewById(R.id.tv_title);
    rlTitle = findViewById(R.id.rl_title);
    line = findViewById(R.id.line);

    tvTitle.setVisibility(View.VISIBLE);
    rlTitle.setVisibility(View.VISIBLE);
    line.setVisibility(View.VISIBLE);


    tvTitle.setText("眉毛");

    rvMakeup.addItemDecoration(new
        MyItemDecoration(5)
    );

    changeTheme(null);
    items.clear();
    items.add(HtMakeup.NO_MAKEUP);

    // HtMakeupConfig makeupList = HtConfigTools.getInstance().getMakeupList();
    HtEyebrowConfig eyebrowList = HtConfigTools.getInstance().getEyebrowList();


    if (eyebrowList == null) {
      HtConfigTools.getInstance().getEyebrowsConfig(new HtConfigCallBack<List<HtMakeup>>() {
        @Override public void success(List<HtMakeup> list) {
          items.addAll(list);
          initRecyclerView();
        }

        @Override public void fail(Exception error) {
          error.printStackTrace();
          Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
        }
      });
    } else {
      items.addAll(eyebrowList.getMakeups());
      initRecyclerView();
    }

  }

  public void initRecyclerView() {
    listAdapter.register(HtMakeup.class, new HtEyebrowItemViewBinder());
    listAdapter.setItems(items);
    rvMakeup.setAdapter(listAdapter);
    rvMakeup.smoothScrollToPosition(HtUICacheUtils.beautyLipstickPosition(HTMakeupEnum.HTMakeupEyebrow.getValue()));
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
      container.setBackground(ContextCompat.getDrawable(getContext(),
          R.color.dark_background));
      tvTitle.setTextColor(
          ContextCompat.getColorStateList(getContext(),
              R.color.color_selector_tab_dark));
      line.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_line));

    } else {
      container.setBackground(ContextCompat.getDrawable(getContext(),
          R.color.light_background));
      tvTitle.setTextColor(
          ContextCompat.getColorStateList(getContext(),
              R.color.color_selector_tab_light));
      line.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.black_line));
    }
  }

  @Override protected void onFragmentStartLazy() {
    super.onFragmentStartLazy();
    //更新ui状态
    HtState.currentThirdViewState = HTViewState.MAKEUP_EYEBROW;
    //同步滑动条
    RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");
    listAdapter.notifyDataSetChanged();
  }

}
