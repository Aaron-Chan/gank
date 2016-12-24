package com.aaron.gank.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Aaron on 2016/12/24.
 * 服务器返回数据 基类
 */

public class Response<T> {

    @SerializedName("error")
    protected boolean error;

    @SerializedName("results")
    protected T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
