package com.app.mlm.bms.activity;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.app.mlm.Constants;
import com.app.mlm.R;
import com.app.mlm.application.MainApp;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.bms.adapter.HDColumnGoodsAdapter;
import com.app.mlm.bms.dialog.CommonDialog;
import com.app.mlm.http.BaseResponse;
import com.app.mlm.http.JsonCallBack;
import com.app.mlm.http.bean.HuodaoBean;
import com.app.mlm.http.bean.SyncChannelListVo;
import com.app.mlm.utils.FastJsonUtil;
import com.app.mlm.utils.Loading;
import com.app.mlm.utils.PreferencesUtil;
import com.app.mlm.utils.ToastUtil;
import com.app.mlm.utils.UpAlarmReportUtils;
import com.app.mlm.widget.SpacesItemDecoration;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 货道配置页
 */
public class ConfigHuodaoActivity extends BaseActivity {
    @Bind(R.id.cleanAll)
    TextView cleanAll;
    @Bind(R.id.fillAll)
    TextView fillAll;
    @Bind(R.id.oneKey)
    TextView oneKey;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    Loading loading;
    HDColumnGoodsAdapter adapter;
    int code = 0;

    List<List<GoodsInfo>> allDataList = new ArrayList<>();
    private List<Integer> lockIds = new ArrayList<>();

    @Override
    protected int provideLayoutResId() {
        return R.layout.activity_config_huodao;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //模拟数据保存
     /*   int[] strings = {2, 2, 2, 2, 2, 2};
        PreferencesUtil.putString("layer", Arrays.toString(strings));
        Log.e("层列数保存取出", PreferencesUtil.getString("layer"));*/
        fillAll.setSelected(true);
        LinearLayoutManager ms = new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(ms);
        recyclerView.addItemDecoration(new SpacesItemDecoration(25, 0, 0, 0));
        initList();
        adapter = new HDColumnGoodsAdapter(this, allDataList);
        recyclerView.setAdapter(adapter);
        showSyncDialog();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        getLockInfo();
    }

    @OnClick({R.id.cleanAll, R.id.fillAll, R.id.oneKey})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cleanAll:
                break;
            case R.id.fillAll:
                fillAllGood();
                break;
            case R.id.oneKey:
                showOneKeyDialog();
                break;
        }
    }

    private void fillAllGood(){
        for (int i = 0; i < allDataList.size();i++){
            List<GoodsInfo> list = allDataList.get(i);
            for(int j = 0; j < list.size();j++){
                GoodsInfo goodsInfo = list.get(j);
                if(!goodsInfo.getMdseUrl().equals("empty")){
                    goodsInfo.setClcCapacity(goodsInfo.getClCapacity());
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

    /**
     * 一键补货，预留功能，当前直接弹窗
     */
    private void showOneKeyDialog() {
        CommonDialog dialog = new CommonDialog(this, "提示", "暂无配货单", "确定", "");
        dialog.show();
    }

    @Override
    public void onActionClicked() {
        CommonDialog dialog = new CommonDialog(this, "提示", "货道配置信息发生了变化，是否应用！", "初始化", "保存");
        dialog.setCommitClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initBox();
            }
        }).setCancelClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUpJson();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onLeftClicked() {
        CommonDialog dialog = new CommonDialog(this, "提示", "货道配置信息发生了变化，是否应用！", "确定", "取消")
                .setCommitClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getUpJson();
                        finish();
                    }
                })
                .setCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        dialog.show();
    }

    private void showSyncDialog() {
        CommonDialog dialog = new CommonDialog(this, "货道初始化", "请点击初始化按钮，然后等待初始化完成", "初始化", "完成")
                .setCommitClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        initBox();
                        //模拟验证货到初始化代码  可删除
//                        int[] strings = {3, 3, 3, 3, 3, 3};
//                        PreferencesUtil.putString("layer", Arrays.toString(strings));
//                        syncData();
//                        adapter.notifyDataSetChanged();
                    }
                });
        dialog.show();
    }

    /**
     * 货道初始化
     */
    private void initBox() {
        loading = Loading.newLoading(ConfigHuodaoActivity.this, "初始化中...");
        loading.show();
        //  ProgressDialog.show(ConfigHuodaoActivity.this,"初始化中...",false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    code = MainApp.bvmAidlInterface.BVMInitXYRoad(1, 0, 0, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                Log.e("初始化返回值", code + "");//初始化返回值: 99
                if (code == 99) {
                    try {
                        int[] count = MainApp.bvmAidlInterface.BVMQueryInitResult(1);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (loading != null && loading.isShowing()) {
                                    loading.dismiss();
                                }
                                //  ProgressDialog.cancel();
                                PreferencesUtil.putString("layer", Arrays.toString(count));
                                Log.e("初始化返回值1111--", Arrays.toString(count));
                                Log.e("初始化返回值2222--", PreferencesUtil.getString("layer"));
                                syncData();
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //错误码
                            // ProgressDialog.cancel();
                            if (loading != null && loading.isShowing()) {
                                loading.dismiss();
                            }
                            UpAlarmReportUtils.upalarmReport(ConfigHuodaoActivity.this, code);
                            Log.e("初始化错误码", code + "");
                        }
                    });
                }
                Log.e("返回码", code + "");
            }
        }).start();

    }

    /**
     * 根据机器获取到的 货道数据 生成空数据
     *
     * @return
     */
    private void initList() {
        String huodaoString = PreferencesUtil.getString("huodao");//是否本地存有huodao 数据
        if (TextUtils.isEmpty(huodaoString)) {
            allDataList.addAll(getData(getHuoDaoData()));
        } else {
            HuodaoBean huodaoBean = FastJsonUtil.getObject(huodaoString, HuodaoBean.class);
            allDataList.addAll(huodaoBean.getAllDataList());
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
     * 同步货道有变动之后处理数据
     */
    private void syncData() {
        List<List<GoodsInfo>> newHuoDaoList = getData(getHuoDaoData());
        for (int i = 0; i < newHuoDaoList.size(); i++) {
            if (i < allDataList.size()) {
                List<GoodsInfo> newList = newHuoDaoList.get(i);
                List<GoodsInfo> oldList = allDataList.get(i);

                for (int j = 0; j < newList.size(); j++) {
                    if (j < oldList.size()) {
                        newList.set(j, oldList.get(j));
                    }
                }
            } else {

            }
        }
        allDataList.clear();
        allDataList.addAll(newHuoDaoList);

    }

    /**
     * 生成提交服务器的json数据
     */
    private String getUpJson() {
        //保存货道数据

        List<GoodsInfo> list = new ArrayList<>();
        for (int i = 0; i < allDataList.size(); i++) {
            for (int j = 0; j < allDataList.get(i).size(); j++) {
                if (!allDataList.get(i).get(j).getMdseUrl().equals("empty")) {
                    list.add(allDataList.get(i).get(j));
                }
            }
        }
        SyncChannelListVo syncChannelListVo = new SyncChannelListVo(list, PreferencesUtil.getString(Constants.VMCODE));
        String upJsonString = FastJsonUtil.createJsonString(syncChannelListVo);
        Log.e("Tag", upJsonString);

        SynChannel(upJsonString);

        return upJsonString;
    }

    private void SynChannel(String upJsonString) {

        OkGo.<BaseResponse>post(Constants.SYN_CHANNEL)
                .tag(this)
                .upJson(upJsonString)
                .execute(new JsonCallBack<BaseResponse>() {
                    @Override
                    public void onSuccess(Response<BaseResponse> response) {
                        if (response.body().getCode() == 0) {
                            HuodaoBean huodaoBean = new HuodaoBean(allDataList);
                            PreferencesUtil.putString("huodao", FastJsonUtil.createJsonString(huodaoBean));
                            finish();
                        }
                    }
                });
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

    private void getLockInfo() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("vmCode", PreferencesUtil.getString(Constants.VMCODE));
        OkGo.<BaseResponse<List<Integer>>>get(Constants.LOCKVMMDSELIST)
                .tag(this)
                .params(httpParams)
                .execute(new JsonCallBack<BaseResponse<List<Integer>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<List<Integer>>> response) {
                        if (response.body().getCode() == 0) {
                            lockIds = response.body().getData();
                            PreferencesUtil.putString(Constants.LOCK_IDS, FastJsonUtil.createJsonString(lockIds));
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<List<Integer>>> response) {
                        ToastUtil.showLongCenterToast("请求数据失败");
                    }
                });
    }

}
