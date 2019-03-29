package com.app.mlm.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.app.mlm.Constants;
import com.app.mlm.R;
import com.app.mlm.application.MainApp;
import com.app.mlm.http.bean.AdBean;
import com.app.mlm.utils.FastJsonUtil;
import com.app.mlm.utils.PreferencesUtil;
import com.app.mlm.widget.CoustomTopView;
import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.countdown
 * @fileName : CountDownManager
 * @date : 2019/1/2  13:14
 * @describe : 屏保页
 * @email : xing.luo@taojiji.com
 */
public class ScreenProtectActivity extends AppCompatActivity {
    @Bind(R.id.topView)
    CoustomTopView topView;
    @Bind(R.id.ivCode)
    ImageView codeView;
    @Bind(R.id.banner)
    ImageView bannerView;
    @Bind(R.id.ad_type2)
    ImageView adType2;
    private List<AdBean> adBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_protect);
        ButterKnife.bind(this);
        String adString = PreferencesUtil.getString(Constants.ADDATA);
        if (!TextUtils.isEmpty(adString)) {
            adBeanList = FastJsonUtil.getObjects(adString, AdBean.class);
            for (AdBean adBean : adBeanList) {
                if (adBean.getFileType() == 1) {
                    if (adBean.getSuffix().toUpperCase().equals("MP4")) {
                        if (playLocalFile(adBean.getFileName())) {

                        } else {
                            downLoadMedia(adBean.getUrl(), adBean.getFileName());
                        }
                    } else {
                        topView.setData(CoustomTopView.TYPE_JPG, adBean.getUrl());
                    }
                } else if (adBean.getFileType() == 2) {
                    Glide.with(this).load(adBean.getUrl()).into(adType2);
                }
            }
        }
        Glide.with(this).load(getResources().getDrawable(R.drawable.banner)).into(bannerView);
        MainApp.shopCarList.clear();
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        finish();
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        topView.playerStop();
    }
}
