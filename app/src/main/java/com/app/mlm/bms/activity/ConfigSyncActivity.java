package com.app.mlm.bms.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.mlm.R;
import com.app.mlm.bms.dialog.DoneDialog;
import com.app.mlm.bms.dialog.SyncProgressDialog;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 同步配置页
 */
public class ConfigSyncActivity extends BaseActivity {
    @Bind(R.id.tvShangpin)
    TextView tvShangpin;
    @Bind(R.id.tvHuodao)
    TextView tvHuodao;
    @Bind(R.id.tvHuogui)
    TextView tvHuogui;

    @Override
    protected int provideLayoutResId() {
        return R.layout.activity_config_sync;
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

    @OnClick({R.id.syncShangpin, R.id.syncHuodao, R.id.syncHuogui})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.syncShangpin:
                SyncProgressDialog dialog = new SyncProgressDialog(this);
                dialog.show();
                break;
            case R.id.syncHuodao:
                DoneDialog dialog1 = new DoneDialog(this);
                dialog1.show();
                break;
            case R.id.syncHuogui:
                break;
        }
    }
}
