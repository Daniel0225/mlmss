package com.app.screenprotect.base;

import android.content.Context;
import android.os.CountDownTimer;

/**
 * Created by luoxing on 2019/1/2.
 */

public class CountDownManager {
    private static CountDownManager mInstance;
    private Context mContext;
    private OnCountDownListener mOnCountDownListener;
    private CountDownTimer mTimer;
    private long millisInFuture;
    private static final long countDownInterval = 1000;

    public CountDownManager(Context context, long millisInFuture) {
        this.mContext = context;
        this.millisInFuture = millisInFuture;
    }

    public static CountDownManager getInstance(Context context, long millisInFuture) {
        synchronized (CountDownManager.class) {
            if(mInstance == null){
                mInstance = new CountDownManager(context, millisInFuture);
            }
        }
        return mInstance;
    }

    public CountDownManager setOnCountDownListener(OnCountDownListener onCountDownListener){
        this.mOnCountDownListener = onCountDownListener;
        return mInstance;
    }

    public interface OnCountDownListener{

        void onTick(long mills);

        void onFinish();
    }

    public CountDownManager initTimer(){
        mTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long l) {
                mOnCountDownListener.onTick(l);
            }

            @Override
            public void onFinish() {
                mOnCountDownListener.onFinish();
            }
        };

        return mInstance;
    }

    public void start(){
        if(mTimer != null){
            mTimer.start();
        }
    }

    public void cancel(){
        if(mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    public void reset(){
        cancel();
        initTimer();
        start();
    }
}
