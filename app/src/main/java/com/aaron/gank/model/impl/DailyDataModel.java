package com.aaron.gank.model.impl;

import com.aaron.gank.data.DailyData;
import com.aaron.gank.data.Response;
import com.aaron.gank.model.IDailyDataModel;
import com.aaron.gank.repository.GankService;
import com.aaron.gank.repository.GankServiceFactory;
import com.aaron.library.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Aaron on 2016/12/24.
 * 每日干货 model
 */

public class DailyDataModel implements IDailyDataModel {

    private static final DailyDataModel INSTANCE = new DailyDataModel();
    private List<Date> mPublishDates;


    private DailyDataModel() {

    }

    public static DailyDataModel getInstance() {
        return INSTANCE;
    }

    @Override
    public Observable<List<DailyData>> getDailyData(Date date) {

        GankService gankService = GankServiceFactory.getInstance().getGankService();

        //先获取发布干货日期，再与当前日期作比较
        gankService.getPublishDates()
                .map(new Func1<Response<List<String>>, List<String>>() {
                    @Override
                    public List<String> call(Response<List<String>> listResponse) {
                        if (listResponse.isError()) {
                            throw new RuntimeException("response's error is true");
                        } else {
                            return listResponse.getResults();
                        }
                    }
                })
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> strings) {
                        return Observable.from(strings);
                    }
                })
                .map(new Func1<String, Date>() {
                    @Override
                    public Date call(String s) {
                        return DateUtils.string2Date(s, GankService.PUBLISH_DATE_FORMAT);
                    }
                });
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);


        return gankService.getDailyData(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH)).toList();
    }
}
