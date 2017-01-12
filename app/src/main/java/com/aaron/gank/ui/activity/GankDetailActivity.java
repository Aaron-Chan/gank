package com.aaron.gank.ui.activity;

import android.webkit.WebView;

import com.aaron.library.activity.WebViewActivity;

public class GankDetailActivity extends WebViewActivity {

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
}
