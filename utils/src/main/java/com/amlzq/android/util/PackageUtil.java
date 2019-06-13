package com.amlzq.android.util;

import android.app.usage.StorageStats;
import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.content.pm.PermissionInfo;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.storage.StorageManager;

import com.amlzq.android.content.ContextHolder;
import com.amlzq.android.content.pm.PackageStatsObserver;
import com.amlzq.android.security.MD5;
import com.amlzq.android.security.SHA;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amlzq on 2018/6/7.
 * <p>
 * Package util
 */

public class PackageUtil {

    /**
     * @hide
     */
    PackageUtil() {
    }

    /**
     * @return 返回包管理员
     */
    public static PackageManager getPackageManager() {
        return ContextHolder.getContext().getPackageManager();
    }

    // =============================================================================================
    // TODO: 常见应用包名
    // todo: https://blog.csdn.net/bzlj2912009596/article/details/80589841
    // =============================================================================================

    public static final String GOOGLE_PLAY = "com.android.vending"; // Google Play
    public static final String YING_YONG_BAO = "com.tencent.android.qqdownloader"; // 应用宝
    public static final String MI = "com.xiaomi.market"; // 小米应用商店

    // =============================================================================================
    // TODO: 包属性
    // =============================================================================================

    /**
     * java.lang.RuntimeException: Package manager has died
     *
     * @param packageName Package name
     * @param flags       flags
     * @return PackageInfoSub
     * @throws PackageManager.NameNotFoundException
     */
    public static PackageInfo getPackageInfo(final String packageName, int flags) throws PackageManager.NameNotFoundException {
        PackageManager manager = getPackageManager();
        return manager.getPackageInfo(packageName, flags);
    }

    /**
     * @param archiveFile 归档(APK)文件
     * @return 返回归档文件的PackageInfo
     * @throws PackageManager.NameNotFoundException
     */
    public static PackageInfo getPackageArchiveInfo(final File archiveFile) {
        PackageManager manager = getPackageManager();
        return manager.getPackageArchiveInfo(archiveFile.getAbsolutePath(), PackageManager.GET_ACTIVITIES);
    }

    /**
     * @param archiveFile APK文件
     * @return 通过APK文件获取包名
     */
    public static String getPackageName(File archiveFile) {
        String packageName = "";
        PackageInfo archiveInfo = getPackageArchiveInfo(archiveFile);
        if (archiveInfo != null && archiveInfo.packageName != null) {
            packageName = archiveInfo.packageName;

        }
        return packageName;
    }

    /**
     * @param packageName Package name
     * @return Version code
     */
    public static int getVersionCode(final String packageName) {
        try {
            return getPackageInfo(packageName, PackageManager.GET_CONFIGURATIONS).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * String versionName = "1.0.0";
     *
     * @param packageName Package name
     * @return Version name
     */
    public static String getVersionName(final String packageName) {
        try {
            return getPackageInfo(packageName, PackageManager.GET_CONFIGURATIONS).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param packageName Package name
     * @return permissions
     */
    public static PermissionInfo[] getPermissions(final String packageName) {
        try {
            return getPackageInfo(packageName, PackageManager.GET_CONFIGURATIONS).permissions;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param packageName Package name
     * @return Signature array
     */
    public static Signature[] getSignatures(String packageName) {
        try {
            return getPackageInfo(packageName, PackageManager.GET_SIGNATURES).signatures;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param packageName Package name
     * @return MD5
     */
    public static String getSignatureDigest(final String packageName) {
        try {
            Signature signature = getSignatures(packageName)[0];
            return MD5.encode(signature.toByteArray());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param packageName Package name
     * @return SHA1
     */
    public static String getSignatureBySHA1(final String packageName) {
        try {
            Signature signature = getSignatures(packageName)[0];
            String sha1 = SHA.encode(SHA.TYPE.SHA1, signature.toByteArray());
            return sha1;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param packageName Package name
     * @return SHA1
     */
    public static String getSignatureBySHA256(final String packageName) {
        try {
            Signature signature = getSignatures(packageName)[0];
            String sha256 = SHA.encode(SHA.TYPE.SHA256, signature.toByteArray());
            return sha256;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param packageName package name
     * @param algorithm   algorithm
     * @return X509 certificate fingerprint
     */
    public static String getCertificateFingerprint(final String packageName, final String algorithm) {
        Signature signature = getSignatures(packageName)[0];
        byte[] cert = signature.toByteArray();

        InputStream inputStream = new ByteArrayInputStream(cert);
        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X509");
        } catch (CertificateException e) {
            e.printStackTrace();
        }

        X509Certificate xc = null;
        try {
            xc = (X509Certificate) cf.generateCertificate(inputStream);
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        String hexString = null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] publicKey = md.digest(xc.getEncoded());
            hexString = FormatUtil.toHexString(publicKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateEncodingException e) {
            e.printStackTrace();
        }
        return hexString;
    }

    /**
     * after invoking, PkgSizeObserver.onGetStatsCompleted() will be called as callback function. <br>
     * About the third parameter ‘Process.myUid() / 100000’，please check:
     * <android_source>/frameworks/base/core/java/android/content/pm/PackageManager.java:
     * getPackageSizeInfo(packageName, UserHandle.myUserId(), observer);
     */
    public static List<Long> getPackageSize(final String packageName) {
        List<Long> longs = new ArrayList<Long>();
        Context cxt = ContextHolder.getContext();
        PackageManager pm = getPackageManager();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // requires the android.Manifest.permission#PACKAGE_USAGE_STATS permission
            StorageStatsManager storageStatsManager = (StorageStatsManager) cxt.getSystemService(Context.STORAGE_STATS_SERVICE);
            UserHandle userHandle = Process.myUserHandle();
            try {
                StorageStats storageStats = storageStatsManager.queryStatsForPackage(StorageManager.UUID_DEFAULT, packageName, userHandle);
                longs.add(storageStats.getCacheBytes());
                longs.add(storageStats.getDataBytes());
                longs.add(storageStats.getAppBytes());
                longs.add(storageStats.getCacheBytes() + storageStats.getDataBytes() + storageStats.getAppBytes());
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // 通过反射机制得到PackageManager类的隐藏函数getPackageSizeInfo
            // Method getPackageSizeInfo = pm.getClass().getDeclaredMethod("getPackageSizeInfo", String.class, int.class, IPackageStatsObserver.class);
            // 调用该函数，并且给其分配参数 ，待调用流程完成后会回调Observer类的函数
            // getPackageSizeInfo.invoke(pm, packageName, observer);
            try {
                Method getPackageSizeInfo = pm.getClass().getDeclaredMethod("getPackageSizeInfo", String.class, IPackageStatsObserver.class);
                getPackageSizeInfo.invoke(pm, packageName, android.os.Process.myUid() / 100000, new PackageStatsObserver() {

                    @Override
                    public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {
                        super.onGetStatsCompleted(pStats, succeeded);
                        longs.add(cacheSize);
                        longs.add(dataSize);
                        longs.add(codeSize);
                        longs.add(totalSize);
                    }
                });
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return longs;
    }

}