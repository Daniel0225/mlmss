package com.app.mlm.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.app.mlm.R;
import com.app.mlm.application.MainApp;
import com.app.mlm.bms.adapter.ChuhuoAdapter;
import com.app.mlm.http.bean.AdBean;
import com.app.mlm.http.bean.PickBackBean;
import com.app.mlm.http.bean.ShipmentBean;
import com.app.mlm.utils.FastJsonUtil;
import com.app.mlm.utils.UpAlarmReportUtils;
import com.app.mlm.widget.SpacesItemDecoration;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class ChuhuoFragment extends ChuhuoBaseFragment {
    @Bind(R.id.tvCountDown)
    TextView tvCountDownView;

    @Bind(R.id.progress_circle)
    ImageView progressCircle;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.single_goods_view)
    View singleGoodsView;

    @Bind(R.id.multi_goods_view)
    View multiGoodsView;
    String json = "";

    private ChuhuoAdapter chuhuoAdapter;

    @SuppressLint("ValidFragment")
    public ChuhuoFragment(String json) {
        this.json = json;
    }

    @Override
    protected int provideLayoutResId() {
        return R.layout.fragment_chuhuo;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initCountDownMananger();

        List<AdBean> shipmentList = new ArrayList<>();
        if (!TextUtils.isEmpty(json)) {
            shipmentList = FastJsonUtil.getObjects(json, AdBean.class);
            //如果是单个商品 那么现实单个的 并开始动画
            //  boolean isSingle = false;
            if (shipmentList.size() == 1) {
                singleGoodsView.setVisibility(View.VISIBLE);
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(progressCircle, "rotation", 0f, 360f);
                objectAnimator.setDuration(4000)
                        .setRepeatCount(ValueAnimator.INFINITE);
                objectAnimator.start();
            } else {//多个 那么初始化recyclerview
                multiGoodsView.setVisibility(View.VISIBLE);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
                recyclerView.addItemDecoration(new SpacesItemDecoration(20, 20, 20, 20));
                chuhuoAdapter = new ChuhuoAdapter(getContext(), null);
                recyclerView.setAdapter(chuhuoAdapter);
            }

            for (int i = 1; i < shipmentList.size(); i++) {
                //int position,int positionX, int positionY,String snm,int goodNum
                pick(i, 1, 1, "2323", 2);
            }
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean isNeedCountDown() {
        return true;
    }

   /* @Override
    public void onTick(int seconds) {
        tvCountDownView.setText(seconds + "S");
        if (seconds == 55) {
//            mActivity.addFragment(new ChuhuoFailedFragment());
//            mActivity.addFragment(new ChuhuoSuccessFragment());
        }
        if (seconds % 10 == 0) {
            chuhuoAdapter.refreshChuhuoStatus(6 - seconds / 10);
        }
    }*/

    @Override
    public void onFinish() {

    }

    /**
     * 取货
     */
    private void pick(int position, int positionX, int positionY, String snm, int goodNum) {
        ShipmentBean shipmentBean = new ShipmentBean();
        shipmentBean.setBoxid(1);
        shipmentBean.setChspeed(1);
        shipmentBean.setElcspeed(1);
        shipmentBean.setGoodsnum(goodNum);
        shipmentBean.setPrice(250);//单位分
        shipmentBean.setOrdernumber(snm);//订单号
        shipmentBean.setPositionX(positionX);//坐标x
        shipmentBean.setPositionY(positionY);//坐标y
        shipmentBean.setLaser(1);
        shipmentBean.setPickup(1);
        Log.e("--------------", positionX + "列" + positionY);
        String pickData = new Gson().toJson(shipmentBean);
        Log.e("--------------", pickData);
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String backjson = MainApp.bvmAidlInterface.BVMStartShip(pickData);
                        Log.e("back", backjson);
                        PickBackBean pickBackBean = JSON.parseObject(backjson, PickBackBean.class);
                        if (pickBackBean.getShipresult() == 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    chuhuoAdapter.refreshChuhuoStatus(position);
                                    Log.e("取货结果:", "取货成功");
                                }
                            });

                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //上传错误代码到后台
                                    UpAlarmReportUtils.upalarmReport(context, pickBackBean.getShipresult());
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
