package com.aaron.gank;

import android.app.Application;
import android.content.Context;

import com.aaron.library.utils.ToastUtils;
import com.orhanobut.logger.Logger;

/**
 * Created by Aaron on 2016/12/23.
 * Application
 */
public class App extends Application {

    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;

        Logger.init("Gank");
        ToastUtils.init(sContext);
    }

}
