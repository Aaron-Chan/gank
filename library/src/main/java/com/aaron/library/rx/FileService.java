package com.aaron.library.rx;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Aaron on 2017/1/9.
 */
public interface FileService {

    @GET
    @Streaming
    Observable<ResponseBody> download(@Url String url);
}
