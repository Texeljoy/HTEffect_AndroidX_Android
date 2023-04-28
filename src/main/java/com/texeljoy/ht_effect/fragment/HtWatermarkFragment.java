package com.texeljoy.ht_effect.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
        });
        htWatermarkRV.setLayoutManager(new GridLayoutManager(getContext(), 5));
        htWatermarkRV.setAdapter(watermarkAdapter);
    }

    public interface waterMarkClick {
        //点击从相册中选择背景
        void clickWaterMarkFromDisk();

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
            handleImageBeforeKitKat(data);
        }

    }

    //对于获取用户图片选中的后处理
    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        if (uri == null) return;
        String imagePath = getImagePath(uri, null);
        if (!isPngOrJpg(imagePath)) {
            Toast.makeText(getContext(), "暂时只兼容png和jpg格式的背景图", Toast.LENGTH_SHORT).show();
            return;
        }
        HtWatermark htWatermark = new HtWatermark("", "", HTDownloadState.COMPLETE_DOWNLOAD,"");
        htWatermark.setFromDisk(true, requireContext(), imagePath);
        items.add(htWatermark);
        watermarkAdapter.selectItem(items.size() - 1);
        watermarkAdapter.notifyDataSetChanged();
    }

    /**
     * @param uri 传过来的uri
     * @param selection 规则
     * @return 路径
     */
    private String getImagePath(@NonNull Uri uri, @Nullable String selection) {
        String path = null;
        Cursor cursor = requireActivity().getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    /**
     * 用于判断格式是否正确
     *
     * @param path 路径
     * @return 结果
     */
    private boolean isPngOrJpg(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        String type = options.outMimeType;
        Log.i("添加的绿幕的背景图格式：", options.outMimeType);
        return "image/png".equals(type) || "image/jpg".equals(type) || "image/jpeg".equals(type);
    }



}
