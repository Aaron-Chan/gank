package com.aaron.gank.repository;

import android.support.annotation.NonNull;

import com.aaron.library.rx.ServiceFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Aaron on 2016/12/24.
 * GankService 工厂
 */

public class GankServiceFactory extends ServiceFactory {

    private static final GankServiceFactory INSTANCE = new GankServiceFactory();
    private static GankService sGankService;


    private GankServiceFactory() {
        super();
    }

    public static GankServiceFactory getInstance() {
        return INSTANCE;
    }

    public synchronized GankService getGankService() {
        if (sGankService == null) {
            sGankService = createService(GankService.class, GankService.BASE_URL);
        }
        return sGankService;
    }

    @NonNull
    @Override
    protected Gson getDefaultGson() {
        return new GsonBuilder().setDateFormat(GankService.GANK_ITEM_PUBLISH_DATE_FORMAT).create();
    }


}
