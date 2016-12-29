package com.aaron.library.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * 参考
 * #https://github.com/burgessjp/GanHuoIO/tree/master/library/src/main/java/ren/solid/library/adapter/LoadMoreWrapper
 */

public class LoadMoreWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int ITEM_TYPE_LOAD_FAILED_VIEW = Integer.MAX_VALUE - 1;
    public static final int ITEM_TYPE_NO_MORE_VIEW = Integer.MAX_VALUE - 2;
    public static final int ITEM_TYPE_LOAD_MORE_VIEW = Integer.MAX_VALUE - 3;
    public static final int ITEM_TYPE_NO_VIEW = Integer.MAX_VALUE - 4;//不展示footer view

    private Context mContext;
    private RecyclerView.Adapter mInnerAdapter;

    private View mLoadMoreView;
    private View mLoadMoreFailedView;
    private View mNoMoreView;

    private int mCurrentItemType = ITEM_TYPE_LOAD_MORE_VIEW;
    private LoadMoreScrollListener mLoadMoreScrollListener;


    private boolean isLoadError = false;//标记是否加载出错
    private boolean isHaveStatesView = true;

    public LoadMoreWrapper(Context context, RecyclerView.Adapter adapter) {
        this.mContext = context;
        this.mInnerAdapter = adapter;
        mLoadMoreScrollListener = new LoadMoreScrollListener() {
            @Override
            public void loadMore() {
                if (mOnLoadListener != null && isHaveStatesView) {
                    if (!isLoadError) {
                        showLoadMore();
                        mOnLoadListener.onLoadMore();
                    }
                }
            }
        };
    }

    public void showLoadMore() {
        mCurrentItemType = ITEM_TYPE_LOAD_MORE_VIEW;
        isLoadError = false;
        isHaveStatesView = true;
        notifyItemChanged(getItemCount());
    }

    public void showLoadError() {
        mCurrentItemType = ITEM_TYPE_LOAD_FAILED_VIEW;
        isLoadError = true;
        isHaveStatesView = true;
        notifyItemChanged(getItemCount());
    }

    public void showLoadComplete() {
        mCurrentItemType = ITEM_TYPE_NO_MORE_VIEW;
        isLoadError = false;
        isHaveStatesView = true;
        notifyItemChanged(getItemCount());
    }

    public void disableLoadMore() {
        mCurrentItemType = ITEM_TYPE_NO_VIEW;
        isHaveStatesView = false;
        notifyDataSetChanged();
    }

    //region Get ViewHolder
    private ViewHolder getLoadMoreViewHolder() {
        if (mLoadMoreView == null) {
            mLoadMoreView = new TextView(mContext);
            mLoadMoreView.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            mLoadMoreView.setPadding(20, 20, 20, 20);
            ((TextView) mLoadMoreView).setText("加载中...");
            ((TextView) mLoadMoreView).setGravity(Gravity.CENTER);
        }
        return ViewHolder.newInstance(mContext, mLoadMoreView);
    }

    private ViewHolder getLoadFailedViewHolder() {
        if (mLoadMoreFailedView == null) {
            mLoadMoreFailedView = new TextView(mContext);
            mLoadMoreFailedView.setPadding(20, 20, 20, 20);
            mLoadMoreFailedView.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            ((TextView) mLoadMoreFailedView).setText("加载失败，点击重试");
            ((TextView) mLoadMoreFailedView).setGravity(Gravity.CENTER);
        }
        return ViewHolder.newInstance(mContext, mLoadMoreFailedView);
    }

    private ViewHolder getNoMoreViewHolder() {
        if (mNoMoreView == null) {
            mNoMoreView = new TextView(mContext);
            mNoMoreView.setPadding(20, 20, 20, 20);
            mNoMoreView.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            ((TextView) mNoMoreView).setText("已经没有啦");
            ((TextView) mNoMoreView).setGravity(Gravity.CENTER);
        }
        return ViewHolder.newInstance(mContext, mNoMoreView);
    }
    //endregion

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1 && isHaveStatesView) {
            return mCurrentItemType;
        }
        return mInnerAdapter.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_NO_MORE_VIEW) {
            return getNoMoreViewHolder();
        } else if (viewType == ITEM_TYPE_LOAD_MORE_VIEW) {
            return getLoadMoreViewHolder();
        } else if (viewType == ITEM_TYPE_LOAD_FAILED_VIEW) {
            return getLoadFailedViewHolder();
        }
        return mInnerAdapter.onCreateViewHolder(parent, viewType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == ITEM_TYPE_LOAD_FAILED_VIEW) {
            mLoadMoreFailedView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnLoadListener != null) {
                        mOnLoadListener.onRetry();
                        showLoadMore();
                    }
                }
            });
            return;
        }
        if (!isFooterType(holder.getItemViewType()))
            mInnerAdapter.onBindViewHolder(holder, position);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                //对于stateView 将其宽度设为最大
                if (position == getItemCount() - 1 && isHaveStatesView) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null && isHaveStatesView) {
                    return oldLookup.getSpanSize(position);
                }
                return 1;
            }
        });
        recyclerView.addOnScrollListener(mLoadMoreScrollListener);
    }


    @SuppressWarnings("unchecked")
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);

        if (holder.getLayoutPosition() == getItemCount() - 1 && isHaveStatesView) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

            if (lp != null
                    && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;

                p.setFullSpan(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mInnerAdapter.getItemCount() + (isHaveStatesView ? 1 : 0);
    }

    public boolean isFooterType(int type) {

        return type == ITEM_TYPE_NO_VIEW ||
                type == ITEM_TYPE_LOAD_FAILED_VIEW ||
                type == ITEM_TYPE_NO_MORE_VIEW ||
                type == ITEM_TYPE_LOAD_MORE_VIEW;
    }


    public interface OnLoadListener {
        void onRetry();

        void onLoadMore();
    }

    private OnLoadListener mOnLoadListener;

    public LoadMoreWrapper setOnLoadListener(OnLoadListener onLoadListener) {
        mOnLoadListener = onLoadListener;
        return this;
    }

}

