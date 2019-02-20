package com.app.mlm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
 * @fileName : SearchResultAdapter
 * @date : 2019/1/4  17:35
 * @describe : TODO
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public class SearchResultAdapter extends BaseAdapter {
    private Context context;
    private List<GoodsInfo> data = new ArrayList<>();
    private LayoutInflater inflater;
    private SearchResultAddShopCarListener searchResultAddShopCarListener;

    public SearchResultAdapter(Context context, List<GoodsInfo> data, SearchResultAddShopCarListener searchResultAddShopCarListener) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
        this.searchResultAddShopCarListener = searchResultAddShopCarListener;
    }

    @Override
    public int getCount() {
        return 16;
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
            convertView = inflater.inflate(R.layout.search_item_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        GoodsInfo goodsInfo = data.get(position);
        holder.tvGoodsName.setText(goodsInfo.getMdseName());
        holder.tvGoodsPrice.setText("¥ " + goodsInfo.getMdsePrice());

        if (goodsInfo.getMdseUrl().equals("empty")) {
            holder.ivGoodsImg.setImageResource(R.drawable.empty);
        } else {
            Glide.with(context).load(goodsInfo.getMdseUrl()).into(holder.ivGoodsImg);
        }

        holder.ivAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchResultAddShopCarListener.addCar(position);
            }
        });

        return convertView;
    }

    public interface SearchResultAddShopCarListener {
        void addCar(int position);
    }

    static class ViewHolder {
        @Bind(R.id.ivGoodsImg)
        ImageView ivGoodsImg;
        @Bind(R.id.tvGoodsName)
        TextView tvGoodsName;
        @Bind(R.id.tvGoodsPrice)
        TextView tvGoodsPrice;
        @Bind(R.id.ivAddCart)
        ImageView ivAddCart;
        @Bind(R.id.ivNoGoods)
        ImageView ivNoGoods;
        @Bind(R.id.rvRoot)
        RelativeLayout rvRoot;

        ViewHolder(View view) {ButterKnife.bind(this, view);}
    }
}
