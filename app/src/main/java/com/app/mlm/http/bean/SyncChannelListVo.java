package com.app.mlm.http.bean;

import com.app.mlm.bean.GoodsInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/2/26 0026.
 */

public class SyncChannelListVo implements Serializable {
    private List<GoodsInfo> syncChannelVoList;
    private String vmCode;
    private int operationId;

    public SyncChannelListVo() {
    }

    public SyncChannelListVo(List<GoodsInfo> syncChannelVoList, String vmCode, int operationId) {
        this.syncChannelVoList = syncChannelVoList;
        this.vmCode = vmCode;
        this.operationId = operationId;
    }

    public int getOperationId() {
        return operationId;
    }

    public void setOperationId(int operationId) {
        this.operationId = operationId;
    }

    public List<GoodsInfo> getSyncChannelVoList() {
        return syncChannelVoList;
    }

    public void setSyncChannelVoList(List<GoodsInfo> syncChannelVoList) {
        this.syncChannelVoList = syncChannelVoList;
    }

    public String getVmCode() {
        return vmCode;
    }

    public void setVmCode(String vmCode) {
        this.vmCode = vmCode;
    }
}
