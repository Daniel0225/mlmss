package com.app.mlm.bms.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.mlm.R;
import com.app.mlm.bean.AllTypeInfo;
import com.app.mlm.http.bean.GoodsTypeSelectBean;

import java.util.List;

public class ChooseGoodsTypeAdapter extends RecyclerView.Adapter<ChooseGoodsTypeAdapter.MyViewHolder> {

    private Context context;
    private List<AllTypeInfo> mList;
    private CustomClickListener listener;
    private GoodsTypeSelectBean goodsTypeSelectBean;

    public ChooseGoodsTypeAdapter(Context context, List<AllTypeInfo> mList, CustomClickListener listener, GoodsTypeSelectBean goodsTypeSelectBean) {
        this.context = context;
        this.mList = mList;
        this.listener = listener;
        this.goodsTypeSelectBean = goodsTypeSelectBean;
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
        if (goodsTypeSelectBean.getType() == 0) {
            holder.typeName.setText(item.getBrandName());
        } else if (goodsTypeSelectBean.getType() == 1) {
            holder.typeName.setText(item.getMerchantType());
        } else {
            holder.typeName.setText(item.getPackName());
        }

        holder.typeName.setBackground(context.getResources().getDrawable(R.drawable.shape_gray_light10));
        holder.typeName.setTextColor(Color.BLACK);

        if (goodsTypeSelectBean.getType() == goodsTypeSelectBean.getSelectType()) {
            if (goodsTypeSelectBean.getSelectPosition() == position) {
                holder.typeName.setBackground(context.getResources().getDrawable(R.drawable.shape_blue_dark10));
                holder.typeName.setTextColor(Color.WHITE);
            }
        } else {
            if (position == 0) {
                holder.typeName.setBackground(context.getResources().getDrawable(R.drawable.shape_blue_dark10));
                holder.typeName.setTextColor(Color.WHITE);
            }
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
