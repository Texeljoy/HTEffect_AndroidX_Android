package com.texeljoy.ht_effect.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import com.texeljoy.ht_effect.R;

public class HtRoundImageView extends AppCompatImageView {

    private float radius;
    private float width, height;
    private Path path;

    public HtRoundImageView(Context context) {
        this(context, null);
    }

    public HtRoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HtRoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        path = new Path();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.HtRoundImageView);
        radius = ta.getDimension(R.styleable.HtRoundImageView_riv_radius, 0);
        ta.recycle();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //这里做下判断，只有图片的宽高大于设置的圆角距离的时候才进行裁剪
        if (width > radius * 2 && height > radius * 2) {
            path.moveTo(radius, 0);

            path.lineTo(width - radius, 0);
            path.quadTo(width, 0, width, radius);

            path.lineTo(width, height - radius);
            path.quadTo(width, height, width - radius, height);

            path.lineTo(radius, height);
            path.quadTo(0, height, 0, height - radius);

            path.lineTo(0, radius);
            path.quadTo(0, 0, radius, 0);

            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }
}
