package com.amlzq.android.util;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by amlzq on 2017/8/14.
 * <p>
 * 剪贴板工具箱
 * <p>
 * added in API level 11
 * https://developer.android.com/reference/android/content/ClipboardManager
 * Android提供的剪贴板框架，复制和粘贴不同类型的数据。数据可以是文本，图像，二进制流数据或其它复杂的数据类型。
 * https://www.jianshu.com/p/213d7062cdbe
 */

public class ClipboardUtil {

    /**
     * @hide
     */
    ClipboardUtil() {
    }

    /**
     * 将文本放入剪贴板
     * put text into Clipboard
     *
     * @param context 上下文
     * @param text    文本
     */
    public static void putText(final Context context, final String text) {
        Context appCxt = context.getApplicationContext();
        // 往ClipboardManager中可放的数据类型有三种:text URI Intent
        // 类型一:text
        ClipboardManager clipboard = (ClipboardManager) appCxt.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData cd = ClipData.newPlainText("Text", text);
        clipboard.setPrimaryClip(cd);

        // Example code
        // we can retrive this by using
        // 我们可以通过使用来回溯这个
        // clip.getDescription ();
    }

    /**
     * get text from Clipboard
     *
     * @param context 上下文
     * @return 剪切板文本
     */
    public static String getText(final Context context) {
        Context appCxt = context.getApplicationContext();
        ClipboardManager clipboard = (ClipboardManager) appCxt.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData.Item item = null;

        // 无数据时直接返回
        if (!clipboard.hasPrimaryClip()) {
            Logger.i("Clipboard not have data");
            return "";
        }

        // 如果是文本信息
        if (clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
            ClipData cd = clipboard.getPrimaryClip();
            item = cd.getItemAt(0);
            // 此处是TEXT文本信息
            if (item.getText() == null) {
                Logger.i("Clipboard not have text");
                return "";
            } else {
                return item.getText().toString();
            }
        }
        return "";
    }

}