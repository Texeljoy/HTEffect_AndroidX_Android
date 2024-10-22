package com.texeljoy.ht_effect.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import androidx.annotation.NonNull;;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.hwangjr.rxbus.RxBus;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.listener.DownloadListener2;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.model.HTDownloadState;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HtMakeup;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;
import com.texeljoy.ht_effect.utils.HtUnZip;
import com.texeljoy.hteffect.HTEffect;
import java.io.File;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import me.drakeet.multitype.ItemViewBinder;

/**
 * 美妆子类型Item的适配器
 */
public class HtMakeUpItemViewBinder extends ItemViewBinder<HtMakeup,
    HtMakeUpItemViewBinder.ViewHolder> {

  private Map<String, String> downloadingMakeups = new ConcurrentHashMap<>();


  private Handler handler = new Handler();
  int selectPosition = 0;

  @NonNull @Override protected HtMakeUpItemViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    View root = inflater.inflate(R.layout.item_filter, parent, false);
    return new HtMakeUpItemViewBinder.ViewHolder(root);
  }

  @SuppressLint("SetTextI18n") @Override protected void
  onBindViewHolder(@NonNull HtMakeUpItemViewBinder.ViewHolder holder, @NonNull HtMakeup item) {

    selectPosition = HtUICacheUtils.getMakeupItemPositionCache(item.getIdCard());

    //根据缓存中的选中的哪一个判断当前item是否被选中
    holder.itemView.setSelected(getPosition(holder) == HtUICacheUtils.getMakeupItemPositionCache(item.getIdCard()));


    // selectedPosition = HtSelectedPosition.POSITION_STICKER;
    String currentLanguage = Locale.getDefault().getLanguage();
    if("en".equals(currentLanguage)){
      holder.name.setText(item.getTitleEn());
    }else{
      holder.name.setText(item.getTitle());
    }

    holder.name.setBackgroundColor(Color.TRANSPARENT);

    holder.name.setTextColor(HtState.isDark ? Color.WHITE : ContextCompat
        .getColor(holder.itemView.getContext(),R.color.dark_black));

    if(getPosition(holder) == 0){
      holder.thumbIV.setImageResource(R.drawable.icon_makeup_none);
    }else{
      Glide.with(holder.itemView.getContext())
          .load(item.getIcon())
          .placeholder(R.drawable.icon_placeholder)
          .into(holder.thumbIV);
      Log.d("iicon", "onBindViewHolder: "+item.getIcon());
      //本地加载美妆icon
      //holder.thumbIV.setImageDrawable(HtMakeupResEnum.values()[item.getIdCard() * 6 + getPosition(holder) - 1].getIcon(holder.itemView.getContext()));

    }

    // holder.thumbIV.setImageDrawable(HtHairEnum.values()[getPosition(holder)].getIcon(holder.itemView.getContext()));

    // holder.maker.setBackgroundColor(ContextCompat.getColor
    //     (holder.itemView.getContext(), R.color.filter_maker));

    holder.maker.setVisibility(
        holder.itemView.isSelected() ? View.VISIBLE : View.GONE
    );

    //判断是否已经下载
    if (item.isDownloaded() == HTDownloadState.COMPLETE_DOWNLOAD) {
      holder.downloadIV.setVisibility(View.GONE);
      holder.loadingIV.setVisibility(View.GONE);
      holder.loadingBG.setVisibility(View.GONE);
      holder.stopLoadingAnimation();
    } else {
      //判断是否正在下载，如果正在下载，则显示加载动画
      if (downloadingMakeups.containsKey(item.getName())) {
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
      @Override public void onClick(View view) {
        if (item.getIdCard() >= 0 && item.getIdCard() <= 2){
          HtUICacheUtils.setMakeupItemNameOrTypeCache(item.getIdCard(), String.valueOf(item.getType()));
        }else {
          HtUICacheUtils.setMakeupItemNameOrTypeCache(item.getIdCard(), item.getName());
        }

        //如果没有下载，则开始下载到本地
        if (item.isDownloaded() == HTDownloadState.NOT_DOWNLOAD) {

          // int currentPosition = holder.getAdapterPosition();
          //如果已经在下载了，则不操作
          if (downloadingMakeups.containsKey(item.getName())) {
            return;
          }
          new DownloadTask.Builder(item.getUrl(), new File(HTEffect.shareInstance().getMakeupPath(item.getIdCard())))
              .setMinIntervalMillisCallbackProcess(30)
              .setConnectionCount(1)
              .build()
              .enqueue(new DownloadListener2() {
                @Override
                public void taskStart(@NonNull DownloadTask task) {
                  downloadingMakeups.put(item.getName(), item.getUrl());

                  handler.post(new Runnable() {
                    @Override
                    public void run() {
                      getAdapter().notifyDataSetChanged();
                    }
                  });
                }

                @Override
                public void taskEnd(@NonNull final DownloadTask task, @NonNull EndCause cause, @Nullable final Exception realCause) {
                  downloadingMakeups.remove(item.getName());
                  if (cause == EndCause.COMPLETED) {
                    new Thread(new Runnable() {
                      @Override
                      public void run() {
                        File targetDir =
                            new File(HTEffect.shareInstance().getMakeupPath(item.getIdCard()));
                        File file = task.getFile();
                        try {
                          //解压到贴纸目录
                          HtUnZip.unzip(file, targetDir);
                          if (file != null) {
                            file.delete();
                          }

                          //修改内存与文件
                          item.setDownloaded(HTDownloadState.COMPLETE_DOWNLOAD);
                          item.downloaded();
                          if (item.getIdCard() >= 0 && item.getIdCard() <= 2) {
                            HTEffect.shareInstance().setMakeup(item.getIdCard(),"type",  HtUICacheUtils.getMakeupItemNameOrTypeCache(item.getIdCard()));
                            HTEffect.shareInstance().setMakeup(item.getIdCard(),"value", String.valueOf(HtUICacheUtils.getMakeupItemValueCache(item.getIdCard(),String.valueOf(item.getType()))));
                            HTEffect.shareInstance().setMakeup(item.getIdCard(),"color", HtUICacheUtils.getMakeupItemColorCache(item.getIdCard()));
                          } else {
                            HTEffect.shareInstance().setMakeup(item.getIdCard(),"name", item.getName());
                            HTEffect.shareInstance().setMakeup(item.getIdCard(),"value", String.valueOf(HtUICacheUtils.getMakeupItemValueCache(item.getIdCard(),String.valueOf(item.getName()))));
                          }

                          handler.post(new Runnable() {
                            @Override
                            public void run(){
                              getAdapter().notifyDataSetChanged();
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
                        getAdapter().notifyDataSetChanged();
                        if (realCause != null) {
                          Toast.makeText(holder.itemView.getContext(), realCause.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                      }
                    });
                  }
                  getAdapter().notifyItemChanged(HtUICacheUtils.getMakeupItemPositionCache(item.getIdCard()));
                  HtUICacheUtils.setMakeupItemPostionCache(item.getIdCard(), getPosition(holder));
                  getAdapter().notifyItemChanged(HtUICacheUtils.getMakeupItemPositionCache(item.getIdCard()));
                }
              });

        } else {
          if (holder.getAdapterPosition() == RecyclerView.NO_POSITION) {
            return;
          }
          // if (holder.getAdapterPosition() == selectedPosition){
          //   //如果点击已选中的效果，则取消效果
          //   HTEffect.shareInstance().setARItem(HTItemEnum.HTItemSticker.getValue(), "");
          //   HtSelectedPosition.POSITION_STICKER = -1;
          //   notifyItemChanged(selectedPosition);
          //   // notifyItemChanged(-1);
          // }else{
          if (item.getIdCard() >= 0 && item.getIdCard() <= 2) {
            HTEffect.shareInstance().setMakeup(item.getIdCard(), "type", HtUICacheUtils.getMakeupItemNameOrTypeCache(item.getIdCard()));
            HTEffect.shareInstance().setMakeup(item.getIdCard(), "color", HtUICacheUtils.getMakeupItemColorCache((item.getIdCard())));
            HTEffect.shareInstance().setMakeup(item.getIdCard(),"value", String.valueOf(HtUICacheUtils.getMakeupItemValueCache(item.getIdCard(), HtUICacheUtils.getMakeupItemNameOrTypeCache(item.getIdCard()))));
          } else {
            HTEffect.shareInstance().setMakeup(item.getIdCard(), "name", item.getName());
            HTEffect.shareInstance().setMakeup(item.getIdCard(),"value", String.valueOf(HtUICacheUtils.getMakeupItemValueCache(item.getIdCard(), HtUICacheUtils.getMakeupItemNameOrTypeCache(item.getIdCard()))));
          }

          getAdapter().notifyItemChanged(HtUICacheUtils.getMakeupItemPositionCache(item.getIdCard()));
          HtUICacheUtils.setMakeupItemPostionCache(item.getIdCard(), getPosition(holder));
          getAdapter().notifyItemChanged(HtUICacheUtils.getMakeupItemPositionCache(item.getIdCard()));
        }

        RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");
        // RxBus.get().post(HTEventAction.ACTION_SHOW_FILTER, "");
      }
    });

  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    public final @NonNull AppCompatTextView name;

    public final @NonNull AppCompatImageView thumbIV;

    public final @NonNull AppCompatImageView maker;

    public final @NonNull AppCompatImageView loadingBG;

    public final @NonNull AppCompatImageView loadingIV;

    public final @NonNull AppCompatImageView downloadIV;



    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      name = itemView.findViewById(R.id.tv_name);
      thumbIV = itemView.findViewById(R.id.iv_icon);
      maker = itemView.findViewById(R.id.bg_maker);
      loadingBG = itemView.findViewById(R.id.loadingBG);
      loadingIV = itemView.findViewById(R.id.loadingIV);
      downloadIV = itemView.findViewById(R.id.downloadIV);
    }
    public void startLoadingAnimation() {
      Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.loading_animation);
      loadingIV.startAnimation(animation);
    }

    public void stopLoadingAnimation() {
      loadingIV.clearAnimation();
    }
  }

}
