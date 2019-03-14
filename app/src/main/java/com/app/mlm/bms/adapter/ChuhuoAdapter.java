package com.app.mlm.bms.adapter;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mlm.R;
import com.app.mlm.http.bean.HdDataBean;

import java.util.ArrayList;
import java.util.List;

public class ChuhuoAdapter extends RecyclerView.Adapter<ChuhuoAdapter.MyViewHolder>{
    private List<HdDataBean> mList = new ArrayList<HdDataBean>();
    private int chuhuoPosition = 0; //正在出货的位置
    private Context context;
    private ObjectAnimator objectAnimator;

    public ChuhuoAdapter(Context context, List<HdDataBean> mList) {
        this.mList = mList;
        this.context = context;
    }

    public void refreshChuhuoStatus(int position){
        chuhuoPosition = position;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chuhuo_adatper_item_layout,
            parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
//        ProductInfo item = mList.get(position);
//        Glide.with(MainApp.getAppInstance()).load(item.getMdseUrl()).into(holder.ivGoodsImg);
        if(position < chuhuoPosition){//已出的
            holder.progressEndCircle.setVisibility(View.VISIBLE);
            holder.progressCircle.setVisibility(View.GONE);
            holder.ivResult.setVisibility(View.VISIBLE);
            holder.waiteChuhuo.setVisibility(View.GONE);
            if (mList.get(position).isSuccess()) {//如果出货成功 显示成功的图片
                holder.ivResult.setImageResource(R.drawable.select_nor);
            }else{//显示失败的图片
                holder.ivResult.setImageResource(R.drawable.shibai);
            }
        }else if(position == chuhuoPosition){
            holder.progressCircle.setVisibility(View.VISIBLE);
            holder.progressEndCircle.setVisibility(View.GONE);
            holder.waiteChuhuo.setVisibility(View.GONE);
            objectAnimator = ObjectAnimator.ofFloat(holder.progressCircle,"rotation",0f,360f);
            objectAnimator.setDuration(4000)
                    .setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator.start();
        }else{
            holder.waiteChuhuo.setVisibility(View.VISIBLE);
        }
        holder.tvCount.setText(1 + "/" + mList.size());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView ivGoodsImg;
        ImageView ivResult;
        ImageView progressCircle;
        ImageView progressEndCircle;
        TextView tvCount;
        View waiteChuhuo;
        public MyViewHolder(View itemView) {
            super(itemView);
            ivGoodsImg = itemView.findViewById(R.id.goods_pic);
            progressCircle = itemView.findViewById(R.id.progress_circle);
            progressEndCircle = itemView.findViewById(R.id.progress_end_circle);
            ivResult = itemView.findViewById(R.id.iv_result);
            waiteChuhuo = itemView.findViewById(R.id.wait_chuhuo);
            tvCount = itemView.findViewById(R.id.tvCount);
        }
    }
}
