package com.app.mlm.http.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/3/14.
 */

public class HdDataBean implements Serializable {
    private String hdCode;//货道号
    private int shopNum;//商品数量
    private int shopId;//商品id
    private String shopUrl;//商品图片
    private String orderProject;//订单项目
    private boolean isSuccess = false;//是否取货成功

    private String num; //总数量
    private String snm;//订单号
    private String test;//
    private String vmCode;//

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSnm() {
        return snm;
    }

    public void setSnm(String snm) {
        this.snm = snm;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getVmCode() {
        return vmCode;
    }

    public void setVmCode(String vmCode) {
        this.vmCode = vmCode;
    }

    public String getHdCode() {
        return hdCode;
    }

    public void setHdCode(String hdCode) {
        this.hdCode = hdCode;
    }

    public int getShopNum() {
        return shopNum;
    }

    public void setShopNum(int shopNum) {
        this.shopNum = shopNum;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopUrl() {
        return shopUrl;
    }

    public void setShopUrl(String shopUrl) {
        this.shopUrl = shopUrl;
    }

    public String getOrderProject() {
        return orderProject;
    }

    public void setOrderProject(String orderProject) {
        this.orderProject = orderProject;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }
}
