package com.amlzq.android.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by amlzq on 2018/5/29.
 * <p>
 * 基于android.util.Log增加功能
 * https://developer.android.com/reference/android/util/Log
 * <p>
 * https://developer.android.com/studio/debug/am-logcat?hl=zh-cn
 * <p>
 * format"date time PID-TID/package priority/tag: message"
 * <p>
 * 已经弃用，改为com.amlzq.android:log
 */
@Deprecated
public final class Logger {

    /**
     * @hide
     */
    private Logger() {
    }

    /**
     * Not use for the print method.
     * 不打印日志
     */
    public static final int NOT_PRINT = 8;

    /**
     * tag Used to identify the source of a log message.  It usually identifies
     * the class or activity where the log call occurs.
     */
    public static String TAG = Logger.class.getName();

    /**
     * 请记住，无论日志级别设置如何，logcat 监视器都会继续收集所有消息。此设置仅决定 logcat 监视器的显示内容。
     * Verbose - 显示所有日志消息（默认值）。
     */
    public static int LEVEL = android.util.Log.VERBOSE;

    /**
     * @return 返回日志级别
     */
    public static String getLevel() {
        switch (LEVEL) {
            case android.util.Log.VERBOSE:
                return "VERBOSE";
            case android.util.Log.DEBUG:
                return "DEBUG";
            case android.util.Log.INFO:
                return "INFO";
            case android.util.Log.WARN:
                return "WARN";
            case android.util.Log.ERROR:
                return "ERROR";
            case android.util.Log.ASSERT:
                return "ASSERT";
            case NOT_PRINT:
                return "NOT_PRINT";
            default:
                return "VERBOSE";
        }
    }

    // General log

    public static void v(Object msg) {
        println(android.util.Log.VERBOSE, TAG, "" + msg, null);
    }

    public static void d(Object msg) {
        println(android.util.Log.DEBUG, TAG, "" + msg, null);
    }

    public static void i(Object msg) {
        println(android.util.Log.INFO, TAG, "" + msg, null);
    }

    public static void w(Object msg) {
        println(android.util.Log.WARN, TAG, "" + msg, null);
    }

    public static void e(Object msg) {
        println(android.util.Log.ERROR, TAG, "" + msg, null);
    }

    // format string log

    public static void d(String msg, Object... args) {
        println(android.util.Log.DEBUG, TAG, formatMessage(msg, args), null);
    }

    public static void i(String msg, Object... args) {
        println(android.util.Log.INFO, TAG, formatMessage(msg, args), null);
    }

    public static void w(String msg, Object... args) {
        println(android.util.Log.WARN, TAG, formatMessage(msg, args), null);
    }

    public static void e(String msg, Object... args) {
        println(android.util.Log.ERROR, TAG, formatMessage(msg, args), null);
    }

    // Throwable log

    public static void w(Throwable tr) {
        println(android.util.Log.WARN, TAG, null, tr);
    }

    /**
     * Default tag
     *
     * @param msg The actual message to be logged.
     * @param tr  If an exception was thrown, this can be sent along for the logging facilities
     *            to extract and print useful information.
     */
    public static void e(Object msg, Throwable tr) {
        println(android.util.Log.ERROR, TAG, "" + msg, tr);
    }

    public static void e(Throwable tr) {
        println(android.util.Log.ERROR, TAG, null, tr);
    }

    /**
     * Prints a message at ASSERT priority.
     *
     * @param msg The actual message to be logged.
     * @param tr  If an exception was thrown, this can be sent along for the logging facilities
     *            to extract and print useful information.
     */
    public static void wtf(Object msg, Throwable tr) {
        println(android.util.Log.ASSERT, TAG, "" + msg, tr);
    }

    /**
     * Prints data out to the console using Android's native log mechanism.
     *
     * @param priority Log level of the data being logged.  Verbose, Error, etc.
     * @param tag      Tag for for the log data.  Can be used to organize log statements.
     * @param msg      The actual message to be logged. The actual message to be logged.
     * @param tr       If an exception was thrown, this can be sent along for the logging facilities
     *                 to extract and print useful information.
     */
    public static void println(int priority, String tag, String msg, Throwable tr) {
        if (priority < LEVEL) return;
        // There actually are log methods that don't take a msg parameter.  For now,
        // if that's the case, just convert null to the empty string and move on.
        String useMsg;
        if (TextUtils.isEmpty(msg)) {
            useMsg = "";
        } else {
            useMsg = msg + "\n" + getLocation() + "\n";
        }

        // If an exeption was provided, convert that exception to a usable string and attach
        // it to the end of the msg method.
        if (tr != null) {
            useMsg += Log.getStackTraceString(tr);
        }

        // This is functionally identical to Log.x(tag, useMsg);
        // For instance, if priority were Log.VERBOSE, this would be the same as Log.v(tag, useMsg)
        Log.println(priority, tag, useMsg);
    }

    private static String getLocation() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

        if (stackTraceElements == null) return "";

        for (StackTraceElement targetStack : stackTraceElements) {
            if (targetStack.isNativeMethod())
                continue;

            if (targetStack.getClassName().equals(Thread.class.getName()))
                continue;

            if (targetStack.getClassName().equals(Logger.class.getName()))
                continue;

            return "at " + Thread.currentThread().getName()
                    + " thread " + targetStack.getClassName() + "." + targetStack.getMethodName()
                    + "(" + targetStack.getFileName() + ":" + targetStack.getLineNumber() + ")";
        }
        return "";
    }

    /**
     * Formats a log message with optional arguments.
     */
    private static String formatMessage(String message, Object[] args) {
        return args == null || args.length == 0 ? message : String.format(message, args);
    }

}