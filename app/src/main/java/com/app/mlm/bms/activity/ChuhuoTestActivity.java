package com.app.mlm.bms.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.app.mlm.R;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.bms.adapter.CHColumnGoodsAdapter;
import com.app.mlm.http.bean.HuodaoBean;
import com.app.mlm.utils.FastJsonUtil;
import com.app.mlm.utils.PreferencesUtil;
import com.app.mlm.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 出货测试页
 */
public class ChuhuoTestActivity extends BaseActivity {
    public static boolean isInited = false;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    List<List<GoodsInfo>> list = new ArrayList<>();
    @Override
    protected int provideLayoutResId() {
        return R.layout.activity_chuhuo_test;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        LinearLayoutManager ms = new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(ms);
        initList();
        recyclerView.addItemDecoration(new SpacesItemDecoration(12, 12, 0, 0));
        CHColumnGoodsAdapter adapter = new CHColumnGoodsAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    /**
     * 造数据
     */
    private void initList() {
        list.clear();
        String huodaoString = PreferencesUtil.getString("huodao");
        if (TextUtils.isEmpty(huodaoString)) {
            list.addAll(getData());
        } else {
            HuodaoBean huodaoBean = FastJsonUtil.getObject(huodaoString, HuodaoBean.class);
            list.addAll(huodaoBean.getAllDataList());
        }
    }

    private List<List<GoodsInfo>> getData() {
        List<List<GoodsInfo>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(getDefaultData());
        }
        return list;
    }

    private List<GoodsInfo> getDefaultData() {
        List<GoodsInfo> goodsInfoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.setMdseName("请补货");
            goodsInfo.setMdseUrl("empty");
            goodsInfo.setMdsePrice("0");
            goodsInfoList.add(goodsInfo);
        }
        return goodsInfoList;
    }
}
