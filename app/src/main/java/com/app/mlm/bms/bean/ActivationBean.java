package com.app.mlm.bms.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/3/8.
 */

public class ActivationBean implements Serializable {

    private int vmId;
    private int vtId;
    private int nodeId;
    private int vmModelId;
    private int vmClayers;
    private int spQty;
    private int channelQty;
    private int spSupport;
    private int touchSupport;
    private int printerSupport;
    private int posSupport;
    private int gcSupport;
    private int dbType;
    private int deleteMark;
    private int cameraSupport;
    private int ccSupport;
    private String innerCode;
    private int status;
    private String activationCode;
    private String containerOneNo;
    private String containerOneName;

    public int getVmId() {
        return vmId;
    }

    public void setVmId(int vmId) {
        this.vmId = vmId;
    }

    public int getVtId() {
        return vtId;
    }

    public void setVtId(int vtId) {
        this.vtId = vtId;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public int getVmModelId() {
        return vmModelId;
    }

    public void setVmModelId(int vmModelId) {
        this.vmModelId = vmModelId;
    }

    public int getVmClayers() {
        return vmClayers;
    }

    public void setVmClayers(int vmClayers) {
        this.vmClayers = vmClayers;
    }

    public int getSpQty() {
        return spQty;
    }

    public void setSpQty(int spQty) {
        this.spQty = spQty;
    }

    public int getChannelQty() {
        return channelQty;
    }

    public void setChannelQty(int channelQty) {
        this.channelQty = channelQty;
    }

    public int getSpSupport() {
        return spSupport;
    }

    public void setSpSupport(int spSupport) {
        this.spSupport = spSupport;
    }

    public int getTouchSupport() {
        return touchSupport;
    }

    public void setTouchSupport(int touchSupport) {
        this.touchSupport = touchSupport;
    }

    public int getPrinterSupport() {
        return printerSupport;
    }

    public void setPrinterSupport(int printerSupport) {
        this.printerSupport = printerSupport;
    }

    public int getPosSupport() {
        return posSupport;
    }

    public void setPosSupport(int posSupport) {
        this.posSupport = posSupport;
    }

    public int getGcSupport() {
        return gcSupport;
    }

    public void setGcSupport(int gcSupport) {
        this.gcSupport = gcSupport;
    }

    public int getDbType() {
        return dbType;
    }

    public void setDbType(int dbType) {
        this.dbType = dbType;
    }

    public int getDeleteMark() {
        return deleteMark;
    }

    public void setDeleteMark(int deleteMark) {
        this.deleteMark = deleteMark;
    }

    public int getCameraSupport() {
        return cameraSupport;
    }

    public void setCameraSupport(int cameraSupport) {
        this.cameraSupport = cameraSupport;
    }

    public int getCcSupport() {
        return ccSupport;
    }

    public void setCcSupport(int ccSupport) {
        this.ccSupport = ccSupport;
    }

    public String getInnerCode() {
        return innerCode;
    }

    public void setInnerCode(String innerCode) {
        this.innerCode = innerCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getContainerOneNo() {
        return containerOneNo;
    }

    public void setContainerOneNo(String containerOneNo) {
        this.containerOneNo = containerOneNo;
    }

    public String getContainerOneName() {
        return containerOneName;
    }

    public void setContainerOneName(String containerOneName) {
        this.containerOneName = containerOneName;
    }
}
