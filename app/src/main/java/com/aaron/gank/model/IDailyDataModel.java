package com.aaron.gank.model;

import android.support.annotation.NonNull;

import com.aaron.gank.data.DailyData;
import com.aaron.gank.data.Response;

import java.util.Date;
import java.util.List;

import rx.Observable;

/**
 * Created by Aaron on 2016/12/24.
 */

public interface IDailyDataModel {

    Observable<List<DailyData>> getDailyData(@NonNull List<Date> dates);


    Observable<Response<List<String>>> getPublishDates();

}
