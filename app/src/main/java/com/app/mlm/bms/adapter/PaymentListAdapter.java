package com.app.mlm.bms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mlm.Constants;
import com.app.mlm.R;
import com.app.mlm.bms.bean.PaymentInfo;
import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.bms.adapter
 * @fileName : PaymentListAdapter
 * @date : 2019/1/8  20:43
 * @describe : TODO
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public class PaymentListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<PaymentInfo> data = new ArrayList<>();

    public PaymentListAdapter(Context context, List<PaymentInfo> data) {
        this.context = context;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.data.size();
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
            convertView = inflater.inflate(R.layout.payment_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        PaymentInfo info = data.get(position);
        if(info.isChecked()){
            holder.ivCheck.setImageResource(R.drawable.select_nor);
        }else {
            holder.ivCheck.setImageResource(R.drawable.select);
        }
        Glide.with(context).load(info.getIconId()).into(holder.ivPaymentImg);
        holder.tvPaymentName.setText(info.getName());
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.ivPaymentImg)
        ImageView ivPaymentImg;
        @Bind(R.id.tvPaymentName)
        TextView tvPaymentName;
        @Bind(R.id.ivCheck)
        ImageView ivCheck;

        ViewHolder(View view) {ButterKnife.bind(this, view);}
    }
}
