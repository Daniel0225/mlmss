package com.app.mlm.bms.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.app.mlm.Constants;
import com.app.mlm.R;
import com.app.mlm.bean.AddInfoEvent;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.bms.dialog.DoneDialog;
import com.app.mlm.bms.dialog.SyncProgressDialog;
import com.app.mlm.http.BaseResponse;
import com.app.mlm.http.JsonCallBack;
import com.app.mlm.http.bean.AdBean;
import com.app.mlm.http.bean.CounterBean;
import com.app.mlm.http.bean.HuodaoBean;
import com.app.mlm.http.bean.ProductInfo;
import com.app.mlm.utils.FastJsonUtil;
import com.app.mlm.utils.PinyinComparator;
import com.app.mlm.utils.PreferencesUtil;
import com.app.mlm.utils.TextPinyinUtil;
import com.app.mlm.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

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
                getAddInfo();
                break;
            case R.id.syncHuodao:
                String layerString = PreferencesUtil.getString("layer");
                if (TextUtils.isEmpty(layerString)) {
                    ToastUtil.showLongCenterToast("请先初始化货道");
                    return;
                }
                syncChannel();
                break;
            case R.id.syncHuogui:
                syncCounter();
                break;
        }
    }

    private void getAddInfo() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("vmCode", PreferencesUtil.getString(Constants.VMCODE));
        OkGo.<BaseResponse<List<AdBean>>>get(Constants.AD_URL)
                .params(httpParams)
                .tag(this)
                .execute(new JsonCallBack<BaseResponse<List<AdBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<List<AdBean>>> response) {

                        if (response.body().getCode() == 0) {
                            List<AdBean> adBeanList = response.body().data;
                            PreferencesUtil.putString(Constants.ADDATA, FastJsonUtil.createJsonString(adBeanList));
                            refreshAddInfo(adBeanList);
                        }
                    }
                });
    }

    /**
     * 刷新广告信息
     *
     * @param adBeanList
     */
    private void refreshAddInfo(List<AdBean> adBeanList) {
        String adString = PreferencesUtil.getString(Constants.ADDATA);
        List<AdBean> adBeans = new ArrayList<>();
        if (!TextUtils.isEmpty(adString)) {
            adBeans = FastJsonUtil.getObjects(adString, AdBean.class);
        }

        for (AdBean adBean : adBeanList) {

            if (adBean.getFileType() == 3) {
                for (AdBean ad : adBeans) {
                    if (ad.getFileType() == 3) {
                        if (adBean.getSuffix().equals(ad.getSuffix()) && adBean.getUrl().equals(ad.getUrl())) {//广告信息无变化

                        } else {
                            //广告信息有变化  发消息给首页 更新广告展示
                            EventBus.getDefault().post(new AddInfoEvent());
                        }
                    }
                }
            }
        }
    }

    private void startTimeCounter() {
        new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (dialog.getProgress() < 98) {
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
                    boolean isSuccess = productInfo.save();
                    Log.e("Tag", "isSuccess " + isSuccess);
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
        OkGo.<BaseResponse<List<GoodsInfo>>>get(Constants.SYN_TO_CHANNEL)
                .tag(this)
                .params(httpParams)
                .execute(new JsonCallBack<BaseResponse<List<GoodsInfo>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<List<GoodsInfo>>> response) {
                        if(response.body().getCode() == 0){
                            updateChannelInfo(response.body().getData());
                        }else{
                            ToastUtil.showLongCenterToast(response.body().getMsg());
                        }

                    }
                });
    }

    private void updateChannelInfo(List<GoodsInfo> list) {
        for (GoodsInfo goodsInfo : list) {

            List<ProductInfo> productInfos = LitePal.where("mdseId = ?", String.valueOf(goodsInfo.getMdseId())).find(ProductInfo.class);

            if (productInfos.size() > 0) {
                ProductInfo productInfo = productInfos.get(0);
                goodsInfo.setMdsePack(productInfo.getMdsePack());
                goodsInfo.setMdseBrand(productInfo.getMdseBrand());
                goodsInfo.setMdseName(productInfo.getMdseName());
                goodsInfo.setMdsePrice(String.valueOf(productInfo.getMdsePrice()));
                goodsInfo.setMdseUrl(productInfo.getMdseUrl());
            } else {
                ToastUtil.showLongToast("找不到商品信息,请先同步商品信息");
            }
        }

        List<List<GoodsInfo>> newHuoDaoList = getData(getHuoDaoData());
        for (int i = 0; i < newHuoDaoList.size(); i++) {
            List<GoodsInfo> itemList = newHuoDaoList.get(i);
            for (int j = 0; j < itemList.size(); j++) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append((i + 1));
                if (j < 10) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(j + 1);
                String clCode = stringBuffer.toString();
                for (int h = 0; h < list.size(); h++) {
                    GoodsInfo goods = list.get(h);
                    if (goods.getClCode().equals(clCode)) {
                        itemList.set(j, goods);
                    }
                }
            }
        }

        HuodaoBean huodaoBean = new HuodaoBean(newHuoDaoList);
        PreferencesUtil.putString("huodao", FastJsonUtil.createJsonString(huodaoBean));

        DoneDialog dialog1 = new DoneDialog(ConfigSyncActivity.this);
        dialog1.show();
    }

    /**
     * 解析机器层数据
     */
    private String[] getHuoDaoData() {
        String layerData = PreferencesUtil.getString("layer");
        if (TextUtils.isEmpty(layerData)) {
            return new String[]{};
        } else {
            layerData = layerData.replace("[", "").replace("]", "").replace(" ", "");
            Log.e("Tag", "layerData " + layerData);
            String[] layers = layerData.split(",");
            return layers;
        }
    }

    private List<List<GoodsInfo>> getData(String[] layers) {
        List<List<GoodsInfo>> list = new ArrayList<>();
        for (int i = layers.length - 1; i >= 0; i--) {
            list.add(getDefaultData(Integer.valueOf(layers[i])));
        }
        return list;
    }

    private List<GoodsInfo> getDefaultData(int column) {//传入当前行 有几列
        List<GoodsInfo> goodsInfoList = new ArrayList<>();
        for (int i = 0; i < column; i++) {
            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.setMdseName("请补货");
            goodsInfo.setMdseUrl("empty");
            goodsInfo.setMdsePrice("0");
            goodsInfoList.add(goodsInfo);
        }
        return goodsInfoList;
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
