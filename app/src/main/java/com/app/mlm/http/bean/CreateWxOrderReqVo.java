package com.app.mlm.http.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/3/1 0001.
 */

public class CreateWxOrderReqVo implements Serializable {
    private String vmCode;
    private String productId;
    private String hd;
    private Integer quantity;


    public CreateWxOrderReqVo() {
    }

    public CreateWxOrderReqVo(String vmCode, String productId, String hd, Integer quantity) {
        this.vmCode = vmCode;
        this.productId = productId;
        this.hd = hd;
        this.quantity = quantity;
    }

    public String getVmCode() {
        return vmCode;
    }

    public void setVmCode(String vmCode) {
        this.vmCode = vmCode;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getHd() {
        return hd;
    }

    public void setHd(String hd) {
        this.hd = hd;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
