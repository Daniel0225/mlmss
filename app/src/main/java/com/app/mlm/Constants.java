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
    public static final String BASE_URL = "http://47.106.143.212:8080/api/";
    public static final String ADDATA = "ad_data";
    public static final String DOWN_LOAD = "down_load";
    /**
     * 请求超时时间
     */
    public static final int REQUEST_TIME_OUT = 10;
    public static final String[] BUTTONS = {"1","2","3","4","5","6","7","8","9","清空","0","退格"};
    public static final String[] PAYMENT_NAME = {"现金支付","支付宝支付","微信支付","支付码支付","新北洋智付","掌静脉支付"};
    public static final int[] PAYMENT_ICON = {R.drawable.money, R.drawable.alipay, R.drawable.wechat, R.drawable.zhifuma, R.drawable.xinbeiyang, R.drawable.zhangjingmai};
    public static final String AD_URL = BASE_URL + "getAdInfo";//广告接口
    public static final String GET_PRODUCT_PRICE = BASE_URL + "getProductPrice";//商品同步接口
    public static final String GET_ALL_TYPE = BASE_URL + "getAllType";//商品类型
    public static final String SYN_CHANNEL = BASE_URL + "syncChannel";//货道信息同步接口
    public static final String WXPAY = BASE_URL + "wxpay";//支付
    public static final String SYN_TO_CHANNEL = BASE_URL + "syncToChannel";//货道信息同步接口


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



}
