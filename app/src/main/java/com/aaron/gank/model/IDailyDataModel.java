package com.aaron.gank.model;

import com.aaron.gank.data.DailyData;

import java.util.Date;
import java.util.List;

import rx.Observable;

/**
 * Created by Aaron on 2016/12/24.
 */

public interface IDailyDataModel {

    Observable<List<DailyData>> getDailyData(Date date);

}
