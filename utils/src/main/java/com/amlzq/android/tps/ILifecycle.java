package com.amlzq.android.tps;

import android.app.Activity;
import android.app.Application;

/**
 * 生命周期
 */
public interface ILifecycle {

    /**
     * application.create()中执行<br>
     * 初始化SDK<br>
     */
    public void onApplicationCreate(Application app);


    /**
     * 开屏页
     * android.intent.category.LAUNCHER对应的Activity
     */
    public void onLauncherActivityStart();

    /**
     * 在activity.onCreate()中调用
     *
     * @param activity 上下文
     */
    public void onActivityCreate(final Activity activity);

}
