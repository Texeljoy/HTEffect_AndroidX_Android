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
import com.texeljoy.ht_effect.adapter.HtGestureAdapter;
import com.texeljoy.ht_effect.base.HtBaseLazyFragment;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HtGestureConfig;
import com.texeljoy.ht_effect.model.HtGestureConfig.HtGesture;
import com.texeljoy.ht_effect.utils.HtConfigCallBack;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import com.texeljoy.ht_effect.utils.HtSelectedPosition;
import java.util.ArrayList;
import java.util.List;

/**
 * 手势
 */
public class HtGestureFragment extends HtBaseLazyFragment {


    private final List<HtGesture> items = new ArrayList<>();
    HtGestureAdapter htGestureAdapter;

    @Override protected int getLayoutId() {
        return R.layout.fragment_ht_sticker;
    }

    @Override protected void initView(View view, Bundle savedInstanceState) {
        if (getContext() == null) return;

        items.clear();
        items.add(HtGesture.NO_Gesture);

        HtGestureConfig gestureList = HtConfigTools.getInstance().getGestureList();

        if (gestureList != null && gestureList.getGestures() != null && gestureList.getGestures().size() != 0) {
            items.addAll(gestureList.getGestures());
            initRecyclerView();
        } else {
            HtConfigTools.getInstance().getGestureConfig(new HtConfigCallBack<List<HtGesture>>() {
                @Override public void success(List<HtGesture> list) {
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
        htGestureAdapter = new HtGestureAdapter(items);
        gestureRV.setLayoutManager(new GridLayoutManager(getContext(), 5));
        gestureRV.setAdapter(htGestureAdapter);
        htGestureAdapter.notifyDataSetChanged();
    }

    @Subscribe(thread = EventThread.MAIN_THREAD,
               tags = { @Tag(HTEventAction.ACTION_SYNC_GESTURE_ITEM_CHANGED) })
    public void changedPoint(Object o) {
        int lastposition = HtSelectedPosition.POSITION_GESTURE;
        HtSelectedPosition.POSITION_GESTURE = -1;
        htGestureAdapter.notifyItemChanged(lastposition);

    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
    }
}


