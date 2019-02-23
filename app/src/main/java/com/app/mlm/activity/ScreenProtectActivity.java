package com.app.mlm.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.app.mlm.Constants;
import com.app.mlm.R;
import com.app.mlm.http.bean.AdBean;
import com.app.mlm.utils.FastJsonUtil;
import com.app.mlm.utils.PreferencesUtil;
import com.app.mlm.widget.CoustomTopView;
import com.bumptech.glide.Glide;

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
    private List<AdBean> adBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_protect);
        ButterKnife.bind(this);
        String adString = PreferencesUtil.getString(Constants.ADDATA);
        if (!TextUtils.isEmpty(adString)) {
            adBeanList = FastJsonUtil.getObjects(adString, AdBean.class);
//            for (AdBean adBean : adBeanList) {
//                if (adBean.getFileType() == 1) {
//                    topView.setData(CoustomTopView.TYPE_JPG, adBean.getUrl());
//                } else if (adBean.getFileType() == 2) {
//                    Glide.with(this).load(adBean.getUrl()).into(codeView);
//                }
//            }
        }
        Glide.with(this).load(getResources().getDrawable(R.drawable.banner)).into(bannerView);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        finish();
        return super.dispatchTouchEvent(ev);
    }
}
