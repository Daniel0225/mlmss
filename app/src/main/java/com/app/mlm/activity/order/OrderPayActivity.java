package com.app.mlm.activity.order;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.mlm.Constants;
import com.app.mlm.R;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.bms.dialog.CouponDialog;
import com.app.mlm.http.BaseResponse;
import com.app.mlm.http.JsonCallBack;
import com.app.mlm.http.bean.CreateWxOrderReqVo;
import com.app.mlm.http.bean.CreateWxOrderReqVoList;
import com.app.mlm.http.bean.WxPayBean;
import com.app.mlm.utils.FastJsonUtil;
import com.app.mlm.utils.PreferencesUtil;
import com.app.mlm.utils.TimeCountUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/30.
 */

public class OrderPayActivity extends AppCompatActivity {
    LinearLayout imageView;
    TimeCountUtils timeCount;
    TextView count_down;
    CouponDialog couponDialog;//领优惠券的dialog
    private TextView totalPriceView;
    private TextView totalNumView;
    private double totalPrice;
    private Integer totalNum;
    private TextView originPriceView;
    private ArrayList<GoodsInfo> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_pay);
        init();
        getPayInfo();
        couponDialog = new CouponDialog(this);
        couponDialog.show();
    }

    public void init() {
        totalNum = getIntent().getIntExtra(Constants.TOTAL_NUM, 1);
        totalPrice = getIntent().getDoubleExtra(Constants.TOTAL_PRICE, 0);
        data = (ArrayList<GoodsInfo>) getIntent().getSerializableExtra("goods");
        imageView = (LinearLayout) findViewById(R.id.back);
        count_down = (TextView) findViewById(R.id.count_down);
        totalPriceView = findViewById(R.id.total_price);
        totalNumView = findViewById(R.id.total_num);
        originPriceView = findViewById(R.id.origin_price);

        originPriceView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        totalPriceView.setText("¥ " + totalPrice);
        totalNumView.setText(totalNum + "件");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.show_coupon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                couponDialog.show();
            }
        });
        startTime();
    }

    /**
     * 开启倒计时
     */
    public void startTime() {
        if (timeCount == null) {
            timeCount = new TimeCountUtils(60000, 1000, count_down);
        }
        timeCount.start(); //倒计时后重新获取
    }

    private void getPayInfo() {

        List<CreateWxOrderReqVo> list = new ArrayList<>();
        for (GoodsInfo goodsInfo : data) {
            for (int i = 0; i < goodsInfo.getShopCarNum(); i++) {
                CreateWxOrderReqVo createWxOrderReqVo = new CreateWxOrderReqVo(PreferencesUtil.getString(Constants.VMCODE),
                        String.valueOf(goodsInfo.getMdseId()), goodsInfo.getClCode());
                list.add(createWxOrderReqVo);
            }
        }

        HttpParams httpParams = new HttpParams();
        CreateWxOrderReqVoList createWxOrderReqVoList = new CreateWxOrderReqVoList();
        createWxOrderReqVoList.setCreateWxOrderReqVoList(list);
        String jsonString = FastJsonUtil.createJsonString(createWxOrderReqVoList);
        httpParams.put("createWxOrderReqVoList", jsonString);
        Log.e("Tag", "params " + jsonString);

        OkGo.<BaseResponse<WxPayBean>>post(Constants.WXPAY)
                .tag(this)
                .upJson(jsonString)
                .execute(new JsonCallBack<BaseResponse<WxPayBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<WxPayBean>> response) {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeCount != null) {
            timeCount.cancel();
        }
    }
}
