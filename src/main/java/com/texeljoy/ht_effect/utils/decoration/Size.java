package com.texeljoy.ht_effect.utils.decoration;

import android.hardware.Camera;
import android.os.Build;
import androidx.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Spica27 on 2020/8/7.
 * <p>
 * 尺寸。
 */
public class Size {

    private int width;
    private int height;

    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * 尺寸转化。
     */
    public static List<Size> convert(List<Camera.Size> list) {
        List<Size> sizeList = new ArrayList<>();
        for (Camera.Size size : list) {
            sizeList.add(new Size(size.width, size.height));
        }
        return sizeList;
    }

    /**
     * 尺寸转化。
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static List<Size> convert(android.util.Size[] array) {
        List<Size> sizeList = new ArrayList<>();
        for (android.util.Size size : array) {
            sizeList.add(new Size(size.getWidth(), size.getHeight()));
        }
        return sizeList;
    }

}
