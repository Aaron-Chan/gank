package com.aaron.library.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.aaron.library.R;
import com.aaron.library.presenter.BasePresenter;
import com.aaron.library.view.BaseView;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;

/**
 * Created by Aaron on 2016/12/23.
 * Activity 基类
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {
    protected static final int DEFAULT_INVALID_THEME_ID = -1;
    protected P mPresenter;
    protected Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        if (getCurrentThemeId() != DEFAULT_INVALID_THEME_ID) {
            setTheme(getCurrentThemeId());
        }
        setContentView(getLayoutId());
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        ButterKnife.bind(this);
        initToolbar();
        initViews();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // umeng 统计
        if (!hasFragment()) {
            MobclickAgent.onPageStart(getPageNameForAnalysis());
        }
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // umeng 统计
        if (!hasFragment()) {
            MobclickAgent.onPageEnd(getPageNameForAnalysis());
        }
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected int getCurrentThemeId() {
        return DEFAULT_INVALID_THEME_ID;
    }

    protected abstract void init(Bundle savedInstanceState);

    protected abstract void initData();

    protected abstract void initViews();

    protected abstract boolean hasFragment();

    /**
     * 获取用户统计的页面名称
     *
     * @return 默认为类名
     */
    protected String getPageNameForAnalysis() {
        return this.getClass().getName();
    }

    //get content view resource id
    @LayoutRes
    abstract protected int getLayoutId();

    protected void initToolbar() {
        if (mToolbar == null) {
            throw new NullPointerException("toolbar is null");
        }
        setSupportActionBar(mToolbar);
    }

    public void setTitle(String strTitle, boolean showHome) {
        setTitle(strTitle);
        if (getSupportActionBar() == null) {
            throw new NullPointerException("actionbar is null");
        }
        getSupportActionBar().setDisplayShowHomeEnabled(showHome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(showHome);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getMenuRes() < 0) return true;
        getMenuInflater().inflate(getMenuRes(), menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    protected int getMenuRes() {
        return -1;
    }
}
