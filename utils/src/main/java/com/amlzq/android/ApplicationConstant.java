package com.amlzq.android;

import com.amlzq.android.content.ContextHolder;

/**
 * 应用程序常量
 * MyConstant继承此类
 */
public abstract class ApplicationConstant {

    /**
     * 常量
     */
    public static final String CONSTANT = "";

    /**
     * default authority
     * https://developer.android.com/reference/android/support/v4/content/FileProvider#ProviderDefinition
     */
    public static String AUTHORITY = ContextHolder.getContext().getPackageName() + ".fileProvider";

    /**
     * 目标视图
     */
    public static final String TARGET_VIEW = "TARGET_VIEW";

    /**
     * 是否允许在蜂窝网络(2/3/4/5G移动网络)环境通信
     * default: false
     */
    public static final String PREFS_CELLULAR_NETWORK_CONNECTION = "CELLULAR_NETWORK_CONNECTION";

    /**
     * 是否仅在Wi-Fi网络环境通信
     * default: true
     */
    public static final String ONLY_WIFI_DOWNLOAD = "ONLY_WIFI_CONNECTION";

    /**
     * 默认渠道标识
     * 母包
     */
    public static String DEFAULT_CHANNEL_ID = "maternal";

}
