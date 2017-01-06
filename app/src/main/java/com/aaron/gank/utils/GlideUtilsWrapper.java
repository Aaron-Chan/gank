package com.aaron.gank.utils;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.aaron.library.utils.GlideUtils;

/**
 * Created by Aaron on 2017/1/6.
 */

public class GlideUtilsWrapper {

    public static void loadImage(@NonNull ImageView view, String url) {
        int width = view.getWidth();
        int height = view.getHeight();

        if (width > 0 && height > 0) {
            int maxLength = Math.max(width, height);
            if (maxLength > 0) {
                // 为Gank 省流量
                url += String.format("?imageView2/0/w/%s", maxLength);
            }
        }
        GlideUtils.loadImage(view, url);
    }

    public static void loadImageSize(@NonNull final ImageView view, String url, int width, int height) {
        int maxLength = Math.max(width, height);
        // 为Gank 省流量
        url += String.format("?imageView2/0/w/%s", maxLength);
        GlideUtils.loadImageSize(view, url, width, height);
    }
}
