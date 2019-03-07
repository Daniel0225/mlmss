package com.app.mlm.bms.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.app.mlm.R;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.bms.dialog.DragSortGridDialog;
import com.app.mlm.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.adapter
 * @fileName : CHRowGoodsAdapter
 * @date : 2019/1/3  20:32
 * @describe : 货道配置 -- 多列商品Adapter
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public class HDColumnGoodsAdapter extends RecyclerView.Adapter<HDColumnGoodsAdapter.RowGoodsViewHolder> implements DragSortGridDialog.DataSortedListener {
    private Context context;
    private List<List<GoodsInfo>> data = new ArrayList<>();
    private int sortPosition;
    public HDColumnGoodsAdapter(Context context, List<List<GoodsInfo>> data){
        this.context = context;
        this.data = data;
    }
    @NonNull
    @Override
    public RowGoodsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.goods_row_layout_hdpz, viewGroup,
            false);
        RowGoodsViewHolder viewHolder = new RowGoodsViewHolder(view);
        viewHolder.recyclerView = view.findViewById(R.id.recyclerView);
        viewHolder.tvColumn = view.findViewById(R.id.tvColumn);
        viewHolder.tvSort = view.findViewById(R.id.sort);
        viewHolder.sellSwitch = view.findViewById(R.id.sell_switch);
        viewHolder.statusTv = view.findViewById(R.id.status_tv);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RowGoodsViewHolder rowGoodsViewHolder, int i) {
        LinearLayoutManager ms = new LinearLayoutManager(context);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        rowGoodsViewHolder.recyclerView.setLayoutManager(ms);
        HDRowGoodsAdapter adapter = new HDRowGoodsAdapter(context, data.get(i), i + 1);
        rowGoodsViewHolder.recyclerView.setAdapter(adapter);
        rowGoodsViewHolder.recyclerView.addItemDecoration(new SpacesItemDecoration(0, 0, 0, 1));
        rowGoodsViewHolder.tvColumn.setText(String.valueOf(i + 1));
        rowGoodsViewHolder.tvSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSortDialog(context, data.get(i));
                sortPosition = i;
            }
        });
        rowGoodsViewHolder.sellSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                rowGoodsViewHolder.statusTv.setText(isChecked ? "非常规售卖" : "常规售卖");
            }
        });
    }

    private void showSortDialog(Context context, List<GoodsInfo> goodsInfos) {
        DragSortGridDialog dialog = new DragSortGridDialog(context, goodsInfos, this);
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void sorted(List<GoodsInfo> newData) {
        data.set(sortPosition, newData);
        notifyDataSetChanged();
    }

    public class RowGoodsViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView tvColumn;
        TextView tvSort;
        Switch sellSwitch;
        TextView statusTv;
        public RowGoodsViewHolder(View itemView) {
            super(itemView);
        }
    }


}
