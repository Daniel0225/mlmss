package com.app.mlm.countdown;

import android.content.Context;
import android.os.CountDownTimer;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.countdown
 * @fileName : CountDownManager
 * @date : 2019/1/2  13:14
 * @describe : 倒计时管理
 * @email : xing.luo@taojiji.com
 */
public class CountDownManager {
    private static final long countDownInterval = 1000;
    private Context mContext;
    private CountDownTimer mTimer;
    private long mCountDownMills = 1000 * 60;
    private static CountDownManager mInstance;
    private OnCountDownListener mCountDownListener;

    /**
     * 单例模式，默认60秒倒计时
     *
     * @param context 上下文
     * @return
     */
    public static CountDownManager getInstance(Context context){
        synchronized (CountDownManager.class){
            if(mInstance == null){
                mInstance = new CountDownManager(context);
            }
        }
        return mInstance;
    }

    /**
     * 单例模式
     *
     * @param context 上下文
     * @param countDownMills 倒计时毫秒数
     * @return
     */
    public static CountDownManager getInstance(Context context, long countDownMills){
        synchronized (CountDownManager.class){
            if(mInstance == null){
                mInstance = new CountDownManager(context, countDownMills);
            }
        }
        return mInstance;
    }

    public CountDownManager(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public CountDownManager(Context context, long countDownMills) {
        this.mContext = context.getApplicationContext();
        this.mCountDownMills = countDownMills;
    }

    /**
     *  初始化倒计时
     */
    public void initCountDownTimer(){
        cancelTimer();
        mTimer = new CountDownTimer(mCountDownMills, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                mCountDownListener.onTick((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                mCountDownListener.onFinish();
                mTimer = null;
            }
        };
    }

    /**
     * 重置倒计时
     */
    public void resetTimer(){
        initCountDownTimer();
    }

    /**
     * 取消倒计时
     */
    public void cancelTimer(){
        if(mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }
    }

    /**
     * 设置倒计时监听回调
     *
     * @param listener OnCountDownListener实例
     * @return
     */
    public CountDownManager setCountDownListener(OnCountDownListener listener){
        this.mCountDownListener = listener;
        return mInstance;
    }



    public interface OnCountDownListener{
        void onTick(int seconds);

        void onFinish();
    }
}
