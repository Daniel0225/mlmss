package com.app.mlm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.app.mlm.R;
import com.app.mlm.activity.order.OrderPayActivity;
import com.app.mlm.adapter.ShopCartListAdapter;
import com.app.mlm.bean.GoodsInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopCartFragment extends BaseFragment {
    @Bind(R.id.close)
    ImageView close;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.tvPrice)
    TextView tvPrice;
    @Bind(R.id.tvCount)
    TextView tvCount;
    @Bind(R.id.tvPay)
    TextView tvPay;
    private List<GoodsInfo> data = new ArrayList<>();
    private ShopCartListAdapter adapter;

    public ShopCartFragment() {
    }

    @Override
    protected int provideLayoutResId() {
        return R.layout.fragment_shop_cart;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        adapter = new ShopCartListAdapter(getActivity(), data);
        listView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.close, R.id.tvPay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close:
                mActivity.removeFragment();
                break;
            case R.id.tvPay:
                Intent intent = new Intent(getActivity(), OrderPayActivity.class);
                mActivity.startActivity(intent);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.setSearchLayoutVisible(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        mActivity.setSearchLayoutVisible(View.VISIBLE);
    }
}
