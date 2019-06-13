package com.amlzq.android.security;

/**
 * 进制转换
 */

public class Decimal {

    /**
     * 16进制字符集
     */
    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 转化为十六进制字符串
     *
     * @param bytes bytes
     * @return hex string
     */
    public static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            sb.append(HEX_DIGITS[(aByte & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[aByte & 0x0f]);
        }
        return sb.toString();
    }

}