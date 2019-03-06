package com.app.mlm.http.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/3/2 0002.
 */

public class CounterBean implements Serializable {
    private String counterNumber;
    private String counterName;

    public String getCounterNumber() {
        return counterNumber;
    }

    public void setCounterNumber(String counterNumber) {
        this.counterNumber = counterNumber;
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }
}
