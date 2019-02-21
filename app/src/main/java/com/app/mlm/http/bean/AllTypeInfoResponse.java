package com.app.mlm.http.bean;

import com.app.mlm.bean.AllTypeInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/2/21 0021.
 */

public class AllTypeInfoResponse implements Serializable {
    private List<AllTypeInfo> type;
    private List<AllTypeInfo> brand;
    private List<AllTypeInfo> pack;

    public List<AllTypeInfo> getType() {
        return type;
    }

    public void setType(List<AllTypeInfo> type) {
        this.type = type;
    }

    public List<AllTypeInfo> getBrand() {
        return brand;
    }

    public void setBrand(List<AllTypeInfo> brand) {
        this.brand = brand;
    }

    public List<AllTypeInfo> getPack() {
        return pack;
    }

    public void setPack(List<AllTypeInfo> pack) {
        this.pack = pack;
    }
}
