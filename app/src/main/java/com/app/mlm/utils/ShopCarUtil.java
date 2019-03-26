package com.app.mlm.utils;

import com.app.mlm.bean.GoodsInfo;

import java.util.List;

/**
 * Created by Administrator on 2019/3/26 0026.
 */

public class ShopCarUtil {
    public static int getShopCarNum(List<GoodsInfo> shopCarList) {
        int shopCarNum = 0;
        for (GoodsInfo goodsInfo : shopCarList) {
            shopCarNum += goodsInfo.getShopCarNum();
        }
        return shopCarNum;
    }
}
