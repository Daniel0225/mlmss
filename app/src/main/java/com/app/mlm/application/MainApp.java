package com.app.mlm.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDex;

import com.app.mlm.Meassage.MyClient;
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
    MyClient myclient;
    private DaoSession daoSession;

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
        myclient = new MyClient();
        myclient.connect();
       /* Intent service = new Intent(this, BackService.class);
        startService(service);*/
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
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

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
