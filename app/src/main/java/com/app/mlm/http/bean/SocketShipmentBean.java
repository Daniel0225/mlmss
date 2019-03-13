package com.app.mlm.http.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/3/13.
 */

public class SocketShipmentBean implements Serializable {

    /**
     * busType : vend
     * ctime : 1552480693210
     * t : {"clientHardCode":"","clientIp":"/112.97.63.114:33687","hd":"101","num":"1","snm":"1903132032050000051936137","test":"0","vmCode":"0000051"}
     */

    private String busType;
    private long ctime;
    private TBean t;

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public TBean getT() {
        return t;
    }

    public void setT(TBean t) {
        this.t = t;
    }

    public static class TBean {
        /**
         * clientHardCode :
         * clientIp : /112.97.63.114:33687
         * hd : 101
         * num : 1
         * snm : 1903132032050000051936137
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
    }
}
