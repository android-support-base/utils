package com.amlzq.android.util;

import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * Created by amlzq on 2017/7/28.
 * <p>
 * 显示指标工具
 */

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
     * 密度
     */
    public static float DENSITY = -1.0F;
    /**
     * 密度
     */
    public static float SCALED_DENSITY = -1.0F;
    /**
     * 屏幕宽度，单位像素
     */
    public static int SCREEN_WIDTH = -1;
    /**
     * 屏幕高度，单位像素
     */
    public static int SCREEN_HEIGHT = -1;
    /**
     * X轴缩放比例
     */
    public static float SCALE_X = -1.0F;
    /**
     * Y轴缩放比例
     */
    public static float SCALE_Y = -1.0F;

    public static void init(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        DENSITY = metrics.density;
        SCALED_DENSITY = metrics.scaledDensity;
        SCREEN_WIDTH = metrics.widthPixels;
        SCREEN_HEIGHT = metrics.heightPixels;
        if (SCREEN_WIDTH > SCREEN_HEIGHT) {
            int i = SCREEN_WIDTH;
            SCREEN_WIDTH = SCREEN_HEIGHT;
            SCREEN_HEIGHT = i;
        }
        SCALE_X = SCREEN_WIDTH / 480.0F;
        SCALE_Y = SCREEN_HEIGHT / 800.0F;

        /*
         * SCREEN_WIDTH== 720 SCREEN_HEIGHT== 1184 SCALE_X== 1.5 SCALE_Y1.48
         * DENSITY2.0<br> System.out.print("SCREEN_WIDTH== " + SCREEN_WIDTH +
         * "SCREEN_HEIGHT== " + SCREEN_HEIGHT + "SCALE_X== " + SCALE_X +
         * "SCALE_Y" + SCALE_Y + "DENSITY" + DENSITY);
         */
    }

    /**
     * @param dip
     * @return 转换dip为px
     */
    public static int dip2px(int dip) {
        float scale = DENSITY;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    /**
     * @param px
     * @return 转换px为dip
     */
    public static int px2dip(int px) {
        float scale = DENSITY;
        return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
    }

    /**
     * @param sp
     * @return 转换sp为px
     */
    public static int sp2px(float sp) {
        float fontScale = SCALED_DENSITY;
        return (int) (sp * fontScale + 0.5f);
    }

    /**
     * @param px
     * @return 转换px为sp
     */
    public static int px2sp(float px) {
        float fontScale = SCALED_DENSITY;
        return (int) (px / fontScale + 0.5f);
    }

    public static int getInt(int paramInt) {
        return getIntForScaleX(paramInt);
    }

    private static int getIntForScaleX(int paramInt) {
        return (int) (paramInt * SCALE_X);
    }

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