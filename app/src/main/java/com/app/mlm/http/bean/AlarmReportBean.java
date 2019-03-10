package com.app.mlm.http.bean;

/**
 * Created by Administrator on 2019/3/9.
 */

public class AlarmReportBean {

    /**
     * msg : 处理成功
     * code : 0
     * data : null
     */

    private String msg;
    private int code;
    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
