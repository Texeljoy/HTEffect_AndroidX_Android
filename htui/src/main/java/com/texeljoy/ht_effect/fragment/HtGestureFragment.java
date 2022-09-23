package com.texeljoy.ht_effect.fragment;

import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.shizhefei.fragment.LazyFragment;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.adapter.HtGestureAdapter;
import com.texeljoy.ht_effect.model.HtGestureConfig;
import com.texeljoy.ht_effect.model.HtGestureConfig.HtGesture;
import com.texeljoy.ht_effect.utils.HtConfigCallBack;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import java.util.ArrayList;
import java.util.List;

/**
 * 手势内容
 */
public class HtGestureFragment extends LazyFragment {

    private final List<HtGestureConfig.HtGesture> items = new ArrayList<>();

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_ht_sticker);

        if (getContext() == null) return;

        items.clear();
        items.add(HtGestureConfig.HtGesture.NO_Gesture);

        HtGestureConfig gestureList = HtConfigTools.getInstance().getGestureList();

        if (gestureList != null && gestureList.getGestures() != null && gestureList.getGestures().size() != 0) {
            items.addAll(gestureList.getGestures());
            initRecyclerView();
        } else {
            HtConfigTools.getInstance().getGestureConfig(new HtConfigCallBack<List<HtGesture>>() {
                @Override public void success(List<HtGestureConfig.HtGesture> list) {
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
        HtGestureAdapter htGestureAdapter = new HtGestureAdapter(items);
        gestureRV.setLayoutManager(new GridLayoutManager(getContext(), 5));
        gestureRV.setAdapter(htGestureAdapter);
        htGestureAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
    }
}


