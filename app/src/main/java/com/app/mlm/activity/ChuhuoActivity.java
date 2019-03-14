package com.app.mlm.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.app.mlm.R;
import com.app.mlm.activity.base.BaseActivity;
import com.app.mlm.fragment.ChuhuoFragment;

import butterknife.ButterKnife;

/**

 */
public class ChuhuoActivity extends BaseActivity {
    public FragmentManager manager = getSupportFragmentManager();
    String json = "";//出货的json

    public static void start(Context context) {
        Intent intent = new Intent(context, ChuhuoActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuhuo);
        ButterKnife.bind(this);
        initdata();
        initView();
    }

    private void initdata() {
        json = getIntent().getStringExtra("shipment");
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
        transaction.replace(R.id.container, new ChuhuoFragment(json));
        //transaction.replace(R.id.container, new ChuhuoFailedFragment());
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
