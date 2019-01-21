package com.app.mlm.bms.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.app.mlm.R;
import com.app.mlm.bms.adapter.CHColumnGoodsAdapter;
import com.app.mlm.widget.SpacesItemDecoration;

import butterknife.Bind;

/**
 * 出货测试页
 */
public class ChuhuoTestActivity extends BaseActivity {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    public static boolean isInited = false;

    @Override
    protected int provideLayoutResId() {
        return R.layout.activity_chuhuo_test;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        LinearLayoutManager ms = new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(ms);
        recyclerView.addItemDecoration(new SpacesItemDecoration(8, 8, 0, 0));
        CHColumnGoodsAdapter adapter = new CHColumnGoodsAdapter(this, null);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
