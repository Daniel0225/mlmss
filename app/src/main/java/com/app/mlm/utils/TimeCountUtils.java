package com.app.mlm.utils;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * Created by qibo on 16/8/9.
 */
public class TimeCountUtils extends CountDownTimer {

    private TextView textView;

    public TimeCountUtils(long millisInFuture, long countDownInterval, TextView textView) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
    }


    @Override
    public void onTick(long millisUntilFinished) {
        textView.setTextColor(Color.parseColor("#999999"));
        textView.setClickable(false);
        textView.setText(millisUntilFinished / 1000 + "s");
    }

    @Override
    public void onFinish() {
        textView.setText("0");
        textView.setClickable(true);
        textView.setTextColor(Color.parseColor("#F2302F"));
    }
}
