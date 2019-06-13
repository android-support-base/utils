package com.amlzq.android;

import android.app.Application;

/**
 * 应用程序配置
 * MyConfig继承此类
 */
public abstract class ApplicationConfig {

    /**
     * 应用程序标识
     */
    public static String IDENTIFY = "";
    /**
     * 调试开关
     */
    public static boolean DEBUG = false;
    /**
     * SharedPreferences file name
     * 默认值: IDENTIFY + "Prefs"
     */
    public static String SHARED_PREFERENCES_NAME = "";
    /**
     * 数据库的名称
     * 不可修改
     * 默认值: IDENTIFY + ".db"
     */
    public static String DATABASE_NAME = "";
    /**
     * 渠道标识
     */
    public static String CHANNEL_ID = "";

    // =============================================================================================
    // HTTP Config
    // =============================================================================================

    /**
     * 入口协议
     * 生产环境必须
     * https://
     * http://
     */
    public static String PORTAL_PROTOCOL = "";
    /**
     * 主机名
     * DEBUG ? "" : ""
     */
    public static String PORTAL_HOST = "";
    /**
     * 入口地址
     */
    public static String PORTAL_ADDRESS = "";
    /**
     * 入口端口
     */
    public static String PORTAL_PORT = "";
    /**
     * 入口目录
     */
    public static String DIRECTORY = "";
    /**
     * 连接服务器超时时长
     * 单位 毫秒 TimeUnit.MILLISECONDS
     * 3*1000
     */
    public static int CONNECT_TIMEOUT = 0;
    /**
     * 读取超时时长
     * 单位 毫秒 TimeUnit.MILLISECONDS
     * 3*1000
     */
    public static int READ_TIMEOUT = 0;
    /**
     * 写入超时时长
     * 单位 毫秒 TimeUnit.MILLISECONDS
     * 9*1000
     */
    public static int WRITE_TIMEOUT = 0;

    /**
     * 配置应用程序
     *
     * @param application
     */
    public static void configApplication(Application application) {

    }

}
