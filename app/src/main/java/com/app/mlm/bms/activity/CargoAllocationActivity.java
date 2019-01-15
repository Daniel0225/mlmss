package com.app.mlm.bms.activity;

import android.os.Bundle;

import com.app.mlm.R;

/**
 * Created by Administrator on 2019/1/15.
 * 货道配置
 */

public class CargoAllocationActivity extends BaseActivity {
    @Override
    protected int provideLayoutResId() {
        return R.layout.cargo_allocation_layout;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    /**
     * 点击应用设置好的配置信息
     */
    @Override
    public void onActionClicked() {
        super.onActionClicked();
        finish();
    }
}
