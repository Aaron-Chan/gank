package com.aaron.library.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.aaron.library.R;
import com.aaron.library.R2;
import com.aaron.library.utils.ClipboardUtils;
import com.aaron.library.utils.ShareUtils;

import butterknife.BindView;

public abstract class WebViewActivity extends BaseActivity {

    @BindView(R2.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R2.id.webView)
    WebView mWebView;
    @BindView(R2.id.activity_web_view)
    ViewGroup mRootView;

    private static final String ARG_URL = "url";
    private static final String ARG_TITLE = "title";
    private String mTitle;
    private String mUrl;

    public static void open(Context context, String url, String title, Class<? extends WebViewActivity> subClass) {
        Intent intent = new Intent(context, subClass);
        intent.putExtra(ARG_URL, url);
        intent.putExtra(ARG_TITLE, title);
        context.startActivity(intent);
    }

    public static void open(Context context, String url, @StringRes int titleResId, Class<? extends WebViewActivity> subClass) {
        open(context, url, context.getString(titleResId), subClass);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mUrl = getIntent().getStringExtra(ARG_URL);
        mTitle = getIntent().getStringExtra(ARG_TITLE);
    }

    @Override
    protected void initData() {
        mWebView.loadUrl(mUrl);
    }

    @Override
    protected void initViews() {
        setTitle(mTitle, true);

        // 支持获取手势焦点，输入用户名、密码或其他
        mWebView.requestFocusFromTouch();

        // 支持javascript
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);

        settings.setSupportZoom(true);// 支持缩放
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            settings.setDisplayZoomControls(false); // 隐藏原生缩放控制
        }
        settings.setBuiltInZoomControls(true); // 支持缩放

        // 支持缓存
        settings.setAppCacheEnabled(true);
        settings.setAppCachePath(getCacheDir().getAbsolutePath() + "/web");
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setAppCacheEnabled(true);

        settings.setJavaScriptCanOpenWindowsAutomatically(true); // 支持通过JS打开新窗口
        settings.setLoadsImagesAutomatically(true);  // 支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");// 设置编码格式

        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持重新布局
        settings.setLoadWithOverviewMode(true);

        settings.setAllowFileAccess(true); // 设置可以访问文件

        mWebView.setWebChromeClient(new MyWebChromeClient());
        mWebView.setWebViewClient(new MyWebViewClient());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                mWebView.onResume();
            }
        }
    }

    @Override
    protected int getMenuRes() {
        return R.menu.menu_web_view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_item_copy_url) {
            // 复制链接
            copyUrl();
            return true;
        } else if (itemId == R.id.menu_item_reload) {
            mWebView.loadUrl(mUrl);
            return true;
        } else if (itemId == R.id.menu_item_share) {
            share();
            return true;
        } else if (itemId == R.id.menu_item_open_browser) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(mUrl));
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    protected void share() {
        ShareUtils.share(this, String.format("分享【%s】链接：%s", mTitle, mWebView.getUrl()));
    }

    protected void copyUrl() {
        String url = mWebView.getUrl();
        ClipboardUtils.copyText(url);
        Snackbar.make(mToolbar, "已复制到剪切板", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                mWebView.onPause();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 避免内存泄露
        mRootView.removeAllViews();
        if (mWebView != null) {
            mWebView.clearHistory();
            mWebView.loadUrl("about:blank");
            mWebView.pauseTimers();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                mWebView.destroy();
            }
            mWebView = null;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 处理按键
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    public void showError(String errorMsg) {

    }

    // WebViewClient就是帮助WebView处理各种通知、请求事件的。
    protected class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            onPageLoadFinished(view, url);
        }


    }

    //WebChromeClient是辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等
    protected class MyWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 0) {
                onPageLoadStarted();
            } else if (newProgress > 90) {
                mProgressBar.setVisibility(View.GONE);
            } else {
                mProgressBar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            // 获取网页的title
            mTitle = title;
        }
    }

    protected abstract void onPageLoadStarted();

    protected abstract void onPageLoadFinished(WebView view, String url);


}
