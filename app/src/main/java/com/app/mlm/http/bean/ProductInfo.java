package com.app.mlm.http.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/2/18 0018.
 */

public class ProductInfo extends LitePalSupport implements Serializable {

    /**
     * mdseId : 1
     * mdseName : 七度空间日用卫生巾纯棉10片245mm
     * mdseNumber : BH-MLM-333
     * mdseBrand : 七度空间
     * mdseTypeOne : 咪哩猫
     * mdseTypeTwo : 成人用品
     * mdsePack : 袋装
     * mdseContent : 10
     * mdseUnit : 个
     * mdsePrice : 8
     * mdseStatus : 上架
     * mdseTx : 1546314150000
     * mdseUrl : http://vm.minimall24h.com/Public/images/product/333.jpg
     * merchantId : 1
     * mdseExpiration : 30
     * mdseDesc : 矿泉水
     * firstLetter 首字母
     */

    private int mdseId;
    private String mdseName;
    private String mdseNumber;
    private String mdseBrand;
    private String mdseTypeOne;
    private String mdseTypeTwo;
    private String mdsePack;
    private int mdseContent;
    private String mdseUnit;
    private double mdsePrice;
    private String mdseStatus;
    private long mdseTx;
    private String mdseUrl;
    private int merchantId;
    private String mdseExpiration;
    private String mdseDesc;
    private String quanping;
    private String firstLetter;
    private String merchantUrl;

    public String getMerchantUrl() {
        return merchantUrl;
    }

    public void setMerchantUrl(String merchantUrl) {
        this.merchantUrl = merchantUrl;
    }

    public int getMdseId() {
        return mdseId;
    }

    public void setMdseId(int mdseId) {
        this.mdseId = mdseId;
    }

    public String getMdseName() {
        return mdseName;
    }

    public void setMdseName(String mdseName) {
        this.mdseName = mdseName;
    }

    public String getMdseNumber() {
        return mdseNumber;
    }

    public void setMdseNumber(String mdseNumber) {
        this.mdseNumber = mdseNumber;
    }

    public String getMdseBrand() {
        return mdseBrand;
    }

    public void setMdseBrand(String mdseBrand) {
        this.mdseBrand = mdseBrand;
    }

    public String getMdseTypeOne() {
        return mdseTypeOne;
    }

    public void setMdseTypeOne(String mdseTypeOne) {
        this.mdseTypeOne = mdseTypeOne;
    }

    public String getMdseTypeTwo() {
        return mdseTypeTwo;
    }

    public void setMdseTypeTwo(String mdseTypeTwo) {
        this.mdseTypeTwo = mdseTypeTwo;
    }

    public String getMdsePack() {
        return mdsePack;
    }

    public void setMdsePack(String mdsePack) {
        this.mdsePack = mdsePack;
    }

    public int getMdseContent() {
        return mdseContent;
    }

    public void setMdseContent(int mdseContent) {
        this.mdseContent = mdseContent;
    }

    public String getMdseUnit() {
        return mdseUnit;
    }

    public void setMdseUnit(String mdseUnit) {
        this.mdseUnit = mdseUnit;
    }

    public double getMdsePrice() {
        return mdsePrice;
    }

    public void setMdsePrice(double mdsePrice) {
        this.mdsePrice = mdsePrice;
    }

    public String getMdseStatus() {
        return mdseStatus;
    }

    public void setMdseStatus(String mdseStatus) {
        this.mdseStatus = mdseStatus;
    }

    public long getMdseTx() {
        return mdseTx;
    }

    public void setMdseTx(long mdseTx) {
        this.mdseTx = mdseTx;
    }

    public String getMdseUrl() {
        return mdseUrl;
    }

    public void setMdseUrl(String mdseUrl) {
        this.mdseUrl = mdseUrl;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    public String getMdseExpiration() {
        return mdseExpiration;
    }

    public void setMdseExpiration(String mdseExpiration) {
        this.mdseExpiration = mdseExpiration;
    }

    public String getMdseDesc() {
        return mdseDesc;
    }

    public void setMdseDesc(String mdseDesc) {
        this.mdseDesc = mdseDesc;
    }

    public String getQuanping() {
        return quanping;
    }

    public void setQuanping(String quanping) {
        this.quanping = quanping;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }
}
