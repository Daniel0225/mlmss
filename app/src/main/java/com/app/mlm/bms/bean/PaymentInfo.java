package com.app.mlm.bms.bean;

import com.app.mlm.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.bms.bean
 * @fileName : PaymentInfo
 * @date : 2019/1/8  20:46
 * @describe : TODO
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public class PaymentInfo {
    private String name;
    private int iconId;
    private boolean isChecked;

    public PaymentInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public PaymentInfo(String name, int iconId, boolean isChecked) {
        this.name = name;
        this.iconId = iconId;
        this.isChecked = isChecked;
    }

    public static List<PaymentInfo> getPayments(){
        List<PaymentInfo> paymentInfos = new ArrayList<>();
        for(int i = 0;i < Constants.PAYMENT_NAME.length; i++){
            paymentInfos.add(new PaymentInfo(Constants.PAYMENT_NAME[i], Constants.PAYMENT_ICON[i], false));
        }
        return paymentInfos;
    }
}
