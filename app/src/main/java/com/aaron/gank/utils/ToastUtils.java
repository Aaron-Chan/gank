package com.aaron.gank.utils;

import android.support.annotation.StringRes;
import android.widget.Toast;

import com.aaron.gank.App;

/**
 * Created by Aaron on 2016/12/24.
 * util for toast
 */

public class ToastUtils {

    private ToastUtils() {
    }

    public static void showShort(@StringRes int resId) {
        Toast.makeText(App.sContext, resId, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(String message) {
        Toast.makeText(App.sContext, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(@StringRes int resId) {
        Toast.makeText(App.sContext, resId, Toast.LENGTH_LONG).show();
    }

    public static void showLong(String message) {
        Toast.makeText(App.sContext, message, Toast.LENGTH_LONG).show();
    }
}
