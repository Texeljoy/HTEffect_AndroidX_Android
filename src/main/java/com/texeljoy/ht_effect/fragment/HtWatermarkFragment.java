package com.texeljoy.ht_effect.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.texeljoy.ht_effect.adapter.HtWatermarkAdapter;
import com.texeljoy.ht_effect.base.HtBaseLazyFragment;
import com.texeljoy.ht_effect.model.HTDownloadState;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HtWatermarkConfig;
import com.texeljoy.ht_effect.model.HtWatermarkConfig.HtWatermark;
import com.texeljoy.ht_effect.utils.HtConfigCallBack;
import com.texeljoy.ht_effect.utils.HtConfigTools;
import com.texeljoy.ht_effect.utils.HtSelectedPosition;
import com.texeljoy.ht_effect.utils.HtUploadBitmapUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * AR道具——水印
 */
public class HtWatermarkFragment extends HtBaseLazyFragment {
    private HtWatermarkAdapter watermarkAdapter;

    private final List<HtWatermarkConfig.HtWatermark> items = new ArrayList<>();

    private static final int IMAGE_REQUEST_CODE = 0;//标题图片的选中返回

    @Override protected int getLayoutId() {
        return R.layout.fragment_ht_sticker;
    }

    @Override protected void initView(View view, Bundle savedInstanceState) {
        if (getContext() == null) return;

        items.clear();
        // items.add(HtWatermarkConfig.HtWatermark.NO_WATERMARK);

        HtWatermarkConfig watermarkList = HtConfigTools.getInstance().getWatermarkList();

        if (watermarkList != null && watermarkList.getWatermarks() != null && watermarkList.getWatermarks().size() != 0) {
            items.addAll(watermarkList.getWatermarks());
            initRecyclerView();
        } else {
            HtConfigTools.getInstance().getWatermarksConfig(new HtConfigCallBack<List<HtWatermark>>() {
                @Override public void success(List<HtWatermarkConfig.HtWatermark> list) {
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
    @Subscribe(thread = EventThread.MAIN_THREAD,
               tags = { @Tag(HTEventAction.ACTION_SYNC_WATERMARK_ITEM_CHANGED) })
    public void changedPoint(Object o) {
        int lastposition = HtSelectedPosition.POSITION_WATERMARK;
        HtSelectedPosition.POSITION_WATERMARK = -1;
        watermarkAdapter.notifyItemChanged(lastposition);

    }

    private void initRecyclerView() {
        RecyclerView htWatermarkRV = (RecyclerView) findViewById(R.id.htRecyclerView);
        watermarkAdapter = new HtWatermarkAdapter(items, new waterMarkClick() {
            @Override public void clickWaterMarkFromDisk() {
                //调起相册
                openAlbum();
            }

            @Override public void deleteWaterMarkFromDisk(int position) {
                deleteWatermark(position);
            }
        });
        htWatermarkRV.setLayoutManager(new GridLayoutManager(getContext(), 5));
        htWatermarkRV.setAdapter(watermarkAdapter);
    }

    /*
    删除水印
     */
    private void deleteWatermark(int position) {
        items.remove(items.get(position));
        // HtConfigTools.getInstance().watermarkDownload(new Gson().toJson(items));
        // watermarkAdapter.selectItem(0);
        watermarkAdapter.notifyDataSetChanged();
    }

    public interface waterMarkClick {
        //点击从相册中选择背景
        void clickWaterMarkFromDisk();
        //删除从相册选择的背景
        void deleteWaterMarkFromDisk(int position);

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
                HtWatermark htWatermark = new HtWatermark("", "", HTDownloadState.COMPLETE_DOWNLOAD,"");
                htWatermark.setFromDisk(true, requireContext(), imagePath);
                items.add(htWatermark);
                watermarkAdapter.selectItem(items.size() - 1);
                Bitmap bitmap = BitmapFactory.decodeFile(htWatermark.getIcon());
                RxBus.get().post(HTEventAction.ACTION_REMOVE_STICKER_RECT,"");
                RxBus.get().post(HTEventAction.ACTION_ADD_STICKER_RECT,bitmap);
                watermarkAdapter.notifyDataSetChanged();
            }

        }

    }









}
