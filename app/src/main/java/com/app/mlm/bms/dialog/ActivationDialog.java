package com.app.mlm.bms.dialog;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mlm.R;
import com.app.mlm.activity.MainActivity;
import com.app.mlm.bms.activity.ActivationActivity;
import com.app.mlm.dialog.BaseDialog;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/3/8.
 */

public class ActivationDialog extends BaseDialog {
    @Bind(R.id.icon)
    ImageView icon;
    @Bind(R.id.discription)
    TextView discription;
    @Bind(R.id.button)
    TextView button;
    @Bind(R.id.ivClose)
    ImageView ivClose;
    ActivationActivity activationActivity;
    private String mDescription, mButton;
    private int iconId;

    public ActivationDialog(Context context) {
        super(context, R.layout.activation_done, Gravity.CENTER);
    }

    public ActivationDialog(ActivationActivity activationActivity, String description, String button, int iconId) {
        super(activationActivity, R.layout.activation_done, Gravity.CENTER);
        this.activationActivity = activationActivity;
        this.mDescription = description;
        this.mButton = button;
        this.iconId = iconId;
    }

    @Override
    public void initView() {
        if (!TextUtils.isEmpty(mDescription)) {
            discription.setText(mDescription);
        }
        if (!TextUtils.isEmpty(mButton)) {
            button.setText(mButton);
        }
        if (iconId > 0) {
            icon.setImageResource(iconId);
        }
        startTime();
    }

    @OnClick({R.id.button, R.id.ivClose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                dismiss();
                MainActivity.start(mContext);
            case R.id.ivClose:
                dismiss();
                break;
        }
    }

    /**
     * 开启倒计时
     */
    public void startTime() {
        /** 倒计时60秒，一次1秒 */
        CountDownTimer timer = new CountDownTimer(5 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                button.setTextColor(Color.parseColor("#FFFFFF"));
                button.setClickable(false);
                button.setText("关闭" + "（" + millisUntilFinished / 1000 + "s" + "）");
            }

            @Override
            public void onFinish() {
                dismiss();
                MainActivity.start(mContext);
            }
        }.start();
    }

}
