package com.amlzq.android.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Message-Digest Algorithm
 * 消息摘要算法
 * <br>
 * MD5	1+
 * SHA-1	1+
 * SHA-224	1-8,22 +
 * SHA-256	1+
 * SHA-384	1+
 * SHA-512	1+
 * <br>
 * https://zh.wikipedia.org/wiki/Message-Digest
 * https://developer.android.com/reference/java/security/MessageDigest
 * <br>
 *
 * @author amlzq
 */

public class MsgDigest {

    public enum Algorithm {
        MD2("MD2"),
        MD5("MD5");
        private String algorithm;

        Algorithm(String algorithm) {
            this.algorithm = algorithm;
        }

        public String getValue() {
            return algorithm;
        }
    }

    /**
     * @param algorithm algorithm
     * @param file      file
     * @return 文件的MD5值
     */
    public static String getFile(Algorithm algorithm, File file) {
        FileInputStream fileInputStream = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm.getValue());
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