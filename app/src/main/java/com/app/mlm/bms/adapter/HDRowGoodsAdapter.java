package com.app.mlm.bms.adapter;

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
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.bms.dialog.ConfigGoodsDetailDialog;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.adapter
 * @fileName : CHRowGoodsAdapter
 * @date : 2019/1/3  20:32
 * @describe : 货道配置 -- 单行商品Adapter
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public class HDRowGoodsAdapter extends RecyclerView.Adapter<HDRowGoodsAdapter.RowGoodsViewHolder> {
    private Context context;
    private int selectPosition;//当前操作的位置
    private List<GoodsInfo> data = new ArrayList<>();
    public HDRowGoodsAdapter(Context context, List<GoodsInfo> data){
        this.context = context;
        this.data = data;
    }
    @NonNull
    @Override
    public RowGoodsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.goods_item_layout_hdpz, viewGroup,
            false);
        RowGoodsViewHolder viewHolder = new RowGoodsViewHolder(view);
        viewHolder.ivGoodsImg = view.findViewById(R.id.ivGoodsImg);
        viewHolder.tvGoodsPrice = view.findViewById(R.id.tvGoodsPrice);
        viewHolder.tvCount = view.findViewById(R.id.tvGoodsCount);
        viewHolder.tvOrderNum = view.findViewById(R.id.tvGoodsOrder);
        viewHolder.rvRoot = view.findViewById(R.id.rvRoot);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RowGoodsViewHolder viewHolder, int i) {

        if (i == data.size() - 1) {
            viewHolder.rvRoot.setBackgroundResource(R.drawable.shape_white_rt_rb);
        }else {
            viewHolder.rvRoot.setBackgroundResource(R.color.whiteColor);
        }

        GoodsInfo goodsInfo = data.get(i);
        if (goodsInfo.getMdseUrl().equals("empty")) {
            viewHolder.ivGoodsImg.setImageResource(R.drawable.empty);
        } else {
            Glide.with(MainApp.getAppInstance()).load(goodsInfo.getMdseUrl()).into(viewHolder.ivGoodsImg);
        }

        viewHolder.tvGoodsPrice.setText("¥ " + goodsInfo.getMdsePrice());
        viewHolder.tvCount.setText(goodsInfo.getClcCapacity() + " / " + goodsInfo.getClCapacity());
        viewHolder.tvOrderNum.setText(String.valueOf(i + 1));
        viewHolder.rvRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPosition = i;
                showConfigGoodsDetailDialog(data.get(selectPosition));
            }
        });
    }

    private void showConfigGoodsDetailDialog(GoodsInfo goodsInfo) {
        ConfigGoodsDetailDialog dialog = new ConfigGoodsDetailDialog(context, goodsInfo);
        dialog.showMyDialog(new ConfigGoodsDetailDialog.ProductConfigListener() {
            @Override
            public void confirm(GoodsInfo goodsInfo) {
                data.set(selectPosition, goodsInfo);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RowGoodsViewHolder extends RecyclerView.ViewHolder {

        ImageView ivGoodsImg;
        TextView tvGoodsPrice;
        TextView tvCount;
        TextView tvOrderNum;
        View rvRoot;

        public RowGoodsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
