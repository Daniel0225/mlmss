package com.app.mlm.utils;

import android.content.Context;
import android.os.Build;


/**
 * Created by Administrator on 2018/5/28.
 */

public class ProgressDialog {
    private static android.app.ProgressDialog mProgressDialog;

    public static android.app.ProgressDialog show(Context context) {
        return show(context, "请稍候");
    }

    public static android.app.ProgressDialog show(Context context, CharSequence message) {
        create(context, message, true).show();
        return mProgressDialog;
    }

    public static android.app.ProgressDialog show(Context context, CharSequence message, boolean cancelable) {
        create(context, message, cancelable).show();
        return mProgressDialog;
    }

    public static android.app.ProgressDialog create(Context context, CharSequence message, boolean cancelable) {
        if (mProgressDialog != null) {
            cancel();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mProgressDialog = new android.app.ProgressDialog(context, R.style.ProgressDialog);
        } else {
            mProgressDialog = new android.app.ProgressDialog(context);
        }
        mProgressDialog = new android.app.ProgressDialog(context);
//        mProgressDialog.setProgressStyle(android.support.v7.appcompat.R.style.Widget_AppCompat_ProgressBar);
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(cancelable);
        return mProgressDialog;
    }

    public static void cancel() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
            mProgressDialog = null;
        }
    }
}
