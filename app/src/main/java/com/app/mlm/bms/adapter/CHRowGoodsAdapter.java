package com.app.mlm.bms.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mlm.R;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.bms.activity.ChuhuoTestActivity;
import com.app.mlm.bms.dialog.CommonDialog;
import com.app.mlm.bms.dialog.TestingDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.adapter
 * @fileName : CHRowGoodsAdapter
 * @date : 2019/1/3  20:32
 * @describe : 出货测试 --  单行商品Adapter
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public class CHRowGoodsAdapter extends RecyclerView.Adapter<CHRowGoodsAdapter.RowGoodsViewHolder> {
    private Context context;
    private int count = 10;
    private List<GoodsInfo> data = new ArrayList<>();
    public CHRowGoodsAdapter(Context context, List<GoodsInfo> data){
        this.context = context;
        this.data = data;
    }
    @NonNull
    @Override
    public RowGoodsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.goods_item_layout_bms, viewGroup,
            false);
        RowGoodsViewHolder viewHolder = new RowGoodsViewHolder(view);
        viewHolder.ivGoodsImg = view.findViewById(R.id.ivGoodsImg);
        viewHolder.tvTest = view.findViewById(R.id.tvTest);
        viewHolder.rvRoot = view.findViewById(R.id.rvRoot);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RowGoodsViewHolder viewHolder, int i) {
        if(i == 0){
            viewHolder.rvRoot.setBackgroundResource(R.drawable.shape_white_lt_lb);
        }else if (i == count - 1){
            viewHolder.rvRoot.setBackgroundResource(R.drawable.shape_white_rt_rb);
        }else {
            viewHolder.rvRoot.setBackgroundResource(R.color.whiteColor);
        }

        viewHolder.tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ChuhuoTestActivity.isInited){
                    showTestingDialog();
                }else {
                    showCustomDialog();
                }
            }
        });
    }

    private void showTestingDialog() {
        TestingDialog dialog = new TestingDialog(context);
        dialog.show();
    }

    private void showCustomDialog() {
        CommonDialog commonDialog = new CommonDialog(context, "货道初始化", "点击初始化按钮，然后等待初始化完成", "初始化","完成")
            .setCommitClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doInit();
                    Toast.makeText(context, "初始化", Toast.LENGTH_SHORT).show();
                }
            })
            .setCancelClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "完成", Toast.LENGTH_SHORT).show();
                }
            });
        commonDialog.show();
    }

    /**
     * 执行初始化操作
     */
    private void doInit() {
        //初始化完成，重置ChuhuoTestActivity.isInited = true;
        ChuhuoTestActivity.isInited = true;
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public class RowGoodsViewHolder extends RecyclerView.ViewHolder {

        ImageView ivGoodsImg;
        TextView tvTest;
        View rvRoot;

        public RowGoodsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
