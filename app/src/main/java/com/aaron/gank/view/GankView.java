package com.aaron.gank.view;

import android.support.annotation.NonNull;

import com.aaron.gank.data.entity.GankEntity;
import com.aaron.library.view.IListView;

import java.util.List;

/**
 * Created by Aaron on 2016/12/30.
 */

public interface GankView extends IListView {

    void showGankHuo(@NonNull List<GankEntity> gankEntityList);

}
