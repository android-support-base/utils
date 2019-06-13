package com.amlzq.android.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.amlzq.android.content.ContextHolder;
import com.amlzq.android.security.MD5;

import java.util.UUID;

/**
 * Created by amlzq on 2016/4/07.
 * <p>
 * 设备硬件信息工具类
 * 注意权限问题
 * <p>
 * http://www.metsky.com/archives/668.html
 */

public class DeviceUtil {

    /**
     * @hide
     */
    DeviceUtil() {
    }

    // ===========================================
    // 设备属性
    // ===========================================

    /**
     * @return 设备是虚拟机
     */
    public static boolean isEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }

    /**
     * 最终产品的最终用户可见名称。
     * <p>
     * E.g: Pixel
     * E.g-Emulator: Google Nexus 5 - 6.0.0 - API 23 - 1080x1920
     *
     * @return 设备型号
     */
    public static String getBuildModel() {
        return Build.MODEL;
    }

    /**
     * IMEI
     * <p>
     * 返回唯一的设备ID，例如，GSM的IMEI和MEID或CDMA手机的ESN。 如果设备ID不可用，则返回null。
     * IMEI值仅仅只对Android手机有效,平板无效。
     * <p>
     * E.g: 866940028280741 <br>
     * E.g-Emulator: 000000000000000 <br>
     * Dangerous permission:android.permission.READ_PHONE_STATE <br>
     *
     * @return 设备标识
     */
    public static String getDeviceId() {
        Context cxt = ContextHolder.getContext();
        final TelephonyManager manager = (TelephonyManager) cxt.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            final String DEVICE_ID = "" + manager.getDeviceId();
            return DEVICE_ID;
        } catch (SecurityException e) {
            Logger.w(e);
            return "";
        }
    }

    /**
     * 返回第1行的电话号码字符串，例如GSM电话的MSISDN。 如果不可用则返回null。
     * <p>
     * E.g: 13590008000<br>
     * E.g-Emulator: 15555215554<br>
     * android.permission.READ_PHONE_NUMBERS
     *
     * @return 手机号码
     */
    public static String getPhoneNumber() {
        Context cxt = ContextHolder.getContext();
        TelephonyManager manager = (TelephonyManager) cxt.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            final String LINE1_NUMBER = "" + manager.getLine1Number();
            return LINE1_NUMBER;
        } catch (SecurityException e) {
            Logger.w(e);
            return "";
        }
    }

    /**
     * 返回唯一的用户ID，例如GSM手机的IMSI(国际移动用户识别码)。 如果不可用则返回null。
     * <p>
     * E.g: 460110402453511<br>
     * E.g-Emulator: 310260000000000<br>
     * Dangerous permission:android.permission.READ_PHONE_STATE <br>
     *
     * @return 设备国际移动用户识别码
     */
    public static String getSubscriberId() {
        Context cxt = ContextHolder.getContext();
        TelephonyManager manager = (TelephonyManager) cxt.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            final String SUBSCRIBER_ID = "" + manager.getSubscriberId();
            return SUBSCRIBER_ID;
        } catch (SecurityException e) {
            Logger.w(e);
            return "";
        }
    }

    /**
     * 如果适用，返回SIM的序列号。 如果不可用则返回null。
     * 通常被认为不可信，因为它有时为null。开发文档中说明了：这个ID会改变如果进行了出厂设置。<br>
     * 并且，如果某个Andorid手机被Root过的话， 这个ID也可以被任意改变。<br>
     * <p>
     * E.g: 89860316840204069586<br>
     * E.g-Emulator: 89014103211118510720<br>
     * permission:android.permission.READ_PHONE_STATE<br>
     *
     * @return SIM序列号
     */
    public static String getSimSerialNumber() {
        Context cxt = ContextHolder.getContext();
        TelephonyManager manager = (TelephonyManager) cxt.getSystemService(Context.TELEPHONY_SERVICE);
        try {
            final String SIM_SERIALNUMBER = "" + manager.getSimSerialNumber();
            return SIM_SERIALNUMBER;
        } catch (SecurityException e) {
            Logger.w(e);
            return "";
        }
    }

    /**
     * Android系统第一次启动时产生的一个64bit（16BYTES）数，如果设备被wipe还原，出厂设置或Root，该ID将被重置（变化）。
     * <p>
     * E.g: f99233cd94213410<br>
     * E.g-Emulator: af7792131f37bf6e<br>
     *
     * @return Android标识字符串
     */
    public static String getAndroidId() {
        Context cxt = ContextHolder.getContext();
        try {
            final String ANDROID_ID = "" + Settings.Secure.getString(cxt.getContentResolver(), Settings.Secure.ANDROID_ID);
            return ANDROID_ID;
        } catch (Exception e) {
            Logger.w(e);
            return "";
        }
    }

    /**
     * 组合值
     * 一系列通用的，非危险权限的硬件标识组成设备的通用唯一识别码
     * 通过IMEI和mac来唯一的标识用户。
     * 在平板设备上，无法通过imei标示设备，我们会将mac地址作为用户的唯一标识
     * <p>
     * 规则：
     * <p>
     * E.g: ffffffff-8f0b-b087-60dd-202375a7cc49<br>
     * E.g-Emulator: 00000000-7179-176c-ffff-ffff99d603a9<br>
     *
     * @return 通用唯一标识符(Universally Unique Identifier)
     */
    public static String getUUID() {
        final String DEVICE_ID, SIM_SERIALNUMBER, ANDROID_ID;
        DEVICE_ID = getDeviceId();
        SIM_SERIALNUMBER = getSimSerialNumber();
        ANDROID_ID = getAndroidId();
        UUID uuid = new UUID(ANDROID_ID.hashCode(),
                ((long) DEVICE_ID.hashCode() << 32) | SIM_SERIALNUMBER.hashCode());
        return uuid.toString();
    }

    /**
     * 组合值
     * 有一些特殊的情况，一些如平板电脑的设置没有通话功能，或者你不愿加入READ_PHONE_STATE许可。而你仍然想获得唯
     * 一序列号之类的东西。这时你可以通过取出ROM版本、制造商、CPU型号、以及其他硬件信息来实现这一点。这样计算出
     * 来的ID不是唯一的（因为如果两个手机应用了同样的硬件以及Rom 镜像）。但应当明白的是，出现类似情况的可能性基
     * 本可以忽略。大多数的Build成员都是字符串形式的，我们只取他们的长度信息。我们取到13个数字，并在前面加上“35
     * ”。这样这个ID看起来就和15位IMEI一样了。
     * <p>
     * E.g: 353597513688247<br>
     * E.g-Emulator: 357737776037998<br>
     *
     * @return Pseudo-Unique-ID, 这个在任何Android手机中都有效
     */
    public static String getPseudoIMEI() {
        // we make this look like ProvinceBean valid IMEI
        String devIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.CPU_ABI.length() % 10
                + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10
                + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10
                + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 + Build.TYPE.length() % 10
                + Build.USER.length() % 10; // 13 digits
        return devIDShort;
    }

    /**
     * 组合值
     * 综上所述，我们一共有五种方式取得设备的唯一标识。它们中的一些可能会返回null，<br>
     * 或者由于硬件缺失、权限问题等 获取失败。但你总能获得至少一个能用。<br>
     * 所以，最好的方法就是通过拼接，或者拼接后的计算出的MD5值来产生一个结果。<br>
     * 通过算法，可产生32位的16进制数据
     * <p>
     * E.g: 3FEED4673387DAAD6602BEE5ED216AC6<br>
     * E.g-Emulator: 0340F7D0E623BB7FD4610EFE0F67F783<br>
     *
     * @return Combined Device ID
     */
    public static String getAndroidUniqueId() {
        StringBuffer sb = new StringBuffer();
        sb.append(getDeviceId());
        sb.append(getPseudoIMEI());
        sb.append(getAndroidId());
        sb.append(NetworkUtil.getWiFiAddress());
        sb.append(BluetoothUtil.getAddress());
        String uuid = sb.toString();
        return MD5.encode(uuid.getBytes());
    }

    /**
     * 获取WebView的用户代理字符串。
     * <p>
     * E.g: Mozilla/5.0 (Linux; Android 5.0.2; CHE-TL00 Build/HonorCHE-TL00)
     * AppleWebKit/537.36 (KHmanagerL, like Gecko) Version/4.0 Chrome/37.0.0.0 Mobile
     * Safari/537.36<br>
     * E.g-Emulator: Mozilla/5.0 (Linux; Android 6.0; Google Nexus 5 - 6.0.0 -
     * API 23 - 1080x1920 Build/MRA58K) AppleWebKit/537.36 (KHmanagerL, like Gecko)
     * Version/4.0 Chrome/40.0.0.0 Mobile Safari/537.36<br>
     *
     * @return 用户代理字符串
     */
    public static String getWebViewUserAgent(Context cxt) {
        WebView webview = new WebView(cxt);
        webview.layout(0, 0, 0, 0);
        WebSettings settings = webview.getSettings();
        return settings.getUserAgentString();
    }

    /**
     * 查询Google Service Framework
     * https://www.cnblogs.com/ronaldHU/p/Android.hmanagerl
     * <p>
     * requires permission[com.google.android.providers.gsf.permission.READ_GSERVICES or
     * com.google.android.providers.gsf.permission.WRITE_GSERVICES]
     *
     * @return GSF ID KEY
     */
    public static String getGSFAndroidId() {
        Context cxt = ContextHolder.getContext();
        Uri uri = Uri.parse("content://com.google.android.gsf.gservices");
        String ANDROID_ID = "android_id";
        String params[] = {ANDROID_ID};
        Cursor cursor = cxt.getContentResolver().query(uri, null, null, params, null);
        if (!cursor.moveToFirst() || cursor.getColumnCount() < 2)
            return "";
        try {
            return Long.toHexString(Long.parseLong(cursor.getString(1)));
        } catch (NumberFormatException e) {
            return "";
        } finally {
            cursor.close();
        }
    }

}