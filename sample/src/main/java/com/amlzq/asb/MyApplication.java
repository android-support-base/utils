package com.amlzq.asb;

import android.app.Application;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MyConfig.configApplication(this);
    }

}
