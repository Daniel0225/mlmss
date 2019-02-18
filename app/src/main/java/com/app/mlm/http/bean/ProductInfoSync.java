package com.app.mlm.http.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/2/18 0018.
 */

public class ProductInfoSync implements Serializable {

    private List<AsyncInfo> data;


    class AsyncInfo {
        private List<ProductInfo> productInfoList;
        private AsyncCount asyncCount;

        public AsyncCount getAsyncCount() {
            return asyncCount;
        }

        public void setAsyncCount(AsyncCount asyncCount) {
            this.asyncCount = asyncCount;
        }

        public List<ProductInfo> getProductInfoList() {
            return productInfoList;
        }

        public void setProductInfoList(List<ProductInfo> productInfoList) {
            this.productInfoList = productInfoList;
        }
    }

    class AsyncCount {
        private String count;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }
    }

}
