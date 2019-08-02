package com.amlzq.android.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.LocaleList;
import android.provider.Settings;
import android.support.annotation.NonNull;

import com.amlzq.android.ApplicationConstant;
import com.amlzq.android.ApplicationRequestCode;
import com.amlzq.android.content.ContextHolder;
import com.amlzq.android.content.pm.PackageInfoSub;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by amlzq on 2017/10/20.
 * <p>
 * 操作系统工具类
 * 通用系统属性
 * 非原生ROM
 * 管理应用
 * <p>
 * android.os包下的类
 */

public class SystemUtil {

    /**
     * @hide
     */
    SystemUtil() {
    }

    // =============================================================================================
    // 通用系统属性
    // =============================================================================================

    /**
     * @return 系统的版本号
     */
    public static int getVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * https://zh.wikipedia.org/wiki/ISO_639-1
     *
     * @return 系统的标准化ISO 639语言代码
     * E.g: zh-HK,en-US
     */
    public static String getLanguage() {
        Locale locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = LocaleList.getDefault().get(0);
        } else locale = Locale.getDefault();
        return locale.getLanguage() + "-" + locale.getCountry();
    }

    /**
     * @param propName 名称
     * @return 系统属性
     */
    public static String getProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }

    // =============================================================================================
    // TODO: SDK version codes，高版本使用整型值，解决低版本编译找不到API问题
    // =============================================================================================

    /**
     * API 8 June 2010: Android 2.2
     *
     * @return Exceed API 8
     */
    public static boolean has8() {
        return getVersion() >= Build.VERSION_CODES.FROYO;
    }

    /**
     * API 9 November 2010: Android 2.3
     *
     * @return Exceed API 9
     */
    public static boolean has9() {
        return getVersion() >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * API 10 February 2011: Android 2.3.3.
     *
     * @return Exceed API 10
     */
    public static boolean has10() {
        return getVersion() >= Build.VERSION_CODES.GINGERBREAD_MR1;
    }

    /**
     * API 11 February 2011: Android 3.0.
     *
     * @return Exceed API 11
     */
    public static boolean has11() {
        return getVersion() >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * API 12 May 2011: Android 3.1.
     *
     * @return Exceed API 12
     */
    public static boolean has12() {
        return getVersion() >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    /**
     * API 13 June 2011: Android 3.2.
     *
     * @return Exceed API 13
     */
    public static boolean has13() {
        return getVersion() >= Build.VERSION_CODES.HONEYCOMB_MR2;
    }

    /**
     * API 14 October 2011: Android 4.0.
     *
     * @return Exceed API 14
     */
    public static boolean has14() {
        return getVersion() >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * API 15 December 2011: Android 4.0.3.
     *
     * @return Exceed API 15
     */
    public static boolean has15() {
        return getVersion() >= 15;
    }

    /**
     * API 16 June 2012: Android 4.1.
     *
     * @return Exceed API 16
     */
    public static boolean has16() {
        return getVersion() >= 16;
    }

    /**
     * API 17 November 2012: Android 4.2, Moar jelly beans!
     *
     * @return Exceed API 17
     */
    public static boolean has17() {
        return getVersion() >= 17;
    }

    /**
     * API 18
     * July 2013
     * Android 4.3
     * the revenge of the beans.
     *
     * @return Exceed API 18
     */
    public static boolean has18() {
        return getVersion() >= 18;
    }

    /**
     * API 19
     * October 2013
     * Android 4.4
     * KitKat
     *
     * @return Exceed API 19
     */
    public static boolean has19() {
        return getVersion() >= 19;
    }

    /**
     * API 20
     * Android 4.4W
     * KitKat for watches, snacks on the run.
     *
     * @return Exceed API 20
     */
    public static boolean has20() {
        return getVersion() >= 20;
    }

    /**
     * API 21
     * Android 5.0
     * Lollipop.
     *
     * @return Exceed API 21
     */
    public static boolean has21() {
        return getVersion() >= 21;
    }

    /**
     * API 22
     * Android 5.1.1
     * LollipopMR1
     *
     * @return Exceed API 22
     */
    public static boolean has22() {
        return getVersion() >= 22;
    }

    /**
     * API 23
     * Android 6.0
     * Marshmallow
     *
     * @return Exceed API 23
     */
    public static boolean has23() {
        return getVersion() >= 23;
    }

    /**
     * API 24
     * Android 7.0
     * Nougat
     *
     * @return Exceed API 24
     */
    public static boolean has24() {
        return getVersion() >= 24;
    }

    /**
     * API 25
     * Android 7.1
     * Nougat++
     *
     * @return Exceed API 25
     */
    public static boolean has25() {
        return getVersion() >= 25;
    }

    /**
     * API 26
     * Android 8.0
     * O.
     *
     * @return Exceed API 26
     */
    public static boolean has26() {
        return getVersion() >= 26;
    }

    /**
     * API 27
     * Android 8.1
     * O MR1.
     *
     * @return Exceed API 27
     */
    public static boolean has27() {
        return getVersion() >= 27;
    }

    // =============================================================================================
    // 非原生系统
    // =============================================================================================

    /**
     * @return 获取MIUI系统版本
     */
    public static String getMIUIVersion() {
        String propName = "ro.miui.ui.version.name";
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            // Unable to read sysprop
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }

    /**
     * @return 悬浮窗口权限需要特殊处理的ROM
     */
    public static boolean isEvilROM() {
        if ("Xiaomi".equals(Build.MANUFACTURER)) {
            return true;
        } else if ("Meizu".equals(Build.MANUFACTURER)) {
            return true;
        } else {
            return false;
        }
    }

    // =============================================================================================
    // TODO: 管理应用(安装，命令安装，授权，启动，更新，卸载)
    // =============================================================================================

    /**
     * 通过隐式意图调用系统安装程序安装APK
     *
     * @param context
     * @param file
     * @param authority
     */
    public static void installApp(@NonNull Context context, @NonNull File file, @NonNull String authority) {
        Intent intent = IntentUtil.getInstallCompat(context, file, authority);
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, ApplicationRequestCode.INSTALL);
        } else {
            context.startActivity(intent);
        }
    }

    /**
     * @param context
     * @param file
     */
    public static void installApp(@NonNull Context context, File file) {
        // 提供默认的authority
        installApp(context, file, ApplicationConstant.AUTHORITY);
    }

    /**
     * Install a apk file with root
     * 用root安装apk文件
     *
     * @param file The path of apk file
     */
    public static boolean installApp(String file) {
        boolean result = false;
        DataOutputStream dataOutputStream = null;
        BufferedReader errorStream = null;
        try {
            // 申请su权限
            Process process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            // 执行pm install命令
            String command = "pm install -r " + file + "\n";
            dataOutputStream.write(command.getBytes(Charset.forName("utf-8")));
            dataOutputStream.flush();
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            process.waitFor();
            errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String msg = "";
            String line;
            // 读取命令的执行结果
            while ((line = errorStream.readLine()) != null) {
                msg += line;
            }
            Logger.d("install message is " + msg);
            // 如果执行结果中包含Failure字样就认为是安装失败，否则就认为安装成功
            if (!msg.contains("Failure")) {
                result = true;
            }
        } catch (Exception e) {
            Logger.e(e.getMessage(), e);
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (errorStream != null) {
                    errorStream.close();
                }
            } catch (IOException e) {
                Logger.e(e.getMessage(), e);
            }
        }
        return result;
    }

    /**
     * @param packageName 包名
     * @return 检查某个app是否安装成功
     */
    public static boolean isInstalled(String packageName) {
        // 获取所有已安装程序的包信息
        if (getInstalledPackages() != null) {
            for (int i = 0; i < getInstalledPackages().size(); i++) {
                String pn = getInstalledPackages().get(i).packageName;
                if (pn.equals(packageName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 新进程加载app
     *
     * @param packageName 包名
     */
    public static void launcherApp(@NonNull Context context, @NonNull String packageName) {
        Intent launchIntent = PackageUtil.getPackageManager().getLaunchIntentForPackage(packageName);
//        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(launchIntent);
    }

    /**
     * 新进程加载app
     *
     * @param packageName 包名
     */
    public static void launcherApp(@NonNull String packageName) {
        launcherApp(ContextHolder.getContext(), packageName);
    }

    /**
     * 应用信息界面
     *
     * @param activity    activity
     * @param requestCode 请求码
     */
    public static void openApplicationInfo(Activity activity, int requestCode) {
        Intent intent = new Intent();
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // onActivityResult提前调用
        if (has9()) {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        } else {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", activity.getPackageName());
        }
//        activity.startActivity(intent);
        activity.startActivityForResult(intent, requestCode);
    }

    // =============================================================================================
    // TODO: 管理所有应用的包信息或者应用信息列表
    // =============================================================================================

    /**
     * Return whether the given PackgeInfo represents a system package or not.
     * User-installed packages (Market or otherwise) should not be denoted as
     * system packages.
     *
     * @param info PackageInfo
     * @return true: is system package
     */
    private boolean isSystemPackage(PackageInfo info) {
        return ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    public static List<PackageInfo> installedPackages;

    /**
     * @return PackageInfoSub list
     */
    public static List<PackageInfo> getInstalledPackages() {
        PackageManager manager = PackageUtil.getPackageManager();
        if (installedPackages == null) {
            installedPackages = manager.getInstalledPackages(PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
        }
        return installedPackages;
    }

    /**
     * @return PackageInfoSub list
     */
    public static List<PackageInfo> getInstalledPackages2() {
        PackageManager manager = PackageUtil.getPackageManager();
        if (installedPackages == null) {
            installedPackages = manager.getInstalledPackages(PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
        }
        Collections.sort(installedPackages, new PackageInfoSub.DisplayNameComparator(manager));
        return installedPackages;
    }

    /**
     * get a list of installed apps.
     *
     * @return installed apps list
     */
    public static List<ApplicationInfo> getInstalledApplications() {
        final PackageManager manager = PackageUtil.getPackageManager();
        List<ApplicationInfo> infos = manager.getInstalledApplications(PackageManager.GET_META_DATA);
        Collections.sort(infos, new ApplicationInfo.DisplayNameComparator(manager));
        return infos;
    }

    /**
     * @return ResolveInfo list
     */
    public static List<ResolveInfo> queryIntentActivities() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager manager = PackageUtil.getPackageManager();
        // 通过查询，获得所有ResolveInfo对象.
        List<ResolveInfo> infos = manager.queryIntentActivities(mainIntent, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
        // 调用系统排序，根据name排序，排序很重要，否则只能显示系统应用，而不能列出第三方应用程序.
        Collections.sort(infos, new ResolveInfo.DisplayNameComparator(manager));
        return infos;
    }

    /**
     * 获取设备上安装的程序信息
     * PackageManager.GET_UNINSTALLED_PACKAGES 常量代表已经卸载的也会收集到
     *
     * @return PackageInfoSub list
     */
    public static List<PackageInfo> getUninstalledPackages() {
        PackageManager manager = PackageUtil.getPackageManager();
        List<PackageInfo> infos = manager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES);
        Collections.sort(infos, new PackageInfoSub.DisplayNameComparator(manager));
        return infos;
    }

    // =============================================================================================
    // TODO: 管理特殊应用（系统APP，预装APP，第三方APP）
    // =============================================================================================

    /**
     * Launch system settings app.
     *
     * @param activity    activity
     * @param requestCode requestCode
     */
    public static void launchSettingsApp(Activity activity, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
//        activity.startActivity(intent);
        activity.startActivityForResult(intent, requestCode);
    }

    // =============================================================================================
    // TODO: 系统root
    // =============================================================================================

    /**
     * 判断手机是否root，不弹出root请求框<br/>
     */
    private static boolean isRoot() {
        String binPath = "/system/bin/su";
        String xBinPath = "/system/xbin/su";
        if (new File(binPath).exists() && isExecutable(binPath))
            return true;
        if (new File(xBinPath).exists() && isExecutable(xBinPath))
            return true;
        return false;
    }

    /**
     * 是可执行的
     *
     * @param filePath
     * @return
     */
    private static boolean isExecutable(String filePath) {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("ls -l " + filePath);
            // 获取返回内容
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    p.getInputStream()));
            String str = in.readLine();
            if (str != null && str.length() >= 4) {
                char flag = str.charAt(3);
                if (flag == 's' || flag == 'x')
                    return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.destroy();
            }
        }
        return false;
    }

}