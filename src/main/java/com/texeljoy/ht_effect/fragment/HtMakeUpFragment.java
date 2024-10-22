package com.texeljoy.ht_effect.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.adapter.HtMakeUpViewBinder;
import com.texeljoy.ht_effect.base.HtBaseLazyFragment;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HTViewState;
import com.texeljoy.ht_effect.model.HtMakeUpEnum;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;
import com.texeljoy.ht_effect.utils.MyItemDecoration;
import com.texeljoy.ht_effect.view.HtResetDialog;
import java.util.Arrays;
import java.util.List;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @ClassName HtMakeUpFragment
 * @Description TODO 美妆
 * @Author
 * @Date 2023/9/11 11:30
 */
public class HtMakeUpFragment extends HtBaseLazyFragment {
    private final MultiTypeAdapter adapter = new MultiTypeAdapter();
    RecyclerView rvMakeUp;

    private final List<HtMakeUpEnum> makeUpList;

    private android.widget.ImageView ivReset;
    private android.widget.TextView tvReset;
    private LinearLayout btnReset;
    private LinearLayout container;

    private View line;

    private final HtResetDialog resetDialog = new HtResetDialog();

    {
        makeUpList = Arrays.asList(HtMakeUpEnum.values());
    }

    @Override protected int getLayoutId() {
        return R.layout.fragment_beauty_face_trim;
    }

    @Override protected void initView(View view, Bundle savedInstanceState) {
        btnReset = findViewById(R.id.btn_reset);
        container = findViewById(R.id.container);
        rvMakeUp = findViewById(R.id.rv_skin);
        rvMakeUp.setHasFixedSize(true);
        ivReset = findViewById(R.id.iv_reset);
        tvReset = findViewById(R.id.tv_reset);
        line = findViewById(R.id.line);

        adapter.setItems(makeUpList);

        adapter.register(HtMakeUpEnum.class, new HtMakeUpViewBinder());

        rvMakeUp.addItemDecoration(new
            MyItemDecoration(5)
        );

        rvMakeUp.setAdapter(adapter);

        if (HtUICacheUtils.getBeautyMakeUpPosition() >= 0) {
            rvMakeUp.smoothScrollToPosition(HtUICacheUtils.getBeautyMakeUpPosition());
        }

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                resetDialog.show(getChildFragmentManager(), "make_up");
            }
        });

        changeTheme("");

    }

    @Subscribe(thread = EventThread.MAIN_THREAD,
               tags = { @Tag(HTEventAction.ACTION_SYNC_ITEM_CHANGED) })
    public void changedPoint(Object o) {
        adapter.notifyItemChanged(HtUICacheUtils.beautyFaceTrimPosition());
    }

    /**
     * 当前页面显示时的回调
     */
    @SuppressLint("NotifyDataSetChanged")
    @Override protected void onFragmentStartLazy() {
        super.onFragmentStartLazy();
        //更新ui状态
        HtState.currentSecondViewState = HTViewState.BEAUTY_MAKE_UP;
        HtState.currentThirdViewState = HTViewState.MAKEUP_OUT;
        //同步滑动条
        RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");
        adapter.notifyDataSetChanged();
        syncReset("");
    }

    /**
     * 同步重置按钮状态
     *
     * @param message support版本Rxbus
     * 传入boolean类型会导致接收不到参数
     */
    @SuppressLint("NotifyDataSetChanged")
    @Subscribe(thread = EventThread.MAIN_THREAD,
               tags = { @Tag(HTEventAction.ACTION_SYNC_RESET) })
    public void syncReset(String message) {

        btnReset.setEnabled(HtUICacheUtils.beautyMakeUpResetEnable());
        ivReset.setEnabled(HtUICacheUtils.beautyMakeUpResetEnable());
        tvReset.setEnabled(HtUICacheUtils.beautyMakeUpResetEnable());

        if (message.equals("true")) {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 切换主题的通知
     */
    @Subscribe(thread = EventThread.MAIN_THREAD,
               tags = { @Tag(HTEventAction.ACTION_CHANGE_THEME) })
    public void changeTheme(Object o) {
        if (HtState.isDark) {
            container.setBackground(ContextCompat.getDrawable(getContext(), R.color.dark_background));
            line.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.divide_line));
            ivReset.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_reset_white));
            tvReset.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.color_reset_text_white));
        } else {
            container.setBackground(ContextCompat.getDrawable(getContext(), R.color.light_background));
            line.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.light_gray_line));
            ivReset.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_reset_black));
            tvReset.setTextColor(ContextCompat.getColorStateList(getContext(), R.color.color_reset_text_black));
        }
        adapter.notifyDataSetChanged();
    }
}
