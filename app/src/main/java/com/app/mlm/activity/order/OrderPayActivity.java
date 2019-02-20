package com.app.mlm.activity.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.mlm.Constants;
import com.app.mlm.R;
import com.app.mlm.http.BaseResponse;
import com.app.mlm.http.JsonCallBack;
import com.app.mlm.http.bean.WxPayBean;
import com.app.mlm.utils.PreferencesUtil;
import com.app.mlm.utils.TimeCountUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

/**
 * Created by Administrator on 2018/12/30.
 */

public class OrderPayActivity extends AppCompatActivity {
    LinearLayout imageView;
    TimeCountUtils timeCount;
    TextView count_down;
    private TextView totalPriceView;
    private TextView totalNumView;
    private double totalPrice;
    private Integer totalNum;
    private String productId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_pay);
        init();
        getPayInfo();
    }

    public void init() {
        totalNum = getIntent().getIntExtra("num", 0);
        totalPrice = getIntent().getDoubleExtra("price", 0);
        productId = getIntent().getStringExtra("productId");
        imageView = (LinearLayout) findViewById(R.id.back);
        count_down = (TextView) findViewById(R.id.count_down);
        totalPriceView = findViewById(R.id.total_price);
        totalNumView = findViewById(R.id.total_num);

        totalPriceView.setText("¥ " + totalPrice);
        totalNumView.setText(totalNum + "件");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        HttpParams httpParams = new HttpParams();
        httpParams.put("vmCode", PreferencesUtil.getString(Constants.VMCODE));
        httpParams.put("productId", PreferencesUtil.getString(Constants.VMCODE) + "-1");
        httpParams.put("hd", 1);
        httpParams.put("quantity", 1);

        OkGo.<BaseResponse<WxPayBean>>post(Constants.WXPAY)
                .tag(this)
                .params(httpParams)
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
