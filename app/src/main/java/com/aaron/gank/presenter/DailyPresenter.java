package com.aaron.gank.presenter;

import android.support.annotation.NonNull;

import com.aaron.gank.data.DailyData;
import com.aaron.gank.model.IDailyDataModel;
import com.aaron.gank.model.impl.DailyDataModel;
import com.aaron.gank.rx.BaseSubscriber;
import com.aaron.gank.utils.RxUtils;
import com.aaron.gank.view.DailyView;

import java.util.Date;
import java.util.List;

/**
 * Created by Aaron on 2016/12/25.
 */

public class DailyPresenter extends BasePresenter<DailyView> {

    private IDailyDataModel mDailyDataModel;

    public DailyPresenter(@NonNull DailyView view) {
        super(view);
        mDailyDataModel = DailyDataModel.getInstance();
    }

    public void getDailyData() {
        mCompositeSubscription.add(mDailyDataModel.getDailyData(new Date())
                .compose(RxUtils.<List<DailyData>>getTransformer())
                .subscribe(new BaseSubscriber<List<DailyData>>() {
                    @Override
                    public void onSuccess(List<DailyData> dailyDatas) {
                        mView.hideLoading();
                        mView.showDaily(dailyDatas);
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        mView.hideLoading();
                        mView.showError(e.toString());
                    }
                }));
    }
}
