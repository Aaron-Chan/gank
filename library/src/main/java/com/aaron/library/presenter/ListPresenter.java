package com.aaron.library.presenter;

import android.support.annotation.NonNull;

import com.aaron.library.view.IListView;

import java.util.List;

/**
 * Created by Aaron on 2016/12/29.
 */

public abstract class ListPresenter<V extends IListView> extends BasePresenter<V> {

    private int mCurrentPageIndex;
    private List mItems;
    private boolean isCanLoadMore = true;

    public ListPresenter(@NonNull V view, @NonNull List items) {
        super(view);
        // 跟IListView 共同持有List的引用
        mItems = items;
    }

    protected abstract void loadData(int pageIndex);

    public void onRefresh() {
        mView.showLoading();
        mCurrentPageIndex = getInitPageIndex();
        if (isCanLoadMore)
            mView.showLoadMore();
        loadData(mCurrentPageIndex);
    }

    public void onLoadMore() {
        loadData(++mCurrentPageIndex);
    }

    protected int getInitPageIndex() {
        return 1;
    }

    protected int getPageSize() {
        return 10;
    }

    public void disAbleLoadMore() {
        isCanLoadMore = false;
        mView.disableLoadMore();
    }

    protected void onDataObtained(int pageIndex, List items) {
        mView.showContent();
        if (pageIndex == getInitPageIndex() && items.size() <= 0) {//无数据
            mView.showEmpty();
        } else if (pageIndex == getInitPageIndex()) {//刷新
            mItems.clear();
            mItems.addAll(items);
        } else if (items != null && items.size() != 0) {//加载更多
            mItems.addAll(items);
        } else {//没有更多数据了
            mCurrentPageIndex--;
            mView.showLoadCompleted();
        }
        mView.showContent(mItems);
    }

    protected void onDataObtainFailed(Throwable throwable) {
        boolean firstLoad = mCurrentPageIndex == getInitPageIndex();
        mView.showLoadError(firstLoad);
    }


    public void onRetry() {
        loadData(mCurrentPageIndex);
    }
}
