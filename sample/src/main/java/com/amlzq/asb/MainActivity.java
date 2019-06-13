package com.amlzq.asb;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amlzq.android.security.Hmac;
import com.amlzq.android.security.MD5;
import com.amlzq.android.util.DeviceUtil;
import com.amlzq.android.util.KeyboardUtil;
import com.amlzq.android.util.RegexUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {
    static final String TAG = "MainActivity";

    EditText mETInput;
    TextView mTVInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mETInput = findViewById(R.id.et_input);
        mTVInfo = findViewById(R.id.tv_info);
        mTVInfo.setText(DeviceUtil.getBuildModel());
//        mTVInfo.setText(
//                AppUtil.getSignatureDigest()
//                        + "\n" + AppUtil.getSignatureBySHA1()
//                        + "\n" + AppUtil.getSignatureBySHA256()
//                        + "\n" + PackageUtil.getCertificateFingerprint(getPackageName(), MD5.TYPE.MD5.getValue())
//        );

//        PackageManager pm = getPackageManager();

//        PackageInfoSub pi = PackageUtil.getInstalledPackages().get(10);

//        mTVInfo.append("\n" + "" + NetworkUtil.getIPAddress(NetworkUtil.IPv4)
//                + "\n" + NetworkUtil.getIPAddress(NetworkUtil.IPv6));

//        mTVInfo.append("\n" + "installed packages size:" + PackageUtil.getInstalledPackages().size());

//        mTVInfo.append("\n" + "installed packages size:" + Arrays.toString(new List[]{PackageUtil.getPackageSize(getPackageName())}));

//        String key = "9155FE2DB82D1DF74"; // Demo
        String key = "27F84D38E3517A417";

//        String message = "{\"gameid\":\"427\",\"extend\":\"CP_2208020181114\",\"total_amount\":\"0.01\",\"props_name\":\"\\u4e5d\\u9634\\u771f\\u7ecf\"}"; // demo
//        String message = "{\"gameid\":\"485\",\"extend\":\"CP_THOD_EzuM5Yep3MX4oAt3\",\"total_amount\":\"6.00\",\"props_name\":\"\\u5b9d\\u77f3\\u0036\\u0030\\u4e2a\"}";
        String message = "{\"gameid\":\"485\",\"extend\":\"CP_THOD_EzuM5Yep3MX4oAt3\",\"total_amount\":\"6.00\",\"props_name\":\"\\u5b9d\\u77f360\\u4e2a\"}";

        printMessage(key, message);

        JSONObject jo = new JSONObject();
        try {
            jo.put("gameid", "485");
            jo.put("extend", "CP_THOD_EzuM5Yep3MX4oAt3");
            jo.put("total_amount", "6.00");
            jo.put("props_name", "\\u5b9d\\u77f3\\u0036\\u0030\\u4e2a");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        message = jo.toString();
        printMessage(key, message);

//        mTVInfo.append("\n" + "时间戳（毫秒）" + System.currentTimeMillis() + "");
//        mTVInfo.append("\n" + "时间戳（秒）" + DateUtil.getSecondTimestamp());

//        Log.i(TAG, mTVInfo.getText().toString());

//        for (PackageInfo pi : PackageUtil.getInstalledPackages()) {
//            if ((ApplicationInfo.FLAG_SYSTEM & pi.applicationInfo.flags) == 0) {
//                mTVInfo.append("\n" + "packageName:" + pi.packageName
//                        + "\n" + "label:" + pm.getApplicationLabel(pi.applicationInfo).toString()
//                        + "\n" + "system:" + String.valueOf(true)
//                        + "\n" + "firstInstallTime:" + pi.firstInstallTime
//                        + "\n" + "lastUpdateTime:" + pi.lastUpdateTime
//                );
//            }
//
//            Logger.d("packageName:%s", pi.packageName);
//            Logger.d("label:%s", pm.getApplicationLabel(pi.applicationInfo).toString());
//            Logger.d("system:" + String.valueOf(ApplicationInfo.FLAG_SYSTEM == pi.applicationInfo.flags));
//            Logger.d("firstInstallTime:%s", pi.firstInstallTime);
//            Logger.d("lastUpdateTime:%s", pi.lastUpdateTime);
//
//            if ((pi.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
//                // 非系统应用
//            } else {
//                // 系统应用　　　　　　　　
//            }
//            // 获取该应用安装包的Intent，用于启动该应用
//            pm.getLaunchIntentForPackage(pi.packageName);
//            pm.getApplicationIcon(pi.applicationInfo);
//
//        }

    }

    private void printMessage(String key, String message) {
        mTVInfo.append("\n" + message);
        mTVInfo.append("\n" + key);
        Log.i(TAG, "message: " + message + "," + message.length());
        Log.i(TAG, "key:" + key + "," + key.length());
        String MD5_ = MD5.encode(message);
        String MD5_BYTE = MD5.encode(message.getBytes());
        String HmacMD5 = Hmac.encrypt(Hmac.Algorithm.HmacMD5, message, key);
        String HmacSHA1 = Hmac.encrypt(Hmac.Algorithm.HmacSHA1, message, key);
        String HmacSHA256 = Hmac.encrypt(Hmac.Algorithm.HmacSHA256, message, key);
        String HmacSHA512 = Hmac.encrypt(Hmac.Algorithm.HmacSHA512, message, key);
        Log.i(TAG, MD5_ + "," + MD5_.length());
        Log.i(TAG, MD5_BYTE + "," + MD5_BYTE.length());
        Log.i(TAG, HmacMD5 + "," + HmacMD5.length());
        Log.i(TAG, HmacSHA1 + "," + HmacSHA1.length());
        Log.i(TAG, HmacSHA256 + "," + HmacSHA256.length());
        Log.i(TAG, HmacSHA512 + "," + HmacSHA512.length());
    }

    public void onAction(View view) {
//        NotificationUtil.openManagement(this, ApplicationRequestCode.ACTION_NOTIFICATION_MANAGER);
//        PermissionUtil.openManagement(this, ApplicationRequestCode.ACTION_PERMISSION_MANAGER);

//        SystemUtil.launchSettingsApp(this, 10011);
        KeyboardUtil.hideSoftInput(this);

        String text = mETInput.getText().toString();
        boolean result = RegexUtil.isMobile(text, RegexUtil.REGEX_MOBILE_86);
        Log.e(TAG, String.valueOf(result));
    }

}