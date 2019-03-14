package com.app.mlm.http.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/3/14.
 */

public class UploadShipmentStatusBean implements Serializable {
    private String deviceID;
    private String device;
    private String status;//0成功 ，1机器故障，2失败
    private String hdId;//货道
    private String type;
    private String num;
    private List<FailVendInfoVo> failVendInfoVoList;
    private List<SuccessVendInfoVo> successVendInfoVos;
    private String snm;
    private Long ctime;
    private String weight;
    private String discardNum;

    public String getDeviceID() {
        return deviceID;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHdId() {
        return hdId;
    }

    public void setHdId(String hdId) {
        this.hdId = hdId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public List<FailVendInfoVo> getFailVendInfoVoList() {
        return failVendInfoVoList;
    }

    public void setFailVendInfoVoList(List<FailVendInfoVo> failVendInfoVoList) {
        this.failVendInfoVoList = failVendInfoVoList;
    }

    public List<SuccessVendInfoVo> getSuccessVendInfoVos() {
        return successVendInfoVos;
    }

    public void setSuccessVendInfoVos(List<SuccessVendInfoVo> successVendInfoVos) {
        this.successVendInfoVos = successVendInfoVos;
    }

    public String getSnm() {
        return snm;
    }

    public void setSnm(String snm) {
        this.snm = snm;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDiscardNum() {
        return discardNum;
    }

    public void setDiscardNum(String discardNum) {
        this.discardNum = discardNum;
    }

    public static class FailVendInfoVo {
        private String hdId;//货道号
        private int num;//数量都传1
        private int itemNumber;//订单项目号

        public String getHdId() {
            return hdId;
        }

        public void setHdId(String hdId) {
            this.hdId = hdId;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getItemNumber() {
            return itemNumber;
        }

        public void setItemNumber(int itemNumber) {
            this.itemNumber = itemNumber;
        }
    }

    public static class SuccessVendInfoVo {
        private String hdId;//货道号
        private int num;//数量都传1
        private int itemNumber;//订单项目号

        public String getHdId() {
            return hdId;
        }

        public void setHdId(String hdId) {
            this.hdId = hdId;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getItemNumber() {
            return itemNumber;
        }

        public void setItemNumber(int itemNumber) {
            this.itemNumber = itemNumber;
        }
    }

}
