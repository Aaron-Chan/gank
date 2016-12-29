package com.aaron.gank.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.aaron.gank.R;
import com.aaron.gank.presenter.BasePresenter;
import com.aaron.gank.view.base.BaseView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aaron on 2016/12/23.
 * Activity 基类
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {

    protected P mPresenter;

    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initToolbar();
        initViews();
        initData();
    }

    protected abstract void init(Bundle savedInstanceState);

    protected abstract void initData();

    protected abstract void initViews();

    //get content view resource id
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
