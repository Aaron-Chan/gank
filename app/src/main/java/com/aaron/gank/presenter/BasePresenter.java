package com.aaron.gank.presenter;

import com.aaron.gank.view.BaseView;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Aaron on 2016/12/23.
 * base class of Presenter
 */

public abstract class BasePresenter<V extends BaseView> {

    protected CompositeSubscription mCompositeSubscription;
    protected V mView;

    public BasePresenter(V view) {
        mView = view;
        mCompositeSubscription = new CompositeSubscription();
    }

    public void subscribe() {

    }

    public void unsubscribe() {
        mCompositeSubscription.unsubscribe();
        mCompositeSubscription = null;
        mView = null;
    }

}
