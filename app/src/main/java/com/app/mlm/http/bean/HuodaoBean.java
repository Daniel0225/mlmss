package com.app.mlm.http.bean;

import com.app.mlm.bean.GoodsInfo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/2/21 0021.
 */

public class HuodaoBean implements Serializable {
    private List<List<GoodsInfo>> allDataList;

    public HuodaoBean() {
    }

    public HuodaoBean(List<List<GoodsInfo>> allDataList) {
        this.allDataList = allDataList;
    }

    public List<List<GoodsInfo>> getAllDataList() {
        return allDataList;
    }

    public void setAllDataList(List<List<GoodsInfo>> allDataList) {
        this.allDataList = allDataList;
    }
}
