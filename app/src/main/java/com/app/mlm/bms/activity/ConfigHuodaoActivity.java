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
import com.app.mlm.widget.SpacesItemDecoration;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
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

    List<List<GoodsInfo>> allDataList = new ArrayList<>();

    @Override
    protected int provideLayoutResId() {
        return R.layout.activity_config_huodao;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //模拟数据保存
        int[] strings = {6, 5, 8, 7, 5, 8};
        // Collections.reverse(Arrays.asList(strings));
        //   Log.e("数组", Arrays.toString(strings));
        PreferencesUtil.putString("layer", Arrays.toString(strings));
        Log.e("层列数保存取出", PreferencesUtil.getString("layer"));
        fillAll.setSelected(true);
        LinearLayoutManager ms = new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(ms);
        recyclerView.addItemDecoration(new SpacesItemDecoration(25, 0, 0, 0));
        initList();
        HDColumnGoodsAdapter adapter = new HDColumnGoodsAdapter(this, allDataList);
        recyclerView.setAdapter(adapter);
        showSyncDialog();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        //  String json="";
        // String code=MainApp.bvmAidlInterface.BVMStartShip();
    }

    @OnClick({R.id.cleanAll, R.id.fillAll, R.id.oneKey})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cleanAll:
                break;
            case R.id.fillAll:
                break;
            case R.id.oneKey:
                showOneKeyDialog();
                break;
        }
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
                //查询层列数
           /*     try {
                    int[] count = MainApp.bvmAidlInterface.BVMQueryInitResult(1);
                    for (int i = 0; i < count.length; i++) {
                        Log.e("count", count[i] + "");//count: 0
                    }
                    Log.e("count", "第0个" + count[0] + "第1个" + count[1] + "第2个" + count[2] + "第3个" + count[3]);//count: 0
                } catch (RemoteException e) {
                    e.printStackTrace();
                }*/
//                loading = Loading.newLoading(ConfigHuodaoActivity.this, "初始化中...");
               /* if (loading != null) {
                    loading.dismiss();
                }*/
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
                });
        dialog.show();
    }

    private void showSyncDialog() {
        CommonDialog dialog = new CommonDialog(this, "货道初始化", "请点击初始化按钮，然后等待初始化完成", "初始化", "完成")
                .setCommitClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int code = 0;
                        try {
                            code = MainApp.bvmAidlInterface.BVMInitXYRoad(1, 0, 0, 0);
                            loading = Loading.newLoading(ConfigHuodaoActivity.this, "初始化中...");
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        Log.e("初始化返回值", code + "");//初始化返回值: 99
                        if (code == 99) {
                            try {
                                int[] count = MainApp.bvmAidlInterface.BVMQueryInitResult(1);
                                loading.dismiss();
                                //  Collections.reverse(Arrays.asList(count));
                                // Log.e("数组", Arrays.toString(count));
                                PreferencesUtil.putString("layer", Arrays.toString(count));
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        } else {
                            //错误码
                            loading.dismiss();
                            Log.e("初始化错误码", code + "");
                        }
                        Log.e("返回码", code + "");
                    }
                });
        dialog.show();
    }

    /**
     * 根据机器获取到的 货道数据 生成空数据
     *
     * @return
     */
    private void initList() {
        String huodaoString = PreferencesUtil.getString("huodao");
        if (TextUtils.isEmpty(huodaoString)) {
            allDataList = getData(getHuoDaoData());
        } else {
            HuodaoBean huodaoBean = FastJsonUtil.getObject(huodaoString, HuodaoBean.class);
            allDataList = huodaoBean.getAllDataList();
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
        SyncChannelListVo syncChannelListVo = new SyncChannelListVo(list, "0000051");
        String upJsonString = FastJsonUtil.createJsonString(syncChannelListVo);
        Log.e("Tag", upJsonString);

        HuodaoBean huodaoBean = new HuodaoBean(allDataList);
        PreferencesUtil.putString("huodao", FastJsonUtil.createJsonString(huodaoBean));
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
                    }
                });
    }

    /**
     * 解析机器层数据
     */
    private String[] getHuoDaoData() {
        String layerData = PreferencesUtil.getString("layer");
        layerData = layerData.replace("[", "").replace("]", "").replace(" ", "");
        Log.e("Tag", "layerData " + layerData);
        String[] layers = layerData.split(",");
        return layers;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
