package com.aaron.gank.model.impl;

import com.aaron.gank.constans.Constants;
import com.aaron.gank.model.ICategoryModel;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Aaron on 2016/12/30.
 */

public class CategoryModel implements ICategoryModel {

    private static final CategoryModel INSTANCE = new CategoryModel();

    private CategoryModel() {

    }

    public static CategoryModel getInstance() {
        return INSTANCE;
    }

    @Override
    public Observable<List<String>> getCategories() {
        List<String> categories = new ArrayList<>();
        categories.add(Constants.CATEGORY_ALL);
        categories.add(Constants.CATEGORY_ANDROID);
        categories.add(Constants.CATEGORY_IOS);
        categories.add(Constants.CATEGORY_JS);
        categories.add(Constants.CATEGORY_VIDEO);
        categories.add(Constants.CATEGORY_RESOURCES);
        categories.add(Constants.CATEGORY_APP);
        categories.add(Constants.CATEGORY_RECOMMEND);
        return Observable.just(categories);
    }

}
