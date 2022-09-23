package com.texeljoy.ht_effect.fragment;

import android.os.Bundle;
import android.widget.Toast;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.shizhefei.fragment.LazyFragment;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.adapter.HtWatermarkAdapter;
import com.texeljoy.ht_effect.model.HtWatermarkConfig;
import com.texeljoy.ht_effect.model.HtWatermarkConfig.HtWatermark;
import com.texeljoy.ht_effect.utils.HtConfigCallBack;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import java.util.ArrayList;
import java.util.List;

/**
 * AR道具——水印
 */
public class HtWatermarkFragment extends LazyFragment {

    private final List<HtWatermarkConfig.HtWatermark> items = new ArrayList<>();



    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        setContentView(R.layout.fragment_ht_sticker);

        if (getContext() == null) return;

        items.clear();
        items.add(HtWatermarkConfig.HtWatermark.NO_WATERMARK);

        HtWatermarkConfig watermarkList = HtConfigTools.getInstance().getWatermarkList();

        if (watermarkList != null && watermarkList.getWatermarks() != null && watermarkList.getWatermarks().size() != 0) {
            items.addAll(watermarkList.getWatermarks());
            initRecyclerView();
        } else {
            HtConfigTools.getInstance().getWaterMarkConfig(new HtConfigCallBack<List<HtWatermark>>() {
                @Override public void success(List<HtWatermarkConfig.HtWatermark> list) {
                    items.addAll(list);
                    initRecyclerView();
                }

                @Override public void fail(Exception error) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void initRecyclerView() {
        RecyclerView htWatermarkRV = (RecyclerView) findViewById(R.id.htRecyclerView);
        HtWatermarkAdapter watermarkAdapter = new HtWatermarkAdapter(items);
        htWatermarkRV.setLayoutManager(new GridLayoutManager(getContext(), 5));
        htWatermarkRV.setAdapter(watermarkAdapter);
    }

}
