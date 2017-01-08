package com.aaron.library;

import android.app.Application;
import android.content.Context;

import com.aaron.library.utils.ToastUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by AaronChan on 2017/1/8.
 */

public class AaronApplication extends Application {

    public static Context sContext;
    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;

        ToastUtils.init(sContext);
        // 内存泄露检查
        refWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        AaronApplication application = (AaronApplication) context.getApplicationContext();
        return application.refWatcher;
    }
}
