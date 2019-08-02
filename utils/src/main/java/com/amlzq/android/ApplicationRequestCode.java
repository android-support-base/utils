package com.amlzq.android;

/**
 * Created by amlzq on 2018/6/7.
 * <p>
 * startActivityForResult的请求码
 * MyRequestCode继承此类
 */
public abstract class ApplicationRequestCode {

    public static final int UNIQUE = 0x000000;
    public static final int INSTALL = 0x000001;
    public static final int PERMISSION_PHONE_CALLS = 0x000002;
    public static final int PERMISSION_LOCATION = 0x000003;
    public static final int PERMISSION_STORAGE = 0x000004;
    public static final int PERMISSION_STATIS = 0x000005;

    /**
     * 应用信息视图
     */
    public static final int ACTION_SETTINGS = 0x000007;

    /**
     * 应用通知管理
     */
    public static final int ACTION_NOTIFICATION_MANAGER = 0x000008;

    /**
     * 应用权限管理
     */
    public static final int ACTION_PERMISSION_MANAGER = 0x000009;

    /**
     * 应用"允许显示其他应用的上层"管理
     */
    public static final int ACTION_SYSTEM_ALERT_WINDOW_MANAGER = 0x000010;

}
