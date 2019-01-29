package com.app.mlm.Meassage.entity;

import com.alibaba.fastjson.JSON;

/**
 * 出货报告
 *
 * @author Administrator
 */
public class VendOutEntity {
    String deviceId;// 机器号
    int device;// 设备（定值，1指当前设备）
    int status;// 出货状态 0出货成功 1出货未知 2出货失败
    int hdId;// 货道id(定值1，只有一个货道)
    int type;// 出货类型 1为APC要求出货。其他为其他方式要求出货
    int num;// 榨汁所用橙子数量
    int dropnum;// 榨汁丢弃橙子数量
    String snm;// 订单号
    int weight;//重量
    long ctime;// 创建时间

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    public int getDevice() {
        return device;
    }

    public void setDevice(int device) {
        this.device = device;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getHdId() {
        return hdId;
    }

    public void setHdId(int hdId) {
        this.hdId = hdId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getDropnum() {
        return dropnum;
    }

    public void setDropnum(int dropnum) {
        this.dropnum = dropnum;
    }


    public String getSnm() {
        return snm;
    }

    public void setSnm(String snm) {
        this.snm = snm;
    }


    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String toString() {
        return JSON.toJSONString(this);
    }
}
