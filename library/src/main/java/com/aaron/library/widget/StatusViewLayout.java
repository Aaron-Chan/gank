package com.aaron.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.aaron.library.R;


/**
 * Created by _SOLID
 * Date:2016/7/8
 * Time:10:36
 */
public class StatusViewLayout extends FrameLayout {

    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private OnClickListener mOnRetryListener;
    private TextView mErrorMsgView;
    private TextView mEmptyMsgView;

    public StatusViewLayout(Context context) {
        this(context, null);
    }

    public StatusViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    private void initView() {
        mLoadingView = LayoutInflater.from(getContext()).inflate(R.layout.status_view_loading, null);
        mErrorView = LayoutInflater.from(getContext()).inflate(R.layout.status_view_error, null);
        mEmptyView = LayoutInflater.from(getContext()).inflate(R.layout.status_view_empty, null);

        mErrorMsgView = (TextView) mErrorView.findViewById(R.id.tv_status_error_msg);
        mEmptyMsgView = (TextView) mEmptyView.findViewById(R.id.tv_status_empty_msg);

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;

        addView(mLoadingView, layoutParams);
        addView(mErrorView, layoutParams);
        addView(mEmptyView, layoutParams);

        mErrorView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnRetryListener != null) {
                    showLoading();
                    mOnRetryListener.onClick(view);
                }
            }
        });
    }

    public void setOnRetryListener(OnClickListener listener) {
        mOnRetryListener = listener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        showLoading();
    }

    public void showLoading() {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setVisibility(View.GONE);
        }
        mLoadingView.setVisibility(View.VISIBLE);
    }

    public void showError() {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setVisibility(View.GONE);
        }
        mErrorView.setVisibility(View.VISIBLE);
    }

    public void showError(String msg) {
        showError();
        mErrorMsgView.setText(msg);
    }

    public void showEmpty() {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setVisibility(View.GONE);
        }
        mEmptyView.setVisibility(View.VISIBLE);
    }

    public void showEmpty(String msg) {
        showEmpty();
        mEmptyMsgView.setText(msg);

    }

    public void showContent() {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setVisibility(View.GONE);
        }
        getChildAt(getChildCount() - 1).setVisibility(View.VISIBLE);
    }
}
