package com.aaron.gank.model.impl;

import android.support.annotation.NonNull;

import com.aaron.gank.data.DailyData;
import com.aaron.gank.data.Response;
import com.aaron.gank.model.IDailyDataModel;
import com.aaron.gank.repository.GankService;
import com.aaron.gank.repository.GankServiceFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by Aaron on 2016/12/24.
 * 每日干货 model
 */

public class DailyDataModel implements IDailyDataModel {

    private static final DailyDataModel INSTANCE = new DailyDataModel();

    private DailyDataModel() {

    }

    public static DailyDataModel getInstance() {
        return INSTANCE;
    }

    @Override
    public Observable<List<DailyData>> getDailyData(@NonNull List<Date> dates) {
        return Observable.just(dates)
                .flatMapIterable(new Func1<List<Date>, Iterable<Date>>() {
                    @Override
                    public Iterable<Date> call(List<Date> dates) {
                        return dates;
                    }
                })
                .flatMap(new Func1<Date, Observable<DailyData>>() {
                    @Override
                    public Observable<DailyData> call(Date date) {
                        GankService gankService = GankServiceFactory.getInstance().getGankService();

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);

                        return gankService.getDailyData(calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH) + 1,
                                calendar.get(Calendar.DAY_OF_MONTH));
                    }
                })
                .toSortedList(new Func2<DailyData, DailyData, Integer>() {
                    @Override
                    public Integer call(DailyData dailyData, DailyData dailyData2) {
                        // 降序排列
                        return dailyData2.getResults().androidData.get(0).getPublishedAt()
                                .compareTo(dailyData.getResults().androidData.get(0).getPublishedAt());
                    }
                });
    }

    @Override
    public Observable<Response<List<String>>> getPublishDates() {
        GankService gankService = GankServiceFactory.getInstance().getGankService();

        return gankService.getPublishDates();
    }
}
