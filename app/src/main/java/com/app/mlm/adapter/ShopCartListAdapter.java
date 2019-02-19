package com.app.mlm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mlm.R;
import com.app.mlm.bean.GoodsInfo;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.adapter
 * @fileName : ShopCartListAdapter
 * @date : 2019/1/4  14:09
 * @describe : TODO
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public class ShopCartListAdapter extends BaseAdapter {
    private Context mContext;
    private List<GoodsInfo> data = new ArrayList<>();
    private LayoutInflater mInflater;
    private ShopCarHandleListener shopCarHandleListener;

    public ShopCartListAdapter(Context mContext, List<GoodsInfo> data, ShopCarHandleListener listener) {
        this.mContext = mContext;
        this.data = data;
        this.mInflater = LayoutInflater.from(mContext);
        this.shopCarHandleListener = listener;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = this.mInflater.inflate(R.layout.shop_cart_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        GoodsInfo goodsInfo = data.get(position);
        Glide.with(mContext).load(goodsInfo.getMdseUrl()).into(holder.ivGoodsImg);
        holder.tvGoodsName.setText(goodsInfo.getMdseName());
        holder.tvGoodsPrice.setText(goodsInfo.getMdsePrice());
        holder.tvGoodsNum.setText(String.valueOf(goodsInfo.getShopCarNum()));

        if (goodsInfo.getShopCarNum() > 1) {
            holder.ivJian.setImageResource(R.drawable.jian_nor);
            holder.ivJian.setEnabled(true);
        } else {
            holder.ivJian.setImageResource(R.drawable.jian);
            holder.ivJian.setEnabled(false);
        }
        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopCarHandleListener.addOne(position);
            }
        });
        holder.ivJian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopCarHandleListener.reduceOne(position);
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopCarHandleListener.deleteOne(position);
            }
        });
        return convertView;
    }

    public interface ShopCarHandleListener {
        void addOne(int position);

        void reduceOne(int position);

        void deleteOne(int position);
    }

    static class ViewHolder {
        @Bind(R.id.ivGoodsImg)
        ImageView ivGoodsImg;
        @Bind(R.id.tvGoodsName)
        TextView tvGoodsName;
        @Bind(R.id.tvGoodsPrice)
        TextView tvGoodsPrice;
        @Bind(R.id.goods_num)
        TextView tvGoodsNum;
        @Bind(R.id.ivJian)
        ImageView ivJian;
        @Bind(R.id.ivAdd)
        ImageView ivAdd;
        @Bind(R.id.iv_delete)
        ImageView ivDelete;

        ViewHolder(View view) {ButterKnife.bind(this, view);}
    }
}
