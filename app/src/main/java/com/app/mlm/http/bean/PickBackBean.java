package com.app.mlm.http.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/3/1.
 */

public class PickBackBean implements Serializable {
    private String boxid;//货柜
    private int shipresult;//取货 0表示成功，其他表示失败
    private String ordernumber;//出货结果订单号
    private String ordertime;//订单时间
    private String laserresult;//单位：mm 激光测距结果，当选择进行激光测距时，返回测距数值，不选择激光测距，结果为0:

    public String getBoxid() {
        return boxid;
    }

    public void setBoxid(String boxid) {
        this.boxid = boxid;
    }

    public int getShipresult() {
        return shipresult;
    }

    public void setShipresult(int shipresult) {
        this.shipresult = shipresult;
    }

    public String getOrdernumber() {
        return ordernumber;
    }

    public void setOrdernumber(String ordernumber) {
        this.ordernumber = ordernumber;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
    }

    public String getLaserresult() {
        return laserresult;
    }

    public void setLaserresult(String laserresult) {
        this.laserresult = laserresult;
    }
}
