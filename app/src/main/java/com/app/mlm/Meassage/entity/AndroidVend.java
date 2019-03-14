package com.app.mlm.Meassage.entity;

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
     * clientHardCode :
     * clientIp : /112.97.63.114:33702
     * hd : 101
     * num : 1
     * snm : 1903132347060000051776757
     * test : 0
     * vmCode : 0000051
     */

    private String clientHardCode;
    private String clientIp;
    private String hd;
    private String num;
    private String snm;
    private String test;
    private String vmCode;

    public String getClientHardCode() {
        return clientHardCode;
    }

    public void setClientHardCode(String clientHardCode) {
        this.clientHardCode = clientHardCode;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getHd() {
        return hd;
    }

    public void setHd(String hd) {
        this.hd = hd;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSnm() {
        return snm;
    }

    public void setSnm(String snm) {
        this.snm = snm;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public String getVmCode() {
        return vmCode;
    }

    public void setVmCode(String vmCode) {
        this.vmCode = vmCode;
    }

    @Override
    public String toString() {
        return "AndroidVend{" +
                "clientHardCode='" + clientHardCode + '\'' +
                ", clientIp='" + clientIp + '\'' +
                ", hd='" + hd + '\'' +
                ", num='" + num + '\'' +
                ", snm='" + snm + '\'' +
                ", test='" + test + '\'' +
                ", vmCode='" + vmCode + '\'' +
                '}';
    }
}
