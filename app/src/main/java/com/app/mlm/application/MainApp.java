package com.app.mlm.application;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.widget.Toast;

import com.app.mlm.Constants;
import com.app.mlm.Meassage.MyClient;
import com.app.mlm.MlmServiceConfigure;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.bms.activity.BackgroundManangerSystemActivity;
import com.app.mlm.greendao.DaoMaster;
import com.app.mlm.greendao.DaoSession;
import com.app.mlm.http.HttpHelper;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.snbc.bvm.BVMAidlInterface;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;
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
    public static List<GoodsInfo> shopCarList = new ArrayList<>();
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
    MyClient myclient;
    private DaoSession daoSession;
    private MyReceiver receiver;

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
        initServerState();
        initRxHttp();
        initGreenDao();
        myclient = new MyClient();
        myclient.connect();
        Intent service = new Intent(this, MlmServiceConfigure.class);
        startService(service);
        initOkGo();
        // 初始化LitePal数据库
        LitePal.initialize(this);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SnbcBvmService");
        intent.setPackage("com.snbc.bvm");
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.DOOR_BROADCAST);
        filter.addAction(Constants.ERRORSTATERECEIVER_BROADCAST);
        filter.addAction(Constants.FGWORKRECEIVER_BROADCAST);
        filter.addAction(Constants.SENZRECEIVER_BROADCAST);
        filter.addAction(Constants.GOODSSTATERECEIVER_BROADCAST);
        filter.addAction(Constants.HEARTBEAT_BROADCAST);
        registerReceiver(receiver, filter);

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
        unregisterReceiver(receiver);
    }

    //内部类实现广播接收者
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case Constants.DOOR_BROADCAST:
                    int status = Integer.parseInt(intent.getStringExtra("SENSTATE"));
                    switch (status) {
                        case 1://柜门开
                            Log.e("收到开柜广播", "-----");
                            Toast.makeText(MainApp.appInstance, "收到开机广播", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainApp.appInstance, BackgroundManangerSystemActivity.class));
                            break;
                        case 2://柜门关
                            // startActivity(new Intent(MainApp.appInstance, ScreenProtectActivity.class));
                            break;
                    }
                    break;
                case Constants.ERRORSTATERECEIVER_BROADCAST://故障状态广播

                    break;
                case Constants.FGWORKRECEIVER_BROADCAST://整机状态广播

                    break;
                case Constants.SENZRECEIVER_BROADCAST: //闸门门禁广播
                    break;
                case Constants.GOODSSTATERECEIVER_BROADCAST://货道状态广播
                    break;
                case Constants.HEARTBEAT_BROADCAST://进程状态广播
                    break;
            }
        }
    }
}
