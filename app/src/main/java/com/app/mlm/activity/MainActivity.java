package com.app.mlm.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mlm.BackService;
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
import com.app.mlm.http.bean.CreatQrcodeBean;
import com.app.mlm.utils.FastJsonUtil;
import com.app.mlm.utils.MyDialogUtil;
import com.app.mlm.utils.PreferencesUtil;
import com.app.mlm.utils.ToastUtil;
import com.app.mlm.utils.Utils;
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
import java.util.Timer;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * @author :
 * @version : 1.0.0
 * @package : com.app.mlm.countdown
 * @fileName : CountDownManager
 * @date : 2019/1/2  13:14
 * @describe : 首页
 * @email : xing.luo@taojiji.com
 */
public class MainActivity extends BaseActivity {
    final static int COUNTS = 5;//点击次数
    final static long DURATION = 3 * 1000;//规定有效时间
    public FragmentManager manager = getSupportFragmentManager();
    @Bind(R.id.topView)
    CoustomTopView topView;
    @Bind(R.id.rlSearch)
    RelativeLayout rlSearch;
    @Bind(R.id.enter_maintain)
    Button enterMaintain;
    long[] mHits = new long[COUNTS];
    AlertDialog myDialogUtil;
    CountDownTimer timer;
    MainFragment mainFragment = new MainFragment();
    private IntentFilter intentFilter;
    private MainChangeReceiver mainChangeReceiver;
    private Timer timerC = new Timer();

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
        intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.PRICECHANGE);
        mainChangeReceiver = new MainChangeReceiver();
        registerReceiver(mainChangeReceiver, intentFilter);
        initView();
        Intent service = new Intent(this, BackService.class);
        startService(service);
        //  PreferencesUtil.putString(Constants.VMCODE, "0000051");//先存入机器码  正式的时候要去掉
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
                        } else {
                            Toast.makeText(MainActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<List<AdBean>>> response) {
                        ToastUtil.showLongToast("请求服务器失败,请稍后重试");
                    }
                });
        //处理断电数据
        if (!TextUtils.isEmpty(PreferencesUtil.getString(Constants.GETSHOPS))) {
            dealUpShipmenData();
        }
        enterMaintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
                mHits[mHits.length - 1] = SystemClock.uptimeMillis();//System.currentTimeMillis()
                if ((mHits[mHits.length - 1] - mHits[0] <= DURATION)) {
                    if (Utils.isFastClick()) {
                        maintain();
                    }
                }
            }
        });
    }

    /**
     * 维护
     */
    private void maintain() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("vmid", PreferencesUtil.getInt(Constants.VMID));
        OkGo.<BaseResponse<CreatQrcodeBean>>get(Constants.CREATEOPERATIONQRCODE)
                .params(httpParams)
                .tag(this)
                .execute(new JsonCallBack<BaseResponse<CreatQrcodeBean>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<CreatQrcodeBean>> response) {
                        if (response.body().getCode() == 0) {
                            myDialogUtil = MyDialogUtil.getDialog(MainActivity.this, initLogOutDialogView(response.body().getData().getUrl()), Gravity.CENTER);
                            myDialogUtil.show();
                        }
                    }

                    @Override
                    public void onError(Response<BaseResponse<CreatQrcodeBean>> response) {
                        ToastUtil.showLongToast("请求服务器失败,请稍后重试");
                    }
                });
    }

    public View initLogOutDialogView(String str) {
        View verifyCodeView = LayoutInflater.from(this).inflate(R.layout.dialog_qrcode, null);
        Bitmap bitmap = Utils.encodeAsBitmap(str, 600, 600);
        WebView webView = verifyCodeView.findViewById(R.id.qrcode);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl(str);
        // qrcode.setImageBitmap(bitmap);
        TextView time = verifyCodeView.findViewById(R.id.time);
        if (timer != null) {
            timer.cancel();
        }
        timer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                myDialogUtil.dismiss();
            }
        }.start();
        return verifyCodeView;
    }

    /**
     * 处理断电取货后上传数据到后台
     */
    private void dealUpShipmenData() {
        String upJson = new Gson().toJson(PreferencesUtil.getString(Constants.GETSHOPS));
        Log.e("---33", upJson);
        if (!TextUtils.isEmpty(upJson)) {
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
                            //    ToastUtil.showLongToast("请求服务器失败，请稍后重试");
                        }
                    });
        }
    }

  /*  @Override
    public long millisInFuture() {
        return 1000 * 30;
    }*/

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
        unregisterReceiver(mainChangeReceiver);
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
        transaction.replace(R.id.container, mainFragment);
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

    public void setSearchLayoutVisible(int visibility) {
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

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    //刷新UI广播
    class MainChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String state = intent.getAction();
            if (state.equals(Constants.PRICECHANGE)) {
                Log.e("收到消息", "进来了");
                mainFragment.onResume();
            }
        }
    }
}
