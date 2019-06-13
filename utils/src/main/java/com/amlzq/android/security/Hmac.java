package com.amlzq.android.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * HMAC
 * <br/>
 * <br/>
 * https://en.wikipedia.org/wiki/HMAC
 * <br/>
 *
 * @author amlzq
 */

public class Hmac {

    /**
     * @param algorithm
     * @param plain     原始字符串
     * @param secretKey
     * @return
     */
    public static String encrypt(Algorithm algorithm, String plain, String secretKey) {
        SecretKeySpec sks = new SecretKeySpec(secretKey.getBytes(), algorithm.getValue());
        try {
            Mac mac = Mac.getInstance(algorithm.getValue());
            mac.init(sks);
            byte[] hashBytes = mac.doFinal(plain.getBytes());
            return Decimal.toHexString(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt() {
        return null;
    }

    public enum Algorithm {
        HmacMD5("HmacMD5"),
        HmacSHA1("HmacSHA1"),
        HmacSHA256("HmacSHA256"),
        HmacSHA512("HmacSHA512");
        private String algorithm; // the name of the algorithm requested.

        Algorithm(String algorithm) {
            this.algorithm = algorithm;
        }

        public String getValue() {
            return algorithm;
        }
    }

    // 示例
    // HmacMD5("", "")    = 74e6f7298a9c2d168935f58c001bad88
    // HmacSHA1("", "")   = fbdb1d1b18aa6c08324b7d64b71fb76370690e1d
    // HmacSHA256("", "") = b613679a0814d9ec772f95d778c35fc5ff1697c493715653c6c712144292c5ad
    // HmacSHA512("", "") =

}