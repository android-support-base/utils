package com.amlzq.android.util;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;

/**
 * Created by amlzq on 2018/6/14.
 * <p>
 * 原生分享
 * <p>
 *
 * @author https://www.jianshu.com/p/88f166dd43b7
 * @author https://blog.csdn.net/zh_ang_lei/article/details/52385678
 */
@SuppressWarnings("unused")
public class ShareUtil {

    private Context context;

    public ShareUtil(Context context) {
        this.context = context;
    }

    public static final String WEIXIN_PACKAGE_NAME = "";
    public static final String QQ_PACKAGE_NAME = "";
//  public static final String ;

    /**
     * @param packageName 包名
     * @param className   类名
     * @param content     分享文字
     * @param title       A CharSequence dialog title to provide to the user when used with a.
     * @param subject     A constant string holding the desired subject line of a message.
     */
    @TargetApi(Build.VERSION_CODES.DONUT)
    public void shareText(String packageName, String className, String content, String title, String subject) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        //      if(null != className && null != packageName && !TextUtils.isEmpty(className) && !TextUtils.isEmpty(packageName)){
        //
        //      }else {
        //          if(null != packageName && !TextUtils.isEmpty(packageName)){
        //              intent.setPackage(packageName);
        //          }
        //      }
        if (StringUtil.notEmpty(className) && StringUtil.notEmpty(packageName)) {
            ComponentName componentName = new ComponentName(packageName, className);
            intent.setComponent(componentName);
        } else if (StringUtil.notEmpty(packageName)) {
            intent.setPackage(packageName);
        }

        intent.putExtra(Intent.EXTRA_TEXT, content);
        if (null != title && !TextUtils.isEmpty(title)) {
            intent.putExtra(Intent.EXTRA_TITLE, title);
        }
        if (null != subject && !TextUtils.isEmpty(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }
        intent.putExtra(Intent.EXTRA_TITLE, title);
        Intent chooserIntent = Intent.createChooser(intent, "分享到：");
        context.startActivity(chooserIntent);
    }

    /**
     * @param packageName 包名
     * @param className   类名
     * @param content     分享网页
     * @param title       标题
     * @param subject     主题
     */
    @TargetApi(Build.VERSION_CODES.DONUT)
    public void shareUrl(String packageName, String className, String content, String title, String subject) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
//      if(null != className && null != packageName && !TextUtils.isEmpty(className) && !TextUtils.isEmpty(packageName)){
//
//      }else {
//          if(null != packageName && !TextUtils.isEmpty(packageName)){
//              intent.setPackage(packageName);
//          }
//      }
        if (StringUtil.notEmpty(className) && StringUtil.notEmpty(packageName)) {
            ComponentName componentName = new ComponentName(packageName, className);
            intent.setComponent(componentName);
        } else if (StringUtil.notEmpty(packageName)) {
            intent.setPackage(packageName);
        }

        intent.putExtra(Intent.EXTRA_TEXT, content);
        if (null != title && !TextUtils.isEmpty(title)) {
            intent.putExtra(Intent.EXTRA_TITLE, title);
        }
        if (null != subject && !TextUtils.isEmpty(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }
        intent.putExtra(Intent.EXTRA_TITLE, title);
        Intent chooserIntent = Intent.createChooser(intent, "分享到：");
        context.startActivity(chooserIntent);
    }

    /**
     * 分享图片
     *
     * @param packageName 包名
     * @param className   类名
     * @param file        文件
     */
    @TargetApi(Build.VERSION_CODES.DONUT)
    public void shareImg(String packageName, String className, File file) {
        if (file.exists()) {
            Uri uri = Uri.fromFile(file);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/*");
            if (StringUtil.notEmpty(packageName) && StringUtil.notEmpty(className)) {
                intent.setComponent(new ComponentName(packageName, className));
            } else if (StringUtil.notEmpty(packageName)) {
                intent.setPackage(packageName);
            }
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            Intent chooserIntent = Intent.createChooser(intent, "分享到:");
            context.startActivity(chooserIntent);
        } else {
            Toast.makeText(context, "文件不存在", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 分享音乐
     *
     * @param packageName 包名
     * @param className   类名
     * @param file        文件
     */
    @TargetApi(Build.VERSION_CODES.DONUT)
    public void shareAudio(String packageName, String className, File file) {
        if (file.exists()) {
            Uri uri = Uri.fromFile(file);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("audio/*");
            if (StringUtil.notEmpty(packageName) && StringUtil.notEmpty(className)) {
                intent.setComponent(new ComponentName(packageName, className));
            } else if (StringUtil.notEmpty(packageName)) {
                intent.setPackage(packageName);
            }
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            Intent chooserIntent = Intent.createChooser(intent, "分享到:");
            context.startActivity(chooserIntent);
        } else {
            Toast.makeText(context, "文件不存在", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 分享视频
     *
     * @param packageName 包名
     * @param className   类名
     * @param file        文件
     */
    public void shareVideo(String packageName, String className, File file) {
        setIntent("video/*", packageName, className, file);
    }

    /**
     * @param type        类型
     * @param packageName 包名
     * @param className   类名
     * @param file        文件
     */
    @TargetApi(Build.VERSION_CODES.DONUT)
    public void setIntent(String type, String packageName, String className, File file) {
        if (file.exists()) {
            Uri uri = Uri.fromFile(file);
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType(type);
            if (StringUtil.notEmpty(packageName) && StringUtil.notEmpty(className)) {
                intent.setComponent(new ComponentName(packageName, className));
            } else if (StringUtil.notEmpty(packageName)) {
                intent.setPackage(packageName);
            }
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            Intent chooserIntent = Intent.createChooser(intent, "分享到:");
            context.startActivity(chooserIntent);
        } else {
            Toast.makeText(context, "文件不存在", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 分享多张图片和文字至朋友圈
     *
     * @param title       标题
     * @param packageName 包名
     * @param className   类名
     * @param file        文件
     */
    public void shareImgToWXCircle(String title, String packageName, String className, File file) {
        if (file.exists()) {
            Uri uri = Uri.fromFile(file);
            Intent intent = new Intent();
            ComponentName comp = new ComponentName(packageName, className);
            intent.setComponent(comp);
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.putExtra("Kdescription", title);
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "文件不存在", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * @param packageName 包名
     * @return 是否安装分享app
     */
    public boolean checkInstall(String packageName) {
        try {
            context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "请先安装应用app", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * @param url 跳转官方安装网址
     */
    public void toInstallWebView(String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

}
