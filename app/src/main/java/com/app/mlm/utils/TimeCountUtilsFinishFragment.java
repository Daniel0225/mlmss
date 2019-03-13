package com.app.mlm.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.app.mlm.activity.MainActivity;

/**
 * Created by Administrator on 2019/3/12.
 */

public class TimeCountUtilsFinishFragment extends CountDownTimer {
    private TextView textView;
    private MainActivity mActivity;

    public TimeCountUtilsFinishFragment(MainActivity mActivity, long millisInFuture, long countDownInterval, TextView textView) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
        this.mActivity = mActivity;
    }


    @Override
    public void onTick(long millisUntilFinished) {
        textView.setTextColor(Color.parseColor("#999999"));
        textView.setClickable(false);
        textView.setText(millisUntilFinished / 1000 + "s");
    }

    @Override
    public void onFinish() {
        textView.setText("0s");
        textView.setClickable(true);
        textView.setTextColor(Color.parseColor("#999999"));
        mActivity.removeFragment();
    }
}
