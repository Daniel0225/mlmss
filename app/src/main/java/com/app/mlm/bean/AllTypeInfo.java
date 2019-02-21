package com.app.mlm.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/2/21 0021.
 */

public class AllTypeInfo implements Serializable {
    private Integer brandId;
    private String brandName;
    private Integer merchantId;
    private Integer packId;
    private String merchantType;
    private String merchantUrl;
    private String merchantMark;
    private String merchantIslog;
    private String packName;

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getPackId() {
        return packId;
    }

    public void setPackId(Integer packId) {
        this.packId = packId;
    }

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public String getMerchantUrl() {
        return merchantUrl;
    }

    public void setMerchantUrl(String merchantUrl) {
        this.merchantUrl = merchantUrl;
    }

    public String getMerchantMark() {
        return merchantMark;
    }

    public void setMerchantMark(String merchantMark) {
        this.merchantMark = merchantMark;
    }

    public String getMerchantIslog() {
        return merchantIslog;
    }

    public void setMerchantIslog(String merchantIslog) {
        this.merchantIslog = merchantIslog;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }
}
