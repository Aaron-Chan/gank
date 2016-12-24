package com.aaron.gank.model;

import com.aaron.gank.data.Response;
import com.aaron.gank.data.entity.GankEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by Aaron on 2016/12/24.
 */

public interface IDataModel {

    Observable<Response<List<GankEntity>>> getData(String category, int count, int page);


    Observable<Response<List<GankEntity>>> getRandomData(String category, int count);

}
