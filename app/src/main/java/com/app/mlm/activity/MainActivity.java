package com.app.mlm.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.app.mlm.Constants;
import com.app.mlm.R;
import com.app.mlm.activity.base.BaseActivity;
import com.app.mlm.bean.AddInfoEvent;
import com.app.mlm.dialog.SearchDialog;
import com.app.mlm.fragment.MainFragment;
import com.app.mlm.http.BaseResponse;
import com.app.mlm.http.JsonCallBack;
import com.app.mlm.http.bean.AdBean;
import com.app.mlm.http.bean.AllDataBean;
import com.app.mlm.utils.FastJsonUtil;
import com.app.mlm.utils.PreferencesUtil;
import com.app.mlm.utils.ToastUtil;
import com.app.mlm.widget.CoustomTopView;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.countdown
 * @fileName : CountDownManager
 * @date : 2019/1/2  13:14
 * @describe : 首页
 * @email : xing.luo@taojiji.com
 */
public class MainActivity extends BaseActivity {
    public FragmentManager manager = getSupportFragmentManager();
    @Bind(R.id.topView)
    CoustomTopView topView;
    @Bind(R.id.rlSearch)
    RelativeLayout rlSearch;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
        //  startService();
        // bindService();
        PreferencesUtil.putString(Constants.VMCODE, "0000051");//先存入机器码  正式的时候要去掉
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
                            setTopViewValue(adBeanList);
                        }
                    }
                });
        //处理断电数据
        if (!TextUtils.isEmpty(PreferencesUtil.getString(Constants.GETSHOPS))) {
            dealUpShipmenData();
        }
    }

    /**
     * 处理断电取货后上传数据到后台
     */
    private void dealUpShipmenData() {
        String upJson = new Gson().toJson(PreferencesUtil.getString(Constants.GETSHOPS));
        OkGo.<BaseResponse<AllDataBean>>post(Constants.VENDREPORT)
                .tag(this)
                .upJson(upJson)
                .execute(new JsonCallBack<BaseResponse<AllDataBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<AllDataBean>> response) {
                        if (response.body().getCode() == 0) {
                            //如果数据上传完成则清空
                            PreferencesUtil.putString(Constants.GETSHOPS, "");
                        } else {
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<AllDataBean>> response) {
                        ToastUtil.showLongToast(response.body().getMsg());
                    }
                });
    }

    @Override
    public long millisInFuture() {
        return 1000 * 30;
    }

    @Override
    protected void onPause() {
        super.onPause();
        topView.playerPause();
        Log.e("Tag", "pause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("Tag", "onResume");
        topView.playerRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMainThread(AddInfoEvent addInfoEvent) {
        String adString = PreferencesUtil.getString(Constants.ADDATA);
        List<AdBean> adBeans = new ArrayList<>();
        if (!TextUtils.isEmpty(adString)) {
            adBeans = FastJsonUtil.getObjects(adString, AdBean.class);
        }
        topView.playerStop();
        setTopViewValue(adBeans);
    }

    private void initView() {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, new MainFragment());
        transaction.commit();
    }

    /**
     * 设置顶部图片
     */
    private void setTopViewValue(List<AdBean> adBeanList) {
        for (AdBean adBean : adBeanList) {
            if (adBean.getFileType() == 3) {
                if (adBean.getSuffix().toUpperCase().equals("MP4")) {
                    if (playLocalFile(adBean.getFileName())) {

                    } else {
                        downLoadMedia(adBean.getUrl(), adBean.getFileName());
                    }
                } else {
                    topView.setData(CoustomTopView.TYPE_JPG, adBean.getUrl());
                }
            }
        }
    }

    private boolean playLocalFile(String fileName) {
        String filePath = getExternalCacheDir().getPath() + "/" + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            Uri uri = Uri.fromFile(file);
            topView.setData(CoustomTopView.TYPE_MP4, uri.toString());
            return true;
        } else {
            return false;
        }

    }

    /**
     * 下载视频
     *
     * @param
     */
    private void downLoadMedia(String url, String fileName) {
        String cachePath = getExternalCacheDir().getPath();
        OkGo.<File>get(url)
                .tag(this)

                .execute(new FileCallback(cachePath, fileName) {
                    @Override
                    public void onSuccess(Response<File> response) {
                        PreferencesUtil.putString(Constants.DOWN_LOAD, fileName);
                        playLocalFile(fileName);
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        Log.e("Tag", progress.fraction + "");
                    }
                });

    }

    @OnClick({R.id.rlSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlSearch:
                SearchDialog dialog = new SearchDialog(this);
                dialog.show();
                break;
        }
    }

    public void addFragment(Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.setCustomAnimations(R.anim.push_bottom_in, R.anim.push_bottom_in, R.anim.push_bottom_out, R.anim.push_bottom_out);
        transaction.add(R.id.container, fragment);
        transaction.addToBackStack(fragment.getClass().getSimpleName());
        transaction.commit();
    }

    public void removeFragment() {
        manager.popBackStack();
    }

    public void setSearchLayoutVisible(int visibility){
        this.rlSearch.setVisibility(visibility);
    }

    @Override
    public void onTick(long mills) {
        super.onTick(mills);
    }

    @Override
    public void onFinish() {
        startActivity(new Intent(this, ScreenProtectActivity.class));
    }


}
