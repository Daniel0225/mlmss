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
import com.app.mlm.utils.FastJsonUtil;
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

    List<List<GoodsInfo>> allDataList = new ArrayList<>();

    @Override
    protected int provideLayoutResId() {
        return R.layout.activity_config_huodao;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        fillAll.setSelected(true);
        LinearLayoutManager ms = new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(ms);
        recyclerView.addItemDecoration(new SpacesItemDecoration(8, 8, 0, 0));
        initList();
        HDColumnGoodsAdapter adapter = new HDColumnGoodsAdapter(this, allDataList);
        recyclerView.setAdapter(adapter);
        showSyncDialog();
        //模拟数据保存
        int[] strings = {6, 5, 8, 7, 5, 8};
        // Collections.reverse(Arrays.asList(strings));
        //   Log.e("数组", Arrays.toString(strings));
        PreferencesUtil.putString("layer", Arrays.toString(strings));
        Log.e("层列数保存取出", PreferencesUtil.getString("layer"));
    }

    private void initList() {
        String huodaoString = PreferencesUtil.getString("huodao0");
        if (TextUtils.isEmpty(huodaoString)) {
            allDataList = getData();
        } else {
            for (int i = 0; i < 5; i++) {
                String huodaoStrings = PreferencesUtil.getString("huodao" + i);
                allDataList.add(FastJsonUtil.getObjects(huodaoStrings, GoodsInfo.class));
            }
        }
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
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                        Log.e("初始化返回值", code + "");//初始化返回值: 99
                        if (code == 99) {
                            try {
                                int[] count = MainApp.bvmAidlInterface.BVMQueryInitResult(1);
                                //  Collections.reverse(Arrays.asList(count));
                                // Log.e("数组", Arrays.toString(count));
                                PreferencesUtil.putString("layer", Arrays.toString(count));
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        } else {
                            //错误码
                            Log.e("初始化错误码", code + "");
                        }
                        Log.e("返回码", code + "");
                    }
                });
        dialog.show();
    }

    private List<List<GoodsInfo>> getData() {
        List<List<GoodsInfo>> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(getDefaultData());
        }
        return list;
    }

    private List<GoodsInfo> getDefaultData() {
        List<GoodsInfo> goodsInfoList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
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
            list.addAll(allDataList.get(i));
            String huodaoDataString = FastJsonUtil.createJsonString(allDataList.get(i));
            PreferencesUtil.putString("huodao" + i, huodaoDataString);
        }
        String upJsonString = FastJsonUtil.createJsonString(list);
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
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
