package com.aaron.gank.presenter;

import android.support.annotation.NonNull;

import com.aaron.gank.data.DailyData;
import com.aaron.gank.model.IDailyDataModel;
import com.aaron.gank.model.impl.DailyDataModel;
import com.aaron.gank.view.DailyView;
import com.aaron.library.presenter.ListPresenter;
import com.aaron.library.rx.BaseSubscriber;
import com.aaron.library.utils.RxUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Aaron on 2016/12/25.
 */

public class DailyPresenter extends ListPresenter<DailyView> {

    private IDailyDataModel mDailyDataModel;

    public DailyPresenter(@NonNull DailyView view, @NonNull List items) {
        super(view, items);
        mDailyDataModel = DailyDataModel.getInstance();
    }

    @Override
    protected void loadData(final int pageIndex) {
        mDailyDataModel.getDailyData(new Date())
                .compose(RxUtils.<List<DailyData>>getTransformer())
                .subscribe(new BaseSubscriber<List<DailyData>>() {
                    @Override
                    public void onSuccess(@NonNull List<DailyData> dailyDatas) {
                        onDataObtained(pageIndex, dailyDatas);
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        onDataObtainFailed(e);
                    }
                });
    }
}
