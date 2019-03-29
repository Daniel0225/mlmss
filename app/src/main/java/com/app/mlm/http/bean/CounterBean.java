package com.app.mlm.http.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/3/2 0002.
 */

public class CounterBean implements Serializable {
    private int vmId;
    private String vmName;
    private String innerCode;

    public int getVmId() {
        return vmId;
    }

    public void setVmId(int vmId) {
        this.vmId = vmId;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getInnerCode() {
        return innerCode;
    }

    public void setInnerCode(String innerCode) {
        this.innerCode = innerCode;
    }
}
