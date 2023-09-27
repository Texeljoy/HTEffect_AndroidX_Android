package com.texeljoy.ht_effect.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.hwangjr.rxbus.RxBus;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HtMakeupStyle;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;
import com.texeljoy.hteffect.HTEffect;
import me.drakeet.multitype.ItemViewBinder;

/**
 * 妆容推荐Item的适配器
 */
public class HtMakeupStyleItemViewBinder extends ItemViewBinder<HtMakeupStyle,
    HtMakeupStyleItemViewBinder.ViewHolder> {

  @NonNull @Override protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    View root = inflater.inflate(R.layout.item_filter, parent, false);
    return new ViewHolder(root);
  }

  @SuppressLint("SetTextI18n")
  @Override
  protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull HtMakeupStyle item) {
    holder.downloadIV.setVisibility(View.GONE);

    holder.itemView.setSelected(getPosition(holder) ==
        HtUICacheUtils.beautyMakeUpStylePosition());

    holder.name.setText(item.getName(holder.itemView.getContext()));

    holder.name.setTextColor(HtState.isDark ? Color.WHITE : ContextCompat
        .getColor(holder.itemView.getContext(),R.color.dark_black));

    holder.icon
       .setImageDrawable(item.getIcon(holder.itemView.getContext()));

    holder.name.setBackgroundColor(Color.TRANSPARENT);


    holder.maker.setVisibility(
        holder.itemView.isSelected() ? View.VISIBLE : View.GONE
    );

    //同步滑动条
    RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (holder.itemView.isSelected()) {
          return;
        }
        HtState.currentMakeUpStyle = item;

        //应用效果
        //HTEffect.shareInstance().setMakeup(item.getLightMakeup(),100);
        // HtUICacheUtils.beautyMakeupStyleValue(item.name(), 100);

        //HtState.currentMakeup = item;

        holder.itemView.setSelected(true);
        getAdapter().notifyItemChanged(HtUICacheUtils.beautyMakeUpStylePosition());
        HtUICacheUtils.beautyMakeUpStylePosition(getPosition(holder));


        HTEffect.shareInstance().setStyle(getPosition(holder));

        if(item == HtMakeupStyle.NONE){
          HtUICacheUtils.initCache(false);
        }
        RxBus.get().post(HTEventAction.ACTION_CHANGE_ENABLE,"");
        // if(item == HtMakeupStyle.NONE){
        //   HtUICacheUtils.initCache(false);
        //   RxBus.get().post(HTEventAction.ACTION_CHANGE_ENABLE,"");
        // }else{
        //   RxBus.get().post(HTEventAction.ACTION_CHANGE_ENABLE,"");
        // }
        getAdapter().notifyItemChanged(HtUICacheUtils.beautyMakeUpStylePosition());

        // RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");
      }
    });

  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    private final @NonNull AppCompatTextView name;

    private final @NonNull AppCompatImageView icon;

    private final @NonNull AppCompatImageView maker;

    private final @NonNull AppCompatImageView downloadIV;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      name = itemView.findViewById(R.id.tv_name);
      icon = itemView.findViewById(R.id.iv_icon);
      maker = itemView.findViewById(R.id.bg_maker);
      downloadIV = itemView.findViewById(R.id.downloadIV);
    }

  }
}
