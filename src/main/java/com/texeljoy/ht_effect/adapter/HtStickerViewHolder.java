package com.texeljoy.ht_effect.adapter;


import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.texeljoy.ht_effect.R;

/**
 * 贴纸适配器的ViewHolder
 */
public class HtStickerViewHolder extends RecyclerView.ViewHolder {

    public ImageView downloadIV, loadingIV;
    public ImageView thumbIV;

    public HtStickerViewHolder(View itemView) {
        super(itemView);
        thumbIV = itemView.findViewById(R.id.thumbIV);
        downloadIV = itemView.findViewById(R.id.downloadIV);
        loadingIV = itemView.findViewById(R.id.loadingIV);
    }

    public void startLoadingAnimation() {
        Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.loading_animation);
        loadingIV.startAnimation(animation);
    }

    public void stopLoadingAnimation() {
        loadingIV.clearAnimation();
    }

}