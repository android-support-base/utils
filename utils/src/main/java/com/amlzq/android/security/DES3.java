package com.amlzq.android.security;

import android.util.Base64;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * Created by amlzq on 2018/5/1.
 * <p>
 * 3DES加解密工具类
 */

public class DES3 {

    // 算法名称
    private static final String KEY_ALGORITHM = "DESede";
    // 算法名称/加密模式/填充方式
    // DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
    // 常采用的是 NoPadding（不填充）、Zeros 填充（0填充）、PKCS5Padding 填充
    private static final String CIPHER_ALGORITHM = "desede/CBC/PKCS5Padding";
    // 非ECB模式，则需要初始化向量（8位）
    private static final String iv_key = "password";

    /**
     * 生成密钥key对象
     *
     * @param keyStr 密钥
     * @return 密钥对象
     * @throws Exception Exception
     */
    private static SecretKey keyGenerator(String keyStr) throws Exception {
        byte[] temp = keyStr.getBytes("UTF-8");
        byte[] key;
        if (temp.length < 24) {
            key = new byte[24];
            System.arraycopy(temp, 0, key, 0, temp.length);
        } else {
            key = new byte[temp.length];
            System.arraycopy(temp, 0, key, 0, key.length);
        }
        DESedeKeySpec secretKey = new DESedeKeySpec(key);
        // 创建一个密匙工厂，然后用它把DESedeKeySpec转换成SecretKeyFactory
        SecretKeyFactory KeyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
        return KeyFactory.generateSecret(secretKey);
    }

    /**
     * 加密数据
     *
     * @param key  密钥
     * @param data 待加密数据 - 明文
     * @return Base64编码的密文
     * @throws Exception Exception
     */
    public static String encrypt(String key, String data) throws Exception {
        return encrypt(key, data, iv_key);
    }

    /**
     * 加密数据
     *
     * @param key   密钥
     * @param data  待加密数据 - 明文
     * @param ivKey 向量密钥
     * @return Base64编码的密文
     * @throws Exception Exception
     */
    public static String encrypt(String key, String data, String ivKey) throws Exception {
        Key secretKey = keyGenerator(key);
        // 实例化Cipher对象，它用于完成实际的加密操作
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // CBC模式，初始化向量IV
        IvParameterSpec paramSpec = new IvParameterSpec(ivKey.getBytes());
        // 初始化Cipher对象，设置为加密模式
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
        // 执行加密操作
        return Base64.encodeToString(cipher.doFinal(data.getBytes()), Base64.DEFAULT);
    }

    /**
     * @param key  密钥
     * @param data Base64编码的密文
     * @return 解密后的数据 - 明文
     * @throws Exception Exception
     */
    public static String decrypt(String key, String data) throws Exception {
        return decrypt(key, data, iv_key);
    }

    /**
     * @param key   密钥
     * @param data  Base64编码的密文
     * @param ivKey 向量密钥
     * @return 解密后的数据 - 明文
     * @throws Exception Exception
     */
    public static String decrypt(String key, String data, String ivKey) throws Exception {
        Key desKey = keyGenerator(key);
        // 实例化Cipher对象，它用于完成实际的解密操作
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // CBC模式,需要初始化向量IV
        IvParameterSpec paramSpec = new IvParameterSpec(ivKey.getBytes());
        // 初始化Cipher对象，设置为解密模式
        cipher.init(Cipher.DECRYPT_MODE, desKey, paramSpec);
        // 执行解密操作
        return new String(cipher.doFinal(Base64.decode(data, Base64.DEFAULT)));
    }

}