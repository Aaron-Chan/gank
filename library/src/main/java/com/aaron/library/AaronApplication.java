package com.aaron.library;

import android.app.Application;
import android.content.Context;

import com.aaron.library.utils.ToastUtils;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by AaronChan on 2017/1/8.
 */

public class AaronApplication extends Application {

    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;

        ToastUtils.init(sContext);
        // 内存泄露检查
        initLeakCanary();

    }

    private void initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}
