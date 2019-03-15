package com.app.mlm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mlm.R;
import com.app.mlm.activity.MainActivity;
import com.app.mlm.utils.TimeCountUtilsFinish;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 出货失败Fragment
 * A simple {@link Fragment} subclass.
 */
public class LianxikefuFragment extends ChuhuoBaseFragment {
    TimeCountUtilsFinish timeCount;
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.tvCountDown)
    TextView tvCountDown;

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
        startTime();
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

    /**
     * 开启倒计时
     */
    public void startTime() {
        if (timeCount == null) {
            timeCount = new TimeCountUtilsFinish(mActivity, 10000, 1000, tvCountDown);
        }
        timeCount.start(); //倒计时后重新获取
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timeCount != null) {
            timeCount.cancel();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ivBack, R.id.tvCountDown})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                //mActivity.finish();
                getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                break;
            case R.id.tvCountDown:
                break;
        }
    }
}
