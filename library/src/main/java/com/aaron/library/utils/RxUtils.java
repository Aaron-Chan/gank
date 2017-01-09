package com.aaron.library.utils;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by AaronChan on 2016/12/26.
 */
public class RxUtils {
    private static Observable.Transformer sDefaultTransformer;
    private static Observable.Transformer sIOTransformer;

    static {
        sDefaultTransformer = createDefaultTransformer();
        sIOTransformer = createIOTransformer();
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

    private static <T> Observable.Transformer<T, T> createIOTransformer() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.observeOn(Schedulers.io()).subscribeOn(Schedulers.io());
            }
        };
    }

    @SuppressWarnings("unchecked")
    public static <T> Observable.Transformer<T, T> getDefaultTransformer() {
        return sDefaultTransformer;
    }

    @SuppressWarnings("unchecked")
    public static <T> Observable.Transformer<T, T> getIOTransformer() {
        return sIOTransformer;
    }
}
