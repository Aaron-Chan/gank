package com.aaron.gank.model;

import com.aaron.gank.data.DailyData;

import java.util.Date;

import rx.Observable;

/**
 * Created by Aaron on 2016/12/24.
 */

public interface IDailyDataModel {

    Observable<DailyData> getDailyData(Date date);

}
