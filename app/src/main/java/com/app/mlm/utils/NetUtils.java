package com.app.mlm.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @version : 1.0.0
 * @package : com.app.mlm.utils
 * @fileName : NetUtils
 * @date : 2018/12/29  13:11
 * @describe : 网络相关工具类
 */
public class NetUtils {

    /**
     * 判断是否有网络
     *
     * @return 返回值 连接返回true，未连接返回false
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
