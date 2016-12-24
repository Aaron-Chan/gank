package com.aaron.gank.model.impl;

import com.aaron.gank.data.SearchData;
import com.aaron.gank.model.ISearchModel;
import com.aaron.gank.repository.GankService;
import com.aaron.gank.repository.GankServiceFactory;

import rx.Observable;

/**
 * Created by Aaron on 2016/12/24.
 * 搜索 model
 */

public class SearchModel implements ISearchModel {

    private static final SearchModel INSTANCE = new SearchModel();

    private SearchModel() {

    }

    public static SearchModel getInstance() {
        return INSTANCE;
    }

    @Override
    public Observable<SearchData> search(String category, int count, int page) {
        GankService gankService = GankServiceFactory.getInstance().getGankService();
        return gankService.getSearchData(category, count, page);
    }


}
