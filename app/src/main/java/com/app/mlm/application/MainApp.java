package com.app.mlm.application;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.app.mlm.greendao.DaoMaster;
import com.app.mlm.greendao.DaoSession;
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
        initGreenDao();
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

    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     */
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "goodsinfo.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    private DaoSession daoSession;
    public DaoSession getDaoSession() {
        return daoSession;
    }
}
