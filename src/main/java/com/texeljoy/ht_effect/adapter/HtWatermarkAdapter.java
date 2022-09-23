package com.texeljoy.ht_effect.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.liulishuo.okdownload.core.dispatcher.DownloadDispatcher;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.model.HTDownloadState;
import com.texeljoy.ht_effect.model.HtWatermarkConfig;
import com.texeljoy.ht_effect.utils.HtSelectedPosition;
import com.texeljoy.hteffect.HTEffect;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 水印的Item适配器
 */
public class HtWatermarkAdapter extends RecyclerView.Adapter<HtStickerViewHolder> {

    private final int ITEM_TYPE_ONE = 1;
    private final int ITEM_TYPE_TWO = 2;

    private int selectedPosition = HtSelectedPosition.POSITION_WATERMARK;

    private final List<HtWatermarkConfig.HtWatermark> watermarkList;

    private final Map<String, String> downloadingWatermarks = new ConcurrentHashMap<>();

    public HtWatermarkAdapter(List<HtWatermarkConfig.HtWatermark> stickerList) {
        this.watermarkList = stickerList;
        DownloadDispatcher.setMaxParallelRunningCount(5);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE_ONE;
        } else {
            return ITEM_TYPE_TWO;
        }
    }

    @NonNull
    @Override
    public HtStickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_TYPE_ONE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ht_sticker_one, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ht_sticker, parent, false);
        }
        return new HtStickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HtStickerViewHolder holder, int position) {

        final HtWatermarkConfig.HtWatermark htWatermark = watermarkList.get(holder.getAdapterPosition());

        if (selectedPosition == position) {
            holder.itemView.setSelected(true);
        } else {
            holder.itemView.setSelected(false);
        }

        //显示封面
        if (htWatermark == HtWatermarkConfig.HtWatermark.NO_WATERMARK) {
            holder.thumbIV.setImageResource(R.mipmap.icon_ht_none_sticker);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(watermarkList.get(position).getIcon())
                    .into(holder.thumbIV);
        }

        htWatermark.setDownloaded(HTDownloadState.COMPLETE_DOWNLOAD);
        //判断是否已经下载
        if (htWatermark.isDownloaded() == HTDownloadState.COMPLETE_DOWNLOAD) {
            holder.downloadIV.setVisibility(View.GONE);
            holder.loadingIV.setVisibility(View.GONE);
            holder.stopLoadingAnimation();
        } else {
            //判断是否正在下载，如果正在下载，则显示加载动画
            if (downloadingWatermarks.containsKey(htWatermark.getName())) {
                holder.downloadIV.setVisibility(View.GONE);
                holder.loadingIV.setVisibility(View.VISIBLE);
                holder.startLoadingAnimation();
            } else {
                holder.downloadIV.setVisibility(View.VISIBLE);
                holder.loadingIV.setVisibility(View.GONE);
                holder.stopLoadingAnimation();
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //如果没有下载，则开始下载到本地
                if (htWatermark.isDownloaded() == HTDownloadState.NOT_DOWNLOAD) {

                    //如果已经在下载了，则不操作
                    if (downloadingWatermarks.containsKey(htWatermark.getName())) {
                        return;
                    }
                    // new DownloadTask.Builder(htWatermark.getUrl(), new File(HTEffect.shareInstance().getWatermarkPath()))
                    //         .setMinIntervalMillisCallbackProcess(30)
                    //         .build()
                    //         .enqueue(new DownloadListener2() {
                    //             @Override
                    //             public void taskStart(@NonNull DownloadTask task) {
                    //                 downloadingWatermarks.put(htWatermark.getName(), htWatermark.getUrl());
                    //                 handler.post(new Runnable() {
                    //                     @Override
                    //                     public void run() {
                    //                         notifyDataSetChanged();
                    //                     }
                    //                 });
                    //             }
                    //
                    //             @Override
                    //             public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable final Exception realCause) {
                    //                 if (cause == EndCause.COMPLETED) {
                    //                     downloadingWatermarks.remove(htWatermark.getName());
                    //
                    //                     //修改内存与文件
                    //                     htWatermark.setDownloaded(HTDownloadState.COMPLETE_DOWNLOAD);
                    //                     htWatermark.downloaded();
                    //
                    //                     handler.post(new Runnable() {
                    //                         @Override
                    //                         public void run() {
                    //                             notifyDataSetChanged();
                    //                         }
                    //                     });
                    //
                    //                 } else {
                    //                     downloadingWatermarks.remove(htWatermark.getName());
                    //
                    //                     handler.post(new Runnable() {
                    //                         @Override
                    //                         public void run() {
                    //                             notifyDataSetChanged();
                    //                             if (realCause != null) {
                    //                                 Toast.makeText(holder.itemView.getContext(), realCause.getMessage(), Toast.LENGTH_SHORT).show();
                    //                             }
                    //                         }
                    //                     });
                    //                 }
                    //             }
                    //         });
                } else {
                    if (holder.getAdapterPosition() == RecyclerView.NO_POSITION) {
                        return;
                    }

                    //如果已经下载了，则让水印生效
                    if (htWatermark == HtWatermarkConfig.HtWatermark.NO_WATERMARK) {
                        HTEffect.shareInstance().setWatermark("");
                    } else {
                        HTEffect.shareInstance().setWatermark(htWatermark.getName());
                    }
                    //切换选中背景
                    int lastPosition = selectedPosition;
                    selectedPosition = holder.getAdapterPosition();
                    HtSelectedPosition.POSITION_WATERMARK = selectedPosition;
                    notifyItemChanged(selectedPosition);
                    notifyItemChanged(lastPosition);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return watermarkList == null ? 0 : watermarkList.size();
    }
}
