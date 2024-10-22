package com.texeljoy.ht_effect.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Looper;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;
import com.hwangjr.rxbus.RxBus;
import com.hwangjr.rxbus.annotation.Subscribe;
import com.hwangjr.rxbus.annotation.Tag;
import com.hwangjr.rxbus.thread.EventThread;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.adapter.HtGreenScreenAdapter;
import com.texeljoy.ht_effect.base.HtBaseLazyFragment;
import com.texeljoy.ht_effect.model.HTDownloadState;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HTViewState;
import com.texeljoy.ht_effect.model.HtGreenScreenConfig;
import com.texeljoy.ht_effect.model.HtGreenScreenConfig.HtGreenScreen;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.utils.HtConfigCallBack;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import com.texeljoy.ht_effect.utils.HtSelectedPosition;
import com.texeljoy.ht_effect.utils.HtUploadBitmapUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * AI抠图——绿幕抠图
 */
public class HtPorpraitGreenScreenFragment extends HtBaseLazyFragment {

    private final List<HtGreenScreenConfig.HtGreenScreen> items = new ArrayList<>();
    HtGreenScreenAdapter greenScreenAdapter;
    private static final int IMAGE_REQUEST_CODE = 0;//标题图片的选中返回

    @Override protected int getLayoutId() {
        return R.layout.fragment_ht_sticker;
    }

    @Override protected void initView(View view, Bundle savedInstanceState) {
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
        greenScreenAdapter = new HtGreenScreenAdapter(items, new greenScreenClick() {
            @Override public void clickGreenScreenFromDisk() {
                //调起相册
                openAlbum();
            }

            @Override public void deleteGreenScreenFromDisk(int position) {
                deleteGreenScreen(position);

            }
        });
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

    /**
     * 打开相册
     */
    private void openAlbum() {
        //检测权限
        if (ContextCompat.checkSelfPermission(requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[] {
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
        }
        //调取系统相册
        Intent intent = new Intent(Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, IMAGE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || data.getData() == null) return;
        if (requestCode == IMAGE_REQUEST_CODE) {
            String imagePath = HtUploadBitmapUtils.handleImageBeforeKitKat(data, getActivity());

            if(imagePath != null){
                HtGreenScreen htGreenScreen = new HtGreenScreen("", "", HTDownloadState.COMPLETE_DOWNLOAD,"");
                htGreenScreen.setFromDisk(true, requireContext(), imagePath);
                items.add(htGreenScreen);
                greenScreenAdapter.selectItem(items.size() - 1);
                greenScreenAdapter.notifyDataSetChanged();
            }

        }

    }

    /*
    删除绿幕
     */
    private void deleteGreenScreen(int position) {
        items.remove(items.get(position));
        // HtConfigTools.getInstance().watermarkDownload(new Gson().toJson(items));
        // watermarkAdapter.selectItem(0);
        greenScreenAdapter.notifyDataSetChanged();
    }

    public interface greenScreenClick {
        //点击从相册中选择背景
        void clickGreenScreenFromDisk();
        //删除从相册选择的背景
        void deleteGreenScreenFromDisk(int position);

    }

    @Override protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        // RxBus.get().unregister(this);
    }
}
