package com.texeljoy.ht_effect.fragment;

import android.os.Bundle;
import android.os.Looper;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.adapter.HtThreedAdapter;
import com.texeljoy.ht_effect.base.HtBaseLazyFragment;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HtThreedConfig;
import com.texeljoy.ht_effect.model.HtThreedConfig.HtThreed;
import com.texeljoy.ht_effect.utils.HtConfigCallBack;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import com.texeljoy.ht_effect.utils.HtSelectedPosition;
import java.util.ArrayList;
import java.util.List;

/**
 * 3d
 */
public class HtThreedFragment extends HtBaseLazyFragment {


    private final List<HtThreed> items = new ArrayList<>();
    HtThreedAdapter htThreedAdapter;

    @Override protected int getLayoutId() {
        return R.layout.fragment_ht_sticker;
    }

    @Override protected void initView(View view, Bundle savedInstanceState) {
        if (getContext() == null) return;

        items.clear();
        items.add(HtThreed.NO_Threed);

        HtThreedConfig threedList = HtConfigTools.getInstance().getThreedList();

        if (threedList != null && threedList.getThreeds() != null && threedList.getThreeds().size() != 0) {
            items.addAll(threedList.getThreeds());
            initRecyclerView();
        } else {
            HtConfigTools.getInstance().getThreedsConfig(new HtConfigCallBack<List<HtThreed>>() {
                @Override public void success(List<HtThreed> list) {
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

    @Override
    protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
        //RxBus.get().post(HTEventAction.ACTION_SHOW_GESTURE, items.get(HtSelectedPosition.POSITION_GESTURE).getHint());
    }

    public void initRecyclerView() {
        RecyclerView gestureRV = (RecyclerView) findViewById(R.id.htRecyclerView);
        htThreedAdapter = new HtThreedAdapter(items);
        gestureRV.setLayoutManager(new GridLayoutManager(getContext(), 5));
        gestureRV.setAdapter(htThreedAdapter);
        htThreedAdapter.notifyDataSetChanged();
    }

    @Subscribe(thread = EventThread.MAIN_THREAD,
               tags = { @Tag(HTEventAction.ACTION_SYNC_THREED_ITEM_CHANGED) })
    public void changedPoint(Object o) {
        int lastposition = HtSelectedPosition.POSITION_GESTURE;
        HtSelectedPosition.POSITION_GESTURE = -1;
        htThreedAdapter.notifyItemChanged(lastposition);

    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
    }
}


