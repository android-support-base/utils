package com.amlzq.android.content.pm;

import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageStats;
import android.os.RemoteException;

/**
 * Created by amlzq on 2018/9/11.
 * PackageStatsObserver
 * AIDL文件形成的Bindler机制服务类
 */

public abstract class PackageStatsObserver extends IPackageStatsObserver.Stub {

    public long cacheSize; // 缓存大小
    public long dataSize; // 数据大小
    public long codeSize; // 应用程序大小
    public long totalSize;

    /**
     * 回调函数
     *
     * @param pStats    返回数据封装在PackageStats对象中
     * @param succeeded 代表回调成功
     * @throws RemoteException RemoteException
     */
    @Override
    public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {
        cacheSize = pStats.cacheSize; // 缓存大小
        dataSize = pStats.dataSize; // 数据大小
        codeSize = pStats.codeSize; // 应用程序大小
        totalSize = cacheSize + dataSize + codeSize;
    }

}