package com.app.mlm.http;

import android.app.Application;
import android.content.Context;

import com.allen.library.RxHttpUtils;
import com.allen.library.config.OkHttpConfig;
import com.allen.library.cookie.store.SPCookieStore;
import com.app.mlm.Constants;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * @version : 1.0.0
 * @package : com.app.mlm.http
 * @fileName : HttpHelper
 * @date : 2018/12/29  16:37
 * @describe : TODO
 */
public class HttpHelper {
    public static String HOST = "http://www.baidu.com";

    /**
     * 初始化网络请求
     */
    public static void initRxHttp(Application application) {
        OkHttpClient okHttpClient = new OkHttpConfig
                .Builder(application)
                //添加baseUrl拦截器；公共请求参数拦截器；动态参数拦截器
                //.setAddInterceptor(new BaseUrlInterceptor()
                //    , new DynamicParameterInterceptor()
                //    , new CommonParameternterceptor(getCommonParameters(BaseApplication.getAppInstance()))
                // )
                //开启缓存策略(默认false)
                //1、在有网络的时候，先去读缓存，缓存时间到了，再去访问网络获取数据；
                //2、在没有网络的时候，去读缓存中的数据。
                .setCache(false)
                //全局持久话cookie,保存到内存（new MemoryCookieStore()）或者保存到本地（new SPCookieStore(this)）
                //不设置的话，默认不对cookie做处理
                .setCookieType(new SPCookieStore(application))
                //可以添加自己的拦截器(比如使用自己熟悉三方的缓存库等等)
                //.setAddInterceptor(null)
                //全局ssl证书认证
                //1、信任所有证书,不安全有风险（默认信任所有证书）
                //.setSslSocketFactory()
                //2、使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(cerInputStream)
                //3、使用bks证书和密码管理客户端证书（双向认证），使用预埋证书，校验服务端证书（自签名证书）
                //.setSslSocketFactory(mBksInputStream, Keys.MKF_CA_CODE, mCerInputStream)
                //全局超时配置
                .setReadTimeout(Constants.REQUEST_TIME_OUT)
                //全局超时配置
                .setWriteTimeout(Constants.REQUEST_TIME_OUT)
                //全局超时配置
                .setConnectTimeout(Constants.REQUEST_TIME_OUT)
                //全局是否打开请求log日志
                .setDebug(false)
                .build();

        RxHttpUtils
                .getInstance()
                .init(application)
                .config()
                //配置全局baseUrl
                .setBaseUrl(HOST)
                //开启全局配置
                .setOkClient(okHttpClient);
    }

    /**
     * 获取固定的公共参数
     *
     * @return 返回包含公共参数的map
     */
    public static Map<String, String> getCommonParameters(Context context) {
        Map<String, String> parameterMaps = new HashMap<>();

        return parameterMaps;
    }

}
