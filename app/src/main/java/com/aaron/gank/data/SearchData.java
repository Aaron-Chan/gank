package com.aaron.gank.data;

import com.aaron.gank.data.entity.SearchEntity;

import java.util.List;

/**
 * Created by Aaron on 2016/12/24.
 */

public class SearchData extends Response<List<SearchEntity>> {
    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
