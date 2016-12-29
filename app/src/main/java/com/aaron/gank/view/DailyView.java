package com.aaron.gank.view;

import android.support.annotation.NonNull;

import com.aaron.gank.data.DailyData;
import com.aaron.gank.view.base.BaseView;
import com.aaron.gank.view.base.LoadingView;

import java.util.List;

/**
 * Created by Aaron on 2016/12/25.
 */

public interface DailyView extends BaseView, LoadingView {

    void showDaily(@NonNull List<DailyData> dailyList);

}
