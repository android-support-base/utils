package com.amlzq.android.util;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * Created by amlzq on 2017/7/28.
 * <p>
 * 屏幕显示工具类
 */
// @SuppressWarnings("unused")
public class DisplayUtil {

    /**
     * @hide
     */
    DisplayUtil() {
    }

    // =============================================================================================
    // 屏幕属性
    // =============================================================================================

    /**
     * 屏幕宽度，单位像素
     */
    public static int SCREEN_WIDTH = -1;
    /**
     * 屏幕高度，单位像素
     */
    public static int SCREEN_HEIGHT = -1;
    /**
     * 密度
     */
    private static float DENSITY = -1.0F;
    /**
     * X轴缩放比例
     */
    private static float SCALE_X = -1.0F;
    /**
     * Y轴缩放比例
     */
    private static float SCALE_Y = -1.0F;

    public static void init(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        SCREEN_WIDTH = dm.widthPixels;
        SCREEN_HEIGHT = dm.heightPixels;
        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            int i = SCREEN_WIDTH;
            SCREEN_WIDTH = SCREEN_HEIGHT;
            SCREEN_HEIGHT = i;
        }
        DENSITY = dm.density;
        SCALE_X = SCREEN_WIDTH / 480.0F;
        SCALE_Y = SCREEN_HEIGHT / 800.0F;

        /*
         * SCREEN_WIDTH== 720 SCREEN_HEIGHT== 1184 SCALE_X== 1.5 SCALE_Y1.48
         * DENSITY2.0<br> System.out.print("SCREEN_WIDTH== " + SCREEN_WIDTH +
         * "SCREEN_HEIGHT== " + SCREEN_HEIGHT + "SCALE_X== " + SCALE_X +
         * "SCALE_Y" + SCALE_Y + "DENSITY" + DENSITY);
         */
    }

    public static int getScreenWidth(Context context) {
        if (SCREEN_WIDTH > 0) {
            return SCREEN_WIDTH;
        }
        init(context);
        return SCREEN_WIDTH;
    }

    public static int getScreenHeight(Context context) {
        if (SCREEN_HEIGHT > 0) {
            return SCREEN_HEIGHT;
        }
        init(context);
        return SCREEN_HEIGHT;
    }

    // 转换dip为px
    public static int dip2px(Context context, int dip) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    // 转换px为dip
    public static int px2dip(Context context, int px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
    }

    // 转换sp为px
    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    // 转换px为sp
    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int getInt(Context context, int paramInt) {
        return getIntForScalX(context, paramInt);
    }

    private static int getIntForScalX(Context context, int paramInt) {
        return (int) (paramInt * getScaleX(context));
    }

    public static float getScaleX(Context context) {
        if (SCALE_X > 0.0F) {
            return SCALE_X;
        }
        init(context);
        return SCALE_X;
    }

    @SuppressWarnings("deprecation")
    public static String getOrientationName(int orient) {
        String orientName;
        switch (orient) {
            case Configuration.ORIENTATION_UNDEFINED:
                orientName = "undefined";
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                orientName = "portrait";
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                orientName = "landscape";
                break;
            case Configuration.ORIENTATION_SQUARE:
                orientName = "square";
                break;
            default:
                orientName = "unknown";
        }
        return orientName;
    }

}