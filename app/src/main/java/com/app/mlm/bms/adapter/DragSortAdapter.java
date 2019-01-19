package com.app.mlm.bms.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mlm.R;
import com.app.mlm.bean.GoodsInfo;

import java.util.List;

public class DragSortAdapter extends RecyclerView.Adapter<DragSortAdapter.MyViewHolder>{

    private List<GoodsInfo> mList;

    public DragSortAdapter(List<GoodsInfo> mList) {
        this.mList = mList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drag_gird_item,
            parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GoodsInfo item = mList.get(position);
        holder.tvGoodsSort.setText(String.valueOf(item.getPosition()));
    }

    @Override
    public int getItemCount() {
        if (mList == null){
            return 0;
        }else {
            return mList.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView ivGoodsImg;
        TextView tvGoodsSort;
        public MyViewHolder(View itemView) {
            super(itemView);
            ivGoodsImg = (ImageView)itemView.findViewById(R.id.ivGoodsImg);
            tvGoodsSort = (TextView)itemView.findViewById(R.id.tvGoodsOrder);
        }
    }

}
