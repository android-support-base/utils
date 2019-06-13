package com.amlzq.android.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;

import com.amlzq.android.ApplicationRequestCode;

/**
 * Created by amlzq on 2018/6/27.
 * <p>
 * 权限工具
 */

public class PermissionUtil {

    /**
     * 打开应用权限管理界面
     *
     * @param activity    activity
     * @param requestCode requestCode
     */
    @RequiresApi(api = Build.VERSION_CODES.DONUT)
    public static void openManagement(Activity activity, int requestCode) {
        switch (Build.MANUFACTURER) {
            case Manufacturer.HUAWEI:
                Huawei(activity, requestCode);
                break;
            case Manufacturer.MEIZU:
                Meizu(activity, requestCode);
                break;
            case Manufacturer.XIAOMI:
                Xiaomi(activity, requestCode);
                break;
            case Manufacturer.SONY:
                Sony(activity, requestCode);
                break;
            case Manufacturer.OPPO:
                SystemUtil.openApplicationInfo(activity, requestCode);
                // Unable to find explicit activity class {com.color.safecenter/com.color.safecenter.permission.PermissionManagerActivity}; have you declared this activity in your AndroidManifest.xml?
//                OPPO(activity, requestCode);
                break;
            case Manufacturer.VIVO:
                VIVO(activity, requestCode);
                break;
            case Manufacturer.LG:
                LG(activity, requestCode);
                break;
            case Manufacturer.LETV:
                Letv(activity, requestCode);
                break;
            default:
                SystemUtil.openApplicationInfo(activity, requestCode);
                break;
        }
    }

    public static void Huawei(Activity activity, int requestCode) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", activity.getPackageName());
        ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
        intent.setComponent(comp);
//        activity.startActivity(intent);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void Meizu(Activity activity, int requestCode) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", activity.getPackageName());
//        activity.startActivity(intent);
        activity.startActivityForResult(intent, requestCode);
    }

    @RequiresApi(api = Build.VERSION_CODES.DONUT)
    public static void Xiaomi(Activity activity, int requestCode) {
        String rom = SystemUtil.getMIUIVersion();
        Intent intent = null;
        if (Manufacturer.Miui.V5.equals(rom)) {
            Uri packageURI = Uri.parse("package:" + activity.getApplicationInfo().packageName);
            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
        } else if (Manufacturer.Miui.V6.equals(rom) || Manufacturer.Miui.V7.equals(rom)) {
            intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.putExtra("extra_pkgname", activity.getPackageName());
        } else {
            // v8 and v9
            intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            intent.putExtra("extra_pkgname", activity.getPackageName());
        }
//        activity.startActivity(intent);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void Sony(Activity activity, int requestCode) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", activity.getPackageName());
        ComponentName comp = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
        intent.setComponent(comp);
//        activity.startActivity(intent);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * @param activity    activity
     * @param requestCode requestCode
     */
    public static void OPPO(Activity activity, int requestCode) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", activity.getPackageName());
        ComponentName comp = new ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity");
        intent.setComponent(comp);
        activity.startActivityForResult(intent, requestCode);
//        activity.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public static void VIVO(Activity activity, int requestCode) {
        // 点击设置图标>加速白名单>我的app
        // 点击软件管理>软件管理权限>软件>我的app>信任该软件
        Intent intent = activity.getPackageManager().getLaunchIntentForPackage("com.iqoo.secure");
        activity.startActivityForResult(intent, requestCode);
    }

    public static void LG(Activity activity, int requestCode) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", activity.getPackageName());
        ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
        intent.setComponent(comp);
        activity.startActivityForResult(intent, requestCode);
//        activity.startActivity(intent);
    }

    public static void Letv(Activity activity, int requestCode) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", activity.getPackageName());
        ComponentName comp = new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps");
        intent.setComponent(comp);
//        activity.startActivity(intent);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 只能打开到自带安全软件
     *
     * @param activity    activity
     * @param requestCode requestCode
     */
    public static void Qihoo360(Activity activity, int requestCode) {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("packageName", activity.getPackageName());
        ComponentName comp = new ComponentName("com.qihoo360.mobilesafe", "com.qihoo360.mobilesafe.ui.index.AppEnterActivity");
        intent.setComponent(comp);
//        activity.startActivity(intent);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 打开“显示在其他应用的上层”的视图
     *
     * @param activity activity
     */
    @TargetApi(Build.VERSION_CODES.M)
    public static void openSystemAlertWindowManager(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));

        // MIUI等ROM没有单独的悬浮窗权限页面
        // ActivityNotFoundException
        if (IntentUtil.isActivityAvailable(activity, intent)) {
            activity.startActivityForResult(intent, ApplicationRequestCode.ACTION_SYSTEM_ALERT_WINDOW_MANAGER);
        } else {
            SystemUtil.openApplicationInfo(activity, ApplicationRequestCode.ACTION_SYSTEM_ALERT_WINDOW_MANAGER);
        }
    }

}