package com.app.mlm.http.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/3/12.
 */

public class UpTemperatureBean implements Serializable {

    /**
     * msg : 处理成功
     * code : 0
     * data : {}
     */

    private String msg;
    private int code;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
    }
}
