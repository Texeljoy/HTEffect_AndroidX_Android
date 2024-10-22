package com.texeljoy.ht_effect.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import androidx.annotation.NonNull;;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.hwangjr.rxbus.RxBus;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HtHairConfig;
import com.texeljoy.ht_effect.model.HtHairEnum;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;
import com.texeljoy.hteffect.HTEffect;
import java.util.Locale;
import me.drakeet.multitype.ItemViewBinder;

/**
 * 滤镜Item的适配器
 */
public class HtHairItemViewBinder extends ItemViewBinder<HtHairConfig.HtHair,
    HtHairItemViewBinder.ViewHolder> {

  @NonNull @Override protected HtHairItemViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    View root = inflater.inflate(R.layout.item_filter, parent, false);
    return new HtHairItemViewBinder.ViewHolder(root);
  }

  @SuppressLint("SetTextI18n") @Override protected void
  onBindViewHolder(@NonNull HtHairItemViewBinder.ViewHolder holder, @NonNull HtHairConfig.HtHair item) {

    //根据缓存中的选中的哪一个判断当前item是否被选中
    holder.itemView.setSelected(getPosition(holder) ==
        HtUICacheUtils.beautyHairPosition());

    String currentLanguage = Locale.getDefault().getLanguage();
    if("en".equals(currentLanguage)){
      holder.name.setText(item.getTitleEn());
    }else{
      holder.name.setText(item.getTitle());
    }

    holder.name.setBackgroundColor(Color.TRANSPARENT);

    holder.name.setTextColor(HtState.isDark ? Color.WHITE : ContextCompat
        .getColor(holder.itemView.getContext(),R.color.dark_black));

    holder.thumbIV.setImageDrawable(HtHairEnum.values()[getPosition(holder)].getIcon(holder.itemView.getContext()));

    // holder.maker.setBackgroundColor(ContextCompat.getColor
    //     (holder.itemView.getContext(), R.color.filter_maker));

    holder.maker.setVisibility(
        holder.itemView.isSelected() ? View.VISIBLE : View.GONE
    );

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

        HTEffect.shareInstance().setHairStyling(item.getId(), HtUICacheUtils.beautyHairValue(item.getName()));
        //HtUICacheUtils.beautyFilterValue(item, 100);

        HtState.currentHair = item;

        holder.itemView.setSelected(true);
        getAdapter().notifyItemChanged(HtUICacheUtils.beautyHairPosition());
        HtUICacheUtils.beautyHairPosition(getPosition(holder));
        HtUICacheUtils.beautyHairName(item.getName());
        getAdapter().notifyItemChanged(HtUICacheUtils.beautyHairPosition());


        RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");
        // RxBus.get().post(HTEventAction.ACTION_SHOW_FILTER, "");
      }
    });

  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    public final @NonNull AppCompatTextView name;

    public final @NonNull AppCompatImageView thumbIV;

    public final @NonNull AppCompatImageView maker;

    public final @NonNull AppCompatImageView loadingIV;

    public final @NonNull AppCompatImageView downloadIV;



    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      name = itemView.findViewById(R.id.tv_name);
      thumbIV = itemView.findViewById(R.id.iv_icon);
      maker = itemView.findViewById(R.id.bg_maker);
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
