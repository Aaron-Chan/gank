package com.aaron.gank.repository;

import android.support.annotation.NonNull;

import com.aaron.gank.App;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.orhanobut.logger.Logger.d;
import static okhttp3.internal.Util.UTF_8;

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
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .cache(new Cache(App.sContext.getCacheDir(), 100 * 1024 * 1024))
                    .addInterceptor(new LoggingInterceptor())
                    .build();
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(createGson()))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(GankService.BASE_URL)
                    .client(client)
                    .build();
            sGankService = retrofit.create(GankService.class);
        }
        return sGankService;
    }

    @NonNull
    private Gson createGson() {
        return new GsonBuilder().setDateFormat(GankService.GANK_ITEM_PUBLISH_DATE_FORMAT).create();
    }

    /**
     * 打印Log拦截器
     */
    private static final class LoggingInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            d(String.format("Sending request %s on %s%n%s", request.url(), chain.connection(),
                    request.headers()));
            Response response;
            try {
                response = chain.proceed(request);
                long t2 = System.nanoTime();
                Logger.d(String.format(Locale.CHINA, "Received response for %s code %d in %.1fms%n%s", response.request().url(), response.code(),
                        (t2 - t1) / 1e6d, response.headers()));
                ResponseBody responseBody = response.body();
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();
                d("ret ==> " + buffer.clone().readString(UTF_8));
            } catch (Exception e) {
                Logger.e(String.format("exception occur for %s", e.toString()));
                throw new IOException(e);
            }

            return response;
        }
    }


}
