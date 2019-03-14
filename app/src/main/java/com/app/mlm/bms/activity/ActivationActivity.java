package com.app.mlm.bms.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mlm.BuildConfig;
import com.app.mlm.Constants;
import com.app.mlm.R;
import com.app.mlm.activity.MainActivity;
import com.app.mlm.bms.bean.ActivationBean;
import com.app.mlm.bms.dialog.ActivationDialog;
import com.app.mlm.http.BaseResponse;
import com.app.mlm.http.JsonCallBack;
import com.app.mlm.utils.InputLowerToUpper;
import com.app.mlm.utils.PreferencesUtil;
import com.app.mlm.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/3/8.
 */

public class ActivationActivity extends BaseActivity {
    @Bind(R.id.tv_no)
    EditText tvNo;//编号
    @Bind(R.id.tv_cdkey)
    EditText tvCdkey;//编号
    @Bind(R.id.activation)
    TextView activation;//激活
    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            if (TextUtils.isEmpty(tvNo.getText().toString()) || TextUtils.isEmpty(tvCdkey.getText().toString())) {
                activation.setBackground(getResources().getDrawable(R.drawable.shape_blue_light));
                activation.setTextColor(getResources().getColor(R.color.colorPrimary));
                activation.setClickable(false);
            } else {
                activation.setBackground(getResources().getDrawable(R.drawable.shape_blue_dark));
                activation.setTextColor(getResources().getColor(R.color.withe));
                activation.setClickable(true);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

    };

    public static void start(Context context) {
        Intent intent = new Intent(context, ActivationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int provideLayoutResId() {
        return R.layout.activation_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        tvNo.setTransformationMethod(new InputLowerToUpper());
        tvNo.addTextChangedListener(textWatcher);
        tvCdkey.addTextChangedListener(textWatcher);
        tvNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                tvNo.setBackground(getResources().getDrawable(R.drawable.round_activation));
                tvCdkey.setBackground(getResources().getDrawable(R.drawable.round_activation_no));
            }
        });
        tvCdkey.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                tvNo.setBackground(getResources().getDrawable(R.drawable.round_activation_no));
                tvCdkey.setBackground(getResources().getDrawable(R.drawable.round_activation));
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        if (BuildConfig.DEBUG) {
            MainActivity.start(this);
        }
    }

    private void setActivation() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("vmCode", tvNo.getText().toString());
        httpParams.put("ActivationCode", tvCdkey.getText().toString());
        OkGo.<BaseResponse<ActivationBean>>get(Constants.ACTIVATION)
                .tag(this)
                .params(httpParams)
                .execute(new JsonCallBack<BaseResponse<ActivationBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<ActivationBean>> response) {
                        if (response.body().getCode() == 0) {
                            PreferencesUtil.putString(Constants.VMCODE, response.body().getData().getInnerCode());
                            PreferencesUtil.putInt("status", response.body().getData().getStatus());
                            PreferencesUtil.putString("vmName", response.body().getData().getVmName());
                            ActivationDialog dialog1 = new ActivationDialog(ActivationActivity.this);
                            dialog1.show();
                            Log.e("激活", String.valueOf(response));
                        } else {
                            Toast.makeText(ActivationActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<ActivationBean>> response) {
                        ToastUtil.showLongToast(response.body().getMsg());
                    }
                });
    }

    @OnClick({R.id.tv_no, R.id.tv_cdkey, R.id.activation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activation:
                setActivation();
                break;
        }
    }
}
