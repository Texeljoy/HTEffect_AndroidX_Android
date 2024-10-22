package com.texeljoy.ht_effect.adapter;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.model.HtFunnyFilterConfig;
import com.texeljoy.ht_effect.model.HtFunnyFilterEnum;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;
import com.texeljoy.hteffect.HTEffect;
import com.texeljoy.hteffect.model.HTFilterEnum;
import java.util.Locale;
import me.drakeet.multitype.ItemViewBinder;

/**
 * 滤镜Item的适配器
 */
public class HtFunnyFilterItemViewBinder extends ItemViewBinder<HtFunnyFilterConfig.HtFunnyFilter,
    HtFunnyFilterItemViewBinder.ViewHolder> {

  @NonNull @Override protected HtFunnyFilterItemViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    View root = inflater.inflate(R.layout.item_drawable_top_button, parent, false);
    return new HtFunnyFilterItemViewBinder.ViewHolder(root);
  }

  @SuppressLint("SetTextI18n") @Override protected void
  onBindViewHolder(@NonNull HtFunnyFilterItemViewBinder.ViewHolder holder, @NonNull HtFunnyFilterConfig.HtFunnyFilter item) {

    //根据缓存中的选中的哪一个判断当前item是否被选中
    holder.itemView.setSelected(getPosition(holder) ==
        HtUICacheUtils.getFunnyFilterPosition());

    String currentLanguage = Locale.getDefault().getLanguage();
    if("en".equals(currentLanguage)){
      holder.text.setText(item.getTitleEn());
    }else{
      holder.text.setText(item.getTitle());
    }

    // holder.text.setBackgroundColor(Color.TRANSPARENT);
    //
    // holder.text.setTextColor(HtState.isDark ? Color.WHITE : ContextCompat
    //     .getColor(holder.itemView.getContext(),R.color.dark_black));

    if (HtState.isDark) {
      holder.image.setImageDrawable(HtFunnyFilterEnum.values()[getPosition(holder)].getIconResBlack(holder.itemView.getContext()));
      holder.text.setTextColor(
          ContextCompat.getColorStateList(holder.itemView.getContext(),
              R.color.color_selector_tab_dark));
    }else{
      holder.image.setImageDrawable(HtFunnyFilterEnum.values()[getPosition(holder)].getIconResWhite(holder.itemView.getContext()));
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

        HtState.currentFunnyFilter = item;
        holder.itemView.setSelected(true);
        getAdapter().notifyItemChanged(HtUICacheUtils.getFunnyFilterPosition());
        HtUICacheUtils.setFunnyFilterPosition(getPosition(holder));
        HtUICacheUtils.setFunnyFilterName(item.getName());
        getAdapter().notifyItemChanged(HtUICacheUtils.getFunnyFilterPosition());

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
