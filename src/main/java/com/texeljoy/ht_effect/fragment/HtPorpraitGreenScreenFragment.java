package com.texeljoy.ht_effect.fragment;

import android.os.Bundle;
import android.widget.Toast;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.shizhefei.fragment.LazyFragment;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.adapter.HtGreenScreenAdapter;
import com.texeljoy.ht_effect.model.HtGreenScreenConfig;
import com.texeljoy.ht_effect.model.HtGreenScreenConfig.HtGreenScreen;
import com.texeljoy.ht_effect.utils.HtConfigCallBack;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import java.util.ArrayList;
import java.util.List;

/**
 * AI抠图——绿幕抠图
 */
public class HtPorpraitGreenScreenFragment extends LazyFragment {

    private final List<HtGreenScreenConfig.HtGreenScreen> items = new ArrayList<>();


    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);

        setContentView(R.layout.fragment_ht_sticker);

        if (getContext() == null) return;

        items.clear();
        items.add(HtGreenScreenConfig.HtGreenScreen.NO_GreenScreen);
        //items.add(new HtGreenScreenConfig.HtGreenScreen(HtGreenScreenAdapter.EDIT_GREEN_SCREEN, "", HTDownloadState.COMPLETE_DOWNLOAD));

        HtGreenScreenConfig greenScreenList = HtConfigTools.getInstance().getGreenScreenList();

        if (greenScreenList != null && greenScreenList.getGreenScreens() != null && greenScreenList.getGreenScreens().size() != 0) {
            items.addAll(greenScreenList.getGreenScreens());
            initRecyclerView();
        } else {
            HtConfigTools.getInstance().getGreenScreenConfig(new HtConfigCallBack<List<HtGreenScreen>>() {
                @Override public void success(List<HtGreenScreenConfig.HtGreenScreen> list) {
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
        RecyclerView htGreenScreenRV = (RecyclerView) findViewById(R.id.htRecyclerView);
        HtGreenScreenAdapter greenScreenAdapter = new HtGreenScreenAdapter(items);
        htGreenScreenRV.setLayoutManager(new GridLayoutManager(getContext(), 5));
        htGreenScreenRV.setAdapter(greenScreenAdapter);
    }
}
