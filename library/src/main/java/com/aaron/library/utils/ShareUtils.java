package com.aaron.library.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.aaron.library.R;

/**
 * Created by AaronChan on 2017/1/8.
 */

public class ShareUtils {
    private ShareUtils() {
        throw new IllegalAccessError();
    }

    public static void shareImage(Context context, Uri uri, String title) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        context.startActivity(Intent.createChooser(shareIntent, title));
    }


    public static void share(Context context, String content) {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.putExtra(Intent.EXTRA_TEXT, content);
        sendIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(sendIntent, context.getResources().getString(R.string.send_to)));
    }
}
