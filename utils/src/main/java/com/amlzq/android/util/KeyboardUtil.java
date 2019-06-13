package com.amlzq.android.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by amlzq on 2018/10/4.
 * 操作软键盘
 * <p>
 *
 * @author https://github.com/Sugarya/FragmentCapsulation/blob/master/app/src/main/java/com/sugary/fragmentcapsulation/utils/KeyboardUtil.java
 */

public class KeyboardUtil {

    /**
     * @hide
     */
    KeyboardUtil() {
    }

    // =============================================================================================
    //  软键盘相关操作
    // =============================================================================================

    /**
     * 打开软键盘
     *
     * @param act  Activity
     * @param view View
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static void showSoftInput(Activity act, View view) {
        InputMethodManager manager = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (act.getCurrentFocus() != null && manager != null) {
            manager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        }
    }

    /**
     * 关闭软键盘
     */
    public static void hideSoftInput(Context cxt, View view) {
        InputMethodManager manager = (InputMethodManager) cxt.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = manager.isActive();//isOpen若返回true，则表示输入法打开
        if (isOpen) {
            manager.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
        }
    }

    /**
     * 关闭软键盘
     *
     * @param act Activity
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static void hideSoftInput(Activity act) {
        InputMethodManager manager = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (act.getCurrentFocus() != null && manager != null) {
            manager.hideSoftInputFromWindow(act.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        act.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    /**
     * @param act Activity
     */
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public static void toggleSoftInput(Activity act) {
        InputMethodManager manager = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (act.getCurrentFocus() != null && manager != null) {
            manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 关闭软键盘
     *
     * @param activity
     */
    public static void closeInputKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (manager != null) {
                boolean active = manager.isActive();
                if (active) {
                    View currentFocus = activity.getCurrentFocus();
                    if (currentFocus != null) {
                        IBinder windowToken = currentFocus.getWindowToken();
                        if (windowToken != null) {
                            manager.hideSoftInputFromWindow(
                                    windowToken,
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    }
                }
            }
        }
    }

}