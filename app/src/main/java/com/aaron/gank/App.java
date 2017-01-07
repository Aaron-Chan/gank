package com.aaron.gank;

import android.app.Application;
import android.content.Context;

import com.aaron.library.utils.ToastUtils;
import com.orhanobut.logger.Logger;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Aaron on 2016/12/23.
 * Application
 */
public class App extends Application {

    public static Context sContext;
    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;

        Logger.init("Gank");
        ToastUtils.init(sContext);
        MultiypeItemProvider.register();

        // 内存泄露检查
        refWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        App application = (App) context.getApplicationContext();
        return application.refWatcher;
    }

}
