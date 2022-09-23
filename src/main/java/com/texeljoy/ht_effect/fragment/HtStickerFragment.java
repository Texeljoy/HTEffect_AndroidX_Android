package com.texeljoy.ht_effect.fragment;

import android.os.Bundle;
import android.widget.Toast;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.shizhefei.fragment.LazyFragment;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.adapter.HtStickerAdapter;
import com.texeljoy.ht_effect.model.HtStickerConfig;
import com.texeljoy.ht_effect.model.HtStickerConfig.HtSticker;
import com.texeljoy.ht_effect.utils.HtConfigCallBack;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import java.util.ArrayList;
import java.util.List;

/**
 * AR道具——贴纸
 */
public class HtStickerFragment extends LazyFragment {

  private final List<HtStickerConfig.HtSticker> items = new ArrayList<>();


  @Override
  protected void onCreateViewLazy(Bundle savedInstanceState) {
    super.onCreateViewLazy(savedInstanceState);

    setContentView(R.layout.fragment_ht_sticker);

    if (getContext() == null) return;

    items.clear();
    items.add(HtSticker.NO_STICKER);

    HtStickerConfig stickerList = HtConfigTools.getInstance().getStickerConfig();

    if (stickerList != null && stickerList.getStickers() != null && stickerList.getStickers().size() != 0) {
      items.addAll(stickerList.getStickers());
      initRecyclerView();
    } else {
      HtConfigTools.getInstance().getStickersConfig(new HtConfigCallBack<List<HtSticker>>() {
        @Override public void success(List<HtStickerConfig.HtSticker> list) {
          items.addAll(list);
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
    HtStickerAdapter stickerkAdapter = new HtStickerAdapter(items);
    htStickerRV.setLayoutManager(new GridLayoutManager(getContext(), 5));
    htStickerRV.setAdapter(stickerkAdapter);
  }

  @Override
  protected void onDestroyViewLazy() {
    super.onDestroyViewLazy();
  }
}
