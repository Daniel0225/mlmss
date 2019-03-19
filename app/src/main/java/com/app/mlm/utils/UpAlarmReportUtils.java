package com.app.mlm.utils;

import android.content.Context;

import com.app.mlm.Constants;
import com.app.mlm.http.BaseResponse;
import com.app.mlm.http.JsonCallBack;
import com.app.mlm.http.bean.AlarmReportBean;
import com.lzy.okgo.OkGo;
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
        OkGo.<BaseResponse<AlarmReportBean>>post(Constants.ALARM_REPORT)
                .tag(context)
                .params(httpParams)
                .execute(new JsonCallBack<BaseResponse<AlarmReportBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<AlarmReportBean>> response) {
                        // Toast.makeText(context, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
