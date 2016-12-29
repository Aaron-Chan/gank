package com.aaron.library.view;

import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by Aaron on 2016/12/29.
 */

public interface IListView extends BaseView {

    void showLoading();

    void showEmpty();

    void showContent(@Nullable List items);

    void showLoadMore();

    void showLoadCompleted();

    void disableLoadMore();

    void showContent();

    void showLoadError(boolean firstLoad);
}
