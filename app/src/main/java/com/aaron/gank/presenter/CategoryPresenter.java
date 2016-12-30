package com.aaron.gank.presenter;

import android.support.annotation.NonNull;

import com.aaron.gank.model.ICategoryModel;
import com.aaron.gank.model.impl.CategoryModel;
import com.aaron.gank.view.CategoryView;
import com.aaron.library.presenter.BasePresenter;
import com.aaron.library.rx.BaseSubscriber;
import com.aaron.library.utils.RxUtils;

import java.util.List;

/**
 * Created by Aaron on 2016/12/30.
 */

public class CategoryPresenter extends BasePresenter<CategoryView> {

    private ICategoryModel mCategoryModel;

    public CategoryPresenter(@NonNull CategoryView view) {
        super(view);
        mCategoryModel = CategoryModel.getInstance();
    }

    public void getCategoriesNames() {
        mCategoryModel.getCategories()
                .compose(RxUtils.<List<String>>getTransformer())
                .subscribe(new BaseSubscriber<List<String>>() {
                    @Override
                    public void onSuccess(@NonNull List<String> strings) {
                        mView.showCategories(strings);
                    }

                    @Override
                    public void onFailed(Throwable e) {
                        mView.showError("获取目录失败");
                    }
                });

    }
}
