package com.app.mlm.http.bean;

import android.databinding.BaseObservable;
import android.text.TextUtils;

import java.io.Serializable;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.http.bean
 * @fileName : BaseBean
 * @date : 2019/1/14  17:40
 * @describe : TODO
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public class BaseBean<T> extends BaseObservable implements Serializable {
    protected T data;
    private String msg;
    private String code;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSuccess(){
        if(getData() == null){
            return false;
        }
        return "0".equals(getCode());
    }
}