package com.aaron.library.rx;

import com.orhanobut.logger.Logger;

import rx.Subscriber;

/**
 * Created by AaronChan on 2016/12/28.
 * 订阅者基类
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (e == null) {
            e = new Throwable("error message is empty");
        }
        Logger.e(e, "");
        onFailed(e);
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    public abstract void onSuccess(T t);

    public abstract void onFailed(Throwable e);
}
