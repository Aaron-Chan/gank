package com.aaron.gank.presenter;

import android.support.annotation.NonNull;

import com.aaron.gank.view.BaseView;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Aaron on 2016/12/23.
 * base class of Presenter
 */

public abstract class BasePresenter<V extends BaseView> {

    protected CompositeSubscription mCompositeSubscription;
    protected V mView;

    public BasePresenter(@NonNull V view) {
        mView = view;
        mCompositeSubscription = new CompositeSubscription();
    }


    public void unsubscribe() {
        mCompositeSubscription.unsubscribe();
        mCompositeSubscription = null;
        mView = null;
    }

}
