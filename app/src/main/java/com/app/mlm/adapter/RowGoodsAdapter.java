package com.app.mlm.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mlm.R;
import com.app.mlm.application.MainApp;
import com.app.mlm.bean.AddShopCarEvent;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.dialog.GoodsDetailDialog;
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

        viewHolder.tvGoodsName.setText(goodsInfo.getMdseName());
        viewHolder.tvGoodsPrice.setText("¥ " + goodsInfo.getMdsePrice());
        if (goodsInfo.getMdseUrl().equals("empty")) {
            viewHolder.ivGoodsImg.setImageResource(R.drawable.empty);
        } else {
            Glide.with(context).load(goodsInfo.getMdseUrl()).into(viewHolder.ivGoodsImg);
        }

        viewHolder.rvRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(i).getMdseUrl().equals("empty")) {
                    ToastUtil.showLongToast("该货道暂无商品");
                    return;
                }
                GoodsDetailDialog dialog = new GoodsDetailDialog(context, data.get(i));
                dialog.show();
            }
        });

        viewHolder.ivAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(i).getMdseUrl().equals("empty")) {
                    ToastUtil.showLongToast("该货道暂无商品");
                    return;
                }
                MainApp.addShopCar(data.get(i));
                ToastUtil.showLongToast("加入成功");
                EventBus.getDefault().post(new AddShopCarEvent());//发送消息到首页 更新购物车TAB角标数据
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RowGoodsViewHolder extends RecyclerView.ViewHolder {
        ImageView ivNoGoods;
        ImageView ivGoodsImg;
        ImageView ivAddCart;
        TextView tvGoodsName;
        TextView tvGoodsPrice;
        View rvRoot;

        public RowGoodsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
