package com.texeljoy.ht_effect.fragment;

import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.adapter.HtGiftAdapter;
import com.texeljoy.ht_effect.base.HtBaseLazyFragment;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HtGiftConfig;
import com.texeljoy.ht_effect.model.HtGiftConfig.HtGift;
import com.texeljoy.ht_effect.utils.HtConfigCallBack;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import com.texeljoy.ht_effect.utils.HtSelectedPosition;
import java.util.ArrayList;
import java.util.List;

/**
 * AR道具——贴纸
 */
public class HtGiftFragment extends HtBaseLazyFragment {

  private final List<HtGift> items = new ArrayList<>();
  HtGiftAdapter giftAdapter;


  @Override protected int getLayoutId() {
    return R.layout.fragment_ht_sticker;
  }

  @Override protected void initView(View view, Bundle savedInstanceState) {
    if (getContext() == null) return;

    items.clear();
    // items.add(HtGift.NO_Gift);

    HtGiftConfig giftList = HtConfigTools.getInstance().getGiftList();

    if (giftList != null && giftList.getGifts() != null && giftList.getGifts().size() != 0) {
      items.addAll(giftList.getGifts());
      // items.add(HtGift.NEW_Gift);
      initRecyclerView();
    } else {
      HtConfigTools.getInstance().getGiftsConfig(new HtConfigCallBack<List<HtGift>>() {
        @Override public void success(List<HtGift> list) {
          items.addAll(list);
          // items.add(HtGift.NEW_Gift);
          initRecyclerView();
        }

        @Override public void fail(Exception error) {
          Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        }
      });
    }

  }

  public void initRecyclerView() {
    RecyclerView htGiftRV = (RecyclerView) findViewById(R.id.htRecyclerView);
    giftAdapter = new HtGiftAdapter(items);
    htGiftRV.setLayoutManager(new GridLayoutManager(getContext(), 5));
    htGiftRV.setAdapter(giftAdapter);
  }

  @Override
  protected void onDestroyViewLazy() {
    super.onDestroyViewLazy();
  }

  @Subscribe(thread = EventThread.MAIN_THREAD,
             tags = { @Tag(HTEventAction.ACTION_SYNC_GIFT_ITEM_CHANGED) })
  public void changedPoint(Object o) {
    int lastposition = HtSelectedPosition.POSITION_GIFT;
    HtSelectedPosition.POSITION_GIFT = -1;
    giftAdapter.notifyItemChanged(lastposition);

  }
}
