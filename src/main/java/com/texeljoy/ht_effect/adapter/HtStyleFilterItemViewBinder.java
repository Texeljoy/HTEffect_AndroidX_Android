package com.texeljoy.ht_effect.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.hwangjr.rxbus.RxBus;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.model.HtStyleFilterConfig;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;
import com.texeljoy.ht_effect.view.HtRoundImageView;
import com.texeljoy.hteffect.HTEffect;
import com.texeljoy.hteffect.model.HTFilterEnum;
import me.drakeet.multitype.ItemViewBinder;

/**
 * 滤镜Item的适配器
 */
public class HtStyleFilterItemViewBinder extends ItemViewBinder<HtStyleFilterConfig.HtStyleFilter,
    HtStyleFilterItemViewBinder.ViewHolder> {

  @NonNull @Override protected HtStyleFilterItemViewBinder.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    View root = inflater.inflate(R.layout.item_filter, parent, false);
    return new HtStyleFilterItemViewBinder.ViewHolder(root);
  }

  @SuppressLint("SetTextI18n") @Override protected void
  onBindViewHolder(@NonNull HtStyleFilterItemViewBinder.ViewHolder holder, @NonNull HtStyleFilterConfig.HtStyleFilter item) {

    //根据缓存中的选中的哪一个判断当前item是否被选中
    holder.itemView.setSelected(getPosition(holder) ==
        HtUICacheUtils.beautyFilterPosition());

    holder.name.setText(item.getTitle());

    holder.name.setBackgroundColor(Color.TRANSPARENT);

    holder.name.setTextColor(HtState.isDark ? Color.WHITE : ContextCompat
        .getColor(holder.itemView.getContext(),R.color.dark_black));

    // holder.thumbIV.setClipToOutline(true);
    // GradientDrawable drawable = new GradientDrawable();
    // drawable.setCornerRadius(10);
    // holder.thumbIV.setImageDrawable(drawable);

    String resName = "ic_filter_" + item.getName();
    int resID = holder.itemView.getResources().getIdentifier(resName, "drawable",
        holder.itemView.getContext().getPackageName());

    Glide.with(holder.itemView.getContext())
        .load(resID)
        // .placeholder(R.drawable.icon_placeholder)
        .into(holder.thumbIV);
    // holder.thumbIV.setImageDrawable(HtStyleFilterEnum.values()[getPosition(holder)].getIcon(holder.itemView.getContext()));

    // holder.maker.setBackgroundColor(ContextCompat.getColor
    //     (holder.itemView.getContext(), R.color.makeup_maker));
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


        HTEffect.shareInstance().setFilter(HTFilterEnum.HTFilterBeauty.getValue(), item.getName());
        //HtUICacheUtils.beautyFilterValue(item, 100);

        HtState.currentStyleFilter = item;

        holder.itemView.setSelected(true);
        getAdapter().notifyItemChanged(HtUICacheUtils.beautyFilterPosition());
        HtUICacheUtils.beautyFilterPosition(getPosition(holder));
        HtUICacheUtils.beautyFilterName(item.getName());
        getAdapter().notifyItemChanged(HtUICacheUtils.beautyFilterPosition());


        RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");
        RxBus.get().post(HTEventAction.ACTION_SHOW_FILTER, "");
      }
    });

  }

  static class ViewHolder extends RecyclerView.ViewHolder {

    public final @NonNull AppCompatTextView name;

    public final @NonNull HtRoundImageView thumbIV;

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
