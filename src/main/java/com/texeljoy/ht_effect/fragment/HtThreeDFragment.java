package com.texeljoy.ht_effect.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.adapter.HtThreeDItemViewBinder;
import com.texeljoy.ht_effect.base.HtBaseFragment;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HTViewState;
import com.texeljoy.ht_effect.model.HtMakeup;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;
import com.texeljoy.ht_effect.utils.MyItemDecoration;
import java.util.Arrays;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * 3d
 */
public class HtThreeDFragment extends HtBaseFragment {

  private View container;
  private RecyclerView rvMakeup;
  private MultiTypeAdapter adapter;
  private AppCompatImageView returnIv;

  @Override protected int getLayoutId() {
    return R.layout.fragment_makeup;
  }

  @Override protected void initView(View view, Bundle savedInstanceState) {
    container = view.findViewById(R.id.container);
    rvMakeup = view.findViewById(R.id.rv_makeup);
    returnIv = view.findViewById(R.id.iv_return);

    rvMakeup.addItemDecoration(new
        MyItemDecoration(5)
    );

    changeTheme(null);

    adapter = new MultiTypeAdapter(Arrays.asList(HtMakeup.values()));
    adapter.register(HtMakeup.class, new HtThreeDItemViewBinder());
    rvMakeup.setAdapter(adapter);

    rvMakeup.smoothScrollToPosition(HtUICacheUtils.beautyMakeupPosition());

    returnIv.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        RxBus.get().post(HTEventAction.ACTION_CHANGE_PANEL, HTViewState.MODE);
      }
    });

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
      returnIv.setSelected(false);
    } else {
      container.setBackground(ContextCompat.getDrawable(getContext(),
          R.color.light_background));
      returnIv.setSelected(true);
    }
  }


}
