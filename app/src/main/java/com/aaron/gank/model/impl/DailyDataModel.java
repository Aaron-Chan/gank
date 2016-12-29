package com.aaron.gank.model.impl;

import com.aaron.gank.data.DailyData;
import com.aaron.gank.model.IDailyDataModel;

import java.util.Date;
import java.util.List;

import rx.Observable;

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
    public Observable<List<DailyData>> getDailyData(Date date) {
        return null;
//        GankService gankService = GankServiceFactory.getInstance().getGankService();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//
//         gankService.getDailyData(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
    }
}