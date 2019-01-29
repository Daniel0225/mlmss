package com.app.mlm.Meassage.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * 出货
 * 维持连接的消息对象。
 * <p>
 * 创建时间：2010-7-18 上午12:22:09
 *
 * @author HouLei
 * @since 1.0
 */
public class AndroidVend implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int hd; // 货道
    private int num; // 数量（默认1）
    private int test; // 是否测试（1是，0否）
    private String snm; // 订单号

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public int getHd() {
        return hd;
    }

    public void setHd(int hd) {
        this.hd = hd;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

    public String getSnm() {
        return snm;
    }

    public void setSnm(String snm) {
        this.snm = snm;
    }

    public String toString() {
        return JSON.toJSONString(this);
    }
}
