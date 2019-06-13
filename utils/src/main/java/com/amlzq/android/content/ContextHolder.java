package com.amlzq.android.content;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

/**
 * Created by amlzq on 2018/5/1.
 * Context holder
 */
public class ContextHolder {

    private static Context applicationContext;

    /**
     * @hide
     */
    ContextHolder() {
    }

    /* Public Methods */

    /**
     * 初始化context，如果由于不同机型导致反射获取context失败可以在Application调用此方法
     *
     * @param context context
     */
    public static void init(Context context) {
        applicationContext = context;
    }

    /**
     * 反射获取 application context
     */
    @SuppressLint("PrivateApi")
    public static Context getContext() {
        if (null == applicationContext) {
            try {
                Application application = (Application) Class.forName("android.app.ActivityThread")
                        .getMethod("currentApplication")
                        .invoke(null, (Object[]) null);
                if (application != null) {
                    applicationContext = application;
                    return application;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Application application = (Application) Class.forName("android.app.AppGlobals")
                        .getMethod("getInitialApplication")
                        .invoke(null, (Object[]) null);
                if (application != null) {
                    applicationContext = application;
                    return application;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            throw new IllegalStateException("ContextHolder is not initialed, it is recommend to init with application context.");
        }
        return applicationContext;
    }

    /**
     * Returns a localized string from the application's package's
     * default string table.
     *
     * @param resId Resource id for the string
     * @return The string data associated with the resource, stripped of styled text information.
     */
    @NonNull
    public static final String getString(@StringRes int resId) {
        return applicationContext.getResources().getString(resId);
    }

    @NonNull
    public static final Drawable getDrawable(@DrawableRes int resId) {
        return ContextCompat.getDrawable(applicationContext, resId);
    }

    @ColorInt
    public static final int getColor(@ColorRes int resId) {
        return ContextCompat.getColor(applicationContext, resId);
    }

}