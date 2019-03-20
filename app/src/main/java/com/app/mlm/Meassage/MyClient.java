package com.app.mlm.Meassage;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.app.mlm.Constants;
import com.app.mlm.Meassage.entity.AndroidCommonVo;
import com.app.mlm.activity.ChuhuoActivity;
import com.app.mlm.application.MainApp;
import com.app.mlm.bean.AndroidHeartBeat;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.bms.activity.BackgroundManangerSystemActivity;
import com.app.mlm.bms.bean.ActivationBean;
import com.app.mlm.http.BaseResponse;
import com.app.mlm.http.JsonCallBack;
import com.app.mlm.http.bean.AllDataBean;
import com.app.mlm.http.bean.HuodaoBean;
import com.app.mlm.http.bean.ProductInfo;
import com.app.mlm.http.bean.SocketShipmentBean;
import com.app.mlm.utils.FastJsonUtil;
import com.app.mlm.utils.PinyinComparator;
import com.app.mlm.utils.PreferencesUtil;
import com.app.mlm.utils.TextPinyinUtil;
import com.app.mlm.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.litepal.LitePal;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

public class MyClient {
    public static final String IP = "47.106.143.212";
    public static final int PORT = 65132;
    public static final int keepAliveDelay = 10000;// 心跳时长
    public Socket socket;
    public InputStream inStream;
    public OutputStream outStream;
    public boolean isconnect = false;// 是否连接
    public boolean isStopReConnect = false;// 是否停止重连
    AndroidHeartBeat heart;// 心跳
    Handler handler;
    private long lastSendTime, lastReciveTime;
    private PinyinComparator comparator = new PinyinComparator();

    public MyClient() {
    }

    /**
     * 启动长连接，并发送第一条心跳包(服务器连接后5S不发送心跳包自动断开连接)
     */
    public void connect() {
        new Thread() {
            public void run() {
                try {
                    socket = new Socket(IP, PORT);
                    isconnect = true;
                    //通知页面 网络已连接

                    inStream = socket.getInputStream();
                    outStream = socket.getOutputStream();
                    lastSendTime = System.currentTimeMillis();
                    lastReciveTime = System.currentTimeMillis();
                    heart = new AndroidHeartBeat(PreferencesUtil.getString(Constants.VMCODE), "heartbeat");
                    sendHeartMessage();
//					isconnect = true;
//					Constants.isNetCon = true;
                    // 心跳
                    new Thread(new KeepAliveWatchDog()).start();
                    //接收消息
                    new Thread(new ReceiveWatchDog()).start();
                    //判断接收超时
                    new Thread(new ReciveTimeoutRunnable()).start();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
//					//通知页面 网络断开
                    //启动重连线程
//					new Thread(new ReConnect()).start();
                }
            }

            ;
        }.start();

    }

    public void sendHeartMessage() {
        try {
            Log.e("---", "发送：\t 心跳:" + heart.toString());
            ObjectOutputStream objout = new ObjectOutputStream(outStream);
            objout.writeObject(heart.toString());
            objout.flush();
            //MCBDetail.uploadDBReport();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("---", "发送：心跳失败");
            isconnect = false;
            //启动重连线程
//			new Thread(new ReConnect()).start();
        }

    }

    public void sendMessage(String msg) {
        try {
            Log.d("push", "发送：\t 普通消息:" + msg);
            ObjectOutputStream objout = new ObjectOutputStream(outStream);
            objout.writeObject(msg);
            objout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean isConnect() {
        if (socket != null) {
            return socket.isConnected();
        }
        return false;
    }

    public void setConnect(boolean isconnect) {
        this.isconnect = isconnect;
    }

    public void close() {
        try {
            isconnect = false;
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 开始重连
     */
    public void startReConnect() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        new Thread(new ReConnect()).start();
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
                goodsInfo.setMdsePrice(productInfo.getMdsePrice());
                if(goodsInfo.getActivityPrice() != 0){
                    goodsInfo.setRealPrice(goodsInfo.getRealPrice());
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


    class KeepAliveWatchDog implements Runnable {
        long checkDelay = 1000;

        public void run() {
            while (isconnect) {
                if (System.currentTimeMillis() - lastSendTime > keepAliveDelay) {
                    try {
                        sendHeartMessage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    lastSendTime = System.currentTimeMillis();
                } else {
                    try {
                        Thread.sleep(checkDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 接收消息(线程)
     *
     * @author Administrator
     */
    class ReceiveWatchDog implements Runnable {
        public void run() {
            while (isconnect) {
                if (socket.isConnected() == false) {
                    isconnect = false;
                    // 通知页面 断开功能
                    Log.e("main", "连接断开..........");
                    startReConnect();// connect(); 另起线程 定时连接
                    return;
                }
                try {
                    if (inStream.available() > 0) {// 获取流的总大小
                        lastReciveTime = System.currentTimeMillis();
                        ObjectInputStream ois = new ObjectInputStream(inStream);
                        Object obj = ois.readObject();
                        Log.e("---push", "接收：\t" + obj.toString() + "\t");
                        //Util.WriteFileData(FileManager.BASE_PATH+FileManager.LOGINFO, Util.getCurDate()+" "+obj.toString()+"\r\n");
                        AndroidCommonVo vo = JSON.parseObject(obj.toString(),
                                AndroidCommonVo.class);
                        if (vo.getBusType().equals("heartbeat")) {// 心跳包
                        /*	AndroidHeartBeat beat = JSON.parseObject(vo.getT(),
                                    AndroidHeartBeat.class);
							Message msg = Message.obtain();
							Bundle bundle = new Bundle();
							bundle.putSerializable("beat", beat);
							msg.setData(bundle);
							handler.sendMessage(msg);*/
                            Log.d("1111push", "接收心跳包");

                        } else if (vo.getBusType().equals("vend")) {// 出货指令
                           /* AndroidVend vend = JSON.parseObject(vo.getT(),
                                    AndroidVend.class);*/
                            rebackShipment(obj.toString());
                            Log.d("main", "接收到出货指令:" + obj.toString());
                       /*     StringBuffer buf = new StringBuffer();
                            buf.append(Util.getCurDate() + " ");
                            buf.append("out order:");
                            //打印出货指令到日志
                            buf.append(vend.getSnm());
                            buf.append("\r\n");*/
                            //Util.WriteFileData(FileManager.BASE_PATH+FileManager.LOGINFO, buf.toString());

                            //回复确认
                            //MCBDetail.ReVendoutOK(handler, vend);
                            //无需回复
//							Message msg = Message.obtain();
//							msg.what = MsgType.SHIPMENT;
//							Bundle bundle = new Bundle();
//							bundle.putSerializable("beat", vend);
//							msg.setData(bundle);
//							handler.sendMessage(msg);

                            Log.d("main", "接收到出货指令:" + obj.toString());

                        } else if (vo.getBusType().equals("priceChange")) {//限时售价
                            Log.d("main", "接收到限时售价指令:" + obj.toString());
                            syncProduceInfo(vo.getBusType());
                        } else if (vo.getBusType().equals("vmSync")) {//同步机器信息接口
                            Log.d("main", "同步机器信息接口:" + obj.toString());
                            setActivation(vo.getBusType());
                        } else if (vo.getBusType().equals("activityStop")) {//活动结束
                            Log.d("main", "接收到限时售价活动结束指令:" + obj.toString());
                            syncProduceInfo(vo.getBusType());
                        } else if (vo.getBusType().equals("gzhJump")) {//跳转到维护页面
                            Log.d("main", "接收到跳转到维护页面指令:" + obj.toString());
                            Intent intent = new Intent(MainApp.getAppInstance().getApplicationContext(), BackgroundManangerSystemActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            MainApp.getAppInstance().getApplicationContext().startActivity(intent);
                        } else if (vo.getBusType().equals("priceChange") || vo.getBusType().equals("syncQrCode")) {
                            //价格修改、更新微信二维码

						/*	Message msg = Message.obtain();
                            msg.what = MsgType.UP_CODE;
							handler.sendMessage(msg);*/
                        } else if (vo.getBusType().equals("syncVersion")) {
                        /*	//下位机更新
                            BinFileEntity binentity = JSON.parseObject(vo.getT(),
									BinFileEntity.class);
							Message msg = Message.obtain();
							msg.what = MsgType.DOWNLOADMCBFILE_C;
							Bundle bundle = new Bundle();
							bundle.putSerializable("beat", binentity);
							msg.setData(bundle);
							handler.sendMessage(msg);
							Util.WriteFileData(FileManager.BASE_PATH+FileManager.LOGINFO, "syncVersion \r\n");*/
                        } else if (vo.getBusType().equals("RESETMCB")) {
                            //重启下位机
						/*	Message msg = Message.obtain();
							msg.what = MsgType.RESET_C;
							handler.sendMessage(msg);*/
                        } else if (vo.getBusType().equals("STOPSALE")) {
                            //暂停服务
							/*Message msg = Message.obtain();
							msg.what = MsgType.UI_STOP;
							handler.sendMessage(msg);*/
                        } else if (vo.getBusType().equals("RECOVER")) {
                            //恢复服务
							/*Message msg = Message.obtain();
							msg.what = MsgType.RESET_C;
							handler.sendMessage(msg);*/
                        } else if (vo.getBusType().equals("SetSizeA")) {
						/*	Message msg = Message.obtain();
							msg.what = MsgType.SETSIZE_A;
							handler.sendMessage(msg);*/
                        } else if (vo.getBusType().equals("SetSizeB")) {
							/*Message msg = Message.obtain();
							msg.what = MsgType.SETSIZE_B;
							handler.sendMessage(msg);*/
                        } else if (vo.getBusType().equals("SetSizeC")) {
						/*	Message msg = Message.obtain();
							msg.what = MsgType.SETSIZE_C;
							handler.sendMessage(msg);*/
                        } else if (vo.getBusType().equals("sendVmLogMail")) {
                            //上传日志至邮箱
							/*Message msg = Message.obtain();
							msg.what = MsgType.SEND_LOG;
							handler.sendMessage(msg);*/
                        } else if (vo.getBusType().equals("adChange")) {
                            //广告更新
						/*	Message msg = Message.obtain();
							msg.what = MsgType.AD_CHANGE;
							handler.sendMessage(msg);*/
                        }
                    } else {
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 断网重连
     *
     * @author Administrator
     */
    class ReConnect implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (!isStopReConnect && isconnect == false) {
//				connect();
                Log.e("main", "connecting.......");
                Util.WriteFileData(FileManager.BASE_PATH
                        + FileManager.LOGINFO, Util.getCurDate() + " "
                        + "long connection re connecting.... \r\n");
                try {
                    socket = new Socket(IP, PORT);
                    isconnect = true;

                    inStream = socket.getInputStream();
                    outStream = socket.getOutputStream();
                    lastSendTime = System.currentTimeMillis();
                    lastReciveTime = System.currentTimeMillis();
                    heart = new AndroidHeartBeat(Util.getLoginPropertiesValue("machineId"), "heartbeat");
                    sendHeartMessage();
                    // 心跳
                    new Thread(new KeepAliveWatchDog()).start();
                    //接收消息
                    new Thread(new ReceiveWatchDog()).start();
                    //判断接收超时
                    new Thread(new ReciveTimeoutRunnable()).start();
                } catch (Exception e) {
                    e.printStackTrace();
                    Util.WriteFileData(FileManager.BASE_PATH
                            + FileManager.LOGINFO, Util.getCurDate() + " "
                            + "re connect failed waiting..." + e.toString() + " \r\n");

                    try {
                        Thread.sleep(keepAliveDelay);// 45秒重连
                    } catch (InterruptedException e2) {
                        // TODO Auto-generated catch block
                        e2.printStackTrace();
                        Util.WriteFileData(FileManager.BASE_PATH
                                + FileManager.LOGINFO, Util.getCurDate() + " "
                                + "re connect 45s later Exception ..." + e2.toString() + " \r\n");
                    }
                }

            }
        }

    }

    class ReciveTimeoutRunnable implements Runnable {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (isconnect) {
                if (System.currentTimeMillis() - lastReciveTime > 50000) {
                    Util.WriteFileData(FileManager.BASE_PATH
                            + FileManager.LOGINFO, Util.getCurDate() + " "
                            + "long connection timeout \r\n");
                    Log.e("error", "timeout......ing");
                    //接收超时 断开重连
                    isconnect = false;
                    startReConnect();
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
