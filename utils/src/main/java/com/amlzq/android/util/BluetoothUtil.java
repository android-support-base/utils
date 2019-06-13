package com.amlzq.android.util;

import android.bluetooth.BluetoothAdapter;

/**
 * 蓝牙
 */
public class BluetoothUtil {

    // =============================================================================================
    // 蓝牙通信
    // =============================================================================================

    /**
     * 返回本地蓝牙适配器的硬件地址。
     * 蓝牙没有必要打开, 也能读取
     * <p>
     * E.g: 88:28:B3:3F:D0:55 <br>
     * E.g-Emulator: NULL <br>
     *
     * @return 蓝牙硬件地址字符串
     * @permission android.permission.BLUETOOTH
     */
    public static String getAddress() {
        final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        try {
            final String BLUETOOTH_ADDRESS = "" + adapter.getAddress();
            return BLUETOOTH_ADDRESS;
        } catch (NullPointerException e) {
            Logger.w(e);
            return "";
        }
    }

}
