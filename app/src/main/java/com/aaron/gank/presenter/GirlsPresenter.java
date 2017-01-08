package com.aaron.gank.presenter;

import android.support.annotation.NonNull;

import com.aaron.gank.constans.Constants;
import com.aaron.gank.data.Response;
import com.aaron.gank.data.entity.GankEntity;
import com.aaron.gank.model.impl.DataModel;
import com.aaron.gank.rx.DefaultSubscriber;
import com.aaron.gank.view.GrilsView;
import com.aaron.library.presenter.ListPresenter;
import com.aaron.library.utils.RxUtils;

import java.util.List;

/**
 * Created by AaronChan on 2017/1/7.
 */

public class GirlsPresenter extends ListPresenter<GrilsView> {

    public GirlsPresenter(@NonNull GrilsView view, @NonNull List items) {
        super(view, items);
    }

    @Override
    protected void loadData(final int pageIndex) {
        mCompositeSubscription.add(DataModel.getInstance().getData(Constants.CATEGORY_WELFARE, getPageSize(), pageIndex)
                .compose(RxUtils.<Response<List<GankEntity>>>getTransformer())
                .subscribe(new DefaultSubscriber<Response<List<GankEntity>>>() {
                    @Override
                    public void onSuccess(@NonNull Response<List<GankEntity>> listResponse) {
                        List<GankEntity> results = listResponse.getResults();
                        onDataObtained(pageIndex, results);
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        onDataObtainFailed(e);
                    }
                }));
    }
}
