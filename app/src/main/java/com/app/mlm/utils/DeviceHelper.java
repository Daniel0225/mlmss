package com.app.mlm.utils;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

/**
 * @version : 1.0.0
 * @package : com.app.mlm.utils
 * @fileName : DeviceHelper
 * @date : 2018/12/29  13:22
 * @describe : 获取设备相关信息工具类
 */
public class DeviceHelper {
    private static Context mContext;
    private volatile static DeviceHelper sInstance;

    public DeviceHelper(Context context) {
        this.mContext = context.getApplicationContext();
    }

    /**
     * @return 单例
     */
    public static DeviceHelper getInstance(Context context) {
        synchronized (DeviceHelper.class) {
            if (sInstance == null) {
                sInstance = new DeviceHelper(context);
            }
        }
        return sInstance;
    }

    /**
     * 获取设备deviceId
     *
     * @return 返回设备deviceId
     */
    public String getDeviceId() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            if (!Utils.checkPermission(mContext, Manifest.permission.READ_PHONE_STATE)) {
                return "-1";
            }
            String deviceId = telephonyManager.getDeviceId();
            if (!TextUtils.isEmpty(deviceId)) {
                return deviceId;
            }
            deviceId = getAndroidId();
            if (!TextUtils.isEmpty(deviceId)) {
                return deviceId;
            }
            return "-1";
        } catch (Exception e) {
            return "-1";
        }
    }

    /**
     * 获取AndroidId
     *
     * @return 获取AndroidId
     */
    public String getAndroidId() {
        try {
            return Settings.System.getString(mContext.getContentResolver(), Settings.System.ANDROID_ID);
        } catch (Exception e) {
            return "-1";
        }
    }

    /**
     * 获取设备系统版本号
     *
     * @return 返回手机系统版本号
     */
    public String getDeviceVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取设备分辨率
     *
     * @return 返回手机分辨率
     */
    public String getResolution() {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels + "*" + displayMetrics.heightPixels;
    }


}
