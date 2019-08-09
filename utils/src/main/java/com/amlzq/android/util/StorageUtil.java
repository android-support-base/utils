package com.amlzq.android.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.support.annotation.RequiresApi;

import com.amlzq.android.content.ContextHolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by amlzq on 2017/8/14.
 * <p>
 * Data and file storage util 数据和文件存储工具
 * <p>
 * Shared preferences 共享首选项
 * Internal file storage 内部存储
 * External file storage 外部存储
 * Databases SQLite 数据库
 * ContentProvider 内容提供程序
 * Network 网络连接
 * <p>
 * https://developer.android.com/guide/topics/data/data-storage.html
 * https://github.com/changjiashuai/Storage
 */

public class StorageUtil {

    /**
     * @hide
     */
    private StorageUtil() {
        throw new AssertionError();
    }

    /**
     * 使用共享首选项，在键值对中存储私有原始数据。
     * https://developer.android.com/guide/topics/data/data-storage.html#db
     */

    /**
     * @see com.amlzq.android.io.PrefsUtil
     */

    /**
     * 使用内部存储，在设备内存中存储私有数据。
     * https://developer.android.com/guide/topics/data/data-storage#filesInternal
     * 保存到内部存储的文件是应用的私有文件，其他应用和用户不能访问这些文件。 当用户卸载您的应用时，这些文件也会被移除。
     */

    /**
     * @return 内部存储的应用目录列表
     */
    public String[] getInternalFileList() {
        return ContextHolder.getContext().fileList();
    }

    // 创建私有文件并写入到内部存储

    public static void writeFile(String fileName, String data) {
        FileOutputStream fos = null;
        try {
            fos = ContextHolder.getContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFile(String fileName, String data) {
        FileInputStream fis = null;
        try {
            fis = ContextHolder.getContext().openFileInput(fileName);
            fis.read(data.getBytes());
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return 内部存储的应用缓存目录
     * @path /data/data/applicationId/cache
     */
    public static File getInternalCacheDir() {
        return ContextHolder.getContext().getCacheDir();
    }

    /**
     * @return 内部存储的应用文件目录
     * @path /data/data/applicationId/files
     */
    public File getInternalFilesDir() {
        return ContextHolder.getContext().getFilesDir();
    }

    /**
     * 使用外部存储，在共享的外部存储中存储公共数据。
     * https://developer.android.com/guide/topics/data/data-storage.html#filesExternal
     * - 在外部存储中保存可与其他应用共享的文件
     * ```
     * Environment.STANDARD_DIRECTORIES 标准目录列表
     * DIRECTORY_MUSIC,
     * DIRECTORY_PODCASTS,
     * DIRECTORY_RINGTONES,
     * DIRECTORY_ALARMS,
     * DIRECTORY_NOTIFICATIONS,
     * DIRECTORY_PICTURES,
     * DIRECTORY_MOVIES,
     * DIRECTORY_DOWNLOADS,
     * DIRECTORY_DCIM,
     * DIRECTORY_DOCUMENTS
     * ```
     */

    /**
     * Checks if external storage is available for read and write
     * 检查外部存储器是否可用于读写
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if external storage is available to at least read
     * 检查外部存储是否仅支持读取
     */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * @return 外部存储的可用字节数
     */
    public static long getExternalStorageAvailableBytes() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long availableBytes; // 可用字节数
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableBytes = stat.getBlockSizeLong() * stat.getAvailableBlocksLong();
        } else {
            availableBytes = (long) stat.getBlockSize() * (long) stat.getAvailableBlocks();
        }
        return availableBytes;
    }

    /**
     * @return 标准目录，用于放置应该位于用户常规音乐列表中的任何音频文件。
     * @path
     */
    public static File getExternalPublicMusicDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
    }

    /**
     * @return 标准目录，用于放置应该在用户可以选择的播客列表中的任何音频文件（而不是常规音乐）。
     * @path
     */
    public static File getExternalPublicPodcastsDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS);
    }

    /**
     * @return 标准目录，用于放置应该在用户可以选择的铃声列表中的任何音频文件（而不是常规音乐）。
     * @path
     */
    public static File getExternalPublicRingtonesDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES);
    }

    /**
     * @return 标准目录，用于放置应该在用户可以选择的警报列表中的任何音频文件（而不是常规音乐）。
     * @path
     */
    public static File getExternalPublicAlarmsDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);
    }

    /**
     * @return 标准目录，用于放置应该位于用户可以选择的通知列表中的任何音频文件（而不是常规音乐）。
     * @path
     */
    public static File getExternalPublicNotificationsDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS);
    }

    /**
     * @return 标准目录，用于放置用户可用的图片。
     * @path
     */
    public static File getExternalPublicPicturesDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    }

    /**
     * @return 放置可用电影的标准目录
     * @path
     */
    public static File getExternalPublicMoviesDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
    }

    /**
     * @return 放置已下载文件的标准目录
     * @path
     */
    public static File getExternalPublicDownloadsDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    }

    /**
     * Digital Camera Images 数码相机IMAGE
     * photographs and screenshot 照片和截图
     *
     * @return 设备启动相机时，传统的图片和视频位置。
     * @path
     */
    public static File getExternalPublicDCIMDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    }

    /**
     * @return 相机摄影和拍照的目录
     */
    public static File getExternalPublicCameraDir() {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + File.separator + "Camera");
    }

    /**
     * @return 用于放置用户创建的文档的标准目录。
     * @path
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static File getExternalPublicDocumentsDir() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
    }

    /**
     * 在外部存储中保存应用私有文件
     * 从 Android 4.4 开始，读取或写入应用私有目录中的文件不再需要 READ_EXTERNAL_STORAGE 或 WRITE_EXTERNAL_STORAGE 权限。
     * 当用户卸载您的应用时，此目录及其内容将被删除。此外，系统媒体扫描程序不会读取这些目录中的文件，因此不能从 MediaStore 内容提供程序访问这些文件。
     */

    /**
     * 保存缓存文件， 如果用户卸载您的应用，这些文件也会被自动删除。
     *
     * @return
     * @path
     */
    public static File getExternalPrivateCacheDir() {
        return ContextHolder.getContext().getExternalCacheDir();
    }

    public static File getExternalPrivateFilesDir() {
        return ContextHolder.getContext().getExternalFilesDir(null);
    }

    public static File getExternalPrivateDir() {
        return new File("/Android/data/" + ContextHolder.getContext().getPackageName() + File.separator);
    }

    /**
     * 使用数据库，在私有数据库中存储结构化数据。
     * https://developer.android.com/guide/topics/data/data-storage.html#db
     */

    /**
     * @see android.database.sqlite.SQLiteOpenHelper
     */

    /**
     * 使用网络连接，在网络中使用您自己的网络服务器存储数据。
     * https://developer.android.com/guide/topics/data/data-storage#netw
     */

    /**
     * @see java.net.HttpURLConnection
     */

}
