package com.aaron.library.utils;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.aaron.library.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SizeReadyCallback;

/**
 * Created by Aaron on 2017/1/6.
 */

public class GlideUtils {

    private GlideUtils() {
        throw new IllegalAccessError();
    }

    /**
     * 加载网络图片
     *
     * @param view
     * @param url
     */
    public static void loadImage(@NonNull ImageView view, String url) {
        loadUrl(view, url, R.mipmap.img_default);
    }

    public static void loadImageSize(@NonNull final ImageView view, String url, int width, int height) {
        Glide.with(view.getContext())
                .load(url)
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .into(view)
                .getSize(new SizeReadyCallback() {
                    @Override
                    public void onSizeReady(int width, int height) {
                        if (!view.isShown()) {
                            view.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private static void loadUrl(@NonNull final ImageView view, String url,
                                @DrawableRes int defaultImage) {

        Glide.with(view.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(defaultImage)
                .dontAnimate()
                //.centerCrop()
                .into(view)
                .getSize(new SizeReadyCallback() {
                    @Override
                    public void onSizeReady(int width, int height) {
                        if (!view.isShown()) {
                            view.setVisibility(View.VISIBLE);
                        }
                    }
                });

    }


    /**
     * 加载本地图片
     *
     * @param view
     * @param resId
     */
    public static void loadNative(@NonNull final ImageView view, @DrawableRes int resId) {

        Glide.with(view.getContext())
                .load(resId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .centerCrop()
                .into(view)
                .getSize(new SizeReadyCallback() {
                    @Override
                    public void onSizeReady(int width, int height) {
                        if (!view.isShown()) {
                            view.setVisibility(View.VISIBLE);
                        }
                    }
                });

    }

}
