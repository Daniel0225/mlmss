package com.app.mlm.application;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.app.mlm.Meassage.MyClient;
import com.app.mlm.R;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.greendao.DaoMaster;
import com.app.mlm.greendao.DaoSession;
import com.app.mlm.http.HttpHelper;
import com.app.mlm.utils.CrashHandler;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.snbc.bvm.BVMAidlInterface;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * @version : 1.0.0
 * @package : com.app.mlm.application
 * @fileName : MainApp
 * @date : 2018/12/29  16:25
 * @describe : Application
 */
public class MainApp extends Application {
    public static BVMAidlInterface bvmAidlInterface;
    public static ArrayList<GoodsInfo> shopCarList = new ArrayList<>();
    public static MyClient myclient;
    private static MainApp appInstance;
    public SharedPreferences mShard;
    public ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bvmAidlInterface = BVMAidlInterface.Stub.asInterface(service);
            try {
                int code1 = bvmAidlInterface.BVMSetKey("2lqFW9J9HyFYWol7");
                Log.e("密钥返回", code1 + "");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private DaoSession daoSession;
    //   private MyReceiver receiver;

    public static MainApp getAppInstance() {
        return appInstance;
    }

    /**
     * 加入购物车 判断购物车里面是否已经有该商品 有的话直接增加数量即可
     *
     * @param goodsInfo
     */
    public static void addShopCar(GoodsInfo goodsInfo) {
        boolean isContain = false;
        for (GoodsInfo goods : shopCarList) {
            if (goods.getMdseId() == goodsInfo.getMdseId()) {
                goods.setShopCarNum(goods.getShopCarNum() + 1);
                isContain = true;
                break;
            }
        }
        if (!isContain) {
            shopCarList.add(goodsInfo);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;
        // 初始化LitePal数据库
        LitePal.initialize(this);
        initServerState();
        initRxHttp();
        initGreenDao();
        CrashHandler.getInstance().init(this);
        myclient = new MyClient();
        myclient.connect();
      /*  Intent service = new Intent(this, MlmServiceConfigure.class);
        startService(service);*/
        initOkGo();
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SnbcBvmService");
        intent.setPackage("com.snbc.bvm");
        bindService(intent, connection, Context.BIND_AUTO_CREATE);


    }

    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        //使用sp保持cookie，如果cookie不过期，则一直有效
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
        builder.connectTimeout(10000, TimeUnit.MILLISECONDS);//超时时间10秒
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);

        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())
                .setCacheMode(CacheMode.NO_CACHE)
                .setRetryCount(0);
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

    @Override
    public void onTerminate() {
        super.onTerminate();
        //unregisterReceiver(receiver);
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return this.getString(R.string.usercenter_version_ok);
        }
    }
}
