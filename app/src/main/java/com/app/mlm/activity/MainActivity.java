package com.app.mlm.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.app.mlm.R;
import com.app.mlm.activity.base.BaseActivity;
import com.app.mlm.dialog.SearchDialog;
import com.app.mlm.fragment.MainFragment;
import com.app.mlm.widget.CoustomTopView;

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
    @Bind(R.id.topView)
    CoustomTopView topView;
    public FragmentManager manager = getSupportFragmentManager();
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
        initView();
    }

    @Override
    public long millisInFuture() {
        return 1000 * 10;
    }

    private void initView() {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, new MainFragment());
        transaction.commit();

        topView.setData(CoustomTopView.TYPE_JPG, "http://att.bbs.duowan.com/forum/201510/15/004345bkeuibigwvupwlxj.gif");
//        topView.setData(CoustomTopView.TYPE_MP4, "http://120.25.246.21/vrMobile/travelVideo/zhejiang_xuanchuanpian.mp4");
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
        transaction.setCustomAnimations(R.anim.window_in, R.anim.window_in_b, R.anim.window_out, R.anim.window_out_b);
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
