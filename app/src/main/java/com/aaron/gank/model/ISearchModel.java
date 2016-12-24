package com.aaron.gank.model;

import com.aaron.gank.data.SearchData;

import rx.Observable;

/**
 * Created by Aaron on 2016/12/24.
 */

public interface ISearchModel {

    Observable<SearchData> search(String category, int count, int page);

}
