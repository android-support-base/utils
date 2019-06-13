package com.amlzq.android.util;

/**
 * Created by amlzq on 2018/6/26.
 * <p>
 * 软件平台标识和参数名
 */

@SuppressWarnings("unused")
public class SWPlatform {

    class Wechat {
        String NAME = "Wechat";
        String APP_ID = "com.tencent.mm.opensdk.AppId";
        String APP_SECRET = "com.tencent.mm.opensdk.AppSecret";
    }

    class QQ {
        String NAME = "QQ";
        String APP_ID = "com.tencent.open.AppId";
        String APP_KEY = "com.tencent.open.AppKey";
    }

    class SinaWeibo {
        String NAME = "Sina_Weibo";
    }

    class Facebook {
        String NAME = "Facebook";
    }

    class Google {
        String NAME = "Google";
    }

    class Umeng {
        String NAME = "Umeng";
        String APPKEY = "UMENG_APPKEY";
        String CHANNEL = "UMENG_CHANNEL";
        String MESSAGE_SECRET = "UMENG_MESSAGE_SECRET";
    }

    class Xiaomi {
        String NAME = "Xiaomi";
        String APP_ID = "XIAOMI_ID";
        String APP_KEY = "XIAOMI_KEY";
    }

    class Meizu {
        String NAME = "Meizu";
        String APP_ID = "com.meizu.AppId";
        String APP_KEY = "com.meizu.AppKey";
    }

}