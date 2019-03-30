package com.app.mlm.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.app.mlm.Constants;
import com.app.mlm.R;
import com.app.mlm.activity.base.BaseActivity;
import com.app.mlm.bms.activity.ActivationActivity;
import com.app.mlm.utils.PreferencesUtil;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.countdown
 * @fileName : CountDownManager
 * @date : 2019/1/2  13:14
 * @describe : 启动页
 * @email : xing.luo@taojiji.com
 */
public class StartupActivity extends BaseActivity {
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        PreferencesUtil.putString(Constants.VMCODE, "0000051");//先存入机器码  正式的时候要去掉
        PreferencesUtil.putInt(Constants.VMID, 1);
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(PreferencesUtil.getString(Constants.VMCODE))) {
                    ActivationActivity.start(mContext);
                } else {
                    MainActivity.start(mContext);
                }
                finish();
            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
