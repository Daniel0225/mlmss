package com.app.mlm.bms.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.app.mlm.R;
import com.app.mlm.widget.titilebar.ITitleBar;
import com.app.mlm.widget.titilebar.TitleBar;

import butterknife.ButterKnife;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.bms.activity
 * @fileName : BaseActivity
 * @date : 2019/1/8  19:48
 * @describe : TODO
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public abstract class BaseActivity extends AppCompatActivity implements ITitleBar {
    protected View mRootView;
    protected TitleBar mTitleBar;
    protected Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        int layoutId = provideLayoutResId();
        if(layoutId > 0){
            mRootView = getLayoutInflater().inflate(layoutId, null);
            setContentView(mRootView);
        }
        ButterKnife.bind(this);
        mTitleBar = (TitleBar)findViewById(R.id.titlebar);
        if(hasTitleBar()){
            mTitleBar.setTitlebarListener(this);
            if(!showRightIcon()){
                mTitleBar.setRightViewVisibility(View.GONE);
            }
        }
        initView(savedInstanceState);
        initListener();
        initData();
    }

    protected boolean hasTitleBar(){
        return null != mTitleBar;
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

    @Override
    public void onLeftClicked() {
        onBackPressed();
    }

    @Override
    public void onRightClicked() {

    }

    @Override
    public void onActionClicked() {

    }

    public boolean showRightIcon(){
        return false;
    }

}
