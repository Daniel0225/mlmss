package com.app.mlm.bms.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.mlm.R;
import com.app.mlm.bms.dialog.SigleChoiceDialog;
import com.dinuscxj.progressbar.CircleProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;
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

    @Override
    protected int provideLayoutResId() {
        return R.layout.activity_temperature_control;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

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
        finish();
    }

    @OnClick({R.id.tvChangwen, R.id.tvZhileng, R.id.tvZhire, R.id.llChuhuo})
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
                    }
                });
                choiceDialog.show();
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
    }

    private void setCurrentMode(String mode) {
        tvCurrentMode.setText("当前温度：" + mode);
    }

}
