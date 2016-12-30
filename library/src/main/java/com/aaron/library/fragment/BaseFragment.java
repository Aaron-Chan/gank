package com.aaron.library.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Aaron on 2016/12/24.
 * Fragment 基类
 */

public abstract class BaseFragment extends Fragment {

    protected View mContentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, mContentView);
        initViews();
        initData();
        return mContentView;
    }


    protected abstract void initData();

    protected abstract void initViews();

    //get content view resource id
    abstract protected int getLayoutId();

}
