package com.amlzq.android.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by amlzq on 2017/7/28.
 * <p>
 * SharedPreferences util
 * Context.MODE_PRIVATE: 指定该SharedPreferences数据只能被本应用程序读、写；
 * Context.MODE_WORLD_READABLE:  指定该SharedPreferences数据能被其他应用程序读；
 * Context.MODE_WORLD_WRITEABLE:  指定该SharedPreferences数据能被其他应用程序写；
 * Context.MODE_APPEND：该模式会检查文件是否存在，存在就往文件追加内容，否则就创建新文件；
 */

public class PrefsUtil {

    /**
     * @hide
     */
    PrefsUtil() {
    }

    /**
     * 在程序初始化时赋值
     * 文件名默认为package name
     * 规范：IDENTIFY + "Prefs";
     * 路径：\data\data\{$applicationId}\shared_prefs\{$fileName}
     */
    public static String FILE_NAME;

    /**
     * @return 检查文件名是否赋值
     */
    private static boolean checkFileName() {
        return TextUtils.isEmpty(FILE_NAME);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
    }

    public static void save(Context context, String key, String value) {
        if (checkFileName()) throw new NullPointerException("shared preferences file name is null");

        SharedPreferences.Editor editor = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String get(Context context, String key, String defValue) {
        if (checkFileName()) throw new NullPointerException("shared preferences file name is null");

        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (preferences == null) {
            return defValue;
        }
        return preferences.getString(key, defValue);
    }

    public static String get(Context context, String key) {
        if (checkFileName()) throw new NullPointerException("shared preferences file name is null");

        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (preferences == null) {
            return null;
        }
        return preferences.getString(key, "");
    }

    public static void save(Context context, String key, int value) {
        if (checkFileName()) throw new NullPointerException("shared preferences file name is null");

        SharedPreferences.Editor editor = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int get(Context context, String key, int defValue) {
        if (checkFileName()) throw new NullPointerException("shared preferences file name is null");

        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (preferences == null) {
            return defValue;
        }
        return preferences.getInt(key, defValue);
    }

    public static void save(Context context, String key, float value) {
        if (checkFileName()) throw new NullPointerException("shared preferences file name is null");

        SharedPreferences.Editor editor = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public static float get(Context context, String key, float defValue) {
        if (checkFileName()) throw new NullPointerException("shared preferences file name is null");

        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (preferences == null) {
            return defValue;
        }
        return preferences.getFloat(key, defValue);
    }

    public static void save(Context context, String key, long value) {
        if (checkFileName()) throw new NullPointerException("shared preferences file name is null");

        SharedPreferences.Editor editor = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static long get(Context context, String key, long defValue) {
        if (checkFileName()) throw new NullPointerException("shared preferences file name is null");

        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (preferences == null) {
            return defValue;
        }
        return preferences.getLong(key, defValue);
    }

    public static void save(Context context, String key, boolean value) {
        if (checkFileName()) throw new NullPointerException("shared preferences file name is null");

        SharedPreferences.Editor editor = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean get(Context context, String key, boolean defValue) {
        if (checkFileName()) throw new NullPointerException("shared preferences file name is null");

        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (preferences == null) {
            return defValue;
        }
        return preferences.getBoolean(key, defValue);
    }

    public static void clearAll(Context context) {
        if (checkFileName()) throw new NullPointerException("shared preferences file name is null");
        SharedPreferences.Editor editor = getEditor(context);
        editor.clear();
        editor.apply();
    }

    /**
     * 返回所有的键值对
     */
    public static Map<String, ?> getAll(Context context) {
        if (checkFileName()) throw new NullPointerException("shared preferences file name is null");

        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (null == preferences) {
            return null;
        }
        return preferences.getAll();
    }

    public static void remove(Context context, String... VarArgs) {
        if (checkFileName()) throw new NullPointerException("shared preferences file name is null");

        SharedPreferences.Editor editor = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
        String[] arrayOfString;
        int j = (arrayOfString = VarArgs).length;
        for (int i = 0; i < j; i++) {
            String VarArg = arrayOfString[i];
            editor.remove(VarArg);
        }
        editor.apply();
    }

    public static boolean contains(Context context, String key) {
        if (checkFileName()) throw new NullPointerException("shared preferences file name is null");

        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return null != preferences && preferences.contains(key);
    }

    public static void save(Context context, Bundle bundle) {
        if (checkFileName()) throw new NullPointerException("shared preferences file name is null");

        SharedPreferences.Editor editor = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
        for (String str : bundle.keySet()) {
            editor.putString(str, bundle.getString(str));
        }
        editor.apply();
    }

    public static void saveArray(Context context, String key, Set<String> set) {
        if (checkFileName()) throw new NullPointerException("shared preferences file name is null");

        SharedPreferences.Editor editor = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.putStringSet(key, set);
        editor.apply();
        editor.commit();
    }

    /**
     * @param context
     * @param key
     * @return 从首选项中检索一组String值。
     */
    public static Set<String> getArray(Context context, String key) {
        if (checkFileName()) throw new NullPointerException("shared preferences file name is null");

        Set<String> set = new HashSet<>();
        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (preferences == null) {
            return null;
        }
        return preferences.getStringSet(key, set);
    }

}
