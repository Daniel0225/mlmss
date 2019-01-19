package com.app.mlm.activity.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.app.mlm.countdown.CountDownManager;
import com.app.screenprotect.base.BaseDispatchActivity;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.activity.base
 * @fileName : BaseActivity
 * @date : 2019/1/2  13:02
 * @describe : Activity基类
 */
public abstract class BaseActivity extends BaseDispatchActivity {
    protected Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
    }

    /**
     * 跳转Activity，不需要传递参数
     *
     * @param clazz 目标Activity
     */
    protected void startActivities(Class clazz){
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * 跳转Activity，需要传递参数
     *
     * @param bundle 参数
     * @param clazz 目标Activity
     */
    protected void startActivities(Bundle bundle, Class clazz){
        Intent intent = new Intent(this, clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
