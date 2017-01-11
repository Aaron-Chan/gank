package com.aaron.library.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by Aaron on 2016/12/24.
 * util for toast
 */

public class ToastUtils {

    @SuppressLint("StaticFieldLeak")
    private static Context sContext;

    private ToastUtils() {
        throw new IllegalAccessError();
    }

    public static void init(Context context) {
        if (!(context instanceof Application)) {
            throw new IllegalArgumentException("context must be instanceof Application");
        }
        sContext = context;
    }

    public static void showShort(@StringRes int resId) {
        Toast.makeText(sContext, resId, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(String message) {
        Toast.makeText(sContext, message, Toast.LENGTH_SHORT).show();
    }

    public static void showLong(@StringRes int resId) {
        Toast.makeText(sContext, resId, Toast.LENGTH_LONG).show();
    }

    public static void showLong(String message) {
        Toast.makeText(sContext, message, Toast.LENGTH_LONG).show();
    }
}
