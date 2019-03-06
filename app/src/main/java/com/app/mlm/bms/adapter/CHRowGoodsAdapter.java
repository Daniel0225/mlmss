package com.app.mlm.bms.adapter;

import android.content.Context;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.app.mlm.R;
import com.app.mlm.application.MainApp;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.bms.activity.ChuhuoTestActivity;
import com.app.mlm.bms.bean.TestResult;
import com.app.mlm.bms.dialog.CommonDialog;
import com.app.mlm.bms.dialog.TestResultDialog;
import com.app.mlm.bms.dialog.TestingDialog;
import com.app.mlm.http.bean.PickBackBean;
import com.app.mlm.http.bean.ShipmentBean;
import com.app.mlm.utils.Loading;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.adapter
 * @fileName : CHRowGoodsAdapter
 * @date : 2019/1/3  20:32
 * @describe : 出货测试 --  单行商品Adapter
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public class CHRowGoodsAdapter extends RecyclerView.Adapter<CHRowGoodsAdapter.RowGoodsViewHolder> {
    private Context context;
    private List<GoodsInfo> data = new ArrayList<>();
    private Loading loading;
    private int row;

    public CHRowGoodsAdapter(Context context, List<GoodsInfo> data, int row) {
        this.context = context;
        this.data = data;
        this.row = row;
    }
    @NonNull
    @Override
    public RowGoodsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.goods_item_layout_bms, viewGroup,
            false);
        RowGoodsViewHolder viewHolder = new RowGoodsViewHolder(view);
        viewHolder.ivGoodsImg = view.findViewById(R.id.ivGoodsImg);
        viewHolder.tvTest = view.findViewById(R.id.tvTest);
        viewHolder.rvRoot = view.findViewById(R.id.rvRoot);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RowGoodsViewHolder viewHolder, int i) {
        if(i == 0){
            viewHolder.rvRoot.setBackgroundResource(R.drawable.shape_white_lt_lb);
        } else if (i == data.size() - 1) {
            viewHolder.rvRoot.setBackgroundResource(R.drawable.shape_white_rt_rb);
        }else {
            viewHolder.rvRoot.setBackgroundResource(R.color.whiteColor);
        }

        GoodsInfo goodsInfo = data.get(i);
        if (goodsInfo.getMdseUrl().equals("empty")) {
            viewHolder.ivGoodsImg.setImageResource(R.drawable.empty);
        } else {
            Glide.with(context).load(goodsInfo.getMdseUrl()).into(viewHolder.ivGoodsImg);
        }
        viewHolder.tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  if(ChuhuoTestActivity.isInited){
                    showTestingDialog();
                }else {
                    showCustomDialog();
                }*/
                CommonDialog commonDialog = new CommonDialog(context, "出货测试", "是否确认出货", "确定", "取消")
                        .setCommitClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.e("取货", "第" + row + "行" + "第" + i + "列");
                                String result = (row + 1) + "0" + (i + 1);
                                TestResult testResult = new TestResult();
                                testResult.setHuodaoName(result);
                                testResult.setState("0");
                                testResult.setDesc("出货成功");
                                TestResultDialog dialog = new TestResultDialog(context, testResult);
                                dialog.show();
                                Toast.makeText(context, "第" + row + "行" + "第" + i + "列", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setCancelClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(context, "取消", Toast.LENGTH_SHORT).show();
                            }
                        });
                commonDialog.show();
            }
        });
    }

    /**
     * 取货
     */
    private void pick() {
        ShipmentBean shipmentBean = new ShipmentBean();
        shipmentBean.setBoxid("1");
        shipmentBean.setChspeed("1");
        shipmentBean.setElcspeed("1");
        shipmentBean.setGoodsnum("1");
        shipmentBean.setPrice("250");//单位分
        shipmentBean.setOrdernumber("3423423");//订单号
        shipmentBean.setPositionX("2");
        shipmentBean.setPositionY("3");
        String pickData = new Gson().toJson(shipmentBean);
        try {
            String backjson = MainApp.bvmAidlInterface.BVMStartShip(pickData);
            loading = Loading.newLoading(context, "取货中...");
            PickBackBean pickBackBean = JSON.parseObject(backjson, PickBackBean.class);
            if (pickBackBean.getShipresult() == 0) {
                loading.dismiss();
                Log.e("取货结果:", "取货成功");
            } else {
                //上传错误代码到后台
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void showTestingDialog() {
        TestingDialog dialog = new TestingDialog(context);
        dialog.show();
    }

    private void showCustomDialog() {
        CommonDialog commonDialog = new CommonDialog(context, "货道初始化", "点击初始化按钮，然后等待初始化完成", "初始化","完成")
            .setCommitClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doInit();
                    Toast.makeText(context, "初始化", Toast.LENGTH_SHORT).show();
                }
            })
            .setCancelClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "完成", Toast.LENGTH_SHORT).show();
                }
            });
        commonDialog.show();
    }

    /**
     * 执行初始化操作
     */
    private void doInit() {
        //初始化完成，重置ChuhuoTestActivity.isInited = true;
        ChuhuoTestActivity.isInited = true;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RowGoodsViewHolder extends RecyclerView.ViewHolder {

        ImageView ivGoodsImg;
        TextView tvTest;
        View rvRoot;

        public RowGoodsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
