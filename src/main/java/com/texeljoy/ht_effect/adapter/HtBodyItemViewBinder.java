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
import com.hwangjr.rxbus.RxBus;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HtBody;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;
import com.texeljoy.hteffect.HTEffect;
import me.drakeet.multitype.ItemViewBinder;

/**
 * 美型的Item适配器
 */
public class HtBodyItemViewBinder
    extends ItemViewBinder<HtBody, HtBodyItemViewBinder.ViewHolder> {

  @NonNull @Override protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    View root = inflater.inflate(R.layout.item_drawable_top_button, parent, false);
    return new ViewHolder(root);
  }

  @SuppressLint("SetTextI18n")
  @Override protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull HtBody item) {

    //-50到50参数区间 ui逻辑为50 为初始值
    String key = item.name();

    int initialValue = 0;

    holder.itemView.setSelected(getPosition(holder) ==
        HtUICacheUtils.beautyBodyPosition());

    holder.text.setText(item.getName(holder.itemView.getContext()));

    //根据屏幕是否占满显示不同的图标
    if (HtState.isDark) {
      holder.image.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(),
          item.getDrawableRes_white()));

      holder.text.setTextColor(
          ContextCompat.getColorStateList(holder.itemView.getContext(),
              R.color.color_selector_tab_dark));
    } else {
      holder.image.setImageDrawable(ContextCompat.getDrawable(holder.itemView.getContext(),
          item.getDrawableRes_black()));
      holder.text.setTextColor(
          ContextCompat.getColorStateList(holder.itemView.getContext(),
              R.color.color_selector_tab_light));
    }

    //获取当前的item对应的参数 如果不是0 则表示当前的item是变动的 加上蓝点提示
    holder.point.setVisibility((HtUICacheUtils
        .beautyBodyValue(item) != initialValue) ?
                               View.VISIBLE : View.INVISIBLE);

    //同步滑动条
     RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {

        //todo 是否检测到人体
        if(HTEffect.shareInstance().isFullBody() <= 0){
          RxBus.get().post(HTEventAction.ACTION_NO_BODY,"");
        }


        holder.itemView.setSelected(true);
        getAdapter().notifyItemChanged(HtUICacheUtils.beautyBodyPosition());
        HtUICacheUtils.beautyBodyPosition(getPosition(holder));
        getAdapter().notifyItemChanged(HtUICacheUtils.beautyBodyPosition());
        HtState.currentBody = item;
        //同步滑动条
        RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");
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
