package com.texeljoy.ht_effect.utils.decoration;

import android.content.Context;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewLinearSpaceItemDecoration extends RecyclerView.ItemDecoration {

  public RecyclerViewLinearSpaceItemDecoration() {
    throw new RuntimeException("Use Builder to create!");
  }

  public static class Builder {

    private Context context;
    private Param param;

    public Builder(Context context) {
      this.context = context;
      param = new Param();
    }

    public Builder margin(int margin) {
      param.margin = margin;
      return this;
    }

    public Builder ignoreTypes(int[] ignoreTypes) {
      param.ignoreTypes = ignoreTypes;
      return this;
    }

    public RecyclerViewLinearItemDecoration create() {

      return new RecyclerViewLinearItemDecoration.Builder(context)
          .thickness(param.margin)
          .color(Color.TRANSPARENT)
          .lastLineVisible(true)
          .ignoreTypes(param.ignoreTypes)
          .create();
    }

  }

  public static class Param {

    public int margin;
    public int[] ignoreTypes;

  }

}
