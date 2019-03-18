package com.app.mlm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mlm.Constants;
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
public class ChuhuoFailedFragment extends ChuhuoBaseFragment {
    TimeCountUtilsFinish timeCount;
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.count_down)
    TextView countDown;
    @Bind(R.id.shibai)
    ImageView shibai;
    @Bind(R.id.tvCount)
    TextView tvCount;
    @Bind(R.id.tvCancel)
    TextView tvCancel;
    @Bind(R.id.tvCommit)
    TextView tvCommit;
    String count;
    String successCount;

    public ChuhuoFailedFragment() {
    }

    @Override
    protected int provideLayoutResId() {
        return R.layout.fragment_chuhuo_failed;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }

    @OnClick(R.id.tvCancel)
    public void onClick(View view) {
        Log.e("Tag", "chuhuoshibai fragment click");
        mActivity.addFragment(new LianxikefuFragment());
    }

    @Override
    protected void initData() {
        count = (String) getArguments().get("count");
        successCount = (String) getArguments().get("count");
        tvCount.setText(successCount + "/" + count);
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

    /**
     * 开启倒计时
     */
    public void startTime() {
        if (timeCount == null) {
            timeCount = new TimeCountUtilsFinish(mActivity, 10000, 1000, countDown);
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

    @OnClick({R.id.ivBack, R.id.tvCancel, R.id.tvCommit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                // mActivity.finish();
                getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                Intent intent = new Intent(Constants.ORDER);
                getActivity().sendBroadcast(intent);
                break;
            case R.id.tvCancel:
                if (timeCount != null) {
                    timeCount.cancel();
                }
                mActivity.addFragment(new LianxikefuFragment());
                break;
            case R.id.tvCommit:
                // mActivity.finish();
                getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                Intent intent2 = new Intent(Constants.ORDER);
                getActivity().sendBroadcast(intent2);
                break;
        }
    }
}
