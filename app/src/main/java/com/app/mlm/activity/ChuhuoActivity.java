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
import com.app.mlm.bean.AddShopCarEvent;
import com.app.mlm.dialog.SearchDialog;
import com.app.mlm.fragment.ChuhuoFragment;
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
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**

 */
public class ChuhuoActivity extends BaseActivity {
    public FragmentManager manager = getSupportFragmentManager();

    public static void start(Context context) {
        Intent intent = new Intent(context, ChuhuoActivity.class);
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
        return 1000 * 30;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    private void initView() {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, new ChuhuoFragment());
        transaction.commit();
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

    @Override
    public void onTick(long mills) {
        super.onTick(mills);
    }

    @Override
    public void onFinish() {
//        startActivity(new Intent(this, ScreenProtectActivity.class));
    }


}
