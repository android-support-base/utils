package com.amlzq.android.crash;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.amlzq.android.ApplicationConfig;
import com.amlzq.android.content.ContextHolder;
import com.amlzq.android.util.AppUtil;
import com.amlzq.android.util.PermissionChecker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by amlzq on 2018/5/1.
 * <p>
 * 异常退出管理类
 */

public class CrashHandler implements UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private static final String FILE_NAME = "crash-";
    /**
     * Log文件的后缀
     */
//    private static final String FILE_NAME_SUFFIX = ".trace";
    private static final String FILE_NAME_SUFFIX = ".txt";
    /**
     * 系统默认的异常处理（默认情况下，系统会终止当前的异常程序）
     */
    private UncaughtExceptionHandler mDefaultCrashHandler;
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 是否是debug模式
     */
    private boolean mDebug;
    /**
     * 存储目录
     * 默认为: Android/data/packageName/crash
     */
    private String mStorageDir;
    /**
     * 时间
     */
    private DateFormat mDateFormat;

    // 构造方法私有，防止外部构造多个实例，即采用单例模式
    private CrashHandler() {
        mContext = ContextHolder.getContext();
        mDebug = ApplicationConfig.DEBUG;
        mStorageDir = "/Android/data/" + mContext.getPackageName() + "/crash";
    }

    public static CrashHandler getInstance() {
        return CrashHandlerInstance.instance;
    }

    private static class CrashHandlerInstance {
        static CrashHandler instance = new CrashHandler();
    }

    // 这里主要完成初始化工作
    public void init() {
        // 获取系统默认的异常处理器
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 将当前实例设为系统默认的异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault());
    }

    /**
     * @param debug      debug
     * @param storageDir storageDir
     * @hide
     */
    private void init(boolean debug, String storageDir) {
        // 获取系统默认的异常处理器
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 将当前实例设为系统默认的异常处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        mDebug = debug;
        mStorageDir = storageDir;
    }

    /**
     * 最关键的函数，当程序中有未被捕获的异常，系统将会自动调用#uncaughtException方法
     * thread为出现未捕获异常的线程，ex为未捕获的异常，有了这个ex，我们就可以得到异常信息。
     *
     * @param thread thread
     * @param ex     Throwable
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        // 打印出当前调用栈信息
        ex.printStackTrace();

        // 如果系统提供了默认的异常处理器，则交给系统去结束我们的程序，否则就由我们自己结束自己
        if (!handleException(ex) && mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            // 退出程序
            AppUtil.exit();
            // 杀掉当前进程
            android.os.Process.killProcess(android.os.Process.myPid());
            // 非正常退出
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param throwable throwable
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable throwable) {
        if (throwable == null) {
            return false;
        }
        try {
            // 导出异常信息到SD卡中
            dumpExceptionToSDCard(throwable);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 这里可以通过网络上传异常信息到服务器，便于开发人员分析日志从而解决bug
        uploadExceptionToServer();
        // 使用Toast来显示异常提示
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast toast = Toast.makeText(mContext, "app crash", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                Looper.loop();
            }
        }.start();
        return true;
    }

    /**
     * @param throwable throwable
     * @throws IOException IOException
     */
    private void dumpExceptionToSDCard(Throwable throwable) throws IOException {
        // 如果没有读写SD卡的权限，则不写入文件
        if (null != mContext && !PermissionChecker.getInstance().checkPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            return;
        }

        // 如果SD卡不存在或无法使用，则无法把异常信息写入SD卡
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (mDebug) {
                Log.w(TAG, "sdcard unmounted,skip dump exception");
                return;
            }
        }

        File dir = new File(mStorageDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        long timestamp = System.currentTimeMillis();
        String time = this.mDateFormat.format(new Date());
        String fileName = FILE_NAME + time + "-" + timestamp + FILE_NAME_SUFFIX;
        // 以当前时间创建log文件
        File file = new File(mStorageDir + File.separator + fileName);

        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            // 导出发生异常的时间
            pw.println(time);

            // 导出手机信息
            dumpPhoneInfo(pw);
            collectDeviceInfo(pw);

            pw.println();
            // 导出异常的调用栈信息
            throwable.printStackTrace(pw);

            pw.close();
        } catch (Exception e) {
            Log.e(TAG, "dump crash info failed");
        }
    }

    /**
     * @param pw PrintWriter
     * @throws NameNotFoundException NameNotFoundException
     */
    private void dumpPhoneInfo(PrintWriter pw) throws NameNotFoundException {
        // 应用的版本名称和版本号
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
        // 应用版本
        pw.print("App Version Name: ");
        pw.println(pi.versionName);
        // 应用版本号
        pw.print("App Version Code: ");
        pw.println(pi.versionCode);

        // android版本
        pw.print("Android OS Version Name: ");
        pw.println(Build.VERSION.RELEASE);
        // android版本号
        pw.print("Android OS Version Code: ");
        pw.println(Build.VERSION.SDK_INT);

        // 手机制造商
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);

        // 手机型号
        pw.print("Model: ");
        pw.println(Build.MODEL);
    }

    /**
     * 收集设备参数信息
     *
     * @param pw PrintWriter
     */
    public void collectDeviceInfo(PrintWriter pw) {
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                pw.print(field.getName() + ": ");
                pw.println(field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occurred when collect crash info", e);
            }
        }
    }

    /**
     * 上传log到服务端
     */
    private void uploadExceptionToServer() {
        Log.i(TAG, "upload exception to server is end!!!");
    }

}
