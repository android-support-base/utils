package com.amlzq.android.util;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * ShareContentType
 * Support Share Content Types.
 *
 * @author baishixian
 * @author https://github.com/baishixian/Share2
 * @date 2018/3/29 11:41
 */

@StringDef({ShareContentType.TEXT, ShareContentType.IMAGE,
        ShareContentType.AUDIO, ShareContentType.VIDEO, ShareContentType.FILE})
@Retention(RetentionPolicy.SOURCE)
public @interface ShareContentType {

    /**
     * Share Text
     */
    final String TEXT = "text/plain";

    /**
     * Share Image
     */
    final String IMAGE = "image/*";

    /**
     * Share Audio
     */
    final String AUDIO = "audio/*";

    /**
     * Share Video
     */
    final String VIDEO = "video/*";

    /**
     * Share File
     */
    final String FILE = "*/*";

}