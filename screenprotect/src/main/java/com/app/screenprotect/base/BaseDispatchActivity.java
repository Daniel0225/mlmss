package com.app.screenprotect.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

/**
 * Created by luoxing on 2019/1/2.
 */

public class BaseDispatchActivity extends AppCompatActivity implements CountDownManager.OnCountDownListener{

    CountDownManager countDownManager;
    long millisInFuture;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this. millisInFuture = millisInFuture();
    }

    @Override
    public void onTick(long mills) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        try{
            if(countDownManager == null){
                initCountDownManager();
                countDownManager.start();
            }else {
                countDownManager.reset();
            }
        }catch (Exception e){

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try{
            if(countDownManager != null){
                countDownManager.cancel();
            }
        }catch (Exception e){}
    }

    public void initCountDownManager(){
        countDownManager = CountDownManager.getInstance(this, millisInFuture)
                .setOnCountDownListener(this)
                .initTimer();
    }

    /**
     * 设置默认倒计时时间为10s
     *
     * @return
     */
    public long millisInFuture(){
        return 1000 * 10;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(countDownManager != null){
                    countDownManager.cancel();
                }
                break;
            case MotionEvent.ACTION_UP:
                if(countDownManager == null){
                    initCountDownManager();
                    countDownManager.start();
                }else {
                    countDownManager.reset();
                }
                break;
        }

        return super.dispatchTouchEvent(ev);
    }
}
