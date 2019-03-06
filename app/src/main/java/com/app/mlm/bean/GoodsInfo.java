package com.app.mlm.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.bean
 * @fileName : GoodsInfo
 * @date : 2019/1/3  20:36
 * @describe : TODO
 * @email : xing.luo@taojiji.com
 */
@Entity
public class GoodsInfo implements Serializable {
    private static final long serialVersionUID = 536871008L;
    private Long id;
    private int mdseId;//商品ID
    private String mdsePrice;
    private String mdseBrand;//品牌
    private String mdsePack;//商品包装类型
    private String merchantType;//商家类型
    private String mdseName;
    private String mdseUrl;
    private String clCode;//货道CODE
    private int shopCarNum = 1;
    private String vmCode;//售货机Code
    private Integer vmId;//售货机ID
    private Integer clId;//货道ID
    private Integer vmClayers = 0;//售货机货道层数
    private double clong;//长度
    private double cwidth;//宽度
    private double cheight;//高度
    private Integer realPrice = 0;//实际零售价
    private Integer clCapacity = 0;//货道容量
    private Integer clcCapacity = 0;//货道当前容量
    private String threshold;//阀值
    private Integer Replenish;//补货数
    private Integer channelType;//货道类型
    private String priductBatch;//产品批次

    @Generated(hash = 1892760506)
    public GoodsInfo(Long id, int mdseId, String mdsePrice, String mdseBrand,
                     String mdsePack, String merchantType, String mdseName, String mdseUrl,
                     String clCode, int shopCarNum, String vmCode, Integer vmId,
                     Integer clId, Integer vmClayers, double clong, double cwidth,
                     double cheight, Integer realPrice, Integer clCapacity,
                     Integer clcCapacity, String threshold, Integer Replenish,
                     Integer channelType, String priductBatch) {
        this.id = id;
        this.mdseId = mdseId;
        this.mdsePrice = mdsePrice;
        this.mdseBrand = mdseBrand;
        this.mdsePack = mdsePack;
        this.merchantType = merchantType;
        this.mdseName = mdseName;
        this.mdseUrl = mdseUrl;
        this.clCode = clCode;
        this.shopCarNum = shopCarNum;
        this.vmCode = vmCode;
        this.vmId = vmId;
        this.clId = clId;
        this.vmClayers = vmClayers;
        this.clong = clong;
        this.cwidth = cwidth;
        this.cheight = cheight;
        this.realPrice = realPrice;
        this.clCapacity = clCapacity;
        this.clcCapacity = clcCapacity;
        this.threshold = threshold;
        this.Replenish = Replenish;
        this.channelType = channelType;
        this.priductBatch = priductBatch;
    }

    @Generated(hash = 1227172248)
    public GoodsInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMdseId() {
        return mdseId;
    }

    public void setMdseId(int mdseId) {
        this.mdseId = mdseId;
    }

    public String getMdsePrice() {
        return mdsePrice;
    }

    public void setMdsePrice(String mdsePrice) {
        this.mdsePrice = mdsePrice;
    }

    public String getMdseBrand() {
        return mdseBrand;
    }

    public void setMdseBrand(String mdseBrand) {
        this.mdseBrand = mdseBrand;
    }

    public String getMdsePack() {
        return mdsePack;
    }

    public void setMdsePack(String mdsePack) {
        this.mdsePack = mdsePack;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public String getMdseName() {
        return mdseName;
    }

    public void setMdseName(String mdseName) {
        this.mdseName = mdseName;
    }

    public String getMdseUrl() {
        return mdseUrl;
    }

    public void setMdseUrl(String mdseUrl) {
        this.mdseUrl = mdseUrl;
    }

    public String getClCode() {
        return clCode;
    }

    public void setClCode(String clCode) {
        this.clCode = clCode;
    }

    public int getShopCarNum() {
        return shopCarNum;
    }

    public void setShopCarNum(int shopCarNum) {
        this.shopCarNum = shopCarNum;
    }

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

    public Integer getReplenish() {
        return Replenish;
    }

    public void setReplenish(Integer replenish) {
        Replenish = replenish;
    }

    public Integer getChannelType() {
        return channelType;
    }

    public void setChannelType(Integer channelType) {
        this.channelType = channelType;
    }

    public String getPriductBatch() {
        return priductBatch;
    }

    public void setPriductBatch(String priductBatch) {
        this.priductBatch = priductBatch;
    }
}
