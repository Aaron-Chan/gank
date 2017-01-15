package com.aaron.library;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.aaron.library.utils.ToastUtils;
import com.squareup.leakcanary.LeakCanary;
import com.umeng.analytics.MobclickAgent;

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
        // umeng 禁止默认的页面统计方式
        MobclickAgent.openActivityDurationTrack(false);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        if (BuildConfig.DEBUG) {
        } else {

        }
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
