package com.app.mlm.http;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/28.
 */

public class BaseResponse<T> implements Serializable {
    public T data;
    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
