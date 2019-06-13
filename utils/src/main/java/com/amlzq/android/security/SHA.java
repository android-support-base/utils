package com.amlzq.android.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Secure Hash Algorithm
 * 安全散列（哈希）算法
 * <br>
 * SHA-0：1993年发布，称做安全散列标准（Secure Hash Standard）。
 * SHA-1：1995年发布，2017年攻破，MD5的后继者。
 * SHA-2：2001年发布，包括SHA-224、SHA-256、SHA-384、SHA-512、SHA-512/224、SHA-512/256。尚未攻破
 * SHA-3：2015年发布，
 * <br>
 * https://zh.wikipedia.org/wiki/SHA%E5%AE%B6%E6%97%8F
 * <br>
 *
 * @author amlzq
 */

public class SHA {

    public enum TYPE {
        SHA("SHA"),
        SHA1("SHA-1"),
        SHA224("SHA-224"),
        SHA256("SHA-256"),
        SHA384("SHA-384"),
        SHA512("SHA-512");
        private String algorithm; // the name of the algorithm requested.

        TYPE(String algorithm) {
            this.algorithm = algorithm;
        }

        public String getValue() {
            return algorithm;
        }
    }

    /**
     * @param type  type
     * @param input input string
     * @return encode
     */
    public static String encode(TYPE type, byte[] input) {
        try {
            MessageDigest digest = MessageDigest.getInstance(type.getValue());
            digest.update(input);
            return toHexString(digest.digest());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encode(TYPE type, String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance(type.getValue());
            digest.update(input.getBytes());
            return toHexString(digest.digest());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param bytes bytes
     * @return 32位加密补零
     */
    private static String toHexString(byte[] bytes) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bytes.length; i++) {
            tmp = (Integer.toHexString(bytes[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    /**
     * @param type type
     * @param file file
     * @return 文件的散列值
     */
    public static String getFile(TYPE type, File file) {
        FileInputStream fileInputStream = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(type.getValue());
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                digest.update(buffer, 0, length);
            }
            return new BigInteger(1, digest.digest()).toString(16);
        } catch (Exception e) {
            e.printStackTrace();
            return file.getPath();
        } finally {
            try {
                if (fileInputStream != null)
                    fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}