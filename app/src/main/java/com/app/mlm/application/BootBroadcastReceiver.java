package com.app.mlm.application;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.app.mlm.activity.StartupActivity;

/**
 * Created by Administrator on 2019/3/12.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)) {
            Intent myIntent = new Intent(context, StartupActivity.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(myIntent);
        }
    }
}
