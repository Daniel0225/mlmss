package com.app.mlm.application;

import android.app.Application;
import android.content.SharedPreferences;

import com.app.mlm.http.HttpHelper;

/**
 * @version : 1.0.0
 * @package : com.app.mlm.application
 * @fileName : MainApp
 * @date : 2018/12/29  16:25
 * @describe : Application
 */
public class MainApp extends Application {
    private static MainApp appInstance;
    public SharedPreferences mShard;
    public static MainApp getAppInstance() {
        return appInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        initServerState();
        initRxHttp();
    }

    /**
     * 初始化网络
     */
    private void initRxHttp() {
        HttpHelper.initRxHttp(MainApp.getAppInstance());
    }

    private void initServerState() {
        mShard = getSharedPreferences("mainappmkf", MODE_PRIVATE);
    }

}
