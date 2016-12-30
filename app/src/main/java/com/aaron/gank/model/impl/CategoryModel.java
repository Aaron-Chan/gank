package com.aaron.gank.model.impl;

import com.aaron.gank.model.ICategoryModel;

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

        return null;
    }

}
