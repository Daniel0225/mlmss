package com.app.mlm.http.bean;

/**
 * Created by Administrator on 2019/2/22 0022.
 */

public class GoodsTypeSelectBean {
    private int type = 0;//0 品牌 1 类型 2 包装
    private Integer selectType = 0;//当前选择的type
    private Integer selectPosition = 0;//当前选择type 的position

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Integer getSelectType() {
        return selectType;
    }

    public void setSelectType(Integer selectType) {
        this.selectType = selectType;
    }

    public Integer getSelectPosition() {
        return selectPosition;
    }

    public void setSelectPosition(Integer selectPosition) {
        this.selectPosition = selectPosition;
    }
}
