package com.amlzq.android.security;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加/解密类Cipher<br>
 * 密钥类SecretKeySpec<br>
 * <br>
 *
 * @author amlzq
 */
public class AES {

    /**
     * 算法
     */
    private static final String AES128TYPE = "AES/ECB/PKCS5Padding";
    private static final String AES256TYPE = "AES/ECB/PKCS7Padding";

    /**
     * @param keyStr    钥匙
     * @param plainText 明文
     * @return 编码
     */
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public static String encode(String keyStr, String plainText) {
        byte[] encrypt = null;
        try {
            Key key = generateKey(keyStr);
            Cipher cipher = Cipher.getInstance(AES128TYPE);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            encrypt = cipher.doFinal(plainText.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(encrypt, Base64.NO_WRAP);
    }

    /**
     * @param keyStr      钥匙
     * @param encryptData 密文
     * @return 解码
     */
    @RequiresApi(api = Build.VERSION_CODES.FROYO)
    public static String decode(String keyStr, String encryptData) {
        byte[] decrypt = null;
        try {
            Key key = generateKey(keyStr);
            Cipher cipher = Cipher.getInstance(AES128TYPE);
            cipher.init(Cipher.DECRYPT_MODE, key);
            decrypt = cipher.doFinal(Base64.decode(encryptData, Base64.NO_WRAP));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(decrypt).trim();
    }

    /**
     * @param str 要被加密的字符串
     * @param key 加/解密要用的长度为32的字节数组（256位）密钥
     * @return byte[] 加密后的字节数组
     */
    public static byte[] encodeTo256(String str, byte[] key) {
        // initialize();
        byte[] result = null;
        try {
            Cipher cipher = Cipher.getInstance(AES256TYPE, "BC");
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); // 生成加密解密需要的Key
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            result = cipher.doFinal(str.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param bytes 要被解密的字节数组
     * @param key   加/解密要用的长度为32的字节数组（256位）密钥
     * @return String 解密后的字符串
     */
    public static String decodeTo256(byte[] bytes, byte[] key) {
        // initialize();
        String result = null;
        try {
            Cipher cipher = Cipher.getInstance(AES256TYPE, "BC");
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); // 生成加密解密需要的Key
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decoded = cipher.doFinal(bytes);
            result = new String(decoded, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param key 键
     * @return Generate Key
     * @throws Exception Exception
     */
    private static Key generateKey(String key) throws Exception {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            return keySpec;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * @param source 源字符串
     * @return 加密解密算法 执行一次加密，两次解密<br>
     * G662@2E2CBA6B7L0E662A0AD6M0@MF67<br>
     * System.out.println("加密的：" + convertMD5(s));<br>
     * System.out.println("解密的：" + convertMD5(convertMD5(s)));<br>
     */
    public static String convertMd5(String source) {
        char[] a = source.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String string = new String(a);
        return string;
    }

}