package com.app.mlm.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mlm.R;
import com.app.mlm.bms.adapter.ChuhuoAdapter;
import com.app.mlm.widget.SpacesItemDecoration;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChuhuoFragment extends ChuhuoBaseFragment {
    @Bind(R.id.tvCountDown)
    TextView tvCountDownView;

    @Bind(R.id.progress_circle)
    ImageView progressCircle;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.single_goods_view)
    View singleGoodsView;

    @Bind(R.id.multi_goods_view)
    View multiGoodsView;

    private ChuhuoAdapter chuhuoAdapter;

    public ChuhuoFragment() {
    }

    @Override
    protected int provideLayoutResId() {
        return R.layout.fragment_chuhuo;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initCountDownMananger();

        //如果是单个商品 那么现实单个的 并开始动画
        boolean isSingle = false;
        if(isSingle){
            singleGoodsView.setVisibility(View.VISIBLE);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(progressCircle,"rotation",0f,360f);
            objectAnimator.setDuration(4000)
                    .setRepeatCount(ValueAnimator.INFINITE);
            objectAnimator.start();
        }else{//多个 那么初始化recyclerview
            multiGoodsView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
            recyclerView.addItemDecoration(new SpacesItemDecoration(20, 20, 20, 20));
            chuhuoAdapter = new ChuhuoAdapter(getContext(),null);
            recyclerView.setAdapter(chuhuoAdapter);
        }

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected boolean isNeedCountDown() {
        return true;
    }

    @Override
    public void onTick(int seconds) {
        tvCountDownView.setText(seconds + "S");
        if (seconds == 55) {
//            mActivity.addFragment(new ChuhuoFailedFragment());
//            mActivity.addFragment(new ChuhuoSuccessFragment());
        }
        if(seconds % 10 == 0){
            chuhuoAdapter.refreshChuhuoStatus(6 - seconds / 10);
        }
    }

    @Override
    public void onFinish() {

    }
}
