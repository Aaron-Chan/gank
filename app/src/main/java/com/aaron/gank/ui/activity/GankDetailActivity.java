package com.aaron.gank.ui.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.aaron.gank.R;
import com.aaron.gank.constans.Constants;
import com.aaron.library.activity.WebViewActivity;
import com.aaron.library.utils.SharePrefUtils;

public class GankDetailActivity extends WebViewActivity {

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        changeTheme();
    }

    @Override
    protected void onPageLoadStarted() {
        // do nothing
    }

    @Override
    protected void onPageLoadFinished(WebView view, String url) {
        // do nothing
    }

    @Override
    protected boolean hasFragment() {
        return false;
    }

    @Override
    protected String getPageNameForAnalysis() {
        return "详情页面";
    }

    private void changeTheme() {
        boolean nightMode = SharePrefUtils.getBoolean(this, Constants.SP_KEY_NIGHT_MODE, false);
        setTheme(nightMode ? R.style.NightTheme : R.style.AppTheme);
    }
}
