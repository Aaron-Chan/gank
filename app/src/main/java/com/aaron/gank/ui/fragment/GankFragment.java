package com.aaron.gank.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.aaron.gank.data.entity.GankEntity;
import com.aaron.gank.presenter.GankPresenter;
import com.aaron.gank.view.GankView;
import com.aaron.library.fragment.BaseListFragment;

import java.util.List;

/**
 *
 */
public class GankFragment extends BaseListFragment implements GankView {

    private static final String ARGU_TYPE = "type";
    private String mType;
    private GankPresenter mGankPresenter;

    public GankFragment() {
    }

    public static GankFragment newInstance(@NonNull String type) {
        Bundle bundle = new Bundle();
        bundle.putString(ARGU_TYPE, type);
        GankFragment gankFragment = new GankFragment();
        gankFragment.setArguments(bundle);
        return gankFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getString(ARGU_TYPE);
    }

    @Override
    protected void initData() {
        super.initData();
        mGankPresenter = new GankPresenter(this, mItems, mType);
    }

    @Override
    protected void onLoadMore() {
        mGankPresenter.onLoadMore();
    }

    @Override
    protected void onRefresh() {
        mGankPresenter.onRefresh();
    }

    @Override
    public void onRetry() {
        mGankPresenter.onRefresh();
    }

    @Override
    public void showError(String errorMsg) {

    }

    @Override
    public void showGankHuo(@NonNull List<GankEntity> gankEntityList) {

    }
}
