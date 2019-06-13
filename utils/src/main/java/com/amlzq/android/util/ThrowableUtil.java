package com.amlzq.android.util;

import android.accounts.NetworkErrorException;

import com.amlzq.android.content.ContextHolder;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

public class ThrowableUtil {

    /**
     * 包装异常消息
     *
     * @param tr
     * @return
     */
    public static String wrapperMessage(Throwable tr) {
        String message = "";
        if (tr instanceof SocketTimeoutException) {
//            message = ContextHolder.getString(R.string.time_out);
            message = ContextHolder.getString(R.string.network_exception);
        } else if (tr instanceof NetworkErrorException) {
            message = ContextHolder.getString(R.string.network_exception);
        } else if (tr instanceof UnknownServiceException) {
//            message = ContextHolder.getString(R.string.server_exception);
            message = ContextHolder.getString(R.string.network_exception);
        } else if (tr instanceof UnknownHostException) {
//            message = ContextHolder.getString(R.string.unknown_host_exception);
            message = ContextHolder.getString(R.string.network_exception);
        } else {
            message = tr.getMessage();
        }
        return message;
    }

}
