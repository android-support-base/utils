package com.amlzq.android.util;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.amlzq.android.ApplicationConstant;
import com.amlzq.android.content.ContextHolder;

import java.io.File;

/**
 *
 */
public class UriUtil {

    /**
     * 拍照后存储目录
     */
    public static Uri getUriForFile(String authority, File file) {
        Context context = ContextHolder.getContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context, authority, file);
        } else {
            return Uri.fromFile(file);
        }
    }

    /**
     * 拍照后存储目录
     */
    public static Uri getUriForFile(Context context, File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context, ApplicationConstant.AUTHORITY, file);
        } else {
            return Uri.fromFile(file);
        }
    }

}