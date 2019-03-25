package com.app.mlm.http.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/3/25.
 */

public class MaintainBackBean implements Serializable {

    /**
     * busType : gzhJump
     * ctime : 1553496614354
     * t : {"operationId":1}
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
         * operationId : 1
         */

        private int operationId;

        public int getOperationId() {
            return operationId;
        }

        public void setOperationId(int operationId) {
            this.operationId = operationId;
        }
    }
}
