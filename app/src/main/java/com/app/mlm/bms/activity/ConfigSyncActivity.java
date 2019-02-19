package com.app.mlm.bms.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.app.mlm.Constants;
import com.app.mlm.R;
import com.app.mlm.bms.dialog.DoneDialog;
import com.app.mlm.bms.dialog.SyncProgressDialog;
import com.app.mlm.http.BaseResponse;
import com.app.mlm.http.JsonCallBack;
import com.app.mlm.http.bean.ProductInfo;
import com.app.mlm.utils.PreferencesUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.litepal.LitePal;

import java.util.List;

import butterknife.Bind;
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
                syncProduceInfo();
                break;
            case R.id.syncHuodao:
                DoneDialog dialog1 = new DoneDialog(this);
                dialog1.show();
                break;
            case R.id.syncHuogui:
                break;
        }
    }

    /**
     * 同步商品数据
     */
    private void syncProduceInfo() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("vmCode", PreferencesUtil.getString(Constants.VMCODE));
        OkGo.<BaseResponse<List<ProductInfo>>>get(Constants.GET_PRODUCT_PRICE)
                .tag(this)
                .params(httpParams)
                .execute(new JsonCallBack<BaseResponse<List<ProductInfo>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<List<ProductInfo>>> response) {
                        if (response.body().getCode() == 0) {
                            saveProductInfo(response.body().getData());
                        }
                    }
                });
    }

    private void saveProductInfo(List<ProductInfo> list) {
        LitePal.deleteAll(ProductInfo.class);
        for (ProductInfo product : list) {
            boolean isSuccess = product.save();
            Log.e("Tag", "isSuccess " + isSuccess);
        }
    }

    /**
     * 同步货道配置
     */

}