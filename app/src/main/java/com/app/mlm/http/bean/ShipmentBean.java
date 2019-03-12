package com.app.mlm.http.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/2/21.
 */

public class ShipmentBean implements Serializable {
    private int boxid;//int 副柜id 一体式为1
    private int positionX;//int 货物所在层（1-10）
    private int positionY;//int  货物所在列
    private int elcspeed;//int xy轴电机速度 0(60%)低速/1(80%)中速/2(100%)高速
    private int chspeed;//int 取货斗电机速度 0(60%)低速/1(80%)中速/2(100%)高速
    private String ordernumber;//String 订单号，最大32字节
    private int price;//int 单位：分
    private int goodsnum;// int 暂时为1
    private int laser;//激光测距
    private int pickup;//是否取货

    public int getBoxid() {
        return boxid;
    }

    public void setBoxid(int boxid) {
        this.boxid = boxid;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public int getElcspeed() {
        return elcspeed;
    }

    public void setElcspeed(int elcspeed) {
        this.elcspeed = elcspeed;
    }

    public int getChspeed() {
        return chspeed;
    }

    public void setChspeed(int chspeed) {
        this.chspeed = chspeed;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getGoodsnum() {
        return goodsnum;
    }

    public void setGoodsnum(int goodsnum) {
        this.goodsnum = goodsnum;
    }

    public int getLaser() {
        return laser;
    }

    public void setLaser(int laser) {
        this.laser = laser;
    }

    public int getPickup() {
        return pickup;
    }

    public void setPickup(int pickup) {
        this.pickup = pickup;
    }
}
