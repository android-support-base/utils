package com.amlzq.android.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.res.AssetManager;
import android.os.Bundle;

import com.amlzq.android.content.ContextHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by amlzq on 2018/6/7.
 * <p>
 * 数据工具
 */

public class DataUtil {

    /**
     * @hide
     */
    DataUtil() {
    }

    // ===========================================
    //
    // ===========================================

    public static String getAsset(String fileName) {
        final Context cxt = ContextHolder.getContext();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = cxt.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            Logger.e(e);
        }
        return stringBuilder.toString();
    }

    public Bundle getMetaData(Context context) {
        Bundle bundle = null;
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            bundle = appInfo.metaData;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return bundle;
    }

    public static String getApplication(String key) {
        final Context cxt = ContextHolder.getContext();
        String value = "";
        PackageManager manager = cxt.getPackageManager();
        String packageName = cxt.getPackageName();
        try {
            ApplicationInfo applicationInfo = manager.getApplicationInfo(packageName,
                    PackageManager.GET_META_DATA);
            Object obj = applicationInfo.metaData.get(key);
            value = String.valueOf(obj);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e(e);
        }
        return value;
    }

    public static String getActivity(Activity activity, String key) {
        String value = "";
        PackageManager manager = activity.getPackageManager();
        ComponentName componentName = activity.getComponentName();
        try {
            ActivityInfo info = manager.getActivityInfo(componentName, PackageManager.GET_META_DATA);
            Object obj = info.metaData.get(key);
            value = String.valueOf(obj);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e(e);
        }
        return value;
    }

    public static String getReceiver(Class<?> cls, String key) {
        final Context cxt = ContextHolder.getContext();
        String value = "";
        PackageManager manager = cxt.getPackageManager();
        ComponentName componentName = new ComponentName(cxt, cls);
        try {
            ActivityInfo info = manager.getReceiverInfo(componentName,
                    PackageManager.GET_META_DATA);
            Object obj = info.metaData.get(key);
            value = String.valueOf(obj);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e(e);
        }
        return value;
    }

    public static String getService(Class<?> cls, String key) {
        final Context cxt = ContextHolder.getContext();
        String value = "";
        PackageManager manager = cxt.getPackageManager();
        ComponentName componentName = new ComponentName(cxt, cls);
        try {
            ServiceInfo info = manager.getServiceInfo(componentName,
                    PackageManager.GET_META_DATA);
            Object obj = info.metaData.get(key);
            value = String.valueOf(obj);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.e(e);
        }
        return value;
    }

}