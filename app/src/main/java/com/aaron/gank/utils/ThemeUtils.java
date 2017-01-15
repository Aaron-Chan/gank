package com.aaron.gank.utils;

import android.support.annotation.StyleRes;

import com.aaron.gank.App;
import com.aaron.gank.R;
import com.aaron.gank.constans.Constants;
import com.aaron.library.utils.SharePrefUtils;

/**
 * Created by AaronChan on 2017/1/15.
 */

public class ThemeUtils {

    @StyleRes
    public static int getCurrentTheme() {
        boolean nightMode = SharePrefUtils.getBoolean(App.sContext, Constants.SP_KEY_NIGHT_MODE, false);
        return nightMode ? R.style.NightTheme : R.style.AppTheme;
    }
}
