package com.app.mlm.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.DisplayMetrics;


/**
 * @version : 1.0.0
 * @package : com.app.mlm.utils
 * @fileName : NetUtils
 * @date : 2018/1/2  13:11
 * @describe : 手机相关工具类
 */
public class PhoneUtil {
    public static int getSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 获取版本名称
     *
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 判断SD卡是否在手机上
     *
     * @return
     */
    public static boolean getSDState() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * 获取SD卡路径
     *
     * @return
     */
    public static String getSDpath() {
        if (getSDState()) {
            return Environment.getExternalStorageDirectory().getPath();
        }
        return null;
    }

    /**
     * 获取屏幕的宽（像素值）
     *
     * @param context
     * @return
     */
    public static int getDisplayWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高（像素值）
     *
     * @param context
     * @return
     */
    public static int getDisplayHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取屏幕密度（像素比例：0.75/1.0/1.5/2.0）
     *
     * @param context
     * @return
     */
    public static float getDisplayDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 屏幕密度（每寸像素：120/160/240/320）
     *
     * @param context
     * @return
     */
    public static float getDisplayDensityDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 获取手机状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
