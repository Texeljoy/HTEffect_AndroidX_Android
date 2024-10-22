package com.texeljoy.ht_effect.utils;

import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public class MyItemDecoration extends RecyclerView.ItemDecoration {

  private int margin = 0;

  public MyItemDecoration(int dp) {
    this.margin = DpUtils.dip2px(dp);
  }

  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

    int position = parent.getChildAdapterPosition(view);

    outRect.left = position == 0 ? margin : 0;
    outRect.right = position == (parent.getAdapter().getItemCount() - 1) ? 2 * margin : 0;
  }
}