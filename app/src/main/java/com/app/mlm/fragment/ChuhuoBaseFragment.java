package com.app.mlm.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.mlm.activity.ChuhuoActivity;
import com.app.mlm.activity.MainActivity;
import com.app.mlm.countdown.CountDownManager;

import butterknife.ButterKnife;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.fragment
 * @fileName : BaseFragment  出货方面的fragment基
 * @date : 2019/1/4  14:16
 * @describe : TODO
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public abstract class ChuhuoBaseFragment extends Fragment implements CountDownManager.OnCountDownListener {
    protected CountDownManager countDownManager;
    protected ChuhuoActivity mActivity;
    protected Context context;
    protected View mRootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(provideLayoutResId(), null);
        ButterKnife.bind(this, mRootView);
        context = getActivity();
        mActivity = (ChuhuoActivity) getActivity();
        initCountDownMananger();
        initView(savedInstanceState);
        initListener();
        initData();
        return mRootView;
    }

    protected void initCountDownMananger(){
        if(isNeedCountDown()){
            countDownManager = CountDownManager.getInstance(mActivity)
                .setCountDownListener(this)
                .initCountDownTimer()
                .start();
        }
    }

    protected abstract int provideLayoutResId();

    /**
     * 步骤一：初始化View，比如findViewById等操作
     */
    protected abstract void initView(Bundle savedInstanceState) ;

    /**
     * 步骤二：初始化View的Listener，比如onClick等监听器
     */
    protected abstract void initListener() ;

    /**
     * 步骤三：初始化数据
     */
    protected abstract void initData() ;

    protected boolean isNeedCountDown(){
        return false;
    }

    @Override
    public void onTick(int seconds) {

    }

    @Override
    public void onFinish() {

    }
}
