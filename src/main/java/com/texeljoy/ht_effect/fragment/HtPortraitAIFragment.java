package com.texeljoy.ht_effect.fragment;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.adapter.HtAISegmentationAdapter;
import com.texeljoy.ht_effect.base.HtBaseLazyFragment;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HtAISegmentationConfig;
import com.texeljoy.ht_effect.model.HtAISegmentationConfig.HtAISegmentation;
import com.texeljoy.ht_effect.utils.HtConfigCallBack;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import com.texeljoy.ht_effect.utils.HtSelectedPosition;
import java.util.ArrayList;
import java.util.List;

/**
 * 人像抠图——AI抠图
 */
public class HtPortraitAIFragment extends HtBaseLazyFragment {

    private final List<HtAISegmentationConfig.HtAISegmentation> items = new ArrayList<>();
    HtAISegmentationAdapter adapter;

    @Override protected int getLayoutId() {
        return R.layout.fragment_ht_sticker;
    }

    @Override protected void initView(View view, Bundle savedInstanceState) {
        if (getContext() == null) return;

        items.clear();
        items.add(HtAISegmentationConfig.HtAISegmentation.NO_Portrait);


        HtAISegmentationConfig portraitConfig = HtConfigTools.getInstance().getAISegmentationList();
        if (portraitConfig != null) {
            items.addAll(portraitConfig.getSegmentations());
            initRecyclerView();
        } else {
            HtConfigTools.getInstance().getAISegmentationConfig(new HtConfigCallBack<List<HtAISegmentation>>() {
                @Override public void success(List<HtAISegmentationConfig.HtAISegmentation> list) {
                    items.addAll(list);
                    initRecyclerView();
                }

                @Override public void fail(Exception error) {
                    error.printStackTrace();
                    Looper.prepare();
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            });
        }

    }

    private void initRecyclerView() {
        adapter = new HtAISegmentationAdapter(items);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.htRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Subscribe(thread = EventThread.MAIN_THREAD,
               tags = { @Tag(HTEventAction.ACTION_SYNC_PORTRAITAI_ITEM_CHANGED) })
    public void changedPoint(Object o) {
        int lastposition = HtSelectedPosition.POSITION_AISEGMENTATION;
        HtSelectedPosition.POSITION_AISEGMENTATION = -1;
        adapter.notifyItemChanged(lastposition);

    }

    @Override protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
    }
}
