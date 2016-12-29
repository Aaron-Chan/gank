package com.aaron.gank.rx;

import com.aaron.gank.data.Response;

/**
 * Created by AaronChan on 2016/12/28.
 * 默认的订阅者
 */
public abstract class DefaultSubscriber<T extends Response> extends BaseSubscriber<T> {

    @Override
    public void onNext(T t) {
        if (t.isError()) {
            throw new RuntimeException("response's error is true");
        } else {
            onSuccess(t);
        }
    }
}
