package com.aaron.gank.ui.fragment;

import android.support.annotation.NonNull;

import com.aaron.gank.presenter.DailyPresenter;
import com.aaron.gank.view.DailyView;
import com.aaron.library.fragment.BaseListFragment;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Aaron on 2016/12/25.
 * 每日干货 Fragment
 */

public class DailyFragment extends BaseListFragment implements DailyView {

    private DailyPresenter mDailyPresenter;

    @Override
    protected void initData() {
        mDailyPresenter = new DailyPresenter(this, mItems);
        mDailyPresenter.onRefresh();
    }

    @Override
    protected void onLoadMore() {
        mDailyPresenter.onLoadMore();
    }

    @Override
    protected void onRefresh() {
        mDailyPresenter.onRefresh();
    }

    @Override
    public void onRetry() {
        mDailyPresenter.onRefresh();
    }

    @Override
    public void showError(String errorMsg) {

    }

    @Override
    protected MultiTypeAdapter getAdapter() {
        return new MultiTypeAdapter(mItems){
            @NonNull
            @Override
            public Class onFlattenClass(@NonNull Object item) {
                return super.onFlattenClass(item);
            }
        };
    }
}
