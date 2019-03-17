package com.app.mlm.utils;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.app.mlm.R;


/**
 *
 */
public class MyDialogUtil {

    /**
     * 显示底部dialog
     *
     * @param context
     * @param initDialogView
     * @param gravity        显示位置
     * @return
     */
    public static AlertDialog getDialog(Context context, View initDialogView, int gravity) {
        AlertDialog verifyCodeDialog = new AlertDialog.Builder(context).setView(initDialogView).create();
//        WindowManager.LayoutParams layoutparams = verifyCodeDialog.getWindow().getAttributes() ;
//        layoutparams.alpha = 0.9f ;
        Window window = verifyCodeDialog.getWindow();
        window.setGravity(gravity);
     /*   switch (gravity) {
            case Gravity.CENTER:
                window.setWindowAnimations(R.style.dialog_style_center);
                break;
            default:
                window.setWindowAnimations(R.style.dialog_style);
                break;

        }*/
        window.setBackgroundDrawableResource(R.color.transparent);

        WindowManager.LayoutParams lp = window.getAttributes();       //布局
        WindowManager windowManager = window.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        lp.width = display.getWidth();
        window.setAttributes(lp);

//        verifyCodeDialog.show();
        return verifyCodeDialog;
    }


}
