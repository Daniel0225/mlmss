package com.app.screenprotect.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by luoxing on 2019/1/2.
 */

public class BaseDispatchFragment extends Fragment implements CountDownManager.OnCountDownListener{

    private CountDownManager countDownManager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        countDownManager = CountDownManager.getInstance(getContext(), 1000 * 60)
                .setOnCountDownListener(this)
                .initTimer();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onTick(long mills) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onResume() {
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
    public void onPause() {
        super.onPause();
        try{
            if(countDownManager != null){
                countDownManager.cancel();
            }
        }catch (Exception e){}
    }

    public void initCountDownManager(){
        countDownManager = CountDownManager.getInstance(getActivity(), 1000 * 60)
                .setOnCountDownListener(this)
                .initTimer();
    }

}
