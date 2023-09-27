package com.texeljoy.ht_effect.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.base.HtBaseLazyFragment;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HTViewState;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.utils.HtSelectedPosition;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;
import com.texeljoy.ht_effect.view.HtResetDialog;

public class HtGreenScreenEditFragment extends HtBaseLazyFragment {
    private ImageView ivRestore;
    private TextView tvRestore;
    private ImageView ivSimilarity;
    private TextView tvSimilarity;
    private ImageView ivSmoothness;
    private TextView tvSmoothness;
    private ImageView ivAlpha;
    private TextView tvAlpha;
    private ImageView ivDecolor;
    private TextView tvDecolor;

    private final HtResetDialog resetDialog = new HtResetDialog();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ht_green_screen_edit;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ivRestore = findViewById(R.id.iv_gs_restore);
        tvRestore = findViewById(R.id.tv_gs_restore);
        ivSimilarity = findViewById(R.id.iv_gs_similarity);
        tvSimilarity = findViewById(R.id.tv_gs_similarity);
        ivSmoothness = findViewById(R.id.iv_gs_smoothness);
        tvSmoothness = findViewById(R.id.tv_gs_smoothness);
        ivAlpha = findViewById(R.id.iv_gs_alpha);
        tvAlpha = findViewById(R.id.tv_gs_alpha);
        ivDecolor = findViewById(R.id.iv_gs_decolor);
        tvDecolor = findViewById(R.id.tv_gs_decolor);


        ivSimilarity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                whichSelected(0);
                HtUICacheUtils.beautyEditPosition(0);

            }
        });
        ivSmoothness.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                whichSelected(1);
                HtUICacheUtils.beautyEditPosition(1);

            }
        });
        ivAlpha.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                whichSelected(3);
                HtUICacheUtils.beautyEditPosition(3);

            }
        });

        ivDecolor.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                whichSelected(2);
                HtUICacheUtils.beautyEditPosition(2);

            }
        });

        ivRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetDialog.show(getChildFragmentManager(), "greenscreen");
                //RxBus.get().post(HTEventAction.ACTION_SYNC_RESET, "");
            }
        });


    }


    @SuppressLint("NotifyDataSetChanged")
    @Subscribe(thread = EventThread.MAIN_THREAD,
            tags = {@Tag(HTEventAction.ACTION_CHANGE_EDIT_ITEM)})
    public void whichSelected(int position) {
        if (position == 0) {
            ivSimilarity.setSelected(true);
            tvSimilarity.setSelected(true);
            ivSmoothness.setSelected(false);
            tvSmoothness.setSelected(false);
            ivAlpha.setSelected(false);
            tvAlpha.setSelected(false);
            ivDecolor.setSelected(false);
            tvDecolor.setSelected(false);

            HtState.currentSecondViewState = HTViewState.GREENSCREEN_SIMILARITY;

            RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");

        } else if (position == 1) {
            ivSimilarity.setSelected(false);
            tvSimilarity.setSelected(false);
            ivSmoothness.setSelected(true);
            tvSmoothness.setSelected(true);
            ivAlpha.setSelected(false);
            tvAlpha.setSelected(false);
            ivDecolor.setSelected(false);
            tvDecolor.setSelected(false);
            HtState.currentSecondViewState = HTViewState.GREENSCREEN_SMOOTHNESS;
            RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");

        } else if (position == 3) {
            ivSimilarity.setSelected(false);
            tvSimilarity.setSelected(false);
            ivSmoothness.setSelected(false);
            tvSmoothness.setSelected(false);
            ivAlpha.setSelected(true);
            tvAlpha.setSelected(true);
            ivDecolor.setSelected(false);
            tvDecolor.setSelected(false);
            HtState.currentSecondViewState = HTViewState.GREENSCREEN_ALPHA;
            RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");

        } else if (position == 2) {
            ivSimilarity.setSelected(false);
            tvSimilarity.setSelected(false);
            ivSmoothness.setSelected(false);
            tvSmoothness.setSelected(false);
            ivAlpha.setSelected(false);
            tvAlpha.setSelected(false);
            ivDecolor.setSelected(true);
            tvDecolor.setSelected(true);
            HtState.currentSecondViewState = HTViewState.GREENSCREEN_DECOLOR;
            RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");
        } else {
            ivSimilarity.setSelected(false);
            tvSimilarity.setSelected(false);
            ivSmoothness.setSelected(false);
            tvSmoothness.setSelected(false);
            ivAlpha.setSelected(false);
            tvAlpha.setSelected(false);
            ivDecolor.setSelected(false);
            tvDecolor.setSelected(false);
            RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");
        }
        HtSelectedPosition.POSITION_GREEN_SCREEN_EDIT = position;
    }

    /**
     * 同步重置按钮状态
     *
     * @param message support版本Rxbus
     *                传入boolean类型会导致接收不到参数
     */
    @SuppressLint("NotifyDataSetChanged")
    @Subscribe(thread = EventThread.MAIN_THREAD,
            tags = {@Tag(HTEventAction.ACTION_SYNC_RESET)})
    public void syncReset(String message) {
        ivRestore.setEnabled(HtUICacheUtils.greenscreenResetEnable());
        tvRestore.setEnabled(HtUICacheUtils.greenscreenResetEnable());

        if (message.equals("true")) {
            // for (HtSkinItem item : itemViews) {
            //     item.updateData();
            // }
        }

        // btnBlur.update();
        RxBus.get().post(HTEventAction.ACTION_SYNC_ITEM_CHANGED, "");

    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
        //更新ui状态
        HtState.currentViewState = HTViewState.PORTRAIT;

        // int position = HtUICacheUtils.beautyEditPosition();
        // whichSelected(position);


        // RxBus.get().post(HTEventAction.ACTION_SHOW_SCREEN_COLOR, true);
        syncReset("");
        int selectPosition = HtSelectedPosition.POSITION_GREEN_SCREEN_EDIT;
        whichSelected(selectPosition);
    }
}