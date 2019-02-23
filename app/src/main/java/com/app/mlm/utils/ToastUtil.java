package com.app.mlm.utils;

import android.view.Gravity;
import android.widget.Toast;

import com.app.mlm.application.MainApp;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2018/7/23.
 */

public class ToastUtil {
    public static Toast toast = null;

    public static void showShortToast(String content) {
        if (toast == null) {
            toast = Toast.makeText(MainApp.getAppInstance(), content, Toast.LENGTH_SHORT);

        } else {
            toast.setText(content);
        }
        toast.show();
    }

    public static void showLongToast(String content) {
        if (toast == null) {
            toast = Toast.makeText(MainApp.getAppInstance(), content, Toast.LENGTH_LONG);

        } else {
            toast.setText(content);
        }
        toast.show();
    }

    public static void showLongCenterToast(String content) {
        if (toast == null) {
            toast = Toast.makeText(MainApp.getAppInstance(), content, Toast.LENGTH_LONG);

        } else {
            toast.setText(content);
        }
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    //自动设置时间
    public static void showMyToast(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt);
    }
}

