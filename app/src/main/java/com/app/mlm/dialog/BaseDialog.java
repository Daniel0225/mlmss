package com.app.mlm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.app.mlm.R;

import butterknife.ButterKnife;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.dialog
 * @fileName : BaseDialog
 * @date : 2019/1/2  14:11
 * @describe : Dialog基类
 * @email : xing.luo@taojiji.com
 */
public abstract class BaseDialog  extends Dialog {
    protected Context mContext;
    protected View mRoot;
    private int mLayoutResID;
    private Window mDialogWindow;
    private int mCountDownSec = 0;
    private CountDownTimer mTimer;
    private OnCountDownListener mListener;
    private BaseDialog mBaseDialog;
    private boolean isFullScreen = false;
    private int fullScreenWidth = 0;
    private int fullScreenHeight = 0;

    public BaseDialog(Context context, int layoutResID) {
        super(context, R.style.NoBGDialog);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.mContext = context;
        mDialogWindow = this.getWindow();
        mDialogWindow.setGravity(Gravity.BOTTOM);
        mDialogWindow.setBackgroundDrawableResource(R.color.transparent);
//        mDialogWindow.setWindowAnimations(R.style.BottomAnimation);
        this.mLayoutResID = layoutResID;
    }

    public BaseDialog(Context context, int layoutResID, boolean isFullScreen) {
        super(context, R.style.NoBGDialog);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.mContext = context;
        mDialogWindow = this.getWindow();
        mDialogWindow.setGravity(Gravity.BOTTOM);
        mDialogWindow.setBackgroundDrawableResource(R.color.transparent);
//        mDialogWindow.setWindowAnimations(R.style.BottomAnimation);
        this.mLayoutResID = layoutResID;
        this.isFullScreen = isFullScreen;
    }

    public BaseDialog(Context context, int layoutResID, boolean isFullScreen, int gravity, int width, int height) {
        super(context, R.style.NoBGDialog);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.mContext = context;
        mDialogWindow = this.getWindow();
        mDialogWindow.setGravity(gravity);
        mDialogWindow.setBackgroundDrawableResource(R.color.transparent);
//        mDialogWindow.setWindowAnimations(R.style.BottomAnimation);
        this.mLayoutResID = layoutResID;
        this.isFullScreen = isFullScreen;
        fullScreenWidth = width;
        fullScreenHeight = height;
    }

    public BaseDialog(Context context, int layoutResID, boolean isFullScreen, int gravity) {
        super(context, R.style.NoBGDialog);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.mContext = context;
        mDialogWindow = this.getWindow();
        mDialogWindow.setGravity(gravity);
        mDialogWindow.setBackgroundDrawableResource(R.color.transparent);
//        mDialogWindow.setWindowAnimations(R.style.BottomAnimation);
        this.mLayoutResID = layoutResID;
        this.isFullScreen = isFullScreen;
    }

    public BaseDialog(Context context, int layoutResID, int gravity) {
        super(context, R.style.NoBGDialog);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.mContext = context;
        mDialogWindow = this.getWindow();
        mDialogWindow.setGravity(gravity);
        mDialogWindow.setBackgroundDrawableResource(R.color.transparent);
//        mDialogWindow.setWindowAnimations(R.style.BottomAnimation);
        this.mLayoutResID = layoutResID;
    }

    public BaseDialog(Context context, int layoutResID, int countDownSec, OnCountDownListener listener) {
        super(context, R.style.NoBGDialog);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.mContext = context;
        mDialogWindow = this.getWindow();
        mDialogWindow.setGravity(Gravity.BOTTOM);
        mDialogWindow.setBackgroundDrawableResource(R.color.transparent);
//        mDialogWindow.setWindowAnimations(R.style.BottomAnimation);
        this.mLayoutResID = layoutResID;
        this.mCountDownSec = countDownSec;
        this.mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRoot = LayoutInflater.from(mContext).inflate(mLayoutResID, null);
        this.setContentView(mRoot);
        mBaseDialog = this;
        ButterKnife.bind(this, mRoot);
        WindowManager.LayoutParams lp = mDialogWindow.getAttributes();
        WindowManager windowManager = mDialogWindow.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        if(isFullScreen){
            lp.width = fullScreenWidth != 0 ? fullScreenWidth : display.getWidth();
            lp.height = fullScreenHeight != 0 ? fullScreenHeight : display.getHeight() + 40;
        }else {
            lp.width = display.getWidth() * 2 / 3;
        }
        mDialogWindow.setAttributes(lp);
        initView();
        initCountDown();
    }

    private void initCountDown() {
        if(mCountDownSec == 0) return;
        if(mTimer != null) mTimer.cancel();
        mTimer = new CountDownTimer(mCountDownSec * 1000l, 1000l) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(mListener != null) mListener.onTick((int)millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                if(mListener != null) mListener.onFinish(mBaseDialog);
                mTimer = null;
            }
        };
        mTimer.start();
    }

    public void setOnCountDownListener(OnCountDownListener listener) {
        this.mListener = listener;
    }

    public abstract void initView();

    public interface OnCountDownListener{
        void onTick(int seconds);

        void onFinish(BaseDialog dialog);
    }
}
