package com.app.mlm;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.app.mlm.Meassage.entity.AndroidCommonVo;
import com.app.mlm.activity.ChuhuoActivity;
import com.app.mlm.application.MainApp;
import com.app.mlm.bean.AddInfoEvent;
import com.app.mlm.bean.AndroidHeartBeat;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.bms.activity.BackgroundManangerSystemActivity;
import com.app.mlm.bms.bean.ActivationBean;
import com.app.mlm.http.BaseResponse;
import com.app.mlm.http.JsonCallBack;
import com.app.mlm.http.bean.AdBean;
import com.app.mlm.http.bean.AllDataBean;
import com.app.mlm.http.bean.HuodaoBean;
import com.app.mlm.http.bean.MaintainBackBean;
import com.app.mlm.http.bean.ProductInfo;
import com.app.mlm.http.bean.SocketShipmentBean;
import com.app.mlm.utils.FastJsonUtil;
import com.app.mlm.utils.PinyinComparator;
import com.app.mlm.utils.PreferencesUtil;
import com.app.mlm.utils.TextPinyinUtil;
import com.app.mlm.utils.ToastUtil;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.litepal.LitePal;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import de.greenrobot.event.EventBus;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

/**
 * Created by Lenovo on 2018/9/25.
 */

public class BackService extends Service {
    /**
     * 心跳检测时间
     */
    private static final long HEART_BEAT_RATE = 15 * 1000;//每隔15秒进行一次对长连接的心跳检测
    private static WebSocket mWebSocket;
    Handler handler = new Handler();
    AndroidHeartBeat heart;// 心跳
    private String host_ip = "ws://" + "47.106.143.212" + ":" + "7397";//可替换为自己的主机名和端口号
    private Handler mHandler = new Handler();
    private int TIME = 10000;  //每隔1s执行一次.
    private long sendTime = 0L;
    private PinyinComparator comparator = new PinyinComparator();
    // 发送心跳包
    private Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            if (System.currentTimeMillis() - sendTime >= HEART_BEAT_RATE) {
                try {
                    heart = new AndroidHeartBeat(PreferencesUtil.getString(Constants.VMCODE), "heartbeat");
                    String json = new Gson().toJson(heart);
                    boolean isSuccess = mWebSocket.send(json);//发送一个空消息给服务器，通过发送消息的成功失败来判断长连接的连接状态
                    Log.e("--心跳包", json);
                    if (!isSuccess) {//长连接已断开
                        mHandler.removeCallbacks(heartBeatRunnable);
                        mWebSocket.cancel();//取消掉以前的长连接
                        new InitSocketThread().start();//创建一个新的连接
                    } else {//长连接处于连接状态

                    }
                    sendTime = System.currentTimeMillis();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            mHandler.postDelayed(this, HEART_BEAT_RATE);//每隔一定的时间，对长连接进行一次心跳检测
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("text", "ocreate-----");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new InitSocketThread().start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // 初始化socket
    private void initSocket() throws UnknownHostException, IOException {
        OkHttpClient client = new OkHttpClient.Builder().readTimeout(0, TimeUnit.MILLISECONDS).build();
        Request request = new Request.Builder().url(host_ip).build();
        client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, okhttp3.Response response) {
                super.onOpen(webSocket, response);//开启长连接成功的回调
                mWebSocket = webSocket;
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {//接收消息的回调
                super.onMessage(webSocket, text);
                //收到服务器端传过来的消息text
                Log.e("收到的消息----", text);
                //RebackResultData rebackInfo = new Gson().fromJson(text, RebackResultData.class);
                AndroidCommonVo vo = JSON.parseObject(text,
                        AndroidCommonVo.class);
                if (vo.getBusType().equals("vend")) {// 出货指令
                    rebackShipment(text);
                    Log.d("main", "接收到出货指令:" + text);
                } else if (vo.getBusType().equals("priceChange")) {//限时售价
                    Log.d("main", "接收到限时售价指令:" + text);
                    syncProduceInfo(vo.getBusType());
                } else if (vo.getBusType().equals("vmSync")) {//同步机器信息接口
                    Log.d("main", "同步机器信息接口:" + text);
                    setActivation(vo.getBusType());
                    //同步广播图片或者视频
                    getAddInfo();
                } else if (vo.getBusType().equals("activityStop")) {//活动结束
                    Log.d("main", "接收到限时售价活动结束指令:" + text);
                    syncProduceInfo(vo.getBusType());
                } else if (vo.getBusType().equals("gzhJump")) {//跳转到维护页面
                    Log.d("main", "接收到跳转到维护页面指令:" + text);
                    MaintainBackBean maintainBackBean = FastJsonUtil.getObject(text, MaintainBackBean.class);
                    PreferencesUtil.putInt(Constants.OPERATIONID, maintainBackBean.getT().getOperationId());
                    Intent intent = new Intent(MainApp.getAppInstance().getApplicationContext(), BackgroundManangerSystemActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MainApp.getAppInstance().getApplicationContext().startActivity(intent);
                }
            }

            @Override
            public void onMessage(WebSocket webSocket, ByteString bytes) {
                super.onMessage(webSocket, bytes);
                Log.e("onMessage", "");
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                Log.e("onClosing", "");
            }

            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                Log.e("onClosed", "");
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {//长连接连接失败的回调
                super.onFailure(webSocket, t, response);
                Log.e("onFailure", "");
            }
        });
        client.dispatcher().executorService().shutdown();
        mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);//开启心跳检测
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mWebSocket != null) {
            mWebSocket.close(1000, null);
        }
    }

    private void rebackShipment(String json) {
        SocketShipmentBean socketShipmentBean = FastJsonUtil.getObject(json, SocketShipmentBean.class);
        if (!TextUtils.isEmpty(json)) {
            HttpParams httpParams = new HttpParams();
            httpParams.put("deviceId", PreferencesUtil.getString(Constants.VMCODE));
            httpParams.put("snm", socketShipmentBean.getT().getSnm());
            OkGo.<BaseResponse<AllDataBean>>post(Constants.RECEVE_MSG)
                    .tag(this)
                    .params(httpParams)
                    .execute(new JsonCallBack<BaseResponse<AllDataBean>>() {
                        @Override
                        public void onSuccess(Response<BaseResponse<AllDataBean>> response) {
                            //  Toast.makeText(MainApp.getAppInstance().getApplicationContext(), "接收返回成功", Toast.LENGTH_SHORT).show();
                            Log.e("接收", "返回成功");
                            if (response.body().getCode() == 0) {
                                // Toast.makeText(MainApp.getAppInstance().getApplicationContext(),"接收返回成功++"+response.body().data,Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainApp.getAppInstance().getApplicationContext(), ChuhuoActivity.class);
                                intent.putExtra("shipment", json);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                MainApp.getAppInstance().getApplicationContext().startActivity(intent);
                            } else {
                                Log.e("上传失败", String.valueOf(response.body().getCode()));
                                //  Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Response<BaseResponse<AllDataBean>> response) {
                            ToastUtil.showLongToast("请求服务器失败，请稍后重试");
                        }
                    });
        }
    }

    /**
     * 同步机器信息接口
     */
    private void setActivation(String busType) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("vmCode", PreferencesUtil.getString(Constants.VMCODE));
        OkGo.<BaseResponse<ActivationBean>>get(Constants.SYNCVM)
                .tag(this)
                .params(httpParams)
                .execute(new JsonCallBack<BaseResponse<ActivationBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<ActivationBean>> response) {
                        if (response.body().getCode() == 0) {
                            PreferencesUtil.putString(Constants.VMCODE, response.body().getData().getInnerCode());
                            PreferencesUtil.putInt("status", response.body().getData().getStatus());
                            PreferencesUtil.putString("vmName", response.body().getData().getVmName());
                            PreferencesUtil.putInt(Constants.VMID, response.body().getData().getVmId());
                            Log.e("vmid", PreferencesUtil.getInt(Constants.VMID) + "" + PreferencesUtil.getString(Constants.VMCODE));
                            reply(busType, "成功", 1);
                        } else {
                            // Toast.makeText(MainApp.getAppInstance().getApplicationContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<ActivationBean>> response) {
                        reply(busType, "失败", 2);
                        // ToastUtil.showLongToast(response.body().getMsg());
                    }
                });
    }

    /**
     * 同步商品数据
     */
    private void syncProduceInfo(String busType) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("vmCode", PreferencesUtil.getString(Constants.VMCODE));
        OkGo.<BaseResponse<List<ProductInfo>>>get(Constants.GET_PRODUCT_PRICE)
                .tag(this)
                .params(httpParams)
                .execute(new JsonCallBack<BaseResponse<List<ProductInfo>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<List<ProductInfo>>> response) {
                        if (response.body().getCode() == 0) {
                            saveProductInfo(response.body().getData(), busType);
                            reply(busType, "成功", 1);
                        } else {
                            reply(busType, "失败", 2);
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<List<ProductInfo>>> response) {
                        super.onError(response);
                        reply("priceChange", "失败", 2);
                        // ToastUtil.showLongToast("请求服务器数据失败");
                    }
                });
    }

    private void saveProductInfo(List<ProductInfo> list, String busType) {
        LitePal.deleteAll(ProductInfo.class);

        new Thread(new Runnable() {
            @Override
            public void run() {
                Collections.sort(list, comparator);
                for (int i = 0; i < list.size(); i++) {
                    ProductInfo productInfo = list.get(i);
                    if (TextUtils.isEmpty(productInfo.getMdseName())) {
                        productInfo.setQuanping("");
                    } else {
                        productInfo.setQuanping(TextPinyinUtil.getInstance().getPinyin(productInfo.getMdseName()));
                    }
                    boolean isSuccess = productInfo.save();
                    Log.e("Tag", "isSuccess " + isSuccess);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        syncChannel(busType);
                        // ToastUtil.showLongToast("同步完成");
                    }
                });
            }
        }).start();
    }

    /**
     * 同步货道配置
     */
    private void syncChannel(String busType) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("vmCode", PreferencesUtil.getString(Constants.VMCODE));
        OkGo.<BaseResponse<List<GoodsInfo>>>get(Constants.SYN_TO_CHANNEL)
                .tag(this)
                .params(httpParams)
                .execute(new JsonCallBack<BaseResponse<List<GoodsInfo>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<List<GoodsInfo>>> response) {
                        if (response.body().getCode() == 0) {
                            updateChannelInfo(response.body().getData());
                            reply(busType, "成功", 1);
                        } else {
                            reply(busType, "失败", 2);
                            // ToastUtil.showLongCenterToast(response.body().getMsg());
                        }

                    }

                    @Override
                    public void onError(Response<BaseResponse<List<GoodsInfo>>> response) {
                        super.onError(response);
                        reply(busType, "失败", 2);
                    }
                });
    }

    private void updateChannelInfo(List<GoodsInfo> list) {
        for (GoodsInfo goodsInfo : list) {

            List<ProductInfo> productInfos = LitePal.where("mdseId = ?", String.valueOf(goodsInfo.getMdseId())).find(ProductInfo.class);

            if (productInfos.size() > 0) {
                ProductInfo productInfo = productInfos.get(0);
                goodsInfo.setMdsePack(productInfo.getMdsePack());
                goodsInfo.setMdseBrand(productInfo.getMdseBrand());
                goodsInfo.setMdseName(productInfo.getMdseName());
                goodsInfo.setMdsePrice(goodsInfo.getRealPrice());
                if (goodsInfo.getActivityPrice() != 0) {
                    goodsInfo.setRealPrice(goodsInfo.getActivityPrice());
                }
                goodsInfo.setMdseUrl(productInfo.getMdseUrl());
            } else {
                ToastUtil.showLongToast("找不到商品信息,请先同步商品信息");
            }
        }

        List<List<GoodsInfo>> newHuoDaoList = getData(getHuoDaoData());
        for (int i = 0; i < newHuoDaoList.size(); i++) {
            List<GoodsInfo> itemList = newHuoDaoList.get(i);
            for (int j = 0; j < itemList.size(); j++) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append((newHuoDaoList.size() - i));
                if (j < 10) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(j + 1);
                String clCode = stringBuffer.toString();
                for (int h = 0; h < list.size(); h++) {
                    GoodsInfo goods = list.get(h);
                    if (goods.getClCode().equals(clCode)) {
                        itemList.set(j, goods);
                    }
                }
            }
        }

        HuodaoBean huodaoBean = new HuodaoBean(newHuoDaoList);
        PreferencesUtil.putString("huodao", FastJsonUtil.createJsonString(huodaoBean));
        Intent intent = new Intent();
        intent.setAction(Constants.PRICECHANGE);
        MainApp.getAppInstance().getApplicationContext().sendBroadcast(intent);

    }

    private List<List<GoodsInfo>> getData(String[] layers) {
        List<List<GoodsInfo>> list = new ArrayList<>();
        for (int i = layers.length - 1; i >= 0; i--) {
            list.add(getDefaultData(Integer.valueOf(layers[i])));
        }
        return list;
    }

    private List<GoodsInfo> getDefaultData(int column) {//传入当前行 有几列
        List<GoodsInfo> goodsInfoList = new ArrayList<>();
        for (int i = 0; i < column; i++) {
            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.setMdseName("请补货");
            goodsInfo.setMdseUrl("empty");
            goodsInfo.setMdsePrice(0);
            goodsInfoList.add(goodsInfo);
        }
        return goodsInfoList;
    }

    /**
     * 解析机器层数据
     */
    private String[] getHuoDaoData() {
        String layerData = PreferencesUtil.getString("layer");
        if (TextUtils.isEmpty(layerData)) {
            return new String[]{};
        } else {
            layerData = layerData.replace("[", "").replace("]", "").replace(" ", "");
            Log.e("Tag", "layerData " + layerData);
            String[] layers = layerData.split(",");
            return layers;
        }
    }

    /**
     * 回复响应类型
     */
    private void reply(String busType, String noticeDescribe, int isSuccess) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("vmCode", PreferencesUtil.getString(Constants.VMCODE));
        httpParams.put("noticeType", busType);
        httpParams.put("noticeDescribe", noticeDescribe);
        httpParams.put("isSuccess", isSuccess);
        OkGo.<BaseResponse<AllDataBean>>post(Constants.GET_PRODUCT_PRICE)
                .tag(this)
                .params(httpParams)
                .execute(new JsonCallBack<BaseResponse<AllDataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<AllDataBean>> response) {
                        if (response.body().getCode() == 0) {

                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<AllDataBean>> response) {
                        super.onError(response);
                        ToastUtil.showLongToast("请求服务器数据失败");
                    }
                });
    }

    private void getAddInfo() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("vmCode", PreferencesUtil.getString(Constants.VMCODE));
        OkGo.<BaseResponse<List<AdBean>>>get(Constants.AD_URL)
                .params(httpParams)
                .tag(this)
                .execute(new JsonCallBack<BaseResponse<List<AdBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<List<AdBean>>> response) {

                        if (response.body().getCode() == 0) {
                            List<AdBean> adBeanList = response.body().data;
                            refreshAddInfo(adBeanList);
                        } else {
                            //  Toast.makeText(, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<List<AdBean>>> response) {
                        ToastUtil.showLongToast("请求服务器失败,请稍后重试");
                    }
                });
    }

    /**
     * 刷新广告信息
     *
     * @param adBeanList
     */
    private void refreshAddInfo(List<AdBean> adBeanList) {
        String adString = PreferencesUtil.getString(Constants.ADDATA);
        List<AdBean> adBeans = new ArrayList<>();
        if (!TextUtils.isEmpty(adString)) {
            adBeans = FastJsonUtil.getObjects(adString, AdBean.class);
        }

        for (AdBean adBean : adBeanList) {

            if (adBean.getFileType() == 3) {
                for (AdBean ad : adBeans) {
                    if (ad.getFileType() == 3) {
                        if (adBean.getSuffix().equals(ad.getSuffix()) && adBean.getUrl().equals(ad.getUrl())) {//广告信息无变化

                        } else {
                            PreferencesUtil.putString(Constants.ADDATA, FastJsonUtil.createJsonString(adBeanList));
                            //广告信息有变化  发消息给首页 更新广告展示
                            EventBus.getDefault().post(new AddInfoEvent());
                        }
                    }
                }
            }
        }
    }

    class InitSocketThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                initSocket();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
