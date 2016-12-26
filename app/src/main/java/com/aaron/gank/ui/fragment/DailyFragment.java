package com.aaron.gank.ui.fragment;

import android.support.annotation.NonNull;

import com.aaron.gank.R;
import com.aaron.gank.data.entity.GankEntity;
import com.aaron.gank.presenter.DailyPresenter;
import com.aaron.gank.view.DailyView;

import java.util.List;

/**
 * Created by Aaron on 2016/12/25.
 * 每日干货 Fragment
 */

public class DailyFragment extends BaseFragment implements DailyView {


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
    public void showDaily(@NonNull List<GankEntity> dailyList) {

    }
}
