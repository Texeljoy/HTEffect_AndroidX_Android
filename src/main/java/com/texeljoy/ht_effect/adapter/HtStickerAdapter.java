package com.texeljoy.ht_effect.adapter;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.dispatcher.DownloadDispatcher;
import com.liulishuo.okdownload.core.listener.DownloadListener2;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.model.HTDownloadState;
import com.texeljoy.ht_effect.model.HtStickerConfig;
import com.texeljoy.ht_effect.utils.HtSelectedPosition;
import com.texeljoy.ht_effect.utils.HtUnZip;
import com.texeljoy.hteffect.HTEffect;
import com.texeljoy.hteffect.model.HTItemEnum;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 贴纸Item的适配器
 */
public class HtStickerAdapter extends RecyclerView.Adapter<HtStickerViewHolder> {

    private final int ITEM_TYPE_ONE = 1;

    private final int ITEM_TYPE_TWO = 2;

    private int selectedPosition = HtSelectedPosition.POSITION_STICKER;
    private int lastPosition;

    private final List<HtStickerConfig.HtSticker> stickerList;

    private Handler handler = new Handler();

    private Map<String, String> downloadingStickers = new ConcurrentHashMap<>();

    public HtStickerAdapter(List<HtStickerConfig.HtSticker> stickerList) {
        this.stickerList = stickerList;
        DownloadDispatcher.setMaxParallelRunningCount(5);
    }

    @Override
    public int getItemViewType(int position) {
        // if (position == 0 || position == stickerList.size() - 1) {
        //     return ITEM_TYPE_ONE;
        // } else {
            return ITEM_TYPE_TWO;
        // }
    }

    @NonNull
    @Override
    public HtStickerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        // if (viewType == ITEM_TYPE_ONE) {
        //     view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ht_sticker_one, parent, false);
        // } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ht_sticker, parent, false);
        // }
        return new HtStickerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HtStickerViewHolder holder, int position) {

        final HtStickerConfig.HtSticker htSticker = stickerList.get(holder.getAdapterPosition());
        selectedPosition = HtSelectedPosition.POSITION_STICKER;
        if (selectedPosition == position) {
            holder.itemView.setSelected(true);
        } else {
            holder.itemView.setSelected(false);
        }

        //显示封面
        if (htSticker == HtStickerConfig.HtSticker.NO_STICKER) {
            holder.thumbIV.setImageResource(R.mipmap.icon_ht_none_sticker);
        } else {
            Glide.with(holder.itemView.getContext())
                .load(stickerList.get(position).getIcon())
                .placeholder(R.drawable.icon_placeholder)
                .into(holder.thumbIV);
        }

        //判断是否已经下载
        if (htSticker.isDownloaded() == HTDownloadState.COMPLETE_DOWNLOAD) {
            holder.downloadIV.setVisibility(View.GONE);
            holder.loadingIV.setVisibility(View.GONE);
            holder.loadingBG.setVisibility(View.GONE);
            holder.stopLoadingAnimation();
        } else {
            //判断是否正在下载，如果正在下载，则显示加载动画
            if (downloadingStickers.containsKey(htSticker.getName())) {
                holder.downloadIV.setVisibility(View.GONE);
                holder.loadingIV.setVisibility(View.VISIBLE);
                holder.loadingBG.setVisibility(View.VISIBLE);
                holder.startLoadingAnimation();
            } else {
                holder.downloadIV.setVisibility(View.VISIBLE);
                holder.loadingIV.setVisibility(View.GONE);
                holder.loadingBG.setVisibility(View.GONE);
                holder.stopLoadingAnimation();
            }
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //如果没有下载，则开始下载到本地
                if (htSticker.isDownloaded() == HTDownloadState.NOT_DOWNLOAD) {

                    int currentPosition = holder.getAdapterPosition();

                    //如果已经在下载了，则不操作
                    if (downloadingStickers.containsKey(htSticker.getName())) {
                        return;
                    }

                    new DownloadTask.Builder(htSticker.getUrl(), new File(HTEffect.shareInstance().getARItemPathBy(HTItemEnum.HTItemSticker.getValue())))
                            .setMinIntervalMillisCallbackProcess(30)
                            .setConnectionCount(1)
                            .build()
                            .enqueue(new DownloadListener2() {
                                @Override
                                public void taskStart(@NonNull DownloadTask task) {
                                    downloadingStickers.put(htSticker.getName(), htSticker.getUrl());
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            notifyDataSetChanged();
                                        }
                                    });
                                }

                                @Override
                                public void taskEnd(@NonNull final DownloadTask task, @NonNull EndCause cause, @Nullable final Exception realCause) {
                                    downloadingStickers.remove(htSticker.getName());

                                    if (cause == EndCause.COMPLETED) {
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                File targetDir =
                                                    new File(HTEffect.shareInstance().getARItemPathBy(HTItemEnum.HTItemSticker.getValue()));
                                                File file = task.getFile();
                                                try {
                                                    //解压到贴纸目录
                                                    HtUnZip.unzip(file, targetDir);
                                                    if (file != null) {
                                                        file.delete();
                                                    }

                                                    //修改内存与文件
                                                    htSticker.setDownloaded(HTDownloadState.COMPLETE_DOWNLOAD);
                                                    htSticker.downloaded();

                                                    HTEffect.shareInstance().setARItem(HTItemEnum.HTItemSticker.getValue(), htSticker.getName());
                                                    lastPosition = selectedPosition;
                                                    selectedPosition = currentPosition;
                                                    HtSelectedPosition.POSITION_STICKER = selectedPosition;



                                                    handler.post(new Runnable() {
                                                        @Override
                                                        public void run(){
                                                            notifyDataSetChanged();
                                                        }
                                                    });

                                                } catch (Exception e) {
                                                    if (file != null) {
                                                        file.delete();
                                                    }
                                                }
                                            }
                                        }).start();


                                    } else {
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                notifyDataSetChanged();
                                                if (realCause != null) {
                                                    Toast.makeText(holder.itemView.getContext(), realCause.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }
                                    notifyItemChanged(selectedPosition);
                                    notifyItemChanged(lastPosition);
                                }
                            });

                } else {
                    if (holder.getAdapterPosition() == RecyclerView.NO_POSITION) {
                        return;
                    }
                    if (holder.getAdapterPosition() == selectedPosition){
                        //如果点击已选中的效果，则取消效果
                        HTEffect.shareInstance().setARItem(HTItemEnum.HTItemSticker.getValue(), "");
                        HtSelectedPosition.POSITION_STICKER = -1;
                        notifyItemChanged(selectedPosition);
                        // notifyItemChanged(-1);
                    }else{
                        //如果已经下载了，则让贴纸生效
                        HTEffect.shareInstance().setARItem(HTItemEnum.HTItemSticker.getValue(), htSticker.getName());

                        //切换选中背景
                        int lastPosition = selectedPosition;
                        selectedPosition = holder.getAdapterPosition();
                        HtSelectedPosition.POSITION_STICKER = selectedPosition;
                        notifyItemChanged(selectedPosition);
                        notifyItemChanged(lastPosition);
                    }



                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return stickerList == null ? 0 : stickerList.size();
    }
}
