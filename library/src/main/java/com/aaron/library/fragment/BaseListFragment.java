package com.aaron.library.fragment;


import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aaron.library.R;
import com.aaron.library.R2;
import com.aaron.library.adapter.LoadMoreWrapper;
import com.aaron.library.view.IListView;
import com.aaron.library.widget.StatusViewLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.drakeet.multitype.MultiTypeAdapter;


/**
 * Created by Aaron on 2016/12/29.
 */

public abstract class BaseListFragment extends BaseFragment implements IListView {

    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R2.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R2.id.status_layout)
    StatusViewLayout mStatusLayout;

    protected List mItems;
    private LoadMoreWrapper mLoadMoreWrapper;

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        mRecyclerView.setLayoutManager(getLayoutManager());

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                BaseListFragment.this.onRefresh();
            }
        });

        mItems = new ArrayList();
        MultiTypeAdapter multiTypeAdapter = getAdapter();
        multiTypeAdapter.applyGlobalMultiTypePool();
        mLoadMoreWrapper = new LoadMoreWrapper(getContext(), multiTypeAdapter);
        mLoadMoreWrapper.setOnLoadListener(new LoadMoreWrapper.OnLoadListener() {
            @Override
            public void onRetry() {
                BaseListFragment.this.onRetry();
            }

            @Override
            public void onLoadMore() {
                BaseListFragment.this.onLoadMore();
            }
        });
        mRecyclerView.setAdapter(mLoadMoreWrapper);

        mStatusLayout.setOnRetryListener(new View.OnClickListener() {//错误重试
            @Override
            public void onClick(View v) {
                BaseListFragment.this.onRetry();
            }
        });

    }

    protected abstract void onLoadMore();

    protected MultiTypeAdapter getAdapter() {
        return new MultiTypeAdapter(mItems);
    }


    protected abstract void onRefresh();

    public abstract void onRetry();

    @Override
    public void showLoading() {
        mStatusLayout.showLoading();
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void showEmpty() {
        mStatusLayout.showEmpty();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showContent() {
        mStatusLayout.showContent();
        mSwipeRefreshLayout.setRefreshing(false);
        mLoadMoreWrapper.notifyDataSetChanged();
    }

    @Override
    public void showContent(@Nullable List items) {
        mItems = items;
        showContent();
    }


    @Override
    public void showLoadMore() {
        mLoadMoreWrapper.showLoadMore();
    }

    @Override
    public void showLoadCompleted() {
        mLoadMoreWrapper.showLoadComplete();
    }


    @Override
    public void showLoadError(boolean firstLoad) {
        if (firstLoad) {
            mStatusLayout.showError();
        } else {
            mLoadMoreWrapper.showLoadError();
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void disableLoadMore() {
        mLoadMoreWrapper.disableLoadMore();
    }

    private RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getContext());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base_list;
    }


}
