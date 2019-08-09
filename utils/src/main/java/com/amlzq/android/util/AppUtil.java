package com.amlzq.android.util;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;

import com.amlzq.android.ApplicationConfig;
import com.amlzq.android.content.ContextHolder;
import com.amlzq.android.log.Log;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by amlzq on 2017/7/28.
 * <p>
 * application工具
 * <p>
 * 应用属性
 * 应用生命周期
 * 应用权限
 */
public class AppUtil {

    /**
     * @hide
     */
    AppUtil() {
    }

    // =============================================================================================
    // 应用属性
    // =============================================================================================

    /**
     * @return packageName
     */
    public static String getPackageName() {
        Context cxt = ContextHolder.getContext();
        try {
            String packageName = PackageUtil.getPackageInfo(cxt.getPackageName(), PackageManager.GET_CONFIGURATIONS).packageName;
            Logger.d(packageName);
            return packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @return versionCode
     */
    public static int getVersionCode() {
        return PackageUtil.getVersionCode(getPackageName());
    }

    /**
     * @return versionName
     */
    public static String getVersionName() {
        return PackageUtil.getVersionName(getPackageName());
    }

    /**
     * @return MD5
     */
    public static String getSignatureDigest() {
        return PackageUtil.getSignatureDigest(getPackageName());
    }

    /**
     * @return SHA1
     */
    public static String getSignatureBySHA1() {
        return PackageUtil.getSignatureBySHA1(getPackageName());
    }

    /**
     * @return SHA256
     */
    public static String getSignatureBySHA256() {
        return PackageUtil.getSignatureBySHA256(getPackageName());
    }

    // =============================================================================================
    // 应用生命周期
    // =============================================================================================

    /**
     * 应用退出
     */
    public static void exit() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }


    /**
     * @return 应用处于前景
     * @permission GET_TASKS
     * @author com.hyphenate.util.EasyUtils
     */
    public static boolean isAppRunningForeground() {
        final Context applicationContext = ContextHolder.getContext();
        ActivityManager manager = (ActivityManager) applicationContext.getSystemService(Context.ACTIVITY_SERVICE);
        try {
            List tasks = manager.getRunningTasks(1);
            if (tasks != null && tasks.size() >= 1) {
                boolean result = applicationContext.getPackageName().equalsIgnoreCase(((ActivityManager.RunningTaskInfo) tasks.get(0)).baseActivity.getPackageName());
                Logger.d("app running in foreground: " + result);
                return result;
            } else {
                return false;
            }
        } catch (SecurityException e) {
            Logger.e("doesn't hold GET_TASKS permission", e);
            return false;
        }
    }

    // =============================================================================================
    // 应用权限
    // =============================================================================================

    /**
     * @param context 上下文
     * @return 是否允许悬浮窗
     */
    public static boolean isFloatWindowOpAllowed(Context context) {
        if (Manufacturer.MEIZU.equalsIgnoreCase(Build.MANUFACTURER)
                || Manufacturer.XIAOMI.equalsIgnoreCase(Build.MANUFACTURER)) {
            // 魅族/小米特殊处理
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                return checkOp(context, 24);
            } else {
                return (context.getApplicationInfo().flags & 0x8000000) == 1 << 27;
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                // 需要android.permission.SYSTEM_ALERT_WINDOW权限就能显示一个悬浮窗
                return checkOp(context, 24); //24表示悬浮窗权限在AppOpsManager.OP_SYSTEM_ALERT_WINDOW

            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                // 不需要android.permission.SYSTEM_ALERT_WINDOW权限就能显示一个悬浮窗，因为是通过WindowManager.LayoutParams.TYPE_TOAST实现
                return true;

            } else {
                // 需要android.permission.SYSTEM_ALERT_WINDOW权限就能显示一个悬浮窗
                //0x8000000表示1000000000000000000000000000如果&第28位所得值为1则该位置被置为1，悬浮窗打开
                return (context.getApplicationInfo().flags & 0x8000000) == 1 << 27;
            }
        }
    }

    private static boolean checkOp(Context context, int op) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                Class<?> spClazz = Class.forName(manager.getClass().getName());
                Method method = manager.getClass().getDeclaredMethod("checkOp", int.class, int.class, String.class);
                int property = (Integer) method.invoke(manager, op, Binder.getCallingUid(), context.getPackageName());
                Logger.d(AppOpsManager.MODE_ALLOWED + " invoke " + property);
                return AppOpsManager.MODE_ALLOWED == property;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Logger.w("Below API 19 cannot invoke!");
        }
        return false;
    }

    public static boolean isSystemApplication(String packageName) {
        Context cxt = ContextHolder.getContext();
        PackageManager manager = cxt.getPackageManager();
        try {
            PackageInfo packageInfo = manager.getPackageInfo(packageName, PackageManager.GET_CONFIGURATIONS);
            // 1
            if (new File("/data/app/" + packageInfo.packageName + ".apk").exists()) {
                return true;
            } else
                // 2
                if (packageInfo.versionName != null && packageInfo.applicationInfo.uid > 10000) {
                    return true;
                } else
                    // 3
                    if ((packageInfo.applicationInfo.flags & android.content.pm.ApplicationInfo.FLAG_SYSTEM) != 0) {
                        return true;
                    }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get the directory for the user's public pictures directory.
     * 获取用户公共图片目录。
     * 保存用户图片数据，比如从动态中下载保存的图片，放到系统相册中，新建目录
     */
    public static File getPicturesStorageDir() {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), ApplicationConfig.IDENTIFY);
        if (!file.mkdirs()) {
            Log.e("this app public pictures directory not created");
        }
        return file;
    }

    public static File getDownloadsStorageDir() {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), ApplicationConfig.IDENTIFY);
        if (!file.mkdirs()) {
            Log.e("this app public downloads directory not created");
        }
        return file;
    }

    public static File getMusicStorageDir() {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MUSIC), ApplicationConfig.IDENTIFY);
        if (!file.mkdirs()) {
            Log.e("this app public music directory not created");
        }
        return file;
    }

    public static File getMoviesStorageDir() {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_MOVIES), ApplicationConfig.IDENTIFY);
        if (!file.mkdirs()) {
            Log.e("this app public movies directory not created");
        }
        return file;
    }

    public static File getDocumentsStorageDir() {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), ApplicationConfig.IDENTIFY);
        if (!file.mkdirs()) {
            Log.e("this app public documents directory not created");
        }
        return file;
    }

}
