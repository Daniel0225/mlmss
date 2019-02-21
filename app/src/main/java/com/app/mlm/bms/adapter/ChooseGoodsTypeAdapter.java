package com.app.mlm.bms.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.mlm.R;
import com.app.mlm.bean.AllTypeInfo;

import java.util.List;

public class ChooseGoodsTypeAdapter extends RecyclerView.Adapter<ChooseGoodsTypeAdapter.MyViewHolder> {

    private List<AllTypeInfo> mList;
    private CustomClickListener listener;
    private int type;//0 品牌 1 类型 2 包装

    public ChooseGoodsTypeAdapter(List<AllTypeInfo> mList, CustomClickListener listener, int type) {
        this.mList = mList;
        this.listener = listener;
        this.type = type;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_type_list,
                parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AllTypeInfo item = mList.get(position);
        if (type == 0) {
            holder.typeName.setText(item.getMerchantType());
        } else if (type == 1) {
            holder.typeName.setText(item.getBrandName());
        } else {
            holder.typeName.setText(item.getPackName());
        }

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

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView typeName;
        View mRoot;

        public MyViewHolder(View itemView) {
            super(itemView);
            typeName = (TextView) itemView.findViewById(R.id.name);
            mRoot = itemView.findViewById(R.id.rvRoot);
        }
    }
}
