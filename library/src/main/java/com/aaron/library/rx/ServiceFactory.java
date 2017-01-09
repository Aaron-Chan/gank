package com.aaron.library.rx;

import android.support.annotation.NonNull;

import com.aaron.library.AaronApplication;
import com.google.gson.Gson;
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
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.orhanobut.logger.Logger.d;

/**
 * Created by Aaron on 2017/1/9.
 */

public class ServiceFactory {

    private static final long DEFAULT_TIMEOUT = 15;

    private static final ServiceFactory INSTANCE = new ServiceFactory();
    private Retrofit.Builder mRetrofitBuilder;

    protected ServiceFactory() {
        mRetrofitBuilder = getRetrofitBuilder();
    }

    protected OkHttpClient.Builder getDefaultOkHttpClientBuilder() { // 让子类可重写
        return new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .cache(new Cache(AaronApplication.sContext.getCacheDir(), 100 * 1024 * 1024))
                .addInterceptor(new LoggingInterceptor());
    }

    protected Retrofit.Builder getRetrofitBuilder() { // 让子类可重写
        return new Retrofit.Builder()
                .client(getDefaultOkHttpClientBuilder().build())
                .addConverterFactory(GsonConverterFactory.create(getDefaultGson()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
    }

    @NonNull
    protected Gson getDefaultGson() {
        return new Gson();
    }

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }


    public <S> S createService(Class<S> serviceClass, String baseUrl) {
        Retrofit retrofit = mRetrofitBuilder
                .baseUrl(baseUrl)
                .build();
        return retrofit.create(serviceClass);
    }

    public <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, "http://example.com/api/");
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
                okio.Buffer buffer = source.buffer();
                d("ret ==> " + buffer.clone().readString(okhttp3.internal.Util.UTF_8));
            } catch (Exception e) {
                Logger.e(String.format("exception occur for %s", e.toString()));
                throw new IOException(e);
            }

            return response;
        }
    }


}
