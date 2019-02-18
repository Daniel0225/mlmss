package com.app.mlm.http.bean;

/**
 * Created by Administrator on 2019/2/18 0018.
 */

public class InsertOneVo {
    private String vmCode;//售货机CODE
    private Integer vmId;//售货机id
    private Integer clId;//货道Id
    private String clCode;//货道CODE
    private Integer mdseId;//商品ID
    private Integer vmClayers = 0;//售货机货道层数
    private double clong;//长度
    private double cwidth;//宽度
    private double cheight;//高度
    private Integer realPrice = 0;//实际零售价
    private Integer clCapacity = 0;//货道容量
    private Integer clcCapacity = 0;//货道当前容量
    private String threshold;//阀值

    public String getVmCode() {
        return vmCode;
    }

    public void setVmCode(String vmCode) {
        this.vmCode = vmCode;
    }

    public Integer getVmId() {
        return vmId;
    }

    public void setVmId(Integer vmId) {
        this.vmId = vmId;
    }

    public Integer getClId() {
        return clId;
    }

    public void setClId(Integer clId) {
        this.clId = clId;
    }

    public String getClCode() {
        return clCode;
    }

    public void setClCode(String clCode) {
        this.clCode = clCode;
    }

    public Integer getMdseId() {
        return mdseId;
    }

    public void setMdseId(Integer mdseId) {
        this.mdseId = mdseId;
    }

    public Integer getVmClayers() {
        return vmClayers;
    }

    public void setVmClayers(Integer vmClayers) {
        this.vmClayers = vmClayers;
    }

    public double getClong() {
        return clong;
    }

    public void setClong(double clong) {
        this.clong = clong;
    }

    public double getCwidth() {
        return cwidth;
    }

    public void setCwidth(double cwidth) {
        this.cwidth = cwidth;
    }

    public double getCheight() {
        return cheight;
    }

    public void setCheight(double cheight) {
        this.cheight = cheight;
    }

    public Integer getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(Integer realPrice) {
        this.realPrice = realPrice;
    }

    public Integer getClCapacity() {
        return clCapacity;
    }

    public void setClCapacity(Integer clCapacity) {
        this.clCapacity = clCapacity;
    }

    public Integer getClcCapacity() {
        return clcCapacity;
    }

    public void setClcCapacity(Integer clcCapacity) {
        this.clcCapacity = clcCapacity;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }
}
