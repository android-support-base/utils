package com.amlzq.android.security;

import com.amlzq.android.util.Logger;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 Message-Digest Algorithm
 * MD5消息摘要算法
 * <br>
 * input infinite
 * ouput 128 bits(16/32 byte) hash value
 * UpperCase or toLowerCase
 * <br>
 * 散列算法，用于确保信息传输完整一致。
 * <br>
 * https://zh.wikipedia.org/wiki/MD5
 * <br>
 *
 * @author amlzq
 */
public class MD5 {

    private static final String ALGORITHM = "MD5";

    private static MessageDigest MD;

    static {
        try {
            // 获得MD5摘要算法的MessageDigest对象
            MD = MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            Logger.e("Get MessageDigest instance failed.", e);
        }
    }

    /**
     * @param source      Original string
     * @param charsetName The name of a supported {@linkplain java.nio.charset.Charset
     *                    charset}
     * @return 32-bit
     */
    public static String encode(String source, String charsetName) {
        try {
            return encode(source.getBytes(charsetName));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Default UTF-8 charset
     *
     * @param source Original string
     * @return 32-bit
     */
    public static String encode(String source) {
        try {
            return encode(source.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param source Original string
     * @return 32-bit
     */
    public static String encode(byte[] source) {
        // 使用指定的字节更新摘要
        MD.update(source);
        // 获得摘要数组
        byte messageDigest[] = MD.digest();
        return Decimal.toHexString(messageDigest);
    }

    /**
     * @param source32 32-bit string
     * @return 16-bit
     */
    public static String to16Bit(String source32) {
        return source32.substring(8, 24);
    }

}