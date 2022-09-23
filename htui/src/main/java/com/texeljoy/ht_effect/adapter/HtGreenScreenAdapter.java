package com.texeljoy.ht_effect.adapter;

import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.hwangjr.rxbus.RxBus;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.dispatcher.DownloadDispatcher;
import com.liulishuo.okdownload.core.listener.DownloadListener2;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.model.HTDownloadState;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HtGreenScreenConfig;
import com.texeljoy.ht_effect.model.HtGreenScreenConfig.HtGreenScreen;
import com.texeljoy.ht_effect.utils.HtSelectedPosition;
import com.texeljoy.hteffect.HTEffect;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 绿幕的Item适配器
 */
public class HtGreenScreenAdapter extends RecyclerView.Adapter<HtStickerViewHolder> {
    public static final String EDIT_GREEN_SCREEN = "EDIT_GREEN_SCREEN";
    private final int ITEM_TYPE_ONE = 1;
    private final int ITEM_TYPE_TWO = 2;

    private int selectedPosition = HtSelectedPosition.POSITION_GREEN_SCREEN;

    private final List<HtGreenScreenConfig.HtGreenScreen> greenScreenList;


    private final Handler handler = new Handler();

    private final Map<String, String> downloadingGreenScreens = new ConcurrentHashMap<>();

    public HtGreenScreenAdapter(List<HtGreenScreenConfig.HtGreenScreen> greenScreenList) {
        this.greenScreenList = greenScreenList;
        DownloadDispatcher.setMaxParallelRunningCount(5);
    }

    @Override
    public int getItemViewType(int position) {
        //if (position == 0 || position == 1) {
        if(position == 0){
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

        final HtGreenScreenConfig.HtGreenScreen htGreenScreen = greenScreenList.get(holder.getAdapterPosition());

        if (selectedPosition == position) {
            holder.itemView.setSelected(true);
        } else {
            holder.itemView.setSelected(false);
        }

        //显示封面
        if (htGreenScreen == HtGreenScreenConfig.HtGreenScreen.NO_GreenScreen) {
            holder.thumbIV.setImageResource(R.mipmap.icon_ht_none_sticker);
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(greenScreenList.get(position).getIcon())
                    .into(holder.thumbIV);
        }

        //判断是否已经下载
        if (htGreenScreen.isDownloaded() == HTDownloadState.COMPLETE_DOWNLOAD) {
            holder.downloadIV.setVisibility(View.GONE);
            holder.loadingIV.setVisibility(View.GONE);
            holder.stopLoadingAnimation();
        } else {
            //判断是否正在下载，如果正在下载，则显示加载动画
            if (downloadingGreenScreens.containsKey(htGreenScreen.getName())) {
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
                if (htGreenScreen.isDownloaded() == HTDownloadState.NOT_DOWNLOAD) {

                    //如果已经在下载了，则不操作
                    if (downloadingGreenScreens.containsKey(htGreenScreen.getName())) {
                        return;
                    }
                    new DownloadTask.Builder(htGreenScreen.getUrl(), new File(HTEffect.shareInstance().getGSSegEffectPath()))
                            .setMinIntervalMillisCallbackProcess(30)
                            .build()
                            .enqueue(new DownloadListener2() {
                                @Override
                                public void taskStart(@NonNull DownloadTask task) {
                                    downloadingGreenScreens.put(htGreenScreen.getName(), htGreenScreen.getUrl());
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            notifyDataSetChanged();
                                        }
                                    });
                                }

                                @Override
                                public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable final Exception realCause) {
                                    if (cause == EndCause.COMPLETED) {
                                        downloadingGreenScreens.remove(htGreenScreen.getName());

                                        //修改内存与文件
                                        htGreenScreen.setDownloaded(HTDownloadState.COMPLETE_DOWNLOAD);
                                        htGreenScreen.downLoaded();

                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                notifyDataSetChanged();
                                            }
                                        });

                                    } else {
                                        downloadingGreenScreens.remove(htGreenScreen.getName());

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
                                }
                            });
                } else {
                    if (holder.getAdapterPosition() == RecyclerView.NO_POSITION) {
                        return;
                    }

                    //如果已经下载了，则让水印生效
                   if (htGreenScreen == HtGreenScreen.NO_GreenScreen) {
                       HTEffect.shareInstance().setGSSegEffect("");
                   } else {
                       HTEffect.shareInstance().setGSSegEffect(htGreenScreen.getName());
                   }
                   //tiSDKManager.setGreenScreen(tiGreenScreen.getName());
                    RxBus.get().post(HTEventAction.ACTION_GREEN_SCREEN, htGreenScreen.getName());

                    //切换选中背景
                    int lastPosition = selectedPosition;
                    selectedPosition = holder.getAdapterPosition();
                    HtSelectedPosition.POSITION_GREEN_SCREEN = selectedPosition;
                    notifyItemChanged(selectedPosition);
                    notifyItemChanged(lastPosition);
                    //刷新编辑按钮状态
                    notifyItemChanged(1);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return greenScreenList == null ? 0 : greenScreenList.size();
    }
}
