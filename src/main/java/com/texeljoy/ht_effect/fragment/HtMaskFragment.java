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
import com.texeljoy.ht_effect.adapter.HtMaskAdapter;
import com.texeljoy.ht_effect.base.HtBaseLazyFragment;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HtMaskConfig;
import com.texeljoy.ht_effect.model.HtMaskConfig.HtMask;
import com.texeljoy.ht_effect.utils.HtConfigCallBack;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import com.texeljoy.ht_effect.utils.HtSelectedPosition;
import java.util.ArrayList;
import java.util.List;

/**
 * AR道具——面具
 */
public class HtMaskFragment extends HtBaseLazyFragment {

    private final List<HtMask> items = new ArrayList<>();
    HtMaskAdapter maskAdapter;


    @Override protected int getLayoutId() {
        return R.layout.fragment_ht_sticker;
    }

    @Override protected void initView(View view, Bundle savedInstanceState) {
        if (getContext() == null) return;

        items.clear();
        // items.add(HtMask.NO_MASK);

        HtMaskConfig maskList = HtConfigTools.getInstance().getMaskList();

        if (maskList != null && maskList.getMasks() != null && maskList.getMasks().size() != 0) {
            items.addAll(maskList.getMasks());
            initRecyclerView();
        } else {
            HtConfigTools.getInstance().getMasksConfig(new HtConfigCallBack<List<HtMask>>() {
                @Override public void success(List<HtMask> list) {
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

    private void initRecyclerView() {
        RecyclerView htMaskRV = (RecyclerView) findViewById(R.id.htRecyclerView);
        maskAdapter = new HtMaskAdapter(items);
        htMaskRV.setLayoutManager(new GridLayoutManager(getContext(), 5));
        htMaskRV.setAdapter(maskAdapter);
    }

    @Subscribe(thread = EventThread.MAIN_THREAD,
               tags = { @Tag(HTEventAction.ACTION_SYNC_MASK_ITEM_CHANGED) })
    public void changedPoint(Object o) {
        int lastposition = HtSelectedPosition.POSITION_MASK;
        HtSelectedPosition.POSITION_MASK = -1;
        maskAdapter.notifyItemChanged(lastposition);

    }

}
