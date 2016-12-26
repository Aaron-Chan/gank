package com.aaron.gank.presenter;

import android.support.annotation.NonNull;

import com.aaron.gank.data.DailyData;
import com.aaron.gank.model.IDailyDataModel;
import com.aaron.gank.model.impl.DailyDataModel;
import com.aaron.gank.view.DailyView;

import java.util.Date;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
        Subscription subscription = mDailyDataModel.getDailyData(new Date())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DailyData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(DailyData dailyData) {
                        if (dailyData.isError()) {
                        } else {

                        }
                    }
                });
        mCompositeSubscription.add(subscription);
    }
}
