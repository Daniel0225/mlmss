package com.app.mlm.activity.order;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mlm.Constants;
import com.app.mlm.R;
import com.app.mlm.application.MainApp;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.bms.dialog.CouponDialog;
import com.app.mlm.http.BaseResponse;
import com.app.mlm.http.JsonCallBack;
import com.app.mlm.http.bean.AdBean;
import com.app.mlm.http.bean.CreateWxOrderReqVo;
import com.app.mlm.http.bean.CreateWxOrderReqVoList;
import com.app.mlm.http.bean.CreateZfbOrderReqVoList;
import com.app.mlm.http.bean.WxPayBean;
import com.app.mlm.utils.FastJsonUtil;
import com.app.mlm.utils.PreferencesUtil;
import com.app.mlm.utils.TimeCountUtilsFinish;
import com.app.mlm.utils.ToastUtil;
import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/12/30.
 */

public class OrderPayActivity extends AppCompatActivity {
    LinearLayout imageView;
    TimeCountUtilsFinish timeCount;
    TextView count_down;
    CouponDialog couponDialog;//领优惠券的dialog
    @Bind(R.id.iv_wx_code)
    ImageView ivWxCode;
    @Bind(R.id.zhifubao)
    ImageView zhifubao;
    @Bind(R.id.onSaleView)
    View onSaleView;
    @Bind(R.id.free_price)
    TextView freePriceView;

    private TextView totalPriceView;
    private ImageView payAdImageView;
    private TextView totalNumView;
    private String totalPrice;
    private String originPrice;
    private Integer totalNum;
    private TextView originPriceView;
    private ArrayList<GoodsInfo> goodsInfoList;
    private IntentFilter intentFilter;
    private OrderChangeReceiver orderChangeReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_pay);
        ButterKnife.bind(this);
        init();
        initPayAdImage();
        getPayInfo();
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.mlm.app.Order");
        couponDialog = new CouponDialog(this);
        orderChangeReceiver = new OrderChangeReceiver();
        registerReceiver(orderChangeReceiver, intentFilter);
//        couponDialog.show();
    }

    public void init() {
        totalNum = getIntent().getIntExtra(Constants.TOTAL_NUM, 1);
        totalPrice = getIntent().getStringExtra(Constants.TOTAL_PRICE);
        originPrice = getIntent().getStringExtra(Constants.ORIGIN_PRICE);
        imageView = (LinearLayout) findViewById(R.id.back);
        count_down = (TextView) findViewById(R.id.count_down);
        totalPriceView = findViewById(R.id.total_price);
        totalNumView = findViewById(R.id.total_num);
        originPriceView = findViewById(R.id.origin_price);
        payAdImageView = findViewById(R.id.pay_ad_iv);

        originPriceView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
        totalPriceView.setText("¥ " + totalPrice);
        totalNumView.setText(totalNum + "件");

        double roundPrice = new BigDecimal(originPrice).subtract(new BigDecimal(totalPrice)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        if (roundPrice > 0) {//售价小于原价 显示优惠信息
            onSaleView.setVisibility(View.VISIBLE);
            originPriceView.setVisibility(View.VISIBLE);
            originPriceView.setText("¥ " + originPrice);
            freePriceView.setText("¥ " + roundPrice);

        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.show_coupon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                couponDialog.show();
            }
        });
        startTime();

    }

    private void initPayAdImage() {
        String adString = PreferencesUtil.getString(Constants.ADDATA);
        List<AdBean> adBeans = new ArrayList<>();
        if (!TextUtils.isEmpty(adString)) {
            adBeans = FastJsonUtil.getObjects(adString, AdBean.class);
        }
        for (AdBean adBean : adBeans) {
            if (adBean.getFileType() == 4) {
                Glide.with(OrderPayActivity.this).load(adBean.getUrl()).into(payAdImageView);
            }
        }
    }

    /**
     * 开启倒计时
     */
    public void startTime() {
        if (timeCount == null) {
            timeCount = new TimeCountUtilsFinish(OrderPayActivity.this, 60000, 1000, count_down);
        }
        timeCount.start(); //倒计时后重新获取
    }

    private void getPayInfo() {
        if (getIntent().hasExtra("goods")) {
            goodsInfoList = (ArrayList<GoodsInfo>) getIntent().getSerializableExtra("goods");
        } else {
            goodsInfoList = new ArrayList<>();
            goodsInfoList.addAll(MainApp.shopCarList);
        }
        List<CreateWxOrderReqVo> list = new ArrayList<>();
        for (GoodsInfo goodsInfo : goodsInfoList) {
            for (int i = 0; i < goodsInfo.getShopCarNum(); i++) {
                CreateWxOrderReqVo createWxOrderReqVo = new CreateWxOrderReqVo(PreferencesUtil.getString(Constants.VMCODE),
                        String.valueOf(goodsInfo.getMdseId()), goodsInfo.getClCode());
                Log.e("clcode", goodsInfo.getClCode());
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
                        if(response.body() == null){
                            ToastUtil.showLongCenterToast("获取微信支付信息异常");
                            return;
                        }
                        if (response.body().getCode() == 0) {
                            Glide.with(OrderPayActivity.this).load(response.body().getData().getUrl()).into(ivWxCode);
                        } else {
                            Toast.makeText(OrderPayActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<WxPayBean>> response) {
                        ToastUtil.showLongToast("请求服务器失败,请稍后重试");
                    }
                });

        HttpParams httpParams1 = new HttpParams();
        CreateZfbOrderReqVoList createZfbOrderReqVoList = new CreateZfbOrderReqVoList();
        createZfbOrderReqVoList.setCreateZfbOrderReqVoList(list);
        String zfb = FastJsonUtil.createJsonString(createZfbOrderReqVoList);
        httpParams1.put("createZfbOrderReqVoList", zfb);
        Log.e("Tag", "params " + zfb);
        OkGo.<BaseResponse<WxPayBean>>post(Constants.ALIPAY)
                .tag(this)
                .upJson(zfb)
                .execute(new JsonCallBack<BaseResponse<WxPayBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<WxPayBean>> response) {
                        if(response == null){
                            ToastUtil.showLongCenterToast("获取支付宝支付信息异常");
                            return;
                        }
                        if (response.body().getCode() == 0) {
                            Glide.with(OrderPayActivity.this).load(response.body().getData().getUrl()).into(zhifubao);
                        } else {
                            Toast.makeText(OrderPayActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<WxPayBean>> response) {
                        ToastUtil.showLongToast("请求服务器失败,请稍后重试");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timeCount != null) {
            timeCount.cancel();
        }
        unregisterReceiver(orderChangeReceiver);
    }

    //关闭订单页面广播
    class OrderChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String state = intent.getAction();
            if (state.equals(Constants.ORDER)) {
                finish();
            }
        }
    }
}
