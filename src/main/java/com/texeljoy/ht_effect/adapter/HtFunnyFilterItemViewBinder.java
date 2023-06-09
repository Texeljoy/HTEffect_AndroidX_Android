package com.texeljoy.ht_effect.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.model.HtHaHaFilterConfig;
import com.texeljoy.ht_effect.model.HtHaHaFilterEnum;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;
import com.texeljoy.hteffect.HTEffect;
import com.texeljoy.hteffect.model.HTFilterEnum;
import me.drakeet.multitype.ItemViewBinder;

/**
 * 滤镜Item的适配器
 */
public class HtFunnyFilterItemViewBinder extends ItemViewBinder<HtHaHaFilterConfig.HtHaHaFilter,
    HtFunnyFilterItemViewBinder.ViewHolder> {

  @NonNull @Override protected HtFunnyFilterItemViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    View root = inflater.inflate(R.layout.item_drawable_top_button, parent, false);
    return new ViewHolder(root);
  }

  @SuppressLint("SetTextI18n") @Override protected void
  onBindViewHolder(@NonNull HtFunnyFilterItemViewBinder.ViewHolder holder, @NonNull HtHaHaFilterConfig.HtHaHaFilter item) {

    //根据缓存中的选中的哪一个判断当前item是否被选中
    holder.itemView.setSelected(getPosition(holder) ==
        HtUICacheUtils.beautyFunnyFilterPosition());

    holder.text.setText(item.getTitle());

    // holder.text.setBackgroundColor(Color.TRANSPARENT);
    //
    // holder.text.setTextColor(HtState.isDark ? Color.WHITE : ContextCompat
    //     .getColor(holder.itemView.getContext(),R.color.dark_black));

    if (HtState.isDark) {
      holder.image.setImageDrawable(HtHaHaFilterEnum.values()[getPosition(holder)].getIconResBlack(holder.itemView.getContext()));
      holder.text.setTextColor(
          ContextCompat.getColorStateList(holder.itemView.getContext(),
              R.color.color_selector_tab_dark));
    }else{
      holder.image.setImageDrawable(HtHaHaFilterEnum.values()[getPosition(holder)].getIconResWhite(holder.itemView.getContext()));
      holder.text.setTextColor(
          ContextCompat.getColorStateList(holder.itemView.getContext(),
              R.color.color_selector_tab_light));

    }

    // holder.maker.setBackgroundColor(ContextCompat.getColor
    //     (holder.itemView.getContext(), R.color.filter_maker));
    // holder.maker.setVisibility(
    //     holder.itemView.isSelected() ? View.VISIBLE : View.GONE
    // );

    // if(HtState.currentStyle != HtStyle.YUAN_TU){
    //   holder.itemView.setEnabled(false);
    //   RxBus.get().post(HTEventAction.ACTION_STYLE_SELECTED,"请先取消“风格推荐”效果");
    // }else{
    //   holder.itemView.setEnabled(true);
    //   RxBus.get().post(HTEventAction.ACTION_STYLE_SELECTED,"");
    // }
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (holder.itemView.isSelected()) {
          return;
        }

        //应用效果

        HTEffect.shareInstance().setFilter(HTFilterEnum.HTFilterFunny.getValue(),item.getName());
        //HtUICacheUtils.beautyFilterValue(item, 100);

        HtState.currentHaHaFilter = item;
        holder.itemView.setSelected(true);
        getAdapter().notifyItemChanged(HtUICacheUtils.beautyFunnyFilterPosition());
        HtUICacheUtils.beautyFunnyFilterPosition(getPosition(holder));
        HtUICacheUtils.beautyFunnyFilterName(item.getName());
        getAdapter().notifyItemChanged(HtUICacheUtils.beautyFunnyFilterPosition());

      }
    });

  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    private final @NonNull AppCompatTextView text;

    private final @NonNull AppCompatImageView image;

    private final @NonNull View point;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      text = itemView.findViewById(R.id.htTextTV);
      image = itemView.findViewById(R.id.htImageIV);
      point = itemView.findViewById(R.id.point);
    }
  }

}
