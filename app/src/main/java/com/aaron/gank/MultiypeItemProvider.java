package com.aaron.gank;

import com.aaron.gank.data.DailyData;
import com.aaron.gank.data.entity.GankEntity;
import com.aaron.gank.ui.provider.DailyDataProvider;
import com.aaron.gank.ui.provider.GankEntityProvider;

import me.drakeet.multitype.GlobalMultiTypePool;

/**
 * Created by Aaron on 2016/12/29.
 */

public class MultiypeItemProvider {

    public static void register() {
        GlobalMultiTypePool.register(DailyData.class, new DailyDataProvider());
        GlobalMultiTypePool.register(GankEntity.class, new GankEntityProvider());
    }

}
