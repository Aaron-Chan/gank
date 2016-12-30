package com.aaron.gank.presenter;

import android.support.annotation.NonNull;

import com.aaron.gank.view.GankView;
import com.aaron.library.presenter.BasePresenter;
import com.aaron.library.presenter.ListPresenter;

import java.util.List;

/**
 * Created by Aaron on 2016/12/30.
 */

public class GankPresenter extends ListPresenter<GankView> {

    private String mType;

    public GankPresenter(@NonNull GankView view, List items, @NonNull String type) {
        super(view);
        mType = type;
    }

    @Override
    protected void loadData(int pageIndex) {

    }
}
