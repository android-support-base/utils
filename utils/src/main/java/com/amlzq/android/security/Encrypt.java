package com.amlzq.android.security;

import android.util.Base64;

/**
 * Created by amlzq on 2018/5/1.
 * 加密
 */

public class Encrypt {

    private static char[] k = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z', '*', '!'};

    /**
     * @param str 源字符串
     * @return 编码
     */
    public static String encode(String str) {
        if (str == null) {
            return "";
        }
        str = encodeStr(str);
        StringBuffer sb = new StringBuffer();
        char[] array = str.toCharArray();
        sb.append("x");
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i] + k[(i % k.length)]);
            sb.append("_");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append("y");
        return sb.toString();
    }

    /**
     * @param str 源字符串
     * @return 解码
     */
    public static String decode(String str) {
        if (str == null) {
            return null;
        }
        if ((str.startsWith("x")) && (str.endsWith("y"))) {
            StringBuffer sb = new StringBuffer(str);
            sb.deleteCharAt(0);
            sb.deleteCharAt(sb.length() - 1);
            str = sb.toString();
            String[] strs = str.split("_");
            sb = new StringBuffer();
            for (int i = 0; i < strs.length; i++) {
                sb.append((char) (Integer.parseInt(strs[i]) - k[(i % k.length)]));
            }
            return decodeStr(sb.toString());
        }
        return "";
    }

    /**
     * @param str 源字符串
     * @return 解码
     */
    public static String decode2(String str) {
        if (str == null) {
            return null;
        }

        if ((str.startsWith("x")) && (str.endsWith("y"))) {
            StringBuffer sb = new StringBuffer(str);
            sb.deleteCharAt(0);
            sb.deleteCharAt(sb.length() - 1);
            str = sb.toString();
            String[] strs = str.split("_");
            sb = new StringBuffer();
            for (int i = 0; i < strs.length; i++) {
                sb.append((char) (Integer.parseInt(strs[i]) - k[(i % k.length)]));
            }

            return decodeStr(sb.toString());
        }
        return "";
    }

    /**
     * @param inputMessage 源字符串
     * @return 解码
     */
    private static String decodeStr(String inputMessage) {
        byte[] debytes = Base64.decode(inputMessage, Base64.DEFAULT);
        return new String(debytes);
    }

    /**
     * @param inputMessage 源字符串
     * @return 编码
     */
    private static String encodeStr(String inputMessage) {
        return Base64.encodeToString(inputMessage.getBytes(), Base64.DEFAULT);
    }

}