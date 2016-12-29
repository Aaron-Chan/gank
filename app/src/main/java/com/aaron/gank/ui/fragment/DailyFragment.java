package com.aaron.gank.ui.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.aaron.gank.R;
import com.aaron.gank.data.DailyData;
import com.aaron.gank.presenter.DailyPresenter;
import com.aaron.gank.view.DailyView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Aaron on 2016/12/25.
 * 每日干货 Fragment
 */

public class DailyFragment extends BaseFragment implements DailyView {


    @BindView(R.id.rv_daily)
    RecyclerView mRvDaily;
    private DailyPresenter mDailyPresenter;

    @Override
    protected void initData() {
        mDailyPresenter = new DailyPresenter(this);
        mDailyPresenter.getDailyData();
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_daily;
    }


    @Override
    public void showDaily(@NonNull List<DailyData> dailyList) {

    }

    @Override
    public void showError(String errorMsg) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
