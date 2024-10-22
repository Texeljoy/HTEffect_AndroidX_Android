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
import com.texeljoy.ht_effect.adapter.HtStickerAdapter;
import com.texeljoy.ht_effect.base.HtBaseLazyFragment;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HtStickerConfig;
import com.texeljoy.ht_effect.model.HtStickerConfig.HtSticker;
import com.texeljoy.ht_effect.utils.HtConfigCallBack;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import com.texeljoy.ht_effect.utils.HtSelectedPosition;
import java.util.ArrayList;
import java.util.List;

/**
 * AR道具——贴纸
 */
public class HtStickerFragment extends HtBaseLazyFragment {

  private final List<HtStickerConfig.HtSticker> items = new ArrayList<>();
  HtStickerAdapter stickerkAdapter;

  @Override protected int getLayoutId() {
    return R.layout.fragment_ht_sticker;
  }

  @Override protected void initView(View view, Bundle savedInstanceState) {
    if (getContext() == null) return;

    items.clear();
    // items.add(HtSticker.NO_STICKER);

    HtStickerConfig stickerList = HtConfigTools.getInstance().getStickerList();

    if (stickerList != null && stickerList.getStickers() != null && stickerList.getStickers().size() != 0) {
      items.addAll(stickerList.getStickers());
      // items.add(HtSticker.NEW_STICKER);
      initRecyclerView();
    } else {
      HtConfigTools.getInstance().getStickersConfig(new HtConfigCallBack<List<HtSticker>>() {
        @Override public void success(List<HtStickerConfig.HtSticker> list) {
          items.addAll(list);
          // items.add(HtSticker.NEW_STICKER);
          initRecyclerView();
        }

        @Override public void fail(Exception error) {
          Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
        }
      });
    }

  }

  public void initRecyclerView() {
    RecyclerView htStickerRV = (RecyclerView) findViewById(R.id.htRecyclerView);
    stickerkAdapter = new HtStickerAdapter(items);
    htStickerRV.setLayoutManager(new GridLayoutManager(getContext(), 5));
    htStickerRV.setAdapter(stickerkAdapter);
  }

  @Subscribe(thread = EventThread.MAIN_THREAD,
             tags = { @Tag(HTEventAction.ACTION_SYNC_STICKER_ITEM_CHANGED) })
  public void changedPoint(Object o) {
    int lastposition = HtSelectedPosition.POSITION_STICKER;
    HtSelectedPosition.POSITION_STICKER = -1;
    stickerkAdapter.notifyItemChanged(lastposition);

  }




  @Override
  protected void onDestroyViewLazy() {
    super.onDestroyViewLazy();
  }
}
