package com.aaron.gank.repository;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Aaron on 2016/12/24.
 * GankService 工厂
 */

public class GankServiceFactory {

    private static final long DEFAULT_TIMEOUT = 15;
    private static final GankServiceFactory INSTANCE = new GankServiceFactory();
    private static GankService sGankService;


    private GankServiceFactory() {

    }

    public static GankServiceFactory getInstance() {
        return INSTANCE;
    }

    public synchronized GankService getGankService() {
        if (sGankService == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(GankService.BASE_URL)
                    .client(client)
                    .build();
            sGankService = retrofit.create(GankService.class);
        }
        return sGankService;
    }


}
