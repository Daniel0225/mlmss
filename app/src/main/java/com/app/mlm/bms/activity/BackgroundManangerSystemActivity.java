package com.app.mlm.bms.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mlm.Constants;
import com.app.mlm.R;
import com.app.mlm.application.MainApp;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.bms.dialog.VersionInfoDialog;
import com.app.mlm.http.BaseResponse;
import com.app.mlm.http.JsonCallBack;
import com.app.mlm.http.bean.HuodaoBean;
import com.app.mlm.http.bean.ProductInfo;
import com.app.mlm.utils.FastJsonUtil;
import com.app.mlm.utils.PreferencesUtil;
import com.app.mlm.utils.ToastUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.snbc.bvm.BVMAidlInterface;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 后台配置主页
 */
public class BackgroundManangerSystemActivity extends BaseActivity {
    private static BVMAidlInterface bvmAidlInterface;
    @Bind(R.id.location)
    TextView location;
    String version;
    @Bind(R.id.state)
    TextView state;
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
        // PreferencesUtil.putString(Constants.VMCODE, "0000051");
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {
        MainApp.shopCarList.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(PreferencesUtil.getString(Constants.COUNTER_NAME))) {
            location.setText(PreferencesUtil.getString(Constants.COUNTER_NAME));
        }
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
                startActivityForResult(new Intent(this, ConfigHuodaoActivity.class),1000);
                break;
            case R.id.tongbupeizhi: //同步配置
                startActivity(new Intent(this, ConfigSyncActivity.class));
                break;
            case R.id.wendukongzhi: //温度配置
                startActivity(new Intent(this, TemperatureControlActivity.class));
                break;
            case R.id.banbenxinxi: //版本信息
                VersionInfoDialog versionInfoDialog = new VersionInfoDialog(this, version);
                versionInfoDialog.show();
                break;
            case R.id.fanhui: //返回
                // startActivity(new Intent(this, ChuhuoActivity.class));
                finish();
                break;
            case R.id.state: //状态：正常售卖/系统维护
                switch (PreferencesUtil.getInt("status")) {
                    case 0:
                        state.setText("未启用");
                        break;
                    case 1:
                        state.setText("正常售卖");
                        break;
                    case 2:
                        state.setText("维修中");
                        break;
                }
                break;

        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 1000 && resultCode == RESULT_OK){
            syncChannel();
        }
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

                    @Override
                    public void onError(Response<BaseResponse<List<GoodsInfo>>> response) {
                        ToastUtil.showLongToast("请求服务器失败,请稍后重试");
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
                goodsInfo.setMdsePrice(goodsInfo.getRealPrice());
                if(goodsInfo.getActivityPrice() != 0){
                    goodsInfo.setRealPrice(goodsInfo.getActivityPrice());
                }
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
                stringBuffer.append((newHuoDaoList.size() - i));
                if (j+1 < 10) {
                    stringBuffer.append(0);
                }
                stringBuffer.append(j + 1);
                String clCode = stringBuffer.toString();
                for (int h = 0; h < list.size(); h++) {
                    GoodsInfo goods = list.get(h);
                    if (!TextUtils.isEmpty(goods.getClCode()) && goods.getClCode().equals(clCode)) {
                        itemList.set(j, goods);
                    }
                }
            }
        }

        HuodaoBean huodaoBean = new HuodaoBean(newHuoDaoList);
        PreferencesUtil.putString("huodao", FastJsonUtil.createJsonString(huodaoBean));
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
            goodsInfo.setMdsePrice(0);
            goodsInfoList.add(goodsInfo);
        }
        return goodsInfoList;
    }

}
