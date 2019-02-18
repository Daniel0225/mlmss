package com.app.mlm.bms.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mlm.R;
import com.app.mlm.application.MainApp;
import com.app.mlm.http.bean.ProductInfo;
import com.bumptech.glide.Glide;

import java.util.List;

public class ChooseGoodsAdapter extends RecyclerView.Adapter<ChooseGoodsAdapter.MyViewHolder>{

    private List<ProductInfo> mList;
    private CustomClickListener listener;

    public ChooseGoodsAdapter(List<ProductInfo> mList, CustomClickListener listener) {
        this.mList = mList;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_item_choose_list,
            parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ProductInfo item = mList.get(position);
        holder.tvGoodsName.setText(item.getMdseName());
        holder.tvGoodsPrice.setText("¥ " + item.getMdsePrice());
        Glide.with(MainApp.getAppInstance()).load(item.getMdseUrl()).into(holder.ivGoodsImg);
        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface CustomClickListener {
        void onClick(int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView ivGoodsImg;
        TextView tvGoodsName;
        TextView tvGoodsPrice;
        View mRoot;
        public MyViewHolder(View itemView) {
            super(itemView);
            ivGoodsImg = (ImageView)itemView.findViewById(R.id.ivGoodsImg);
            tvGoodsName = (TextView)itemView.findViewById(R.id.tvGoodsName);
            tvGoodsPrice = (TextView)itemView.findViewById(R.id.tvGoodsPrice);
            mRoot = itemView.findViewById(R.id.rvRoot);
        }
    }
}
