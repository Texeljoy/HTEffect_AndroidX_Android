package com.texeljoy.ht_effect.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.hwangjr.rxbus.RxBus;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.utils.HtSelectedPosition;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;
import com.texeljoy.hteffect.HTEffect;
import com.texeljoy.hteffect.model.HTFilterEnum;
import com.texeljoy.hteffect.model.HTItemEnum;
import java.lang.ref.WeakReference;

/**
 * 重置Dialog
 */
public class HtResetAllDialog extends DialogFragment {

  private View root;
  private Context context;

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    context = new WeakReference<>(getActivity()).get();
    root = LayoutInflater.from(context).inflate(R.layout.dialog_reset_all, null);
    Dialog dialog = new Dialog(context, R.style.TiDialog);
    dialog.setContentView(root);
    dialog.setCancelable(true);
    dialog.setCanceledOnTouchOutside(true);

    Window window = dialog.getWindow();
    if (window != null) {
      WindowManager.LayoutParams params = window.getAttributes();
      params.width = WindowManager.LayoutParams.MATCH_PARENT;
      params.height = WindowManager.LayoutParams.WRAP_CONTENT;
      params.gravity = Gravity.CENTER;
      window.setAttributes(params);
    }

    return dialog;
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    root.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

          HtUICacheUtils.resetSkinValue(getContext());
          HtUICacheUtils.beautySkinResetEnable(false);

          HtUICacheUtils.resetFaceTrimValue(getContext());
          HtUICacheUtils.beautyFaceTrimResetEnable(false);

          HtUICacheUtils.resetGreencreenValue(getContext());
          HtUICacheUtils.greenscreenResetEnable(false);

          HTEffect.shareInstance().setHairStyling(0, 0);
          HtUICacheUtils.beautyHairPosition(0);

          HtSelectedPosition.POSITION_STICKER = -1;
          HTEffect.shareInstance().setARItem(HTItemEnum.HTItemSticker.getValue(), "");
          RxBus.get().post(HTEventAction.ACTION_SYNC_STICKER_ITEM_CHANGED, "");

          HtSelectedPosition.POSITION_MASK = -1;
          HTEffect.shareInstance().setARItem(HTItemEnum.HTItemMask.getValue(), "");
          RxBus.get().post(HTEventAction.ACTION_SYNC_MASK_ITEM_CHANGED, "");

          HtSelectedPosition.POSITION_GIFT = -1;
          HTEffect.shareInstance().setARItem(HTItemEnum.HTItemGift.getValue(), "");
          RxBus.get().post(HTEventAction.ACTION_SYNC_GIFT_ITEM_CHANGED, "");

          HtSelectedPosition.POSITION_WATERMARK = -1;
          HTEffect.shareInstance().setARItem(HTItemEnum.HTItemWatermark.getValue(), "");
          RxBus.get().post(HTEventAction.ACTION_SYNC_WATERMARK_ITEM_CHANGED, "");

          HtSelectedPosition.POSITION_AISEGMENTATION = -1;
          HTEffect.shareInstance().setAISegEffect("");
          RxBus.get().post(HTEventAction.ACTION_SYNC_PORTRAITAI_ITEM_CHANGED, "");

          HtSelectedPosition.POSITION_GREEN_SCREEN = -1;
          HTEffect.shareInstance().setGsSegEffectScene("");
          RxBus.get().post(HTEventAction.ACTION_SYNC_PORTRAITTGS_ITEM_CHANGED, "");

          HtSelectedPosition.POSITION_GESTURE = -1;
          HTEffect.shareInstance().setGestureEffect("");
          RxBus.get().post(HTEventAction.ACTION_SYNC_GESTURE_ITEM_CHANGED, "");

          HTEffect.shareInstance().setFilter(HTFilterEnum.HTFilterBeauty.getValue(), "");
          HtUICacheUtils.beautyFilterPosition(0);
          HTEffect.shareInstance().setFilter(HTFilterEnum.HTFilterEffect.getValue(), "0");
          HtUICacheUtils.beautyEffectFilterPosition(0);
          HTEffect.shareInstance().setFilter(HTFilterEnum.HTFilterFunny.getValue(), "0");
          HtUICacheUtils.beautyFunnyFilterPosition(0);







          //通知刷新列表
          RxBus.get().post(HTEventAction.ACTION_SYNC_RESET, "true");

          //通知更新滑动条显示状态
          RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");


        dismiss();
      }
    });

    root.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dismiss();
      }
    });
  }

}
