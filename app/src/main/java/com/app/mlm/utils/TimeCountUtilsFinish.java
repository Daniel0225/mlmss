package com.app.mlm.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by Administrator on 2019/3/12.
 */

public class TimeCountUtilsFinish extends CountDownTimer {
    private TextView textView;
    private Activity activity;

    public TimeCountUtilsFinish(Activity activity, long millisInFuture, long countDownInterval, TextView textView) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
        this.activity = activity;
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
        activity.finish();
    }
}
