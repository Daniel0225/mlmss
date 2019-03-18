package com.app.mlm.bms.activity;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.mlm.R;
import com.app.mlm.application.MainApp;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.bms.adapter.CHColumnGoodsAdapter;
import com.app.mlm.bms.dialog.CommonDialog;
import com.app.mlm.bms.dialog.TestingDialog;
import com.app.mlm.http.bean.HuodaoBean;
import com.app.mlm.utils.FastJsonUtil;
import com.app.mlm.utils.Loading;
import com.app.mlm.utils.PreferencesUtil;
import com.app.mlm.utils.UpAlarmReportUtils;
import com.app.mlm.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

/**
 * 出货测试页
 */
public class ChuhuoTestActivity extends BaseActivity {
    public static boolean isInited = false;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    List<List<GoodsInfo>> list = new ArrayList<>();
    int code = 0;
    Loading loading;
    CHColumnGoodsAdapter adapter;
    @Override
    protected int provideLayoutResId() {
        return R.layout.activity_chuhuo_test;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        LinearLayoutManager ms = new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(ms);
        initList();
        recyclerView.addItemDecoration(new SpacesItemDecoration(12, 12, 0, 0));
        adapter = new CHColumnGoodsAdapter(this, list);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        //   showTestingDialog();
    }

    /**
     * 造数据
     */
    private void initList() {
        list.clear();
        String huodaoString = PreferencesUtil.getString("huodao");
        if (TextUtils.isEmpty(huodaoString)) {
            list.addAll(getData());
        } else {
            HuodaoBean huodaoBean = FastJsonUtil.getObject(huodaoString, HuodaoBean.class);
            list.addAll(huodaoBean.getAllDataList());
        }
    }

    private List<List<GoodsInfo>> getData() {
        List<List<GoodsInfo>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(getDefaultData());
        }
        return list;
    }

    private List<GoodsInfo> getDefaultData() {
        List<GoodsInfo> goodsInfoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.setMdseName("请补货");
            goodsInfo.setMdseUrl("empty");
            goodsInfo.setMdsePrice("0");
            goodsInfoList.add(goodsInfo);
        }
        return goodsInfoList;
    }

    private void showTestingDialog() {
        TestingDialog dialog = new TestingDialog(this);
        dialog.show();
    }

    private void showCustomDialog() {
        CommonDialog commonDialog = new CommonDialog(this, "货道初始化", "点击初始化按钮，然后等待初始化完成", "初始化", "完成")
                .setCommitClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loading = Loading.newLoading(ChuhuoTestActivity.this, "初始化中...");
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
                                                initList();
                                                adapter.notifyDataSetChanged();
                                                try {
                                                    int code = MainApp.bvmAidlInterface.BVMSetWorkMode(1, 0);
                                                    //  Toast.makeText(ChuhuoTestActivity.this, "初始化"+code+"", Toast.LENGTH_SHORT).show();
                                                } catch (RemoteException e) {
                                                    e.printStackTrace();
                                                }
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
                                            try {
                                                int code = MainApp.bvmAidlInterface.BVMSetWorkMode(1, 0);
                                                //  Toast.makeText(ChuhuoTestActivity.this, "初始化"+code+"", Toast.LENGTH_SHORT).show();
                                            } catch (RemoteException e) {
                                                e.printStackTrace();
                                            }
                                            UpAlarmReportUtils.upalarmReport(ChuhuoTestActivity.this, code);
                                            Log.e("初始化错误码", code + "");
                                        }
                                    });
                                }
                                Log.e("返回码", code + "");
                            }
                        }).start();
                    }
                })
                .setCancelClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ChuhuoTestActivity.this, "取消", Toast.LENGTH_SHORT).show();
                    }
                });
        commonDialog.show();
    }

    @Override
    public void onActionClicked() {
        showCustomDialog();
    }

}
