package com.app.mlm;

/**
 * @version : 1.0.0
 * @package : com.app.mlm
 * @fileName : Constants
 * @date : 2018/12/29  16:55
 * @describe : 常量
 */

public class Constants {

    public static final String VMCODE = "vmCode";
    public static final String VMID = "vmId";
    public static final String BASE_URL = "http://47.106.143.212:8080/api/";
    public static final String ADDATA = "ad_data";
    public static final String DOWN_LOAD = "down_load";
    public static final String SHOP_CAR = "shop_car";
    public static final String LOCK_IDS = "lock_ids";
    //存储温度的最高和最低温度
    public static final String CHUHUO_WENDU = "chuhuo_wendu";//
    public static final String COLL_LOW_TEMP = "coll_low_temp";//制冷最低温度
    public static final String COOL_HIGH_TEMP = "cool_high_Temp";//制冷最高温度
    public static final String HEAT_LOW_TEMP = "heat_low_temp";//制热最低温度
    public static final String HEAT_HIGH_TEMP = "heat_high_Temp";//制热最高温度
    public static final String COUNTER_NUM = "counterNumber";
    public static final String COUNTER_NAME = "counterName";
    public static final String TOTAL_NUM = "totalNum";
    public static final String TOTAL_PRICE = "totalPrice";
    public static final String SHIPMENT = "shipment";

    /**
     * 请求超时时间
     */
    public static final int REQUEST_TIME_OUT = 10;
    public static final String[] BUTTONS = {"1","2","3","4","5","6","7","8","9","清空","0","退格"};
    public static final String[] PAYMENT_NAME = {"支付宝支付", "微信支付"};
    public static final int[] PAYMENT_ICON = {R.drawable.money, R.drawable.alipay, R.drawable.wechat, R.drawable.zhifuma, R.drawable.xinbeiyang, R.drawable.zhangjingmai};
    public static final String AD_URL = BASE_URL + "getAdInfo";//广告接口
    public static final String GET_PRODUCT_PRICE = BASE_URL + "syncMdse";//商品同步接口
    public static final String GET_ALL_TYPE = BASE_URL + "getAllType";//商品类型
    public static final String SYN_CHANNEL = BASE_URL + "syncChannel";//货道信息同步接口
    public static final String WXPAY = "http://47.106.216.231:8083/api/wxpay";//支付http://47.106.216.231:8083/api/wxpay
    public static final String ALIPAY = "http://47.106.216.231:8082/api/alipay";//支付 http://47.106.216.231:8082/api/alipay支付宝
    public static final String SYNC_COUNTER = BASE_URL + "syncCounter";//货柜信息接口
    public static final String SYN_TO_CHANNEL = BASE_URL + "syncToChannel";//货道信息同步到上位机
    public static final String THERMAL = BASE_URL + "syncThermal";//温度接口
    public static final String ALARM_REPORT = BASE_URL + "alarmReport";//故障接口
    public static final String ACTIVATION = BASE_URL + "createActivation";//激活接口

    public static final String CREATEOPERATIONQRCODE = "http://test.minimall24h.com/MilimaoOperation/milimao/core/wxGetToken.php";//维护二维码
    public static final String LOCKVMMDSELIST = BASE_URL + "getLockVmMdseList";//锁单价接口

    //长连接收到取货指令回复接口
    public static final String RECEVE_MSG = BASE_URL + "vendConfirmTest";//收到取货回复
    //出货后反馈给后台数据
    public static final String VENDREPORT = BASE_URL + "vendReport";

    //断电保存数据
    public static final String GETSHOPS = BASE_URL + "getShops";

    public static final String ORDER = "com.mlm.app.Order";//订单广播

    //门禁状态广播
    public static final String DOOR_BROADCAST = "com.snbc.bvm.action.senreceiver";
    //故障状态广播
    public static final String ERRORSTATERECEIVER_BROADCAST = "com.snbc.bvm.action.errorstatereceiver";
    //整机状态广播
    public static final String FGWORKRECEIVER_BROADCAST = "com.snbc.bvm.action.fgworkreceiver";
    //闸门门禁广播
    public static final String SENZRECEIVER_BROADCAST = "com.snbc.bvm.action.senzreceiver";
    //货道状态广播
    public static final String GOODSSTATERECEIVER_BROADCAST = "com.snbc.bvm.action.goodsstatereceiver";
    //进程状态广播
    public static final String HEARTBEAT_BROADCAST = "android.intent.action.active.heartbeat";
    //出货广播
    public static final String SHIPMENT_BROADCAST = "com.app.mlm.shipment_broadcast";


}
