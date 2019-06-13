package com.amlzq.asb;

import android.app.Application;

import com.amlzq.android.ApplicationConfig;
import com.amlzq.android.util.UtilConfig;

/**
 * 配置
 */
public class MyConfig extends ApplicationConfig {

    public static void configApplication(Application application) {
        UtilConfig.DEBUG = BuildConfig.DEBUG;
        UtilConfig.init(application, IDENTIFY);
    }

}
