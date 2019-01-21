package com.app.mlm.bms.dialog;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.app.mlm.R;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.bms.adapter.DragSortAdapter;
import com.app.mlm.dialog.BaseDialog;
import com.app.mlm.widget.SpacesItemDecoration;
import com.app.mlm.widget.dragsort.CallbackItemTouch;
import com.app.mlm.widget.dragsort.ItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.bms.dialog
 * @fileName : DragSortGridDialog
 * @date : 2019/1/19  16:06
 * @describe : 拖拽排序Dialog
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public class DragSortGridDialog extends BaseDialog implements CallbackItemTouch {
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.cancel)
    TextView cancel;
    @Bind(R.id.commit)
    TextView commit;
    private List<GoodsInfo> data = new ArrayList<>();
    private DragSortAdapter dragSortAdapter;

    public DragSortGridDialog(Context context, List<GoodsInfo> goodsInfos) {
        super(context, R.layout.dialog_drag_sort,true, Gravity.CENTER);
        this.data = goodsInfos;
    }

    @Override
    public void initView() {
        for(int i = 0;i < 30; i++){
            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.setPosition(i + 1);
            data.add(goodsInfo);
        }
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(2, 2, 2, 2));
        dragSortAdapter = new DragSortAdapter(data);
        mRecyclerView.setAdapter(dragSortAdapter);
        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(this);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);
    }

    @OnClick({R.id.cancel, R.id.commit})
    public void onViewClicked(View view) {
        dismiss();
        switch (view.getId()) {
            case R.id.cancel:
                break;
            case R.id.commit:
                //doSomething
                break;
        }
    }

    @Override
    public void itemTouchOnMove(int oldPosition, int newPosition) {
        data.add(newPosition, data.remove(oldPosition));
        dragSortAdapter.notifyItemMoved(oldPosition, newPosition);
    }
}
