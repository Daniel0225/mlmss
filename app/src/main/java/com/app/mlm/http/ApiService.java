package com.app.mlm.http;

import com.app.mlm.http.bean.BaseBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.http
 * @fileName : ApiService
 * @date : 2019/1/14  17:38
 * @describe : TODO
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public interface ApiService {

    /**
     * @param deviceId
     * @return
     */
    @Headers({"Content-type:application/json;charset=utf-8", "Accept:application/json"})
    @GET("api/getProductPrice")
    Observable<BaseBean> getHomeGoodsList(@Query("vmCode") String deviceId);
}
