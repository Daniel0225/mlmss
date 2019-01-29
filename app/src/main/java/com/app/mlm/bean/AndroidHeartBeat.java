package com.app.mlm.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 维持连接的消息对象。
 * <p>
 * 创建时间：2010-7-18 上午12:22:09
 *
 * @author HouLei
 * @since 1.0
 */
public class AndroidHeartBeat implements Serializable {

    private static final long serialVersionUID = -2813120366138988480L;

    private String busType;
    private String vmCode;
    private String clientAddress;

    public AndroidHeartBeat() {
    }

    public AndroidHeartBeat(String vmCode, String busType) {
        this.vmCode = vmCode;
        this.busType = busType;

    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getVmCode() {
        return vmCode;
    }

    public void setVmCode(String vmCode) {
        this.vmCode = vmCode;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String toString() {
        return JSON.toJSONString(this);
    }
}
