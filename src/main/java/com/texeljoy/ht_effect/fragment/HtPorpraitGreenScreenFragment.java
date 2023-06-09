package com.texeljoy.ht_effect.fragment;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.adapter.HtGreenScreenAdapter;
import com.texeljoy.ht_effect.base.HtBaseLazyFragment;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HTViewState;
import com.texeljoy.ht_effect.model.HtGreenScreenConfig;
import com.texeljoy.ht_effect.model.HtGreenScreenConfig.HtGreenScreen;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.utils.HtConfigCallBack;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import com.texeljoy.ht_effect.utils.HtSelectedPosition;
import java.util.ArrayList;
import java.util.List;

/**
 * AI抠图——绿幕抠图
 */
public class HtPorpraitGreenScreenFragment extends HtBaseLazyFragment {

    private final List<HtGreenScreen> items = new ArrayList<>();
    HtGreenScreenAdapter greenScreenAdapter;

    @Override protected int getLayoutId() {
        return R.layout.fragment_ht_sticker;
    }

    @Override protected void initView(View view, Bundle savedInstanceState) {
        if (getContext() == null) return;

        items.clear();
        items.add(HtGreenScreen.NO_GreenScreen);
        //items.add(new HtGreenScreenConfig.HtGreenScreen(HtGreenScreenAdapter.EDIT_GREEN_SCREEN, "", HTDownloadState.COMPLETE_DOWNLOAD));

        HtGreenScreenConfig greenScreenList = HtConfigTools.getInstance().getGreenScreenList();

        if (greenScreenList != null && greenScreenList.getGreenScreens() != null && greenScreenList.getGreenScreens().size() != 0) {
            items.addAll(greenScreenList.getGreenScreens());
            initRecyclerView();
        } else {
            HtConfigTools.getInstance().getGreenScreenConfig(new HtConfigCallBack<List<HtGreenScreen>>() {
                @Override public void success(List<HtGreenScreen> list) {
                    items.addAll(list);
                    initRecyclerView();
                }

                @Override public void fail(Exception error) {
                    Looper.prepare();
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            });
        }

    }

    @Override protected void onFragmentStartLazy() {
        // RxBus.get().post(HTEventAction.ACTION_SHOW_SCREEN_COLOR, 0);
        HtState.currentSecondViewState = HTViewState.GREENSCREEN_BACKGROUND;
        RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");


        super.onFragmentStartLazy();
    }

    private void initRecyclerView() {
        RecyclerView htGreenScreenRV = (RecyclerView) findViewById(R.id.htRecyclerView);
        greenScreenAdapter = new HtGreenScreenAdapter(items);
        htGreenScreenRV.setLayoutManager(new GridLayoutManager(getContext(), 5));
        htGreenScreenRV.setAdapter(greenScreenAdapter);
    }

    @Subscribe(thread = EventThread.MAIN_THREAD,
               tags = { @Tag(HTEventAction.ACTION_SYNC_PORTRAITTGS_ITEM_CHANGED) })
    public void changedPoint(Object o) {
        int lastposition = HtSelectedPosition.POSITION_GREEN_SCREEN;
        HtSelectedPosition.POSITION_GREEN_SCREEN = -1;
        greenScreenAdapter.notifyItemChanged(lastposition);

    }

    @Override protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        // RxBus.get().unregister(this);
    }
}
