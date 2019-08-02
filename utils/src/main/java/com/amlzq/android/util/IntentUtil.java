package com.amlzq.android.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;
import java.util.List;

/**
 * Created by amlzq on 2018/6/7.
 * <p>
 * 意图工具
 */

public class IntentUtil {

    // =============================================================================================
    // 判断意图
    // =============================================================================================

    /**
     * @param context Context
     * @param intent  Intent
     * @return 检测 响应某个意图的Activity 是否存在
     */
    public static boolean isActivityAvailable(Context context, Intent intent) {
        final PackageManager manager = context.getPackageManager();
        List<ResolveInfo> resolveInfos = manager.queryIntentActivities(intent,
                PackageManager.GET_ACTIVITIES);
        return resolveInfos.size() > 0;
    }

    public static boolean isServiceAvailable(Context context, Intent intent) {
        final PackageManager manager = context.getPackageManager();
        List<ResolveInfo> resolveInfos = manager.queryIntentActivities(intent,
                PackageManager.GET_SERVICES);
        return resolveInfos.size() > 0;
    }

    // =============================================================================================
    // 常用意图
    // =============================================================================================

    /**
     * Fix Android 7.0 error
     * Caused by: android.os.FileUriExposedException:
     * file:///storage/emulated/0/Download/myApp.apk exposed beyond app through Intent.getData()
     * https://developer.android.com/reference/android/support/v4/content/FileProvider
     *
     * @param context   context
     * @param authority 应用文件目录
     * @param file      file
     * @return 安装应用的意图
     */
    public static Intent getInstallCompat(@NonNull Context context, @NonNull File file, @NonNull String authority) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 判读版本是否在7.0以上
            // 参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3 共享的文件
            Uri apkUri = FileProvider.getUriForFile(context, authority, file);
            // 添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        return intent;
    }

    /**
     * @return Show system settings.
     */
    public static Intent getSettings() {
        return new Intent(Settings.ACTION_SETTINGS);
    }

    /**
     * @return 应用市场意图
     */
    public static Intent getMarket() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.APP_MARKET");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    /**
     * 启动到应用商店app详情界面
     * https://blog.csdn.net/bzlj2912009596/article/details/80589841
     *
     * @param appPkg    目标App的包名
     * @param marketPkg 应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
     */
    public static Intent getAppDetailPage(String appPkg, String marketPkg) {
        // if (TextUtils.isEmpty(packageName)) new IllP("");
        // 非法参数异常

        if (TextUtils.isEmpty(appPkg)) return null;

        Intent intent = null;
        try {
            Uri uri = Uri.parse("market://details?id=" + appPkg);
            intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg)) {
                intent.setPackage(marketPkg);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return intent;
    }

    /**
     * @return 相册/图片选择器的意图
     */
    public static Intent getImagePicker() {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 19) {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        } else {
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
        }
        return intent;
    }

    public static Intent getCamera(String authority, File file) {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, UriUtil.getUriForFile(authority, file));
        return intent;
    }

}
