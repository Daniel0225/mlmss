package com.app.mlm.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * @version : 1.0.0
 * @package : com.app.mlm.utils
 * @fileName : Utils
 * @date : 2018/12/29  13:25
 * @describe : 其他工具类
 */
public class Utils {

    /**
     * 检查权限
     *
     * @param context
     * @param permission
     * @return
     */
    public static boolean checkPermission(Context context, String permission) {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }
}
