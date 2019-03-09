package com.app.mlm.bms.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.app.mlm.Constants;
import com.app.mlm.R;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.bms.dialog.DoneDialog;
import com.app.mlm.bms.dialog.SyncProgressDialog;
import com.app.mlm.http.BaseResponse;
import com.app.mlm.http.JsonCallBack;
import com.app.mlm.http.bean.CounterBean;
import com.app.mlm.http.bean.ProductInfo;
import com.app.mlm.utils.PinyinComparator;
import com.app.mlm.utils.PreferencesUtil;
import com.app.mlm.utils.TextPinyinUtil;
import com.app.mlm.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.litepal.LitePal;

import java.util.Collections;
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
    SyncProgressDialog dialog;
    private PinyinComparator comparator = new PinyinComparator();

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
                dialog = new SyncProgressDialog(this);
                dialog.show();
                startTimeCounter();
                syncProduceInfo();
                break;
            case R.id.syncHuodao:
                syncChannel();

                break;
            case R.id.syncHuogui:
                syncCounter();
                break;
        }
    }

    private void startTimeCounter() {
        new CountDownTimer(100000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (dialog.getProgress() < 100) {
                    Log.e("Tag", "progress " + dialog.getProgress());
                    dialog.setProgress(dialog.getProgress() + 2);
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
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

                    @Override
                    public void onError(Response<BaseResponse<List<ProductInfo>>> response) {
                        super.onError(response);
                        dialog.dismiss();
                        ToastUtil.showLongToast("请求服务器数据失败");
                    }
                });
    }

    private void saveProductInfo(List<ProductInfo> list) {
        LitePal.deleteAll(ProductInfo.class);

//        SaveExecutor saveExecutor = LitePal.saveAllAsync(list);
//        saveExecutor.listen(new SaveCallback() {
//            @Override
//            public void onFinish(boolean success) {
//                dialog.setProgress(100);
//                dialog.dismiss();
//                ToastUtil.showLongToast("同步完成");
//            }
//        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                Collections.sort(list, comparator);
                for (int i = 0; i < list.size(); i++) {
                    ProductInfo productInfo = list.get(i);
                    if (TextUtils.isEmpty(productInfo.getMdseName())) {
                        productInfo.setQuanping("");
                    } else {
                        productInfo.setQuanping(TextPinyinUtil.getInstance().getPinyin(productInfo.getMdseName()));
                    }
                    productInfo.save();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        ToastUtil.showLongToast("同步完成");
                    }
                });
            }
        }).start();



    }

    /**
     * 同步货道配置
     */
    private void syncChannel() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("vmCode", PreferencesUtil.getString(Constants.VMCODE));
        OkGo.<GoodsInfo>get(Constants.SYN_TO_CHANNEL)
                .tag(this)
                .params(httpParams)
                .execute(new JsonCallBack<GoodsInfo>() {
                    @Override
                    public void onSuccess(Response<GoodsInfo> response) {
                        DoneDialog dialog1 = new DoneDialog(ConfigSyncActivity.this);
                        dialog1.show();
                    }
                });
    }

    /**
     * 同步货柜信息
     */
    private void syncCounter() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("vmCode", PreferencesUtil.getString(Constants.VMCODE));

        OkGo.<BaseResponse<List<CounterBean>>>get(Constants.SYNC_COUNTER)
                .tag(this)
                .params(httpParams)
                .execute(new JsonCallBack<BaseResponse<List<CounterBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<List<CounterBean>>> response) {
                        if (response.body().getCode() == 0) {
                            if (response.body().getData().size() > 0) {
                                CounterBean counterBean = response.body().getData().get(0);
                                PreferencesUtil.putString(Constants.COUNTER_NUM, counterBean.getCounterNumber());
                                PreferencesUtil.putString(Constants.COUNTER_NAME, counterBean.getCounterName());
                                DoneDialog dialog1 = new DoneDialog(ConfigSyncActivity.this);
                                dialog1.show();
                            }
                        }

                    }
                });
    }


}
