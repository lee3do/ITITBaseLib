package io.itit.androidlibrary.domain;

import android.os.BatteryManager;

/**
 * Created by Lee_3do on 2017/12/27.
 */

public class BatteryInfo {
    public static BatteryInfo batteryInfo = new BatteryInfo();
    public static BatteryInfo getBatteryInfo(){
        return batteryInfo;
    }
    public int level = 0;
    public int scale = 0;
    public int status = 0;
    public int health = 0;

    public String getStatusStr(){
        StringBuilder sb = new StringBuilder();
        switch (status) {
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                sb.append("unknown");
                break;
            case BatteryManager.BATTERY_STATUS_CHARGING:
                sb.append("charging");
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                sb.append("unplugged");
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                sb.append("full");
                break;
            default:
                sb.append("unknown");
                break;
        }
        return sb.toString();
    }
}
