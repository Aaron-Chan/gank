package com.aaron.gank.utils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by AaronChan on 2016/12/26.
 */
public class RxUtils {
    private static Observable.Transformer sTransformer;

    static {
        sTransformer = createDefaultTransformer();
    }

    private RxUtils() {

    }

    private static <T> Observable.Transformer<T, T> createDefaultTransformer() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
            }
        };
    }

    @SuppressWarnings("unchecked")
    public static <T> Observable.Transformer<T, T> getTransformer() {
        return sTransformer;
    }
}
