package com.amlzq.asb;

import android.content.SharedPreferences;

import com.amlzq.android.ApplicationConfig;
import com.amlzq.android.ApplicationConstant;
import com.amlzq.android.content.ContextHolder;

public class PreferencesManager {

    /**
     * @return 是否允许在蜂窝网络(2 / 3 / 4 / 5G移动网络)环境通信
     */
    public static boolean cellularNetworkConnection() {
        SharedPreferences preferences = ContextHolder.getContext().getSharedPreferences(ApplicationConfig.SHARED_PREFERENCES_NAME, 0);
        return preferences != null && preferences.getBoolean(ApplicationConstant.PREFS_CELLULAR_NETWORK_CONNECTION, false);
    }

    /**
     * @param able 激活移动网络
     */
    public static void updateCellularNetworkConfig(boolean able) {
        SharedPreferences.Editor editor = ContextHolder.getContext().getSharedPreferences(ApplicationConfig.SHARED_PREFERENCES_NAME, 0).edit();
        editor.putBoolean(ApplicationConstant.PREFS_CELLULAR_NETWORK_CONNECTION, able);
        editor.apply();
    }

}