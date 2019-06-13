package com.amlzq.android.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by amlzq on 2018/5/1.
 * <p>
 * 运行时权限检查工具
 * 在Application中调用 PermissionChecker.init(...)方法
 */

public class PermissionChecker {

    /**
     * @hide
     */
    private PermissionChecker() {
    }

    public static PermissionChecker getInstance() {
        return PermissionCheckInstance.instance;
    }

    private static class PermissionCheckInstance {
        static PermissionChecker instance = new PermissionChecker();
    }

    /**
     * android.permission-group.CONTACTS
     * 联系簿
     * permission:android.permission.WRITE_CONTACTS
     * permission:android.permission.GET_ACCOUNTS
     * permission:android.permission.READ_CONTACTS
     */
    public static final int REQUEST_CODE_CONTACTS = 0xAAA1;
    /**
     * android.permission-group.PHONE
     * 电话
     * permission:android.permission.READ_CALL_LOG
     * permission:android.permission.ANSWER_PHONE_CALLS
     * permission:android.permission.READ_PHONE_NUMBERS
     * permission:android.permission.READ_PHONE_STATE
     * permission:android.permission.CALL_PHONE
     * permission:android.permission.WRITE_CALL_LOG
     * permission:android.permission.USE_SIP
     * permission:android.permission.PROCESS_OUTGOING_CALLS
     * permission:com.android.voicemail.permission.ADD_VOICEMAIL
     */
    public static final int REQUEST_CODE_PHONE = 0xAAA2;
    /**
     * android.permission-group.CALENDAR
     * 日历
     * permission:android.permission.READ_CALENDAR
     * permission:android.permission.WRITE_CALENDAR
     */
    public static final int REQUEST_CODE_CALENDAR = 0xAAA3;
    /**
     * android.permission-group.CAMERA
     * 相机
     * permission:android.permission.CAMERA
     */
    public static final int REQUEST_CODE_CAMERA = 0xAAA4;
    /**
     * android.permission-group.SENSORS
     * 传感器
     * permission:android.permission.BODY_SENSORS
     */
    public static final int REQUEST_CODE_SENSORS = 0xAAA5;
    /**
     * android.permission-group.LOCATION
     * 位置
     * permission:android.permission.ACCESS_FINE_LOCATION
     * permission:com.google.android.gms.permission.CAR_SPEED
     * permission:android.permission.ACCESS_COARSE_LOCATION
     */
    public static final int REQUEST_CODE_LOCATION = 0xAAA6;
    /**
     * android.permission-group.STORAGE
     * 存储
     * permission:android.permission.READ_EXTERNAL_STORAGE
     * permission:android.permission.WRITE_EXTERNAL_STORAGE
     */
    public static final int REQUEST_CODE_STORAGE = 0xAAA7;
    /**
     * android.permission-group.MICROPHONE
     * 麦克风
     * permission:android.permission.RECORD_AUDIO
     */
    public static final int REQUEST_CODE_MICROPHONE = 0xAAA8;
    /**
     * android.permission-group.SMS
     * 短信
     * permission:android.permission.READ_SMS
     * permission:android.permission.RECEIVE_WAP_PUSH
     * permission:android.permission.RECEIVE_MMS
     * permission:android.permission.RECEIVE_SMS
     * permission:android.permission.SEND_SMS
     * permission:android.permission.READ_CELL_BROADCASTS
     */
    public static final int REQUEST_CODE_SMS = 0xAAA9;

    /**
     * 申请所有授权
     */
    public static final int REQUEST_CODE_ALL = 0xAAAA;

    /**
     * 询问权限
     *
     * @param activity    activity
     * @param permissions 权限列表
     *                    Manifest.permission.WRITE_EXTERNAL_STORAGE
     *                    Manifest.permission.CALL_PHONE
     *                    Manifest.permission.READ_SMS
     *                    Manifest.permission.RECEIVE_SMS
     * @param requestCode 请求码
     */
    @TargetApi(Build.VERSION_CODES.M)
    public void askForPermissions(Activity activity, List<String> permissions, int requestCode) {
        // 如果不是android6.0以上的系统，则不需要检查是否已经获取授权
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        List<String> permissionTemp = new ArrayList<>();
        permissionTemp.addAll(permissions);
        for (String permission : permissions) {
            // PackageManager.PERMISSION_GRANTED    授予权限
            // PackageManager.PERMISSION_DENIED     没有权限
            // 如果已经授予该权限，则从请求授予列表中去除
            Logger.d("permission" + permission);
            if (selfPermissionGranted(activity, permission)) {
                permissionTemp.remove(permission);
            }
        }
        if (hasAlwaysDeniedPermission(activity, permissionTemp)) {
            showAskDialog(activity);
        } else {
            Logger.d("permissionTemp:" + permissionTemp.size());
            if (permissionTemp.size() > 0) {
                activity.requestPermissions(permissionTemp.toArray(new String[permissionTemp.size()]), requestCode);
            }
        }
    }

    public void askForPermissions(Activity activity, String[] permissions, int requestCode) {
        if (null == permissions || permissions.length <= 0) {
            return;
        }
        System.out.println("askForPermissions");
        List<String> list = new ArrayList<>();
        Collections.addAll(list, permissions);
        askForPermissions(activity, list, requestCode);
    }

    /**
     * 校验权限
     *
     * @param context    context
     * @param permission 需要校验的权限
     * @return 是否授予该权限
     * true - 授予
     * false - 还未授予
     */
    public boolean checkPermission(Context context, String permission) {
        // 如果不是android6.0以上的系统，则不需要检查是否已经获取授权
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        int verify = ContextCompat.checkSelfPermission(context, permission);
        // PackageManager.PERMISSION_GRANTED    授予权限
        // PackageManager.PERMISSION_DENIED     没有权限
        return verify == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 请求授权后的回调类
     *
     * @param activity     activity
     * @param requestCode  requestCode
     * @param permissions  权限列表
     * @param grantResults 授权结果
     */
    public void onRequestPermissionsResult(final Activity activity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_STORAGE || requestCode == REQUEST_CODE_PHONE || requestCode == REQUEST_CODE_SMS || requestCode == REQUEST_CODE_ALL) {
            if (verifyPermissions(grantResults)) {
                // 获取权限后，做初始化操作，例如：创建ROOT目录，读取联系人到DATA中等。

            }
        }
        Logger.e("permission", "onRequestPermissionsResult");
        Logger.e("requestCode", requestCode + "");
        if (requestCode == REQUEST_CODE_STORAGE || requestCode == REQUEST_CODE_ALL) {
            needAskAgainForPermissions(activity, permissions);
        }
    }

    /**
     * 是否需要对没授权，且被拒绝过一次AND不再提醒的重要权限进行二次申请
     *
     * @param activity    activity
     * @param permissions 权限列表
     * @return 是否需要
     * true - 需要再次申请
     * false - 不需要再次申请
     */
    private boolean needAskAgainForPermissions(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            switch (permission) {
                // 与APP稳定性、体验等相关的重要的运行时权限，进行提示
                case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                    // 是否可以弹出一个解释申请该权限的提示给用户，如果为true，则可以弹
                    if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED
                            && !ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                        // false，则自己弹出提示，也可以Intent到系统的APP setting界面
                        showAskDialog(activity);
                        return true;
                    }
                default:
                    break;
            }
        }
        return false;
    }

    /**
     * 显示提示dialog
     */
    private void showAskDialog(final Activity activity) {
        new AlertDialog.Builder(activity).setMessage("permission_message_permission_failed")
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 去设置中设置权限
                        try {
                            PackageInfo info = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);

                            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.parse("package:" + info.packageName));
                            activity.startActivity(intent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).create().show();
    }

    public static boolean selfPermissionGranted(Context context, String permission) {
        // For Android < Android M, self permissions are always granted.
        boolean result = true;
        int targetSdkVersion = -1;
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            targetSdkVersion = info.applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (targetSdkVersion >= Build.VERSION_CODES.M) {
            // targetSdkVersion >= Android M, we can
            // use Context#checkSelfPermission
            result = context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
//            } else {
//                // targetSdkVersion < Android M, we have to use PermissionChecker
//                result = PermissionChecker.checkSelfPermission(context, permission) == PermissionChecker.PERMISSION_GRANTED;
//            }
            Logger.d("permission", permission + result);
        }
        return result;
    }

    /**
     * Some privileges permanently disabled, may need to set up in the execute.
     *
     * @param activity          {@link Activity}.
     * @param deniedPermissions one or more permissions.
     * @return true, other wise is false.
     */
    public static boolean hasAlwaysDeniedPermission(@NonNull Activity activity, @NonNull List<String>
            deniedPermissions) {
        for (String deniedPermission : deniedPermissions) {
            if (!shouldShowRationalePermissions(activity, deniedPermission)) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasAlwaysDeniedPermission(@NonNull Activity activity, @NonNull String[] deniedPermissions) {
        for (String deniedPermission : deniedPermissions) {
            if (!shouldShowRationalePermissions(activity, deniedPermission)) {

                return true;
            }
        }
        return false;
    }

    /**
     * Some privileges permanently disabled, may need to set up in the execute.
     *
     * @param fragment          {@link android.support.v4.app.Fragment}.
     * @param deniedPermissions one or more permissions.
     * @return true, other wise is false.
     */
    public static boolean hasAlwaysDeniedPermission(@NonNull android.support.v4.app.Fragment fragment, @NonNull
            List<String>
            deniedPermissions) {
        for (String deniedPermission : deniedPermissions) {
            if (!shouldShowRationalePermissions(fragment, deniedPermission)) {
                return true;
            }
        }
        return false;
    }

    static boolean shouldShowRationalePermissions(Object o, String... permissions) {
        boolean rationale = false;
        boolean isOk = false;
        for (String permission : permissions) {
            if (o instanceof Activity) {
                Logger.d("rationale", "Activity" + ActivityCompat.shouldShowRequestPermissionRationale((Activity) o, permission));
                rationale = ActivityCompat.shouldShowRequestPermissionRationale((Activity) o, permission);
                isOk = selfPermissionGranted((Activity) o, permission);
            } else if (o instanceof android.support.v4.app.Fragment) {
                Logger.d("rationale", "Fragment");
                rationale = ((android.support.v4.app.Fragment) o).shouldShowRequestPermissionRationale(permission);
                isOk = selfPermissionGranted(((android.support.v4.app.Fragment) o).getActivity(), permission);
            }
            Logger.d("rationale", rationale + "");
            //判断是否已经获取授权
            if (isOk)
                return true;

            //判断是否拒绝弹出系统提示框
            if (rationale)
                return true;

        }
        return false;
    }

    /**
     * @param grantResults Grant results
     * @return Check that all given permissions have been granted by verifying that each entry in the
     * given array is of the value {@link PackageManager#PERMISSION_GRANTED}.
     * @see Activity#onRequestPermissionsResult(int, String[], int[])
     */
    public boolean verifyPermissions(int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

}