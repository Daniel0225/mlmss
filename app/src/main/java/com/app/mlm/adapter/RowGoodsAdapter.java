package com.app.mlm.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mlm.R;
import com.app.mlm.application.MainApp;
import com.app.mlm.bean.AddShopCarEvent;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.dialog.GoodsDetailDialog;
import com.app.mlm.utils.MyDialogUtil;
import com.app.mlm.utils.ToastUtil;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.adapter
 * @fileName : CHRowGoodsAdapter
 * @date : 2019/1/3  20:32
 * @describe : 单行商品Adapter
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public class RowGoodsAdapter extends RecyclerView.Adapter<RowGoodsAdapter.RowGoodsViewHolder> {
    AlertDialog myDialogUtil;
    private Context context;
    private List<GoodsInfo> data = new ArrayList<>();
    public RowGoodsAdapter(Context context, List<GoodsInfo> data){
        this.context = context;
        this.data = data;
    }
    @NonNull
    @Override
    public RowGoodsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.goods_item_layout, viewGroup, false);
        RowGoodsViewHolder viewHolder = new RowGoodsViewHolder(view);
        viewHolder.ivNoGoods = view.findViewById(R.id.ivNoGoods);
        viewHolder.ivGoodsImg = view.findViewById(R.id.ivGoodsImg);
        viewHolder.ivAddCart = view.findViewById(R.id.ivAddCart);
        viewHolder.tvGoodsName = view.findViewById(R.id.tvGoodsName);
        viewHolder.tvGoodsPrice = view.findViewById(R.id.tvGoodsPrice);
        viewHolder.rvRoot = view.findViewById(R.id.rvRoot);
        viewHolder.hasGoodsView = view.findViewById(R.id.has_goods);
        viewHolder.noGoodsView = view.findViewById(R.id.no_goods);
        viewHolder.miniPicView = view.findViewById(R.id.mini_pic);
        viewHolder.tvActivePrice = view.findViewById(R.id.tvActivePrice);
        viewHolder.tvActivePrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
        viewHolder.sellOut = view.findViewById(R.id.sell_out);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
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
        viewHolder.tvGoodsName.setText(goodsInfo.getMdseName());
        if(goodsInfo.getActivityPrice() == 0){
            viewHolder.tvGoodsPrice.setText("¥ " + goodsInfo.getRealPrice());//显示当前售价
            viewHolder.tvActivePrice.setText("");
        }else{
            viewHolder.tvActivePrice.setText("¥" + goodsInfo.getMdsePrice());//原价价的view
            viewHolder.tvGoodsPrice.setText("¥ " + goodsInfo.getRealPrice());//当前售价的view
        }

        if (goodsInfo.getMdseUrl().equals("empty")) {
            viewHolder.noGoodsView.setVisibility(View.VISIBLE);
            viewHolder.hasGoodsView.setVisibility(View.GONE);
        } else {
            Glide.with(context).load(goodsInfo.getMdseUrl()).into(viewHolder.ivGoodsImg);
            viewHolder.noGoodsView.setVisibility(View.GONE);
            viewHolder.hasGoodsView.setVisibility(View.VISIBLE);
        }
        if (TextUtils.isEmpty(goodsInfo.getMerchantUrl())) {
            viewHolder.miniPicView.setVisibility(View.GONE);
        } else {
            Glide.with(context).load(goodsInfo.getMerchantUrl()).into(viewHolder.miniPicView);
            viewHolder.miniPicView.setVisibility(View.VISIBLE);
        }

        if (data.get(i).getClcCapacity() <= 0) {
            viewHolder.sellOut.setVisibility(View.VISIBLE);
            viewHolder.tvGoodsName.setTextColor(R.color.color_666666);
            viewHolder.ivAddCart.setBackgroundResource(R.drawable.add_gray);
            viewHolder.tvGoodsPrice.setTextColor(Color.parseColor("#666666"));
        } else {
            viewHolder.sellOut.setVisibility(View.GONE);
            viewHolder.tvGoodsName.setTextColor(Color.BLACK);
            viewHolder.ivAddCart.setBackgroundResource(R.drawable.go_nor);
            viewHolder.tvGoodsPrice.setTextColor(Color.parseColor("#ffd0021b"));
        }

        viewHolder.rvRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(i).getMdseUrl().equals("empty")) {
                    ToastUtil.showLongToast("该货道暂无商品");
                    return;
                }
                if (data.get(i).getClcCapacity() > 0) {
                    GoodsDetailDialog dialog = new GoodsDetailDialog(context, data.get(i));
                    dialog.show();
                }
            }
        });

        viewHolder.ivAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String[] status = MainApp.bvmAidlInterface.BVMGetFGFault(1);
                    int statusCode = 0;
                    for (int i = 0; i < status.length; i++) {
                        if (status[i].contains("0FA")) {
                            statusCode = 1;
                            break;
                        }
                    }
                    switch (statusCode) {
                        case 0:
                            if (data.get(i).getMdseUrl().equals("empty")) {
                                ToastUtil.showLongToast("该货道暂无商品");
                                return;
                            }
                            if (data.get(i).getClcCapacity() <= 0) {
                                ToastUtil.showLongCenterToast("该商品库存不足");
                                return;
                            }
                            MainApp.addShopCar(data.get(i));
                            EventBus.getDefault().post(new AddShopCarEvent());//发送消息到首页 更新购物车TAB角标数据
                            break;
                        case 1:
                            Toast.makeText(context, "您有未取走的商品", Toast.LENGTH_SHORT).show();
                            int salegood = MainApp.bvmAidlInterface.BVMReSaleGoods(1);
                            Log.e("开柜门返回值", String.valueOf(salegood));
                            if (salegood == 99) {
                                Log.e("开柜门返回值进入", String.valueOf(salegood));
                                myDialogUtil = MyDialogUtil.getDialog(context, initLogOutDialogView(), Gravity.CENTER);
                                myDialogUtil.show();
                            } else {
                                Log.e("开柜门失败返回值", String.valueOf(salegood));
                            }
                            break;
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public View initLogOutDialogView() {
        View verifyCodeView = LayoutInflater.from(context).inflate(R.layout.go_on_chuhuo, null);
        TextView chuhuo = verifyCodeView.findViewById(R.id.chuhuo);
        chuhuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return verifyCodeView;
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RowGoodsViewHolder extends RecyclerView.ViewHolder {
        ImageView ivNoGoods;
        ImageView ivGoodsImg;
        ImageView ivAddCart;
        ImageView miniPicView;
        TextView tvGoodsName;
        TextView tvGoodsPrice;
        TextView tvActivePrice;
        View rvRoot;
        View hasGoodsView;
        View noGoodsView;
        ImageView sellOut;

        public RowGoodsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
