package com.app.mlm.http.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/2/19 0019.
 */

public class WxPayBean implements Serializable {
    private String url;
    private String productName;
    private String salePrice;
    private Integer quantity;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
