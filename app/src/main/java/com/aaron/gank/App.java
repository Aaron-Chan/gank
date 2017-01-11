package com.aaron.gank;

import com.aaron.library.AaronApplication;
import com.orhanobut.logger.Logger;

/**
 * Created by Aaron on 2016/12/23.
 * Application
 */
public class App extends AaronApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;

        Logger.init("Gank");
        MultiypeItemProvider.register();
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }


}
