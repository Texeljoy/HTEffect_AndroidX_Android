package com.texeljoy.ht_effect.adapter;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.texeljoy.ht_effect.R;

/**
 * 贴纸适配器的ViewHolder
 */
public class HtStickerViewHolder extends RecyclerView.ViewHolder {

    public ImageView downloadIV, loadingIV, loadingBG;
    public ImageView thumbIV,deleteIV;

    public HtStickerViewHolder(View itemView) {
        super(itemView);
        thumbIV = itemView.findViewById(R.id.thumbIV);
        downloadIV = itemView.findViewById(R.id.downloadIV);
        loadingBG = itemView.findViewById(R.id.loadingBG);
        loadingIV = itemView.findViewById(R.id.loadingIV);
        deleteIV = itemView.findViewById(R.id.deleteIv);
    }

    public void startLoadingAnimation() {
        Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), R.anim.loading_animation);
        loadingIV.startAnimation(animation);
        // loadingBG.startAnimation(animation);
    }

    public void stopLoadingAnimation() {
        loadingIV.clearAnimation();
        // loadingBG.clearAnimation();
    }

}