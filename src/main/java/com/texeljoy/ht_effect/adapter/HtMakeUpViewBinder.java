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
import com.texeljoy.ht_effect.model.HTViewState;
import com.texeljoy.ht_effect.model.HtMakeUpEnum;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;
import java.util.List;
import me.drakeet.multitype.ItemViewBinder;

/**
 * 美妆的Item适配器
 */
public class HtMakeUpViewBinder
    extends ItemViewBinder<HtMakeUpEnum, HtMakeUpViewBinder.ViewHolder> {

  @NonNull @Override protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
    View root = inflater.inflate(R.layout.item_drawable_top_button, parent, false);
    return new ViewHolder(root);
  }

  @SuppressLint("SetTextI18n")
  @Override protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull HtMakeUpEnum item) {


    String key = item.name();
    List<HtMakeUpEnum> makeUpList;

    int initValue = 0;


    holder.itemView.setSelected(getPosition(holder) ==
        HtUICacheUtils.getBeautyMakeUpPosition());

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
          .getMakeupItemPositionCache(item.getType()) > initValue) ?
                                 View.VISIBLE : View.INVISIBLE);



    //同步滑动条
     RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if(HtState.currentMakeUpStyle.getName().isEmpty()){
          holder.itemView.setSelected(true);
          getAdapter().notifyItemChanged(HtUICacheUtils.getBeautyMakeUpPosition());
          HtUICacheUtils.setBeautyMakeUpPosition(getPosition(holder));
          getAdapter().notifyItemChanged(HtUICacheUtils.getBeautyMakeUpPosition());
          HtState.currentMakeUp = item;
          switch (getPosition(holder)){
            case 0:
              RxBus.get().post(HTEventAction.ACTION_CHANGE_PANEL, HTViewState.MAKEUP_LIPSTICK);
              break;
            case 1:
              RxBus.get().post(HTEventAction.ACTION_CHANGE_PANEL, HTViewState.MAKEUP_EYEBROW);
              break;
            case 2:
              RxBus.get().post(HTEventAction.ACTION_CHANGE_PANEL, HTViewState.MAKEUP_BLUSH);
              break;
            case 3:
              RxBus.get().post(HTEventAction.ACTION_CHANGE_PANEL, HTViewState.MAKEUP_EYESHADOW);
              break;
            case 4:
              RxBus.get().post(HTEventAction.ACTION_CHANGE_PANEL, HTViewState.MAKEUP_EYELINE);
              break;
            case 5:
              RxBus.get().post(HTEventAction.ACTION_CHANGE_PANEL, HTViewState.MAKEUP_EYELASH);
              break;
            case 6:
              RxBus.get().post(HTEventAction.ACTION_CHANGE_PANEL, HTViewState.MAKEUP_BEAUTYPUPILS);
              break;

          }

          //同步滑动条
          // RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");

        }else{
          RxBus.get().post(HTEventAction.ACTION_MAKEUP_STYLE_SELECTED,"");
        }


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
