package com.amlzq.android.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.IntDef;
import android.support.annotation.RequiresPermission;

import com.amlzq.android.content.ContextHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by amlzq on 2018/5/29.
 * <p>
 * 网络工具
 */

public class NetworkUtil {

    /**
     * @hide
     */
    private NetworkUtil() {
        throw new AssertionError();
    }

    /**
     * @return 返回网络连接的经理
     */
    public static ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) ContextHolder.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * @return 有效网络信息
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    public static NetworkInfo getActiveNetworkInfo() {
//        if (ContextCompat.checkSelfPermission(ContextHolder.getContext(), Manifest.permission.ACCESS_NETWORK_STATE)
//                != PackageManager.PERMISSION_GRANTED) {
//            throw new SecurityException("Missing permissions required by ConnectivityManager.getActiveNetworkInfo: android.permission.ACCESS_NETWORK_STATE");
//        }
        return getConnectivityManager().getActiveNetworkInfo();
    }

    // =============================================================================================
    // 网络属性
    // =============================================================================================

    public static final int IPv4 = 1;
    public static final int IPv6 = 2;

    // 用一个@IntDef({})将其全部变量包含，其次需要一个Retention声明其保留级别，最后定义其接口名称
    // 需要support-annotations library
    @IntDef({IPv4, IPv6})
    @Retention(RetentionPolicy.SOURCE)
    public @interface IPType {
    }

    /**
     * Get IP address from first non-localhost interface
     * <p>
     * E.g:  <br>
     * E.g-Emulator:  <br>
     *
     * @return address or empty string
     * @permission: android.permission.INTERNET
     */
    public static String getIPAddress(@IPType int IPType) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String hostAddress = addr.getHostAddress();
                        boolean isIPv4 = hostAddress.indexOf(':') < 0;
                        if (IPType == IPv4) {
                            if (isIPv4)
                                return hostAddress;
                        } else {
                            if (!isIPv4) {
                                int delim = hostAddress.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? hostAddress.toUpperCase() : hostAddress.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.w(e);
        }
        return "";
    }

    /**
     * 获取当前网络类型
     *
     * @return 0：没有网络   1：WIFI网络   2：2G/3G/4G
     */
    public static final int NETTYPE_NO = 0x00;
    public static final int NETTYPE_WIFI = 0x01;
    public static final int NETTYPE_TYPE_MOBILE = 0x02;

    /**
     * 记录 WLAN 接口的MAC地址，是一个唯一ID
     * <p>
     * E.g: 88:28:b3:3f:ca:79 <br>
     * E.g-Emulator: 02:00:00:00:00:00 <br>
     * requires permission: android.permission.ACCESS_WIFI_STATE <br>
     *
     * @return Mac地址
     */
    public static String getWiFiAddress() {
        Context cxt = ContextHolder.getContext().getApplicationContext();
        final WifiManager manager = (WifiManager) cxt.getSystemService(Context.WIFI_SERVICE);
        try {
            final String MAC_ADDRESS = "" + manager.getConnectionInfo().getMacAddress();
            return MAC_ADDRESS;
        } catch (Exception e) {
            Logger.w(e);
            return "";
        }
    }

    /**
     * 网络状态
     */

    /**
     * @return 连入了互联网
     * @permission android.permission.ACCESS_NETWORK_STATE
     * @prefrence @link{https://developer.android.com/training/monitoring-device-state/connectivity-monitoring?hl=zh-cn#DetermineConnection}
     */
    public static boolean isConnected() {
        NetworkInfo info = getActiveNetworkInfo();
//        return info != null && info.isConnectedOrConnecting();
        return info != null && info.isConnected();
    }

    public static int getNetworkType(Context context) {
        int netType = NETTYPE_NO;
        NetworkInfo info = getActiveNetworkInfo();
        if (info == null) {
            return netType;
        }
        int nType = info.getType();
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            String extraInfo = info.getExtraInfo();
            if (extraInfo != null && !extraInfo.isEmpty()) {
                netType = NETTYPE_TYPE_MOBILE;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            netType = NETTYPE_WIFI;
        }
        return netType;
    }

    /**
     * @return 是WiFi网络环境
     * @prefrence @link{https://developer.android.com/training/monitoring-device-state/connectivity-monitoring?hl=zh-cn#DetermineType}
     */
    public static boolean isWiFi() {
        NetworkInfo info = getActiveNetworkInfo();
        return info != null && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * @return 是移动网络环境
     */
    public static boolean isMobile() {
        NetworkInfo info = getActiveNetworkInfo();
        return info != null && info.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    public static boolean pinging = false;

    public static boolean ping(String str) {
        if (pinging) {
            return true;
        }
        pinging = true;
        boolean resault = false;
        Process process;
        try {
            //ping -c 3 -w 100  中  ，-c 是指ping的次数 3是指ping 3次 ，-w 100  以秒为单位指定超时间隔，是指超时时间为100秒
            process = Runtime.getRuntime().exec("ping -c 1 -w 5 " + str);
            int status = process.waitFor();

            InputStream input = process.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
            Logger.d("return ->" + buffer.toString());

            if (status == 0) {
                resault = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pinging = false;
        return resault;
    }

}