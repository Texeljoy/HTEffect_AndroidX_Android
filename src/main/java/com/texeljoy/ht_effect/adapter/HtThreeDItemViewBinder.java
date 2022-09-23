package com.texeljoy.ht_effect.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hwangjr.rxbus.RxBus;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HtMakeup;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;
import me.drakeet.multitype.ItemViewBinder;

/**
 * 轻彩妆Item的适配器
 */
public class HtThreeDItemViewBinder extends ItemViewBinder<HtMakeup,
    HtThreeDItemViewBinder.ViewHolder> {

  @NonNull @Override protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    View root = inflater.inflate(R.layout.item_filter, parent, false);
    return new ViewHolder(root);
  }

  @SuppressLint("SetTextI18n")
  @Override
  protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull HtMakeup item) {
    holder.downloadIV.setVisibility(View.GONE);

    holder.itemView.setSelected(getPosition(holder) ==
        HtUICacheUtils.beautyMakeupPosition());

    //holder.name.setText(item.getName(holder.itemView.getContext()));

    holder.name.setTextColor(HtState.isDark ? Color.WHITE : ContextCompat
        .getColor(holder.itemView.getContext(),R.color.dark_black));

    //holder.icon
    //    .setImageDrawable(item.getIcon(holder.itemView.getContext()));

    holder.name.setBackgroundColor(Color.TRANSPARENT);

    holder.maker.setVisibility(
        holder.itemView.isSelected() ? View.VISIBLE : View.GONE
    );

    //同步滑动条
    RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (view.isSelected()) {
          return;
        }

        //应用效果
        //HTEffect.shareInstance().setMakeup(item.getLightMakeup(),100);
        HtUICacheUtils.beautyMakeupValue(item.name(), 100);

        //HtState.currentMakeup = item;

        holder.itemView.setSelected(true);
        getAdapter().notifyItemChanged(HtUICacheUtils.beautyMakeupPosition());
        HtUICacheUtils.beautyMakeupPosition(getPosition(holder));
        getAdapter().notifyItemChanged(HtUICacheUtils.beautyMakeupPosition());

        RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");
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
