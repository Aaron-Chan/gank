package com.aaron.gank.ui.fragment;


import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.aaron.gank.data.entity.GankEntity;
import com.aaron.gank.data.entity.GirlEntity;
import com.aaron.gank.presenter.GirlsPresenter;
import com.aaron.gank.view.GrilsView;
import com.aaron.library.fragment.BaseListFragment;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * 妹子 Fragment
 */
public class GirlsFragment extends BaseListFragment implements GrilsView {

    private GirlsPresenter mGirlsPresenter;
    private boolean mIsLinearLayoutMode = true; // 默认显示方式


    @Override
    protected void initData() {
        super.initData();
        mGirlsPresenter = new GirlsPresenter(this, mItems);
        mGirlsPresenter.onRefresh();
    }

    @Override
    protected void onLoadMore() {
        mGirlsPresenter.onLoadMore();
    }

    @Override
    protected void onRefresh() {
        mGirlsPresenter.onRefresh();
    }

    @Override
    public void onRetry() {
        mGirlsPresenter.onRetry();
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
                    return GirlEntity.class;
                }
                return super.onFlattenClass(item);
            }
        };
    }

    public void changeDisplayMode() {
        RecyclerView.LayoutManager layoutManager;
        mIsLinearLayoutMode = !mIsLinearLayoutMode;
        if (mIsLinearLayoutMode) {
            layoutManager = new LinearLayoutManager(getContext());
        } else {
            layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }
        mRecyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void customViewConfig() {
        super.customViewConfig();
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
