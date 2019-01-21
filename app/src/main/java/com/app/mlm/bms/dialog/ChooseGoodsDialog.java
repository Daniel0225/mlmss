package com.app.mlm.bms.dialog;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.app.mlm.R;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.bms.adapter.ChooseGoodsAdapter;
import com.app.mlm.bms.adapter.DragSortAdapter;
import com.app.mlm.dialog.BaseDialog;
import com.app.mlm.widget.SpacesItemDecoration;
import com.app.mlm.widget.dragsort.ItemTouchHelperCallback;
import com.app.mlm.widget.titilebar.ITitleBar;
import com.app.mlm.widget.titilebar.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.bms.dialog
 * @fileName : ChooseGoodsDialog
 * @date : 2019/1/19  18:23
 * @describe : 货道配置 --  更换商品 -- 商品列表
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public class ChooseGoodsDialog extends BaseDialog implements ITitleBar {
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.titlebar)
    TitleBar titleBar;
    private List<GoodsInfo> data = new ArrayList<>();
    private ChooseGoodsAdapter adapter;

    public ChooseGoodsDialog(Context context) {
        super(context, R.layout.dialog_choose_goods, true, Gravity.CENTER);
    }

    @Override
    public void initView() {
        titleBar.setTitlebarListener(this);
        titleBar.setRightViewVisibility(View.GONE);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(2, 2, 2, 2));
        adapter = new ChooseGoodsAdapter(data, listener);
        mRecyclerView.setAdapter(adapter);
    }

    private ChooseGoodsAdapter.CustomClickListener listener = new ChooseGoodsAdapter.CustomClickListener() {
        @Override
        public void onClick(int position) {
            Toast.makeText(mContext, String.valueOf(position), Toast.LENGTH_SHORT).show();
            dismiss();
        }
    };

    @Override
    public void onRightClicked() {

    }

    @Override
    public void onLeftClicked() {
        dismiss();
    }

    @Override
    public void onActionClicked() {

    }
}
