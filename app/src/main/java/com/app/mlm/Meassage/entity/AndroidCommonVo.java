package com.app.mlm.Meassage.entity;

import java.io.Serializable;

/**
 * 维持连接的消息对象。
 * <p>
 * 创建时间：2010-7-18 上午12:22:09
 *
 * @author HouLei
 * @since 1.0
 */
public class AndroidCommonVo implements Serializable {
    private static final long serialVersionUID = -3841481739722344086L;

    private String busType; // 客户事件
    private Long ctime;
    private String t; // 消息实体

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    public String getT() {
        return t;
    }

    public void setT(String t) {
        this.t = t;
    }

}
