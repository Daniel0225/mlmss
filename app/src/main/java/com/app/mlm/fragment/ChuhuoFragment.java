package com.app.mlm.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.app.mlm.Constants;
import com.app.mlm.R;
import com.app.mlm.application.MainApp;
import com.app.mlm.bms.adapter.ChuhuoAdapter;
import com.app.mlm.http.BaseResponse;
import com.app.mlm.http.JsonCallBack;
import com.app.mlm.http.bean.AllDataBean;
import com.app.mlm.http.bean.HdDataBean;
import com.app.mlm.http.bean.PickBackBean;
import com.app.mlm.http.bean.ShipmentBean;
import com.app.mlm.http.bean.SocketShipmentBean;
import com.app.mlm.http.bean.UploadShipmentStatusBean;
import com.app.mlm.utils.FastJsonUtil;
import com.app.mlm.utils.PreferencesUtil;
import com.app.mlm.utils.ToastUtil;
import com.app.mlm.utils.UpAlarmReportUtils;
import com.app.mlm.widget.SpacesItemDecoration;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

import static com.lzy.okgo.utils.HttpUtils.runOnUiThread;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChuhuoFragment extends ChuhuoBaseFragment {
/*    @Bind(R.id.tvCountDown)
    TextView tvCountDownView;*/

    public final int MSG_DOWN_SUCCESS = 2;
    @Bind(R.id.progress_circle)
    ImageView progressCircle;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.single_goods_view)
    View singleGoodsView;
    @Bind(R.id.multi_goods_view)
    View multiGoodsView;
    @Bind(R.id.tvCount)
    TextView countView;
    String json = "";
    int count = 0;
    // List<SocketShipmentBean> shipmentList = new ArrayList<>();
    SocketShipmentBean socketShipmentBean = new SocketShipmentBean();
    List<HdDataBean> hdDataBeans = new ArrayList<HdDataBean>();
    //上传取货结果
    UploadShipmentStatusBean uploadShipmentStatusBean = new UploadShipmentStatusBean();
    List<UploadShipmentStatusBean.SuccessVendInfoVo> successVendInfoVos = new ArrayList<UploadShipmentStatusBean.SuccessVendInfoVo>();
    List<UploadShipmentStatusBean.FailVendInfoVo> failVendInfoVos = new ArrayList<UploadShipmentStatusBean.FailVendInfoVo>();
    private ChuhuoAdapter chuhuoAdapter;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_DOWN_SUCCESS:
                    Toast.makeText(getContext(), "开始取第" + count + "个", Toast.LENGTH_SHORT).show();
                    countView.setText(String.format("%d/%d", count + 1, hdDataBeans.size()));
                    String hdCodeT = hdDataBeans.get(count).getHdCode();
                    if (!TextUtils.isEmpty(hdCodeT)) {
                        int one = Integer.parseInt(hdCodeT.substring(0, 1));
                        int two = Integer.parseInt(hdCodeT.substring(1, 2));
                        int three = Integer.parseInt(hdCodeT.substring(2, 3));
                        if (two == 0) {
                            pick(count, hdCodeT, one, three, socketShipmentBean.getT().getSnm(), 1);
                        } else {
                            pick(count, hdCodeT, one, Integer.parseInt(String.valueOf(two) + String.valueOf(three)), socketShipmentBean.getT().getSnm(), 1);
                        }
                    }
                    break;
            }
        }

        ;
    };

 /*   public ChuhuoFragment(String json) {
        this.json = json;
    }*/

    @Override
    protected int provideLayoutResId() {
        return R.layout.fragment_chuhuo;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initCountDownMananger();
        json = (String) getArguments().get("str");
        //shipmentList = new ArrayList<SocketShipmentBean>();
        if (!TextUtils.isEmpty(json)) {
            socketShipmentBean = FastJsonUtil.getObject(json, SocketShipmentBean.class);
            // rebackShipment();
            dealData();
        }
    }

    @Override
    protected void initListener() {

    }

    private void dealData() {
        String hd = socketShipmentBean.getT().getHd();
        Log.e("list长度", hd + "======");
        if (!hd.contains(",")) {
            //只有一条数据
            String trunString = hd.replace("#", ",");
            String[] finalData = trunString.split(",");
            HdDataBean hdDataBean = new HdDataBean();
            hdDataBean.setHdCode(finalData[0]);
            hdDataBean.setShopNum(Integer.parseInt(finalData[1]));
            hdDataBean.setShopId(Integer.parseInt(finalData[2]));
            hdDataBean.setShopUrl(finalData[3]);
            hdDataBean.setOrderProject(finalData[4]);
            hdDataBean.setSnm(socketShipmentBean.getT().getSnm());
            hdDataBean.setVmCode(socketShipmentBean.getT().getVmCode());
            hdDataBean.setNum(socketShipmentBean.getT().getNum());
            hdDataBeans.add(hdDataBean);
            Log.e("list长度", hd + "===qqqqq===" + hdDataBeans.size());
        } else {
            String[] hdData = hd.split(",");
            for (int i = 1; i < hdData.length; i++) {
                String hdback = hdData[i];
                String trunString = hdback.replace("#", ",");
                String[] finalData = trunString.split(",");
                HdDataBean hdDataBean = new HdDataBean();
                hdDataBean.setHdCode(finalData[0]);
                hdDataBean.setShopNum(Integer.parseInt(finalData[1]));
                hdDataBean.setShopId(Integer.parseInt(finalData[2]));
                hdDataBean.setShopUrl(finalData[3]);
                hdDataBean.setOrderProject(finalData[4]);
                hdDataBean.setSnm(socketShipmentBean.getT().getSnm());
                hdDataBean.setVmCode(socketShipmentBean.getT().getVmCode());
                hdDataBean.setNum(socketShipmentBean.getT().getNum());
                hdDataBeans.add(hdDataBean);
                Log.e("list长度", hd + "===wwww===" + hdDataBeans.size());
            }
        }

        // shipmentList = FastJsonUtil.getObjects(json, SocketShipmentBean.class);
        //如果是单个商品 那么现实单个的 并开始动画
        //  boolean isSingle = false;
        if (hdDataBeans.size() == 1) {
            singleGoodsView.setVisibility(View.VISIBLE);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(progressCircle, "rotation", 0f, 360f);
            objectAnimator.setDuration(4000)
                    .setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator.start();
            Log.e("list长度", hd + "===1111111===" + hdDataBeans.size());
        } else {//多个 那么初始化recyclerview
            multiGoodsView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
            recyclerView.addItemDecoration(new SpacesItemDecoration(20, 20, 20, 20));
            chuhuoAdapter = new ChuhuoAdapter(getContext(), null);
            recyclerView.setAdapter(chuhuoAdapter);
            Log.e("list长度", hd + "===22222===" + hdDataBeans.size());
        }
        Log.e("list长度", String.valueOf(hdDataBeans.size()));
        String hdCodeT = hdDataBeans.get(0).getHdCode();
        if (!TextUtils.isEmpty(hdCodeT)) {
            int one = Integer.parseInt(hdCodeT.substring(0, 1));
            int two = Integer.parseInt(hdCodeT.substring(1, 2));
            int three = Integer.parseInt(hdCodeT.substring(2, 3));
            if (two == 0) {
                pick(count, hdCodeT, one, three, socketShipmentBean.getT().getSnm(), 1);
            } else {
                pick(count, hdCodeT, one, Integer.parseInt(String.valueOf(two) + String.valueOf(three)), socketShipmentBean.getT().getSnm(), 1);
            }
        }
    }

  /*  private void rebackShipment() {
        if (!TextUtils.isEmpty(json)) {
            HttpParams httpParams = new HttpParams();
            httpParams.put("deviceId", PreferencesUtil.getString(Constants.VMCODE));
            httpParams.put("machineId", "");
            httpParams.put("snm", socketShipmentBean.getT().getSnm());
            OkGo.<BaseResponse<AllDataBean>>get(Constants.RECEVE_MSG)
                    .tag(this)
                    .params(httpParams)
                    .execute(new JsonCallBack<BaseResponse<AllDataBean>>() {
                        @Override
                        public void onSuccess(Response<BaseResponse<AllDataBean>> response) {
                          *//*  if (response.body().getCode() == 0) {
                                Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }*//*
                        }

                        @Override
                        public void onError(Response<BaseResponse<AllDataBean>> response) {
                            ToastUtil.showLongToast(response.body().getMsg());
                        }
                    });
        }
    }*/

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
    private void pick(int position, String hdCode, int positionX, int positionY, String snm, int goodNum) {
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
        Toast.makeText(getContext(), pickData, Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(getContext(), "取货第" + count + "成功", Toast.LENGTH_SHORT).show();
                                    count++;
                                    //出货成功改变状态
                                    hdDataBeans.get(position).setSuccess(true);
                                    chuhuoAdapter.refreshChuhuoStatus(position);
                                    //添加成功的数据到上传成功的model
                                    UploadShipmentStatusBean.SuccessVendInfoVo successVendInfoVo = new UploadShipmentStatusBean.SuccessVendInfoVo();
                                    successVendInfoVo.setHdId(hdCode);
                                    successVendInfoVo.setNum(1);
                                    successVendInfoVo.setItemNumber(Integer.parseInt(hdDataBeans.get(position).getOrderProject()));
                                    successVendInfoVos.add(successVendInfoVo);
                                    if (count == hdDataBeans.size()) {
                                        //处理上传接口
                                        dealUpShipmenData();
                                        //  mActivity.addFragment(new ChuhuoSuccessFragment());
                                    } else {
                                        mHandler.sendEmptyMessage(MSG_DOWN_SUCCESS);
                                    }

                                    Log.e("取货结果:", "取货成功");
                                }
                            });

                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //上传错误代码到后台
                                    Toast.makeText(getContext(), "取货第" + count + "失败", Toast.LENGTH_SHORT).show();
                                    count++;
                                    UpAlarmReportUtils.upalarmReport(context, pickBackBean.getShipresult());
                                    //添加成功的数据到上传成功的model
                                    UploadShipmentStatusBean.FailVendInfoVo failVendInfoVo = new UploadShipmentStatusBean.FailVendInfoVo();
                                    failVendInfoVo.setHdId(hdCode);
                                    failVendInfoVo.setNum(1);
                                    failVendInfoVo.setItemNumber(Integer.parseInt(hdDataBeans.get(position).getOrderProject()));
                                    failVendInfoVos.add(failVendInfoVo);
                                    if (count == hdDataBeans.size()) {
                                        //处理上传接口
                                        dealUpShipmenData();
                                        //  mActivity.addFragment(new ChuhuoSuccessFragment());
                                    } else {
                                        mHandler.sendEmptyMessage(MSG_DOWN_SUCCESS);
                                    }
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

    /**
     * 处理取货后上传数据到后台
     */
    private void dealUpShipmenData() {
        uploadShipmentStatusBean.setCtime(socketShipmentBean.getCtime());
        uploadShipmentStatusBean.setDeviceID(PreferencesUtil.getString(Constants.VMCODE));
        uploadShipmentStatusBean.setSnm(socketShipmentBean.getT().getSnm());
        uploadShipmentStatusBean.setNum(socketShipmentBean.getT().getNum());
        uploadShipmentStatusBean.setStatus("0");
        uploadShipmentStatusBean.setFailVendInfoVoList(failVendInfoVos);
        uploadShipmentStatusBean.setSuccessVendInfoVos(successVendInfoVos);
        String upJson = new Gson().toJson(uploadShipmentStatusBean);
        OkGo.<BaseResponse<AllDataBean>>post(Constants.VENDREPORT)
                .tag(this)
                .upJson(upJson)
                .execute(new JsonCallBack<BaseResponse<AllDataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<AllDataBean>> response) {
                        if (response.body().getCode() == 0) {
                            mActivity.addFragment(new ChuhuoSuccessFragment());
                            Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            mActivity.addFragment(new ChuhuoFailedFragment());
                            Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<AllDataBean>> response) {
                        ToastUtil.showLongToast(response.body().getMsg());
                    }
                });
    }
}
