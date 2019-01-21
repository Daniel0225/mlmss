package com.app.mlm.bms.bean;

import java.io.Serializable;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.bms.bean
 * @fileName : TestResult
 * @date : 2019/1/19  14:47
 * @describe : 出货测试结果
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public class TestResult implements Serializable {
    private String huodaoName; //货道名称
    private String state; // 状态
    private String desc; //描述

    public String getHuodaoName() {
        return huodaoName;
    }

    public void setHuodaoName(String huodaoName) {
        this.huodaoName = huodaoName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
