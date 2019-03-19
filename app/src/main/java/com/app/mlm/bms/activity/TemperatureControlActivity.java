package com.app.mlm.bms.activity;

import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mlm.Constants;
import com.app.mlm.R;
import com.app.mlm.application.MainApp;
import com.app.mlm.bms.dialog.CommonDialog;
import com.app.mlm.bms.dialog.SigleChoiceDialog;
import com.app.mlm.http.BaseResponse;
import com.app.mlm.http.JsonCallBack;
import com.app.mlm.http.bean.AllDataBean;
import com.app.mlm.utils.PreferencesUtil;
import com.app.mlm.utils.ToastUtil;
import com.app.mlm.utils.UpAlarmReportUtils;
import com.dinuscxj.progressbar.CircleProgressBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 温度配置页面
 */
public class TemperatureControlActivity extends BaseActivity {
    @Bind(R.id.tvCurrentMode)
    TextView tvCurrentMode;
    @Bind(R.id.tvDeviceTemper)
    TextView tvDeviceTemper;
    @Bind(R.id.tvRoomTempr)
    TextView tvRoomTempr;
    @Bind(R.id.tvChangwen)
    TextView tvChangwen;
    @Bind(R.id.tvZhileng)
    TextView tvZhileng;
    @Bind(R.id.tvZhire)
    TextView tvZhire;
    @Bind(R.id.llChuhuo)
    LinearLayout llChuhuo;
    @Bind(R.id.tvAllowChuhuo)
    TextView tvAllowChuhuo;
    @Bind(R.id.tvSettingName)
    TextView tvSettingName;
    @Bind(R.id.tvSettingMode1)
    TextView tvSettingMode1;
    @Bind(R.id.tvSettingMode2)
    TextView tvSettingMode2;
    @Bind(R.id.cpbProgressLow)
    CircleProgressBar cpbProgressLow;
    @Bind(R.id.tvDescLow)
    TextView tvDescLow;
    @Bind(R.id.cpbProgressHigh)
    CircleProgressBar cpbProgressHigh;
    @Bind(R.id.tvDescHigh)
    TextView tvDescHigh;
    @Bind(R.id.temperatureSetting)
    LinearLayout temperatureSetting;
    @Bind(R.id.low_temp_tx)
    TextView lowTempTextView;
    @Bind(R.id.high_temp_tx)
    TextView highTempTextView;
    @Bind(R.id.setcoldline)
    LinearLayout setcoldline;
    @Bind(R.id.uptemp)
    LinearLayout uptemp;
    int[] code;//室内外温度
    private String model = "";
    private String coldModel = "常温";
    private int type = 0;//当前模式

    @Override
    protected int provideLayoutResId() {
        return R.layout.activity_temperature_control;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //UpAlarmReportUtils.upalarmReport(TemperatureControlActivity.this, 1500);
//        try {
//            //获取温度
//            int[] code = MainApp.bvmAidlInterface.BVMGetColdHeatTemp(1);
//            Log.e("箱内温度返回", "箱内温度" + code[0] + "箱外温度" + code[1]);// 箱内温度24箱外温度24
//            int code0 = MainApp.bvmAidlInterface.BVMSetColdHeatModel(1, 1);
//            Log.e("制冷制热模式设置", code0 + "");//99
//
//            int code1 = MainApp.bvmAidlInterface.BVMGetColdHeatModel(1);
//            Log.e("制冷制热模式查询", code1 + "");//1
//
//            int code2 = MainApp.bvmAidlInterface.BVMSetColdModel(1, 1);
//            Log.e("制冷模式设置", code2 + "");//99
//
//            int code3 = MainApp.bvmAidlInterface.BVMGetColdMode(1);
//            Log.e("制冷模式查询", code3 + "");//1
//
//            int code4 = MainApp.bvmAidlInterface.BVMSetColdTemp(1, 15, 5);
//            Log.e("制冷温度设置", code4 + "");//99
//
//            int[] code5 = MainApp.bvmAidlInterface.BVMGetColdTemp(1);
//            Log.e("制冷温度查询", "on" + code5[0] + "off" + code5[1]);//int[0]: 制冷ON温度 int[1] 制冷OFF温度
//
//            int code6 = MainApp.bvmAidlInterface.BVMSetHeatTemp(1, 10, 25);
//            Log.e("制热温度设置", code6 + "");//99
//
//            int[] code7 = MainApp.bvmAidlInterface.BVMGetHeatTemp(1);
//            Log.e("制热温度查询", "on" + code7[0] + "off" + code7[1]);// int[0]: 制冷ON温度 int[1] 制冷OFF温度
//
//            String version = MainApp.bvmAidlInterface.BVMGetVersion();
//            Log.e("中间层版本信息", version + "----");//中间层版本信息: null----
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
       /* cpbProgressLow.setMax(21);
        cpbProgressHigh.setMax(21);*/
        cpbProgressHigh.setProgress(25);
        cpbProgressLow.setProgress(4);
        // 设置你想要的ProgressFormatter
        cpbProgressHigh.setProgressFormatter(new MyProgressFormatter());
        cpbProgressLow.setProgressFormatter(new MyProgressFormatter());
        try {
            code = MainApp.bvmAidlInterface.BVMGetColdHeatTemp(1);
            tvDeviceTemper.setText("箱体温度：" + code[0] + "度");
            tvRoomTempr.setText("室外温度：" + code[1] + "度");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(PreferencesUtil.getString("currentMode"))) {
            switch (PreferencesUtil.getString("currentMode")) {
                case "常温":
                    tvChangwen.performLongClick();
                    resetState((TextView) tvChangwen);
                    setCurrentMode("常温");
                    type = 0;
                    coldModel = "常温";
                    temperatureSetting.setVisibility(View.GONE);
                    break;
                case "制冷":
                    resetState((TextView) tvZhileng);
                    setCurrentMode("制冷");
                    //重置
                    coldModel = tvSettingMode1.getText().toString();
                    lowTempTextView.setText(PreferencesUtil.getInt(Constants.COLL_LOW_TEMP) + "");
                    highTempTextView.setText(PreferencesUtil.getInt(Constants.COOL_HIGH_TEMP) + "");
                    cpbProgressHigh.setProgress(PreferencesUtil.getInt(Constants.COOL_HIGH_TEMP));
                    cpbProgressLow.setProgress(PreferencesUtil.getInt(Constants.COLL_LOW_TEMP));
                    type = 1;
                    temperatureSetting.setVisibility(View.VISIBLE);
                    setcoldline.setVisibility(View.VISIBLE);
                    if (PreferencesUtil.getString("coolMode").equals("弱冷")) {
                        tvSettingMode1.performClick();
                    } else if (PreferencesUtil.getString("coolMode").equals("强冷")) {
                        tvSettingMode2.performClick();
                    }
                    if (!TextUtils.isEmpty(PreferencesUtil.getString(Constants.CHUHUO_WENDU))) {
                        tvAllowChuhuo.setText(PreferencesUtil.getString(Constants.CHUHUO_WENDU));
                    }
                    break;
                case "制热":
                    resetState((TextView) tvZhire);
                    setCurrentMode("制热");
                    //重置
                    coldModel = tvSettingMode1.getText().toString();
                    lowTempTextView.setText(PreferencesUtil.getInt(Constants.HEAT_LOW_TEMP) + "");
                    highTempTextView.setText(PreferencesUtil.getInt(Constants.HEAT_HIGH_TEMP) + "");
                    cpbProgressHigh.setProgress(PreferencesUtil.getInt(Constants.HEAT_HIGH_TEMP));
                    cpbProgressLow.setProgress(PreferencesUtil.getInt(Constants.HEAT_LOW_TEMP));
                    type = 2;
                    coldModel = "";
                    temperatureSetting.setVisibility(View.VISIBLE);
                    setcoldline.setVisibility(View.GONE);
                    if (!TextUtils.isEmpty(PreferencesUtil.getString(Constants.CHUHUO_WENDU))) {
                        tvAllowChuhuo.setText(PreferencesUtil.getString(Constants.CHUHUO_WENDU));
                    }
                    break;
            }
        }
    }

    /**
     * 点击应用设置好的配置信息
     */
    @Override
    public void onActionClicked() {
        super.onActionClicked();
        //upTemp();
        setTempDialog();
    }

    private void setTempDialog() {
        CommonDialog dialog = new CommonDialog(this, "温度控制", "请确定修改温度设置", "确定", "取消")
                .setCommitClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int code = 0;
                        switch (type) {
                            case 0://常温
                                try {
                                    code = MainApp.bvmAidlInterface.BVMSetColdHeatModel(1, 0);
                                    if (code == 99) {
                                        upTemp();
                                    } else {
                                        UpAlarmReportUtils.upalarmReport(TemperatureControlActivity.this, code);
                                    }
                                    finish();
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                    UpAlarmReportUtils.upalarmReport(TemperatureControlActivity.this, code);
                                }
                                break;
                            case 1://制冷
                                try {
                                    code = MainApp.bvmAidlInterface.BVMSetColdHeatModel(1, 1);
                                    if (code == 99) {
                                        code = MainApp.bvmAidlInterface.BVMSetColdTemp(1, Integer.parseInt(highTempTextView.getText().toString()), Integer.parseInt(lowTempTextView.getText().toString()));
                                        if (code == 99) {
                                            upTemp();
                                        } else {
                                            UpAlarmReportUtils.upalarmReport(TemperatureControlActivity.this, code);
                                        }
                                    } else {
                                        UpAlarmReportUtils.upalarmReport(TemperatureControlActivity.this, code);
                                    }
                                    finish();
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                    UpAlarmReportUtils.upalarmReport(TemperatureControlActivity.this, code);
                                }
                                break;

                            case 3://制热
                                try {
                                    code = MainApp.bvmAidlInterface.BVMSetColdHeatModel(1, 2);
                                    if (code == 99) {
                                        code = MainApp.bvmAidlInterface.BVMSetHeatTemp(1, Integer.parseInt(highTempTextView.getText().toString()), Integer.parseInt(lowTempTextView.getText().toString()));
                                        if (code == 99) {
                                            upTemp();
                                        } else {
                                            UpAlarmReportUtils.upalarmReport(TemperatureControlActivity.this, code);
                                        }
                                    } else {
                                        UpAlarmReportUtils.upalarmReport(TemperatureControlActivity.this, code);
                                    }
                                    finish();
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                    UpAlarmReportUtils.upalarmReport(TemperatureControlActivity.this, code);
                                }
                                break;
                        }

                    }
                });
        dialog.show();
    }

    private void upTemp() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("vmCode", PreferencesUtil.getString(Constants.VMCODE));
        httpParams.put("currentMode", model);
        httpParams.put("coolMode", coldModel);
        httpParams.put("caseThermal", 30);
        httpParams.put("outThermal", 50);
        OkGo.<BaseResponse<AllDataBean>>get(Constants.THERMAL)
                .tag(this)
                .params(httpParams)
                .execute(new JsonCallBack<BaseResponse<AllDataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<AllDataBean>> response) {
                        if (response.body().data.getCode() == 0) {
                            if (model.equals("制冷")) {
                                PreferencesUtil.putInt(Constants.COLL_LOW_TEMP, Integer.parseInt(lowTempTextView.getText().toString()));
                                PreferencesUtil.putInt(Constants.COOL_HIGH_TEMP, Integer.parseInt(highTempTextView.getText().toString()));
                            } else if (model.equals("制热")) {
                                PreferencesUtil.putInt(Constants.HEAT_LOW_TEMP, Integer.parseInt(lowTempTextView.getText().toString()));
                                PreferencesUtil.putInt(Constants.HEAT_HIGH_TEMP, Integer.parseInt(highTempTextView.getText().toString()));
                            } else {
                                PreferencesUtil.putInt(Constants.COLL_LOW_TEMP, 4);
                                PreferencesUtil.putInt(Constants.COOL_HIGH_TEMP, 25);
                                PreferencesUtil.putInt(Constants.HEAT_LOW_TEMP, 4);
                                PreferencesUtil.putInt(Constants.HEAT_HIGH_TEMP, 25);
                            }
                            PreferencesUtil.putString("currentMode", model);
                            PreferencesUtil.putString("coolMode", coldModel);
                            // Toast.makeText(TemperatureControlActivity.this, response.body().data.getMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(TemperatureControlActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<AllDataBean>> response) {
                        ToastUtil.showLongToast("请求服务器失败,请稍后重试");
                    }
                });
    }

    @OnClick({R.id.tvChangwen, R.id.tvZhileng, R.id.tvZhire, R.id.llChuhuo, R.id.low_temp_reduce, R.id.low_temp_add, R.id.high_temp_add, R.id.high_temp_reduce,
            R.id.tvSettingMode1, R.id.tvSettingMode2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvChangwen:
                resetState((TextView) view);
                setCurrentMode("常温");
                type = 0;
                coldModel = "常温";
                temperatureSetting.setVisibility(View.GONE);
                break;
            case R.id.tvZhileng:
                resetState((TextView) view);
                setCurrentMode("制冷");
                //重置
                coldModel = tvSettingMode1.getText().toString();
                lowTempTextView.setText("4");
                highTempTextView.setText("25");
                cpbProgressHigh.setProgress(25);
                cpbProgressLow.setProgress(4);
                type = 1;
                temperatureSetting.setVisibility(View.VISIBLE);
                setcoldline.setVisibility(View.VISIBLE);
                break;
            case R.id.tvZhire:
                resetState((TextView) view);
                setCurrentMode("制热");
                //重置
                coldModel = tvSettingMode1.getText().toString();
                lowTempTextView.setText("4");
                highTempTextView.setText("25");
                cpbProgressHigh.setProgress(25);
                cpbProgressLow.setProgress(4);
                type = 2;
                coldModel = "";
                temperatureSetting.setVisibility(View.VISIBLE);
                setcoldline.setVisibility(View.GONE);
                break;
            case R.id.llChuhuo:
                if (type != 0) {
                    SigleChoiceDialog choiceDialog = new SigleChoiceDialog(this, new SigleChoiceDialog.OnItemClickListener() {
                        @Override
                        public void onClick(String value) {
                            tvAllowChuhuo.setText(value);
                            PreferencesUtil.putString(Constants.CHUHUO_WENDU, value);
                        }
                    });
                    choiceDialog.show();
                } else {
                    //常温,为默认时，出货时不需要判定温度
                    PreferencesUtil.putString(Constants.CHUHUO_WENDU, "默认");
                }
                break;
            case R.id.low_temp_reduce:
                int lowTemp = Integer.valueOf(lowTempTextView.getText().toString());
                if (lowTemp > 4) {
                    lowTempTextView.setText(String.valueOf(lowTemp - 1));
                    cpbProgressLow.setProgress(lowTemp - 1);
                } else {
                    Toast.makeText(TemperatureControlActivity.this, "最低温度为4度", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.low_temp_add:
                int lowTempAdd = Integer.valueOf(lowTempTextView.getText().toString());
                if (lowTempAdd < Integer.parseInt(highTempTextView.getText().toString()) - 1 && lowTempAdd < 24) {
                    lowTempTextView.setText(String.valueOf(lowTempAdd + 1));
                    cpbProgressLow.setProgress(lowTempAdd + 1);
                } else {
                    Toast.makeText(TemperatureControlActivity.this, "最低温度已达最高", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.high_temp_reduce:
                int highTemp = Integer.valueOf(highTempTextView.getText().toString());
                if (highTemp > 4 && highTemp > Integer.parseInt(lowTempTextView.getText().toString()) + 1) {
                    highTempTextView.setText(String.valueOf(highTemp - 1));
                    cpbProgressHigh.setProgress(highTemp - 1);
                } else {
                    Toast.makeText(TemperatureControlActivity.this, "最高温度已达最低", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.high_temp_add:
                int highTempAdd = Integer.valueOf(highTempTextView.getText().toString());
                if (highTempAdd < 25) {
                    highTempTextView.setText(String.valueOf(highTempAdd + 1));
                    cpbProgressHigh.setProgress(highTempAdd + 1);
                } else {
                    Toast.makeText(TemperatureControlActivity.this, "最高温度为25度", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tvSettingMode1:
                tvSettingMode1.setBackgroundResource(R.drawable.shape_blue);
                tvSettingMode1.setTextColor(getResources().getColor(R.color.whiteColor));
                tvSettingMode2.setBackgroundResource(R.drawable.shape_blue_light);
                tvSettingMode2.setTextColor(getResources().getColor(R.color.colorPrimary));
                coldModel = tvSettingMode1.getText().toString();
                break;
            case R.id.tvSettingMode2:
                tvSettingMode1.setBackgroundResource(R.drawable.shape_blue_light);
                tvSettingMode1.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvSettingMode2.setBackgroundResource(R.drawable.shape_blue);
                tvSettingMode2.setTextColor(getResources().getColor(R.color.whiteColor));
                coldModel = tvSettingMode2.getText().toString();
                break;
        }
    }

    private void resetState(TextView view) {
        tvChangwen.setBackgroundResource(R.drawable.shape_blue_light);
        tvChangwen.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvZhileng.setBackgroundResource(R.drawable.shape_blue_light);
        tvZhileng.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvZhire.setBackgroundResource(R.drawable.shape_blue_light);
        tvZhire.setTextColor(getResources().getColor(R.color.colorPrimary));
        view.setBackgroundResource(R.drawable.shape_blue);
        view.setTextColor(getResources().getColor(R.color.whiteColor));
        model = view.getText().toString();
    }

    private void setCurrentMode(String mode) {
        tvCurrentMode.setText("当前温度：" + mode);
    }

    private static final class MyProgressFormatter implements CircleProgressBar.ProgressFormatter {
        private static final String DEFAULT_PATTERN = "%s°";

        @Override
        public CharSequence format(int progress, int max) {
            return String.format(DEFAULT_PATTERN, (int) ((float) progress / (float) max * 100));
        }
    }


}
