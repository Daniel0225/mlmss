package com.app.mlm.http.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/3/17.
 */

public class CreateZfbOrderReqVoList {
    private List<CreateWxOrderReqVo> createZfbOrderReqVoList;

    public List<CreateWxOrderReqVo> getCreateZfbOrderReqVoList() {
        return createZfbOrderReqVoList;
    }

    public void setCreateZfbOrderReqVoList(List<CreateWxOrderReqVo> createZfbOrderReqVoList) {
        this.createZfbOrderReqVoList = createZfbOrderReqVoList;
    }
}
