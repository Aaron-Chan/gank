package com.aaron.gank.view;

import android.support.annotation.NonNull;

import com.aaron.library.view.BaseView;

import java.util.List;

/**
 * Created by Aaron on 2016/12/30.
 */

public interface CategoryView extends BaseView {

    void showCategories(@NonNull List<String> names);

}
