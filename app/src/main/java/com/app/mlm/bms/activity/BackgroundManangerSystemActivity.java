package com.app.mlm.bms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.mlm.R;
import com.app.mlm.bms.dialog.CommonDialog;
import com.app.mlm.bms.dialog.VersionInfoDialog;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 后台配置主页
 */
public class BackgroundManangerSystemActivity extends BaseActivity {
    @Bind(R.id.location)
    TextView location;

    @Override
    protected int provideLayoutResId() {
        return R.layout.activity_background_mananger_system;
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

    @OnClick({R.id.chuhuoceshi, R.id.zhifupeizhi, R.id.huodaopeizhi, R.id.tongbupeizhi, R.id.wendukongzhi, R.id.banbenxinxi, R.id.fanhui, R.id.state})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.chuhuoceshi: //出货测试
                CommonDialog commonDialog = new CommonDialog(this, "标题", "内容", "确定", "取消");
                commonDialog.show();
                break;
            case R.id.zhifupeizhi: //支付配置
                startActivity(new Intent(this, ConfigPaymentActivity.class));
                break;
            case R.id.huodaopeizhi: //货道配置
                startActivity(new Intent(this, CargoAllocationActivity.class));
                break;
            case R.id.tongbupeizhi: //同步配置
                startActivity(new Intent(this, ConfigSyncActivity.class));
                break;
            case R.id.wendukongzhi: //温度配置
                startActivity(new Intent(this, TemperatureControlActivity.class));
                break;
            case R.id.banbenxinxi: //版本信息
                VersionInfoDialog versionInfoDialog = new VersionInfoDialog(this);
                versionInfoDialog.show();
                break;
            case R.id.fanhui: //返回
                break;
            case R.id.state: //状态：正常售卖/系统维护

                break;
        }
    }
}
