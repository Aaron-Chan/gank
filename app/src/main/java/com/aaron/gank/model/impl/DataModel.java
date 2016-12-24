package com.aaron.gank.model.impl;

import com.aaron.gank.data.Response;
import com.aaron.gank.data.entity.GankEntity;
import com.aaron.gank.model.IDataModel;
import com.aaron.gank.repository.GankService;
import com.aaron.gank.repository.GankServiceFactory;

import java.util.List;

import rx.Observable;

/**
 * Created by Aaron on 2016/12/24.
 * 干货 model
 */

public class DataModel implements IDataModel {

    private static final DataModel INSTANCE = new DataModel();

    private DataModel() {

    }

    public static DataModel getInstance() {
        return INSTANCE;
    }

    @Override
    public Observable<Response<List<GankEntity>>> getData(String category, int count, int page) {
        GankService gankService = GankServiceFactory.getInstance().getGankService();
        return gankService.getCategoryData(category, count, page);
    }

    @Override
    public Observable<Response<List<GankEntity>>> getRandomData(String category, int count) {
        GankService gankService = GankServiceFactory.getInstance().getGankService();
        return gankService.getRandomData(category, count);
    }

}
