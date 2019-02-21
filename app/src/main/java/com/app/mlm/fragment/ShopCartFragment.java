package com.app.mlm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.app.mlm.R;
import com.app.mlm.activity.order.OrderPayActivity;
import com.app.mlm.adapter.ShopCartListAdapter;
import com.app.mlm.application.MainApp;
import com.app.mlm.bean.AddShopCarEvent;
import com.app.mlm.bean.GoodsInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopCartFragment extends BaseFragment implements ShopCartListAdapter.ShopCarHandleListener {
    @Bind(R.id.close)
    LinearLayout close;
    @Bind(R.id.listView)
    ListView listView;
    @Bind(R.id.tvPrice)
    TextView tvPrice;
    @Bind(R.id.tvCount)
    TextView tvCount;
    @Bind(R.id.tvPay)
    TextView tvPay;
    @Bind(R.id.empty_contain)
    View emptyContain;
    @Bind(R.id.no_empty_contain)
    View noEmptyContain;

    private List<GoodsInfo> data = new ArrayList<>();
    private ShopCartListAdapter adapter;
    private double totalPrice = 0;
    private int totalNum = 0;
    public ShopCartFragment() {
    }

    @Override
    protected int provideLayoutResId() {
        return R.layout.fragment_shop_cart;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        data = MainApp.shopCarList;
        adapter = new ShopCartListAdapter(getActivity(), data, this);
        listView.setAdapter(adapter);

        if (data.size() == 0) {
            emptyContain.setVisibility(View.VISIBLE);
            noEmptyContain.setVisibility(View.GONE);
        } else {
            emptyContain.setVisibility(View.GONE);
            noEmptyContain.setVisibility(View.VISIBLE);
            refreshShopCarInfo();
        }
    }

    private void refreshShopCarInfo() {
        totalNum = 0;
        totalPrice = 0;
        for (GoodsInfo goodsInfo : data) {
            totalNum += goodsInfo.getShopCarNum();
            totalPrice += goodsInfo.getShopCarNum() * Double.valueOf(goodsInfo.getMdsePrice());
        }

        tvPrice.setText("¥ " + totalPrice);
        tvCount.setText(totalNum + "件商品");
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

    @OnClick({R.id.close, R.id.tvPay, R.id.to_pick})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close:
                mActivity.removeFragment();
                break;
            case R.id.tvPay:
                Intent intent = new Intent(getActivity(), OrderPayActivity.class);
                intent.putExtra("price", totalPrice);
                intent.putExtra("num", totalNum);
                mActivity.startActivity(intent);
                break;
            case R.id.to_pick:
                mActivity.removeFragment();
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

    @Override
    public void addOne(int position) {
        int originNum = data.get(position).getShopCarNum();
        data.get(position).setShopCarNum(originNum + 1);
        adapter.notifyDataSetChanged();
        refreshShopCarInfo();
        MainApp.shopCarList = data;
        EventBus.getDefault().post(new AddShopCarEvent());
    }

    @Override
    public void reduceOne(int position) {
        int originNum = data.get(position).getShopCarNum();
        data.get(position).setShopCarNum(originNum - 1);
        adapter.notifyDataSetChanged();
        refreshShopCarInfo();
        MainApp.shopCarList = data;
        EventBus.getDefault().post(new AddShopCarEvent());
    }

    @Override
    public void deleteOne(int position) {
        data.remove(position);
        adapter.notifyDataSetChanged();
        refreshShopCarInfo();
        MainApp.shopCarList = data;
        if (data.size() == 0) {
            mActivity.removeFragment();
        }
        EventBus.getDefault().post(new AddShopCarEvent());
    }
}
