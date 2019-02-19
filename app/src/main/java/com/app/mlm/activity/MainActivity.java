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
import com.app.mlm.ServiceTest;
import com.app.mlm.activity.base.BaseActivity;
import com.app.mlm.dialog.SearchDialog;
import com.app.mlm.fragment.MainFragment;
import com.app.mlm.http.BaseResponse;
import com.app.mlm.http.JsonCallBack;
import com.app.mlm.http.bean.AdBean;
import com.app.mlm.utils.FastJsonUtil;
import com.app.mlm.utils.PreferencesUtil;
import com.app.mlm.widget.CoustomTopView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private ServiceTest.Mybind mybind;
    private List<AdBean> adBeanList;

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
        //  startService();
        // bindService();
        HttpParams httpParams = new HttpParams();
        httpParams.put("vmCode", PreferencesUtil.getString(Constants.VMCODE));
        OkGo.<BaseResponse<List<AdBean>>>get(Constants.AD_URL)
                .params(httpParams)
                .tag(this)
                .execute(new JsonCallBack<BaseResponse<List<AdBean>>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<List<AdBean>>> response) {

                        if (response.body().getCode() == 0) {
                            adBeanList = response.body().data;
                            PreferencesUtil.putString(Constants.ADDATA, FastJsonUtil.createJsonString(adBeanList));
                            setTopViewValue();
                        }
                    }
                });
    }

    @Override
    public long millisInFuture() {
        return 1000 * 30;
    }

    private void initView() {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, new MainFragment());
        transaction.commit();

//        topView.setData(CoustomTopView.TYPE_JPG, "http://att.bbs.duowan.com/forum/201510/15/004345bkeuibigwvupwlxj.gif");
//        topView.setData(CoustomTopView.TYPE_MP4, "http://47.106.143.212:8080/ad/fb00a9c4212d410fa9e84d16e196cd4d.MP4");
    }

    /**
     * 设置顶部图片
     */
    private void setTopViewValue() {
        for (AdBean adBean : adBeanList) {
            if (adBean.getSuffix().equals("mp4")) {
//                adBean.setUrl("http://47.106.143.212:8080/ad/fb00a9c4212d410fa9e84d16e196cd4d.MP4");
                String hasDownLoad = PreferencesUtil.getString(Constants.DOWN_LOAD);
                if (!TextUtils.isEmpty(hasDownLoad) && hasDownLoad.equals(adBean.getUrl())) {
                    playLocalFile();
                } else {
                    downLoadMedia(adBean.getUrl(), adBean.getFileType() + ".mp4");
                }
            }
        }
    }

    private void playLocalFile() {
        String filePath = getExternalCacheDir().getPath() + "/2.mp4";
        File file = new File(filePath);
        Uri uri = Uri.fromFile(file);
        topView.setData(CoustomTopView.TYPE_MP4, uri.toString());
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
                        PreferencesUtil.putString(Constants.DOWN_LOAD, url);
                        playLocalFile();
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
        transaction.setCustomAnimations(R.anim.push_bottom_in, R.anim.push_bottom_in, R.anim.push_bottom_out, R.anim.push_bottom_out);
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
