package com.aaron.gank.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;

import com.aaron.gank.data.entity.GankEntity;
import com.aaron.gank.data.entity.GankImageEntity;
import com.aaron.gank.data.entity.GankTextEntity;
import com.aaron.gank.presenter.GankPresenter;
import com.aaron.gank.view.GankView;
import com.aaron.library.fragment.BaseListFragment;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 *
 */
public class GankFragment extends BaseListFragment implements GankView {

    private static final String ARG_TYPE = "type";
    private String mType;
    private GankPresenter mGankPresenter;

    public GankFragment() {
    }

    public static GankFragment newInstance(@NonNull String type) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TYPE, type);
        GankFragment gankFragment = new GankFragment();
        gankFragment.setArguments(bundle);
        return gankFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getString(ARG_TYPE);
    }

    @Override
    protected void customViewConfig() {
        super.customViewConfig();
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void initData() {
        mGankPresenter = new GankPresenter(this, mItems, mType);
        mGankPresenter.onRefresh();
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
        mGankPresenter.onRetry();
    }

    @Override
    public void showError(String errorMsg) {

    }

    @Override
    protected MultiTypeAdapter getAdapter() {
        return new MultiTypeAdapter(mItems) {
            @NonNull
            @Override
            public Class onFlattenClass(@NonNull Object item) {
                if (item instanceof GankEntity) {
                    if (((GankEntity) item).getImages() != null) {
                        return GankImageEntity.class;
                    } else {
                        return GankTextEntity.class;
                    }
                }
                return super.onFlattenClass(item);
            }
        };
    }
}
