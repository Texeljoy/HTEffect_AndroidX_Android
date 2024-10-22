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
import com.bumptech.glide.Glide;
import com.hwangjr.rxbus.RxBus;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HtMakeupStyleConfig;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;
import com.texeljoy.ht_effect.view.HtRoundImageView;
import com.texeljoy.hteffect.HTEffect;
import java.util.Locale;
import me.drakeet.multitype.ItemViewBinder;

/**
 * 妆容推荐Item的适配器
 */
public class HtStyleItemViewBinder extends ItemViewBinder<HtMakeupStyleConfig.HtMakeupStyle,
    HtStyleItemViewBinder.ViewHolder> {

  @NonNull @Override protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    View root = inflater.inflate(R.layout.item_filter, parent, false);
    return new HtStyleItemViewBinder.ViewHolder(root);
  }
  @SuppressLint("SetTextI18n")
  @Override
  protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull HtMakeupStyleConfig.HtMakeupStyle item) {
    //holder.downloadIV.setVisibility(View.GONE);

    //根据缓存中的选中的哪一个判断当前item是否被选中
    holder.itemView.setSelected(getPosition(holder) ==
        HtUICacheUtils.getBeautyMakeUpStylePosition());

    String currentLanguage = Locale.getDefault().getLanguage();
    if("en".equals(currentLanguage)){
      holder.name.setText(item.getTitleEn());
    }else{
      holder.name.setText(item.getTitle());
    }

    holder.name.setBackgroundColor(Color.TRANSPARENT);

    holder.name.setTextColor(HtState.isDark ? Color.WHITE : ContextCompat
        .getColor(holder.itemView.getContext(),R.color.dark_black));
    String resName;
    // String resName = "ic_style_" + item.getName();
    if (item.getName().isEmpty()) {
      resName = "ic_style_none";
    } else {
      resName = "ic_style_" + item.getName();
    }
    int resID = holder.itemView.getResources().getIdentifier(resName, "drawable",
        holder.itemView.getContext().getPackageName());
    Glide.with(holder.itemView.getContext())
        .load(resID)
        // .placeholder(R.drawable.icon_placeholder)
        .into(holder.thumbIV);

    // holder.maker.setBackgroundColor(ContextCompat.getColor
    //     (holder.itemView.getContext(), R.color.makeup_maker));
    holder.maker.setVisibility(
        holder.itemView.isSelected() ? View.VISIBLE : View.GONE
    );

    //同步滑动条
    // RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (holder.itemView.isSelected()) {
          return;
        }


        //应用效果
        HTEffect.shareInstance().setStyle(item.getName(), HtUICacheUtils.getBeautyMakeUpStyleValue(item.getName()));

        HtState.currentMakeUpStyle = item;
        holder.itemView.setSelected(true);
        getAdapter().notifyItemChanged(HtUICacheUtils.getBeautyMakeUpStylePosition());
        HtUICacheUtils.setBeautyMakeUpStylePosition(getPosition(holder));
        HtUICacheUtils.setBeautyMakeUpStyleName(item.getName());
        getAdapter().notifyItemChanged(HtUICacheUtils.getBeautyMakeUpStylePosition());
        RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");

        if(HtState.currentMakeUpStyle.getName().isEmpty()){
          HtUICacheUtils.initCache(false);
        }
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
