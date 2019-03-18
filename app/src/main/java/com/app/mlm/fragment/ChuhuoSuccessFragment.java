package com.app.mlm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
 * 出货成功Fragment
 * A simple {@link Fragment} subclass.
 */
public class ChuhuoSuccessFragment extends ChuhuoBaseFragment {
    TimeCountUtilsFinish timeCount;
    @Bind(R.id.tvCancel)
    TextView tvCancel;
    @Bind(R.id.tvCommit)
    TextView tvCommit;
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.tvCountDown)
    TextView tvCountDown;

    public ChuhuoSuccessFragment() {
    }

    @Override
    protected int provideLayoutResId() {
        return R.layout.fragment_chuhuo_success;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initListener() {

    }

   /* @OnClick(R.id.tvCancel)
    public void onClick(View view) {
        Log.e("Tag", "chuhuoshibai fragment click");
        mActivity.addFragment(new LianxikefuFragment());
    }*/

    @Override
    protected void initData() {
        startTime();
    }

    @Override
    protected boolean isNeedCountDown() {
        return true;
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
        if (timeCount != null) {
            timeCount.cancel();
        }
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
    }

    @OnClick({R.id.tvCancel, R.id.tvCommit, R.id.ivBack})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvCancel:
                // mActivity.finish();
                getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                Intent intent = new Intent(Constants.ORDER);
                getActivity().sendBroadcast(intent);
                break;
            case R.id.tvCommit:
                getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                Intent intent1 = new Intent(Constants.ORDER);
                getActivity().sendBroadcast(intent1);
                //  mActivity.finish();
                break;
            case R.id.ivBack:
                getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                Intent intent2 = new Intent(Constants.ORDER);
                getActivity().sendBroadcast(intent2);
                //mActivity.finish();
                break;
        }
    }

}
