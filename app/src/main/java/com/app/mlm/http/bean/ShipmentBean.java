package com.app.mlm.http.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/2/21.
 */

public class ShipmentBean implements Serializable {
    private String boxid;//int 副柜id 一体式为1
    private String positionX;//int 货物所在层（1-10）
    private String positionY;//int  货物所在列
    private String elcspeed;//int xy轴电机速度 0(60%)低速/1(80%)中速/2(100%)高速
    private String chspeed;//int 取货斗电机速度 0(60%)低速/1(80%)中速/2(100%)高速
    private String ordernumber;//String 订单号，最大32字节
    private String price;//int 单位：分
    private String goodsnum;// int 暂时为1

    public String getBoxid() {
        return boxid;
    }

    public void setBoxid(String boxid) {
        this.boxid = boxid;
    }

    public String getPositionX() {
        return positionX;
    }

    public void setPositionX(String positionX) {
        this.positionX = positionX;
    }

    public String getPositionY() {
        return positionY;
    }

    public void setPositionY(String positionY) {
        this.positionY = positionY;
    }

    public String getElcspeed() {
        return elcspeed;
    }

    public void setElcspeed(String elcspeed) {
        this.elcspeed = elcspeed;
    }

    public String getChspeed() {
        return chspeed;
    }

    public void setChspeed(String chspeed) {
        this.chspeed = chspeed;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGoodsnum() {
        return goodsnum;
    }

    public void setGoodsnum(String goodsnum) {
        this.goodsnum = goodsnum;
    }
}
