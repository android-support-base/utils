package com.amlzq.android.security;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * Created by amlzq on 2018/5/1.
 * RSA工具类 提供加密，解密，生成密钥对等方法，签名方法
 */

public class RSA {
    public static final String TAG = "RSA";

    private static final String ALGORITHM = "RSA";

    /**
     * @param algorithm 算法
     * @param bysKey    密钥
     * @return getPublicKeyFromX509
     */
    private static PublicKey getPublicKeyFromX509(String algorithm, String bysKey) {
        byte[] decodedKey = Base64.decode(bysKey, Base64.DEFAULT);
        X509EncodedKeySpec x509 = new X509EncodedKeySpec(decodedKey);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            return keyFactory.generatePublic(x509);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param content content
     * @param key     key
     * @return encrypt2
     */
    public static byte[] encrypt2(String content, String key) {
        try {
            PublicKey pubkey = getPublicKeyFromX509(ALGORITHM, key);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pubkey);
            byte plaintext[] = content.getBytes("UTF-8");
            byte[] output = cipher.doFinal(plaintext);
            return output;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param content content
     * @param key     key
     * @return encrypt
     */
    public static String encrypt(String content, String key) {
        try {
            PublicKey pubkey = getPublicKeyFromX509(ALGORITHM, key);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pubkey);
            byte plaintext[] = content.getBytes("UTF-8");
            byte[] output = cipher.doFinal(plaintext);
            return Base64.encodeToString(output, Base64.DEFAULT);
        } catch (Exception e) {
            Log.e(TAG, "RSA加密码出错->" + e.getMessage());
            return null;
        }
    }

    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    /**
     * @param content    content
     * @param privateKey privateKey
     * @return sign
     */
    public static String sign(String content, String privateKey) {
        String charset = "utf-8";
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    Base64.decode(privateKey, Base64.DEFAULT));

            KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
            PrivateKey priKey = keyFactory.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update(content.getBytes(charset));

            byte[] signed = signature.sign();

            return Base64.encodeToString(signed, Base64.DEFAULT);
        } catch (Exception e) {
            Log.e(TAG, "RSA sign error:" + e.getMessage());
        }
        return null;
    }

    /**
     * @param content content
     * @return getMD5
     */
    public static String getMD5(String content) {
        String s = null;
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            md.update(content.getBytes());
            byte tmp[] = md.digest();
            char str[] = new char[16 * 2];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            s = new String(str);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * @param content   content
     * @param sign      sign
     * @param publicKey publicKey
     * @return doCheck
     */
    public static boolean doCheck(String content, String sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.decode(publicKey, Base64.DEFAULT);
            PublicKey pubKey = keyFactory
                    .generatePublic(new X509EncodedKeySpec(encodedKey));

            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);
            signature.initVerify(pubKey);
            signature.update(content.getBytes("utf-8"));
            Log.i(TAG, "content :   " + content);
            Log.i(TAG, "sign:   " + sign);
            boolean bverify = signature.verify(
                    Base64.decode(sign, Base64.DEFAULT));
            Log.i(TAG, "bverify = " + bverify);
            return bverify;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // RSA密钥文件路径
    private static final String KEY_PATH = "D:/RSAKey.txt";
    // 算法名称
    private static final String KEY_ALGORITHM = "RSA";
    // 算法名称/加密模式/填充方式
    // private static final String CIPHER_ALGORITHM    = "RSA/ECB/PKCS1Padding";
    private static final String CIPHER_ALGORITHM = "RSA/None/PKCS1Padding";
    // 秘钥长度
    private static final int DEFAULT_KEY_SIZE = 1024;
    // 当前秘钥、工作模式、填充方式所支持加密的最大字节数
    private static final int DEFAULT_BUFFER_SIZE = (DEFAULT_KEY_SIZE / 8) - 11;
    //与php服务端签名需要的key
    private static String phpSignKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMmfFQt1Seq792sX\n" +
            "TUCKE/X0gVqUD64S6Y/+zet+QJIp2sBMFCbqF8OfhtCvTI4ogdrsqkFNN6YhX4ld\n" +
            "fbGN7x5Fk+LfeUZ/4c6iibIYaOfLZwBU85BM7Z5jvczMsQovtWvCLX1khndPCwp3\n" +
            "1W2xPJosgIy/FqgwwZqJ6Dt5U3FfAgMBAAECgYAq1u9cyOYUxy2SBph2fyAwoWwe\n" +
            "t9kBDqmr2+MbB29m3xfIxiF+Bz1XZnz5uFWW2wqEXsbqKyMI3Ix1HnVHlbXZop2q\n" +
            "ypoOwrIpRYD2a8BCh7HdRgdK88F+GYN5xrDfud6tJIAMmRvnXvjhwWOu/H1wavjk\n" +
            "xHmywbaptSGWDAJJSQJBAP4nUP40CdcDtVVPKta60EuAXd/oDVnV6TZaXttqm25x\n" +
            "flzO9hM6yR6WSiFbTEtfHT9vMAMuVI3889DDw61qRpUCQQDLFhCPgBP/kaO49Kt1\n" +
            "14ZNUZguslSCnCG8Y3X93+7cWzOVZ1UzBqhFl97COAOTVwJekO9cD+dTsKQ6DAzr\n" +
            "EN8jAkALBHdNXcIjcQS2WwU3Y8fNzhXDtBKIF27RrV85UcjFI+Rfb5VZRg4b5lX2\n" +
            "VjfbeJcS/eXt7V86/IExRps/9EB1AkAKWZmGdrSONdHuPynt24oQrrPezV/ZODPP\n" +
            "8hG1WpBF0rASUS3aZ+Bzi4XiyBAGlkycnS5VtsnHTh1QiASwhi+ZAkEAw0U58X9W\n" +
            "RZdoGjKD9ASDLSGxaipcCR6CErOb/m2IaNndU+Q2oQ+GjI7I5+bUUHIyr7L+BCw7\n" +
            "ILrNBl+dZxD0lA==";

    /**
     * @return 随机生成RSA密钥对(默认密钥长度为1024)
     */
    private static KeyPair generateRSAKeyPair() {
        return generateRSAKeyPair(DEFAULT_KEY_SIZE);
    }

    /**
     * @param keyLength 密钥长度，范围：512～2048
     *                  一般1024
     * @return 随机生成RSA密钥对
     */
    private static KeyPair generateRSAKeyPair(int keyLength) {
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            kpg.initialize(keyLength);
            KeyPair kp = kpg.genKeyPair();
            printPublicKeyInfo(kp.getPublic());
            printPrivateKeyInfo(kp.getPrivate());
            return kp;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param keyBytes keyBytes
     * @return 通过公钥byte[]将公钥还原
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws InvalidKeySpecException  InvalidKeySpecException
     */
    private static PublicKey getPublicKey(byte[] keyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * @param keyBytes keyBytes
     * @return 通过私钥byte[]将私钥还原
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws InvalidKeySpecException  InvalidKeySpecException
     */
    private static PrivateKey getPrivateKey(byte[] keyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        // X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * @param modulus        模数
     * @param publicExponent 公钥指数
     * @return 使用N、e值还原公钥 通过模数 + 指数获得公钥
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws InvalidKeySpecException  InvalidKeySpecException
     */
    private static RSAPublicKey getPublicKey(String modulus, String publicExponent) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        RSAPublicKeySpec keySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));
        return (RSAPublicKey) keyFactory.generatePublic(keySpec);
    }

    /**
     * @param modulus         模数
     * @param privateExponent 私钥指数
     * @return 使用N、d值还原私钥 通过模数 + 指数获得私钥
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws InvalidKeySpecException  InvalidKeySpecException
     */
    private static RSAPrivateKey getPrivateKey(String modulus, String privateExponent) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(new BigInteger(modulus), new BigInteger(privateExponent));
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    /**
     * @param publicKeyStr 公钥数据字符串
     * @return 从字符串中加载公钥
     * @throws Exception 加载公钥时产生的异常
     */
    private static PublicKey loadPublicKey(String publicKeyStr) throws Exception {
        try {
            byte[] buffer = Base64.decode(publicKeyStr, Base64.DEFAULT);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 从字符串中加载私钥
     * 加载时使用的是PKCS8EncodedKeySpec（PKCS#8编码的Key指令）。
     *
     * @param privateKeyStr 私钥数据字符串
     * @return 私钥
     * @throws Exception 加载私钥时产生的异常
     */
    public static PrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
        try {
            byte[] buffer = Base64.decode(privateKeyStr, Base64.DEFAULT);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            // X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            return keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 从文件输入流中加载公钥
     *
     * @param in 公钥输入流
     * @return 公钥
     * @throws Exception 加载公钥时产生的异常
     */
    public static PublicKey loadPublicKey(InputStream in) throws Exception {
        try {
            return loadPublicKey(readKey(in));
        } catch (IOException e) {
            throw new Exception("公钥数据流读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥输入流为空");
        }
    }

    /**
     * 从文件输入流中加载私钥
     *
     * @param in 私钥输入流
     * @return 私钥
     * @throws Exception 加载公钥时产生的异常
     */
    private static PrivateKey loadPrivateKey(InputStream in) throws Exception {
        try {
            return loadPrivateKey(readKey(in));
        } catch (IOException e) {
            throw new Exception("私钥数据读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥输入流为空");
        }
    }

    /**
     * @param in in
     * @return 读取密钥信息
     * @throws IOException IOException
     */
    private static String readKey(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        StringBuilder sb = new StringBuilder();
        String readLine;
        while ((readLine = br.readLine()) != null) {
            if (readLine.charAt(0) != '-') {
                sb.append(readLine);
                sb.append('\r');
            }
        }
        return sb.toString();
    }

    /**
     * 保存密钥文件
     *
     * @param kp kp
     * @throws Exception Exception
     */
    private static void saveKeyPair(KeyPair kp) throws Exception {
        FileOutputStream fos = new FileOutputStream(KEY_PATH);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(kp);
        oos.close();
        fos.close();
    }

    /**
     * 读取密钥文件
     *
     * @param path path
     * @throws Exception Exception
     */
    private static KeyPair readKeyPair(String path) throws Exception {
        if (TextUtils.isEmpty(path)) {
            path = KEY_PATH;
        }
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream oos = new ObjectInputStream(fis);
        KeyPair kp = (KeyPair) oos.readObject();
        oos.close();
        fis.close();
        return kp;
    }

    /**
     * 读取源文件内容
     *
     * @param path 文件路径
     * @return byte[] 文件内容
     * @throws IOException IOException
     */
    private static String readFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        String s = br.readLine();
        String str = "";
        while (s.charAt(0) != '-') {
            str += s;
            s = br.readLine();
        }
        br.close();
        return str;
    }

    /**
     * 打印公钥信息
     *
     * @param publicKey publicKey
     */
    private static void printPublicKeyInfo(PublicKey publicKey) {
        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;
        System.out.println("\n---------- RSAPublicKey ----------");
        System.out.println("Modulus.length=" + rsaPublicKey.getModulus().bitLength());
        System.out.println("Modulus=" + rsaPublicKey.getModulus().toString());
        System.out.println("PublicExponent.length=" + rsaPublicKey.getPublicExponent().bitLength());
        System.out.println("PublicExponent=" + rsaPublicKey.getPublicExponent().toString());
        System.out.println("PublicEncoded=" + Base64.encodeToString(rsaPublicKey.getEncoded(), Base64.DEFAULT));
    }

    /**
     * 打印私钥信息
     *
     * @param privateKey privateKey
     */
    private static void printPrivateKeyInfo(PrivateKey privateKey) {
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) privateKey;
        System.out.println("\n---------- RSAPrivateKey ----------");
        System.out.println("Modulus.length=" + rsaPrivateKey.getModulus().bitLength());
        System.out.println("Modulus=" + rsaPrivateKey.getModulus().toString());
        System.out.println("PrivateExponent.length=" + rsaPrivateKey.getPrivateExponent().bitLength());
        System.out.println("PrivateExponent=" + rsaPrivateKey.getPrivateExponent().toString());
        System.out.println("PrivateEncoded=" + Base64.encodeToString(rsaPrivateKey.getEncoded(), Base64.DEFAULT));
    }

    /**
     * 加密
     *
     * @param key  加密的密钥
     * @param data 待加密的数据
     * @return 加密后的数据
     * @throws Exception Exception
     */
    public static byte[] encrypt(Key key, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        // 编码前设定编码方式及密钥
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // 待加密的字节数不能超过密钥的长度值除以 8 再减去 11
        // int blockSize = cipher.getBlockSize();
        int blockSize = DEFAULT_BUFFER_SIZE;
        // 获得加密块加密后块大小
        int outputSize = cipher.getOutputSize(data.length);
        int leavedSize = data.length % blockSize;
        int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
        byte[] raw = new byte[outputSize * blocksSize];
        int i = 0;
        while (data.length - i * blockSize > 0) {
            if (data.length - i * blockSize > blockSize) {
                cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
            } else {
                cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
            }
            i++;
        }
        return raw;
    }

    /**
     * 解密
     *
     * @param key  解密的密钥
     * @param data 待解密的数据
     * @return 解密后的数据
     * @throws Exception Exception
     */
    public static byte[] decrypt(Key key, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        // 编码前设定编码方式及密钥
        cipher.init(Cipher.DECRYPT_MODE, key);
        // int                   blockSize = cipher.getBlockSize();
        int blockSize = DEFAULT_BUFFER_SIZE + 11;
        ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
        int i = 0;
        while (data.length - i * blockSize > 0) {
            bout.write(cipher.doFinal(data, i * blockSize, blockSize));
            i++;
        }
        return bout.toByteArray();
    }

    ///////////////////////////////////////////////////////////////////////////
    // 获取密钥
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 密钥加载模式
     */
    public enum MODE {
        // PEM证书字符串
        PEM_STRING,
        // 十进制 - 模数、指数
        MODULUS_EXPONENT,
        // PEM证书文件
        PEM_FILE,
        // 密钥文件
        KEY_FILE
    }

    /**
     * 私钥类型
     */
    public static final String TYPE_PRIVATE = "0";
    /**
     * 公钥类型
     */
    public static final String TYPE_PUBLIC = "1";

    /**
     * @param mode   密钥加载模式
     * @param params 密钥信息
     *               第一位：密钥类型: 0 - 私钥, 1 - 公钥
     *               第二位：密钥字符串、密钥文件路径、模数
     *               第三位：指数
     * @return Key
     * @throws Exception Exception
     */
    public static Key getKey(MODE mode, String... params) throws Exception {
        if (null == params || params.length <= 1) {
            return null;
        }
        String type = "";
        String arg1 = "";
        String arg2 = "";
        if (params.length == 2) {
            type = params[0];
            arg1 = params[1];
        } else if (params.length == 3) {
            type = params[0];
            arg1 = params[1];
            arg2 = params[2];
        }

        switch (mode) {
            /* PEM证书字符串 */
            case PEM_STRING:
                if (TYPE_PRIVATE.equals(type)) {
                    return loadPrivateKey(arg1);
                } else {
                    return loadPublicKey(arg1);
                }

                /* 十进制 - 模数、指数 */
            case MODULUS_EXPONENT:
                if (TYPE_PRIVATE.equals(type)) {
                    return getPrivateKey(arg1, arg2);
                } else {
                    return getPublicKey(arg1, arg2);
                }

                /* PEM证书文件 */
            case PEM_FILE:
                if (TYPE_PRIVATE.equals(type)) {
                    return loadPrivateKey(readFile(arg1));
                } else {
                    return loadPublicKey(readFile(arg1));
                }

                /* 密钥文件 */
            case KEY_FILE:
                if (TYPE_PRIVATE.equals(type)) {
                    return readKeyPair(arg1).getPrivate();
                } else {
                    return readKeyPair(arg1).getPublic();
                }
            default:
                return null;
        }
    }

    /**
     * @param data data
     * @return 与php端数据交互签名方法
     * @throws Exception Exception
     */
    public static String phpEncrypt(String data) throws Exception {
        PrivateKey privateKey = loadPrivateKey(phpSignKey);
        Signature signature = Signature.getInstance("SHA1withRSA");
        signature.initSign(privateKey);
        signature.update(data.getBytes());
        byte[] bytes = signature.sign();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

}