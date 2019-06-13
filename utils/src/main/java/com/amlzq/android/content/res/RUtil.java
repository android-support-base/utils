package com.amlzq.android.content.res;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by amlzq on 2016/6/18.
 * Resources util
 */
public class RUtil {

    private static Resources mResources;
    private static String mPackageName;

    private static final String ANIM = "anim";
    private static final String ANIMATOR = "animator";
    private static final String ATTR = "attr";
    private static final String COLOR = "color";
    private static final String STRING = "string";
    private static final String DRAWABLE = "drawable";
    private static final String MIPMAP = "mipmap";
    private static final String DIMEN = "dimen";
    private static final String ID = "id";
    private static final String LAYOUT = "layout";
    private static final String MENU = "menu";
    private static final String RAW = "raw";
    private static final String STYLE = "style";
    private static final String INTEGER = "integer";
    private static final String STYLEABLE = "styleable";

    public static final void init(Context context) {
        mResources = context.getResources();
        mPackageName = context.getPackageName();
    }

    public static final Resources getResources() {
        return mResources;
    }

    public static final String getPackageName() {
        return mPackageName;
    }

    /*
     * 通过静态方法获取ID值
     */

    public static final int adnim(String name) {
        return getResources().getIdentifier(name, ANIM, getPackageName());
    }

    public static final int animator(String name) {
        return getResources().getIdentifier(name, ANIMATOR, getPackageName());
    }

    public static final int string(String name) {
        return getResources().getIdentifier(name, STRING, getPackageName());
    }

    public static final int attr(String name) {
        return getResources().getIdentifier(name, ATTR, getPackageName());
    }

    public static final int color(String name) {
        return getResources().getIdentifier(name, COLOR, getPackageName());
    }

    public static final int dimen(String name) {
        return getResources().getIdentifier(name, DIMEN, getPackageName());
    }

    public static final int drawable(String name) {
        return getResources().getIdentifier(name, DRAWABLE, getPackageName());
    }

    public static final int mipmap(String name) {
        return getResources().getIdentifier(name, MIPMAP, getPackageName());
    }

    public static final int id(String name) {
        return getResources().getIdentifier(name, ID, getPackageName());
    }

    public static final int layout(String name) {
        return getResources().getIdentifier(name, LAYOUT, getPackageName());
    }

    public static final int menu(String name) {
        return getResources().getIdentifier(name, MENU, getPackageName());
    }

    public static final int raw(String name) {
        return getResources().getIdentifier(name, RAW, getPackageName());
    }

    public static final int style(String name) {
        return getResources().getIdentifier(name, STYLE, getPackageName());
    }

    public static final int integer(String name) {
        return getResources().getIdentifier(name, INTEGER, getPackageName());
    }

    public static final int styleable(String name) {
        return getResources().getIdentifier(name, STYLEABLE, getPackageName());
    }

}