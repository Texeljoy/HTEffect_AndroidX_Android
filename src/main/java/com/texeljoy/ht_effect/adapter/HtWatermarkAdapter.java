package com.texeljoy.ht_effect.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.hwangjr.rxbus.RxBus;
import com.liulishuo.okdownload.core.dispatcher.DownloadDispatcher;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.fragment.HtWatermarkFragment;
import com.texeljoy.ht_effect.model.HTDownloadState;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HtWatermarkConfig;
import com.texeljoy.ht_effect.utils.FileUtils;
import com.texeljoy.ht_effect.utils.HtSelectedPosition;
import com.texeljoy.hteffect.HTEffect;
import com.texeljoy.hteffect.model.HTItemEnum;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 水印的Item适配器
 */
public class HtWatermarkAdapter extends RecyclerView.Adapter<HtStickerViewHolder> {

    private final int ITEM_TYPE_ONE = 1;
    private final int ITEM_TYPE_TWO = 2;
    Bitmap bitmap;

    private int selectedPosition = HtSelectedPosition.POSITION_WATERMARK;
    private int lastPosition;
    private int deletePosition = -1;

    private final List<HtWatermarkConfig.HtWatermark> watermarkList;
    private final HtWatermarkFragment.waterMarkClick watermarkClick;

    private final Map<String, String> downloadingWatermarks = new ConcurrentHashMap<>();

    public HtWatermarkAdapter(List<HtWatermarkConfig.HtWatermark> stickerList, HtWatermarkFragment.waterMarkClick click) {
        this.watermarkList = stickerList;
        watermarkClick = click;
        DownloadDispatcher.setMaxParallelRunningCount(5);
        RxBus.get().register(this);
    }

    @Override
    public int getItemViewType(int position) {
        // if (position == 0) {
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

        final HtWatermarkConfig.HtWatermark htWatermark = watermarkList.get(holder.getAdapterPosition());
        selectedPosition = HtSelectedPosition.POSITION_WATERMARK;
        holder.deleteIV.setVisibility(View.GONE);



        if (selectedPosition == position) {
            holder.itemView.setSelected(true);

        } else {
            holder.itemView.setSelected(false);
        }

        //显示封面
        if (htWatermark == HtWatermarkConfig.HtWatermark.NO_WATERMARK) {
            holder.thumbIV.setImageResource(R.drawable.shuiyin_shangchuan);
        } else {
            if(position == 0){
                Glide.with(holder.itemView.getContext())
                    .load(R.drawable.shuiyin_shangchuan)
                    .into(holder.thumbIV);
            } else if (htWatermark.isFromDisk()) {
                //来自硬盘的直接加载本体图片
                Glide.with(holder.itemView.getContext())
                    .load(new File(htWatermark.getDir()))
                    .placeholder(R.drawable.icon_placeholder)
                    .into(holder.thumbIV);

            }else{
                Glide.with(holder.itemView.getContext())
                    .load(watermarkList.get(position).getIcon())
                    .placeholder(R.drawable.icon_placeholder)
                    .into(holder.thumbIV);
            }

        }

        htWatermark.setDownloaded(HTDownloadState.COMPLETE_DOWNLOAD);
        //判断是否已经下载
        if (htWatermark.isDownloaded() == HTDownloadState.COMPLETE_DOWNLOAD) {
            holder.downloadIV.setVisibility(View.GONE);
            holder.loadingIV.setVisibility(View.GONE);
            holder.loadingBG.setVisibility(View.GONE);
            holder.stopLoadingAnimation();
        } else {
            //判断是否正在下载，如果正在下载，则显示加载动画
            if (downloadingWatermarks.containsKey(htWatermark.getName())) {
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

            holder.deleteIV.setOnClickListener(new OnClickListener() {
                @Override public void onClick(View v) {
                    if (htWatermark.isFromDisk()) {
                        if(holder.getAdapterPosition() == selectedPosition){
                            //如果点击已选中的效果，则取消效果
                            HTEffect.shareInstance().setARItem(HTItemEnum.HTItemWatermark.getValue(), "");
                            RxBus.get().post(HTEventAction.ACTION_REMOVE_STICKER_RECT,"");
                            HtSelectedPosition.POSITION_WATERMARK = -1;
                            notifyItemChanged(selectedPosition);
                        }
                        htWatermark.delete(deletePosition);
                        watermarkClick.deleteWaterMarkFromDisk(deletePosition);
                        holder.itemView.setClickable(false);
                        FileUtils.deleteDirOrFile(htWatermark.getDir());
                        String path = HTEffect.shareInstance().getARItemPathBy(HTItemEnum.HTItemWatermark.getValue()) + "/" + htWatermark.getName();
                        FileUtils.deleteDirOrFile(path);

                    }

                }
            });

            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (htWatermark.isFromDisk()) {
                        if(holder.getAdapterPosition() == selectedPosition){
                            //如果点击已选中的效果，则取消效果
                            HTEffect.shareInstance().setARItem(HTItemEnum.HTItemWatermark.getValue(), "");
                            RxBus.get().post(HTEventAction.ACTION_REMOVE_STICKER_RECT,"");
                            HtSelectedPosition.POSITION_WATERMARK = -1;
                            notifyItemChanged(selectedPosition);
                        }else{
                            HTEffect.shareInstance().setARItem(HTItemEnum.HTItemWatermark.getValue(), htWatermark.getName());

                            bitmap = BitmapFactory.decodeFile(htWatermark.getDir());
                            RxBus.get().post(HTEventAction.ACTION_REMOVE_STICKER_RECT, "");
                            RxBus.get().post(HTEventAction.ACTION_ADD_STICKER_RECT, bitmap);

                            int lastPosition = selectedPosition;
                            selectedPosition = holder.getAdapterPosition();
                            HtSelectedPosition.POSITION_WATERMARK = selectedPosition;
                            notifyItemChanged(selectedPosition);
                            notifyItemChanged(lastPosition);
                        }

                    }else{
                        if(holder.getAdapterPosition() == 0){
                            watermarkClick.clickWaterMarkFromDisk();
                            return;

                        }else{
                            //如果没有下载，则开始下载到本地
                            if (htWatermark.isDownloaded() == HTDownloadState.NOT_DOWNLOAD) {

                                int currentPosition = holder.getAdapterPosition();

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
                                    HTEffect.shareInstance().setARItem(HTItemEnum.HTItemWatermark.getValue(), "");
                                    RxBus.get().post(HTEventAction.ACTION_REMOVE_STICKER_RECT,"");
                                    //切换选中背景
                                    int lastPosition = selectedPosition;
                                    selectedPosition = holder.getAdapterPosition();
                                    HtSelectedPosition.POSITION_WATERMARK = selectedPosition;
                                    notifyItemChanged(selectedPosition);
                                    notifyItemChanged(lastPosition);
                                }else if (holder.getAdapterPosition() == selectedPosition){
                                    //如果点击已选中的效果，则取消效果
                                    HTEffect.shareInstance().setARItem(HTItemEnum.HTItemWatermark.getValue(), "");
                                    RxBus.get().post(HTEventAction.ACTION_REMOVE_STICKER_RECT,"");
                                    HtSelectedPosition.POSITION_WATERMARK = -1;
                                    notifyItemChanged(selectedPosition);
                                    // notifyItemChanged(-1);
                                } else {
                                    //todo 水印

                                    HTEffect.shareInstance().setARItem(HTItemEnum.HTItemWatermark.getValue(), htWatermark.getName());
                                    // HTEffect.shareInstance().setWatermarkParam();

                                    bitmap = BitmapFactory.decodeFile(htWatermark.getIcon());
                                    RxBus.get().post(HTEventAction.ACTION_REMOVE_STICKER_RECT,"");
                                    RxBus.get().post(HTEventAction.ACTION_ADD_STICKER_RECT,bitmap);
                                    //切换选中背景
                                    int lastPosition = selectedPosition;
                                    selectedPosition = holder.getAdapterPosition();
                                    HtSelectedPosition.POSITION_WATERMARK = selectedPosition;
                                    notifyItemChanged(selectedPosition);
                                    notifyItemChanged(lastPosition);
                                }

                            }
                        }
                    }
                    if(deletePosition != -1){
                        notifyItemChanged(deletePosition);
                        deletePosition = -1;
                    }
                    RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");

                }
            });
            holder.itemView.setOnLongClickListener(new OnLongClickListener() {
                @Override public boolean onLongClick(View v) {
                    if (htWatermark.isFromDisk()) {
                        holder.deleteIV.setVisibility(View.VISIBLE);
                        deletePosition = holder.getAdapterPosition();
                    }
                    return true;
                }
            });




    }

    @Override
    public int getItemCount() {
        return watermarkList == null ? 0 : watermarkList.size();
    }

    public void selectItem(int selectedPosition) {
        int lastPosition = selectedPosition;
        this.selectedPosition = selectedPosition;
        HtSelectedPosition.POSITION_WATERMARK = selectedPosition;
        HTEffect.shareInstance().setARItem(HTItemEnum.HTItemWatermark.getValue(), watermarkList.get(selectedPosition).getName());
        notifyItemChanged(selectedPosition);
        notifyItemChanged(lastPosition);
    }
}
