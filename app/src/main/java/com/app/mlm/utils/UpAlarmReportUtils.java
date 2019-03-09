package com.app.mlm.utils;

import android.content.Context;

import com.app.mlm.Constants;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

/**
 * Created by Administrator on 2019/3/9.
 */

public class UpAlarmReportUtils {
    public static void upalarmReport(Context context, int code) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("deviceId", PreferencesUtil.getString(Constants.VMCODE));
        httpParams.put("device", "");
        httpParams.put("importance", 1);
        httpParams.put("code", code);
        httpParams.put("status", 1);
        httpParams.put("ctime", System.currentTimeMillis());
        OkGo.<String>get(Constants.ALARM_REPORT)
                .tag(context)
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                    }
                });
    }
}
