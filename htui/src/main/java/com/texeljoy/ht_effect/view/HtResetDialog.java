package com.texeljoy.ht_effect.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.fragment.app.DialogFragment;
import com.hwangjr.rxbus.RxBus;
import com.texeljoy.ht_effect.R;
import com.texeljoy.ht_effect.model.HTEventAction;
import com.texeljoy.ht_effect.model.HTViewState;
import com.texeljoy.ht_effect.model.HtState;
import com.texeljoy.ht_effect.utils.HtUICacheUtils;
import java.lang.ref.WeakReference;

/**
 * 重置Dialog
 */
public class HtResetDialog extends DialogFragment {

  private View root;
  private Context context;

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    context = new WeakReference<>(getActivity()).get();
    root = LayoutInflater.from(context).inflate(R.layout.dialog_reset, null);
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
        if (HtState.currentSecondViewState == HTViewState.BEAUTY_SKIN) {
          //当前是美肤
          HtUICacheUtils.resetSkinValue(getContext());
          HtUICacheUtils.beautySkinResetEnable(false);

          //通知刷新列表
          RxBus.get().post(HTEventAction.ACTION_SYNC_RESET, "true");

          //通知更新滑动条显示状态
          RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");
        }

        if (HtState.currentSecondViewState == HTViewState.BEAUTY_FACE_TRIM) {
          //当前是美型
          HtUICacheUtils.resetFaceTrimValue(getContext());

          HtUICacheUtils.beautyFaceTrimResetEnable(false);

          //通知刷新列表
          RxBus.get().post(HTEventAction.ACTION_SYNC_RESET, "true");

          //通知更新滑动条显示状态
          RxBus.get().post(HTEventAction.ACTION_SYNC_PROGRESS, "");

        }

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
