package com.amlzq.android.util;

import android.content.Context;

import com.amlzq.android.content.ContextHolder;
import com.amlzq.android.content.res.RUtil;

/**
 * Created by amlzq on 2018/5/1.
 * Util config
 */

@SuppressWarnings("unused")
public class UtilConfig {

    public static boolean DEBUG = false;

    public static void init(Context cxt, String identify) {
        Logger.TAG = identify;
        Logger.LEVEL = DEBUG ? android.util.Log.VERBOSE : android.util.Log.INFO;

        ContextHolder.init(cxt);

        RUtil.init(cxt);
        DisplayUtil.init(cxt);
    }

}