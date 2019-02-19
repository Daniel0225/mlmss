package com.app.mlm.bms.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mlm.Constants;
import com.app.mlm.R;
import com.app.mlm.ServiceTest;
import com.app.mlm.bms.dialog.VersionInfoDialog;
import com.app.mlm.utils.PreferencesUtil;
import com.snbc.bvm.BVMAidlInterface;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 后台配置主页
 */
public class BackgroundManangerSystemActivity extends BaseActivity {
    private static BVMAidlInterface bvmAidlInterface;
    @Bind(R.id.location)
    TextView location;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bvmAidlInterface = BVMAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected int provideLayoutResId() {
        return R.layout.activity_background_mananger_system;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        PreferencesUtil.putString(Constants.VMCODE, "0000051");
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
                startActivity(new Intent(this, ChuhuoTestActivity.class));
                break;
            case R.id.zhifupeizhi: //支付配置
                startActivity(new Intent(this, ConfigPaymentActivity.class));
                break;
            case R.id.huodaopeizhi: //货道测试
                startActivity(new Intent(this, ConfigHuodaoActivity.class));
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
                finish();
                break;
            case R.id.state: //状态：正常售卖/系统维护

                break;

        }
    }

    public void stopService() {
        Intent stopService = new Intent(BackgroundManangerSystemActivity.this, ServiceTest.class);
        stopService(stopService);
    }

    public void bindService() {
        try {
            int code = bvmAidlInterface.BVMInitXYRoad(1, 0, 1, 3);
            Toast.makeText(this, code, Toast.LENGTH_SHORT).show();
            Log.e("返回码", code + "");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void unbindService() {
        try {
            int code1 = bvmAidlInterface.BVMCloseScanDev();
            Toast.makeText(this, code1, Toast.LENGTH_SHORT).show();
            Log.e("关闭返回码", code1 + "");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}