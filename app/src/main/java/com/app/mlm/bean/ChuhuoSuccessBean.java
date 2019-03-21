package com.app.mlm.bean;

/*
 *出货成功的消息Bean
 */
public class ChuhuoSuccessBean {
    private String hdCode;//出货成功的货道号  通知首页减去对应的库存

    public ChuhuoSuccessBean(String hdCode) {
        this.hdCode = hdCode;
    }

    public String getHdCode() {
        return hdCode;
    }

    public void setHdCode(String hdCode) {
        this.hdCode = hdCode;
    }
}
