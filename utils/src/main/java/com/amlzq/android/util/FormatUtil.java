package com.amlzq.android.util;

import java.text.DecimalFormat;

/**
 * Created by amlzq on 2018/9/10.
 * Format util
 */

public class FormatUtil {

    public static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            String h = Integer.toHexString(bytes[i]);
            int l = h.length();
            if (l == 1)
                h = "0" + h;
            if (l > 2)
                h = h.substring(l - 2, l);
            sb.append(h.toUpperCase());
            if (i < (bytes.length - 1))
                sb.append(':');
        }
        return sb.toString();
    }

    /**
     * MByte/MB
     *
     * @param size value
     * @return MByte
     */
    public static String getMB(long size) {
        DecimalFormat format = new DecimalFormat("#0.00");
        final float number = size / 1024.0f / 1024.0f;
        return format.format(number) + "MB";
    }

    /**
     * 百分数无小数
     *
     * @param percent 百分比
     * @return 34.89%
     */
    public static String getPercent(float percent) {
        DecimalFormat format = new DecimalFormat("0%");
        return format.format(percent);
    }

    /**
     * 百分数并保留两位小数
     *
     * @param percent 百分比
     * @return 34.89%
     */
    public static String getPercent2(float percent) {
        DecimalFormat format = new DecimalFormat("0.00%");
        return format.format(percent);
    }

    public static String getMKBStr(int size) {
        if (size == 0) {
            return "0M";
        }
        String str = "0.0M";
        float m = size / 1024.0f / 1024.0f;
        float k = size / 1024.0f;
        DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String mStr = decimalFormat.format(m);
        String kStr = decimalFormat.format(k);
        if (m > 1.0f) {
            str = mStr + "M";
        } else if (k > 1.0f) {
            str = kStr + "KB";
        } else {
            str = size + "bit";
        }
        return str;
    }

    public static String getMKBStr(float size) {
        if (size == 0) {
            return "0M";
        }
        String str = "0.0M";
        float m = size / 1024.0f / 1024.0f;
        float k = size / 1024.0f;
        DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String mStr = decimalFormat.format(m);
        String kStr = decimalFormat.format(k);
        if (m > 1.0f) {
            str = mStr + "M";
        } else if (k > 1.0f) {
            str = kStr + "KB";
        } else {
            str = size + "bit";
        }
        return str;
    }

    public static String getSpeedStr(int kb) {
        String str = "0.0M";
        float m = kb / 1024.0f;
        DecimalFormat decimalFormat = new DecimalFormat(".00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String mStr = decimalFormat.format(m);
        if (m > 1.0f) {
            str = mStr + "M";
        } else {
            str = kb + "KB";
        }
        return str + "/s";
    }

}