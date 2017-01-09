package com.aaron.gank.presenter;

import android.support.annotation.NonNull;

import com.aaron.gank.data.Response;
import com.aaron.gank.data.entity.GankEntity;
import com.aaron.gank.model.impl.DataModel;
import com.aaron.gank.rx.DefaultSubscriber;
import com.aaron.gank.view.GankView;
import com.aaron.library.presenter.ListPresenter;
import com.aaron.library.utils.RxUtils;

import java.util.List;

/**
 * Created by Aaron on 2016/12/30.
 */

public class GankPresenter extends ListPresenter<GankView> {

    private String mType;

    public GankPresenter(@NonNull GankView view, List items, @NonNull String type) {
        super(view, items);
        mType = type;
    }

    @Override
    protected void loadData(final int pageIndex) {
        mCompositeSubscription.add(DataModel.getInstance().getData(mType, getPageSize(), pageIndex)
                .compose(RxUtils.<Response<List<GankEntity>>>getDefaultTransformer())
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
