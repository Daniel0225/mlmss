package com.app.mlm.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.app.mlm.R;

/**
 * 出货失败Fragment
 * A simple {@link Fragment} subclass.
 */
public class LianxikefuFragment extends ChuhuoBaseFragment {
    public LianxikefuFragment() {
    }

    @Override
    protected int provideLayoutResId() {
        return R.layout.fragment_lianxikefu;
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

    @Override
    protected boolean isNeedCountDown() {
        return true;
    }

    @Override
    public void onTick(int seconds) {

    }

    @Override
    public void onFinish() {

    }
}
