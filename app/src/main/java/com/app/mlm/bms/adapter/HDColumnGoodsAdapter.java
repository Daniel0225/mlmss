package com.app.mlm.bms.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class HDColumnGoodsAdapter extends RecyclerView.Adapter<HDColumnGoodsAdapter.RowGoodsViewHolder> {
    private Context context;
    private List<List<GoodsInfo>> data = new ArrayList<>();
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
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RowGoodsViewHolder rowGoodsViewHolder, int i) {
        LinearLayoutManager ms = new LinearLayoutManager(context);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        rowGoodsViewHolder.recyclerView.setLayoutManager(ms);
        HDRowGoodsAdapter adapter = new HDRowGoodsAdapter(context, null);
        rowGoodsViewHolder.recyclerView.setAdapter(adapter);
        rowGoodsViewHolder.recyclerView.addItemDecoration(new SpacesItemDecoration(0, 0, 2, 2));
        rowGoodsViewHolder.tvColumn.setText(String.valueOf(i + 1));
        rowGoodsViewHolder.tvSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showSortDialog(context, data.get(i));
                showSortDialog(context, new ArrayList<GoodsInfo>());
            }
        });
    }

    private void showSortDialog(Context context, List<GoodsInfo> goodsInfos) {
        DragSortGridDialog dialog = new DragSortGridDialog(context, goodsInfos);
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return 15;
    }

    public class RowGoodsViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView tvColumn;
        TextView tvSort;

        public RowGoodsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
