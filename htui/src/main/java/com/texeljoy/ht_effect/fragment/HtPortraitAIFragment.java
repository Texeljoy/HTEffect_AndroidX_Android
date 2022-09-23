package com.texeljoy.ht_effect.fragment;

import android.os.Bundle;
import android.widget.Toast;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.shizhefei.fragment.LazyFragment;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.adapter.HtAISegmentationAdapter;
import com.texeljoy.ht_effect.model.HtAISegmentationConfig;
import com.texeljoy.ht_effect.model.HtAISegmentationConfig.HtAISegmentation;
import com.texeljoy.ht_effect.utils.HtConfigCallBack;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import java.util.ArrayList;
import java.util.List;

/**
 * 人像抠图——AI抠图
 */
public class HtPortraitAIFragment extends LazyFragment {

    private final List<HtAISegmentationConfig.HtAISegmentation> items = new ArrayList<>();
    
    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        setContentView(R.layout.fragment_ht_sticker);

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
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void initRecyclerView() {
        HtAISegmentationAdapter adapter = new HtAISegmentationAdapter(items);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.htRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
