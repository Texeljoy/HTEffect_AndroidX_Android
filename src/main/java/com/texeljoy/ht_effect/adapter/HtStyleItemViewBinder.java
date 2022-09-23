package com.texeljoy.ht_effect.adapter;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hwangjr.rxbus.RxBus;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.model.HtStyle;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;
import com.texeljoy.hteffect.HTEffect;
import me.drakeet.multitype.ItemViewBinder;

/**
 * 风格的item适配器
 */
public class HtStyleItemViewBinder
    extends ItemViewBinder<HtStyle, HtStyleItemViewBinder.ViewHolder> {

  @NonNull @Override protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    View root = inflater.inflate(R.layout.item_square_image, parent, false);
    return new ViewHolder(root);
  }

  @SuppressLint("SetTextI18n")
  @Override protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull HtStyle item) {

    holder.itemView.setSelected(getPosition(holder) ==
        HtUICacheUtils.beautyStylePosition());

    holder.name.setText(item.getString(holder.itemView.getContext()));

    holder.name.setBackgroundColor(item.getFillColor());

    holder.maker.setBackgroundColor(item.getFillColor());

    holder.preview.setImageDrawable(item.getIcon(holder.itemView.getContext()));

    holder.maker.setVisibility(
        holder.itemView.isSelected() ? View.VISIBLE : View.GONE
    );

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (holder.itemView.isSelected()) {
          return;
        }

        HtState.currentStyle = item;

        holder.itemView.setSelected(true);
        getAdapter().notifyItemChanged(HtUICacheUtils.beautyStylePosition());
        HtUICacheUtils.beautyStylePosition(getPosition(holder));
        //应用参数

        HTEffect.shareInstance().setStyle(item.getParam(),100);
        if(item == HtStyle.YUAN_TU){
          HtUICacheUtils.initCache(false);
          RxBus.get().post(HTEventAction.ACTION_STYLE_SELECTED,"");
          RxBus.get().post(HTEventAction.ACTION_CHANGE_ENABLE,"");
        }else{
          RxBus.get().post(HTEventAction.ACTION_STYLE_SELECTED,"请先取消“风格推荐”效果");
          RxBus.get().post(HTEventAction.ACTION_CHANGE_ENABLE,"");
        }


        getAdapter().notifyItemChanged(HtUICacheUtils.beautyStylePosition());

      }
    });

  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    private final @NonNull AppCompatTextView name;

    private final @NonNull AppCompatImageView preview;

    private final @NonNull AppCompatImageView maker;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      name = itemView.findViewById(R.id.tv_name);
      preview = itemView.findViewById(R.id.iv_preview);
      maker = itemView.findViewById(R.id.bg_maker);
    }
  }

}
