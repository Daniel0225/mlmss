package com.app.mlm.bms.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.mlm.Constants;
import com.app.mlm.R;
import com.app.mlm.bms.dialog.SigleChoiceDialog;
import com.app.mlm.utils.PreferencesUtil;
import com.dinuscxj.progressbar.CircleProgressBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
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

    private String model;
    private String coldModel;

    @Override
    protected int provideLayoutResId() {
        return R.layout.activity_temperature_control;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
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

    }

    /**
     * 点击应用设置好的配置信息
     */
    @Override
    public void onActionClicked() {
        super.onActionClicked();
        upTemp();
    }

    private void upTemp() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("vmCode", PreferencesUtil.getString(Constants.VMCODE));
        httpParams.put("currentMode", "制冷");
        httpParams.put("coolMode", "弱冷");
        httpParams.put("caseThermal", 30);
        httpParams.put("outThermal", 50);
        OkGo.<String>get(Constants.THERMAL)
                .tag(this)
                .params(httpParams)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

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
                temperatureSetting.setVisibility(View.GONE);
                break;
            case R.id.tvZhileng:
                resetState((TextView) view);
                setCurrentMode("制冷");
                temperatureSetting.setVisibility(View.VISIBLE);
                break;
            case R.id.tvZhire:
                resetState((TextView) view);
                setCurrentMode("制热");
                temperatureSetting.setVisibility(View.VISIBLE);
                break;
            case R.id.llChuhuo:
                SigleChoiceDialog choiceDialog = new SigleChoiceDialog(this, new SigleChoiceDialog.OnItemClickListener() {
                    @Override
                    public void onClick(String value) {
                        tvAllowChuhuo.setText(value);
                        PreferencesUtil.putString(Constants.CHUHUO_WENDU, value);
                    }
                });
                choiceDialog.show();
                break;
            case R.id.low_temp_reduce:
                int lowTemp = Integer.valueOf(lowTempTextView.getText().toString());
                if (lowTemp > 0) {
                    lowTempTextView.setText(String.valueOf(lowTemp - 1));
                }
                break;
            case R.id.low_temp_add:
                int lowTempAdd = Integer.valueOf(lowTempTextView.getText().toString());
                lowTempTextView.setText(String.valueOf(lowTempAdd + 1));
                break;
            case R.id.high_temp_reduce:
                int highTemp = Integer.valueOf(highTempTextView.getText().toString());
                if (highTemp > 0) {
                    highTempTextView.setText(String.valueOf(highTemp - 1));
                }
                break;
            case R.id.high_temp_add:
                int highTempAdd = Integer.valueOf(highTempTextView.getText().toString());
                highTempTextView.setText(String.valueOf(highTempAdd + 1));
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

}
