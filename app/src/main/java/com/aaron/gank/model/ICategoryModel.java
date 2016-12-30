package com.aaron.gank.model;

import java.util.List;

import rx.Observable;

/**
 * Created by Aaron on 2016/12/30.
 */

public interface ICategoryModel {
    Observable<List<String>> getCategories();
}
