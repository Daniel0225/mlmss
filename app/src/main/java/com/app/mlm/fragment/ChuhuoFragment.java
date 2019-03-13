package com.app.mlm.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.app.mlm.R;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChuhuoFragment extends ChuhuoBaseFragment {
    @Bind(R.id.tvCountDown)
    TextView tvCountDownView;

    public ChuhuoFragment() {
    }

    @Override
    protected int provideLayoutResId() {
        return R.layout.fragment_chuhuo;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initCountDownMananger();
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
        tvCountDownView.setText(seconds + "S");
        if (seconds == 55) {
//            mActivity.addFragment(new ChuhuoFailedFragment());
            mActivity.addFragment(new ChuhuoSuccessFragment());
        }
    }

    @Override
    public void onFinish() {

    }
}
