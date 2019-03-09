package com.app.mlm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allen.library.RxHttpUtils;
import com.allen.library.base.BaseObserver;
import com.app.mlm.R;
import com.app.mlm.adapter.ColumnGoodsAdapter;
import com.app.mlm.application.MainApp;
import com.app.mlm.bean.AddShopCarEvent;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.bms.activity.BackgroundManangerSystemActivity;
import com.app.mlm.bms.dialog.CommonDialog;
import com.app.mlm.bms.dialog.ShopCarDialog;
import com.app.mlm.http.ApiService;
import com.app.mlm.http.bean.BaseBean;
import com.app.mlm.http.bean.HuodaoBean;
import com.app.mlm.utils.FastJsonUtil;
import com.app.mlm.utils.PreferencesUtil;
import com.app.mlm.widget.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.quhuo)
    LinearLayout quhuo;
    @Bind(R.id.llCart)
    LinearLayout llCart;
    @Bind(R.id.tvCartPrice)
    TextView tvCartPrice;
    @Bind(R.id.tvCartCount)
    TextView tvCartCount;
    @Bind(R.id.gouwuche)
    RelativeLayout gouwuche;
    @Bind(R.id.huodong)
    LinearLayout huodong;
    @Bind(R.id.tv_shop_cart)
    View shopCarTV;

    List<List<GoodsInfo>> dataList = new ArrayList<>();
    ColumnGoodsAdapter adapter;
    ShopCarDialog shopCarDialog;
    public MainFragment() {
    }

    @Override
    protected int provideLayoutResId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        LinearLayoutManager ms = new LinearLayoutManager(getActivity());
        ms.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(ms);
        recyclerView.addItemDecoration(new SpacesItemDecoration(8, 8, 0, 0));
        initList();
        adapter = new ColumnGoodsAdapter(getActivity(), dataList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {

    }

    /**
     * 造数据
     */
    private void initList() {
        dataList.clear();
        String huodaoString = PreferencesUtil.getString("huodao");
        if (TextUtils.isEmpty(huodaoString)) {
            dataList.addAll(getData());
        } else {
            HuodaoBean huodaoBean = FastJsonUtil.getObject(huodaoString, HuodaoBean.class);
            dataList.addAll(huodaoBean.getAllDataList());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void initData() {
        RxHttpUtils.createApi(ApiService.class)
                .getHomeGoodsList("0000003")
                .subscribeOn(Schedulers.io())
                .subscribe(new BaseObserver<BaseBean>(getActivity()) {
                    @Override
                    public void doOnSubscribe(Disposable d) {

                    }

                    @Override
                    public void doOnError(String errorMsg) {

                    }

                    @Override
                    public void doOnNext(BaseBean baseBean) {
                        List<GoodsInfo> data = (List<GoodsInfo>) baseBean.getData();
                    }

                    @Override
                    public void doOnCompleted() {

                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        recyclerView.scrollToPosition(0);
        for(int i = 0; i < recyclerView.getChildCount();i ++){
            if(recyclerView.getChildAt(i) instanceof LinearLayout){
                if(((LinearLayout)recyclerView.getChildAt(i)).getChildAt(1) instanceof RecyclerView){
                    ((RecyclerView)((LinearLayout)recyclerView.getChildAt(i)).getChildAt(1)).scrollToPosition(0);
                }
            }
        }
    }

    @OnClick({R.id.quhuo, R.id.gouwuche, R.id.huodong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.quhuo:
                mActivity.addFragment(new QuhuoFragment());
                break;
            case R.id.gouwuche:
                if (shopCarDialog == null) {
                    shopCarDialog = new ShopCarDialog(getContext());
                }
                shopCarDialog.show();
//                mActivity.addFragment(new ShopCartFragment());
                break;
            case R.id.huodong:
//                Toast.makeText(getActivity(), "敬请期待", Toast.LENGTH_SHORT).show();
                CommonDialog commonDialog = new CommonDialog(getActivity(), "系统维护", "请确认是否维护售货机", "确定", "取消");
                commonDialog.setCommitClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), BackgroundManangerSystemActivity.class));
                    }
                });
                commonDialog.show();
                break;
        }
    }

    private List<List<GoodsInfo>> getData() {
        List<List<GoodsInfo>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(getDefaultData());
        }
        return list;
    }

    private List<GoodsInfo> getDefaultData() {
        List<GoodsInfo> goodsInfoList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.setMdseName("请补货");
            goodsInfo.setMdseUrl("empty");
            goodsInfo.setMdsePrice("0");
            goodsInfoList.add(goodsInfo);
        }
        return goodsInfoList;
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMainThread(AddShopCarEvent addShopCarEvent) {
        refreshShopCar();
    }

    /**
     * 刷新购物车TAB UI
     */
    public void refreshShopCar() {
        int totalNum = 0;
        double totalPrice = 0;
        for (GoodsInfo goodsInfo : MainApp.shopCarList) {
            totalNum += goodsInfo.getShopCarNum();
            totalPrice += goodsInfo.getShopCarNum() * Double.valueOf(goodsInfo.getMdsePrice());
        }

        if (totalNum > 0) {
            shopCarTV.setVisibility(View.GONE);
            tvCartCount.setVisibility(View.VISIBLE);
            tvCartPrice.setVisibility(View.VISIBLE);
            tvCartCount.setText(String.valueOf(totalNum));
            tvCartPrice.setText("¥ " + totalPrice);
        } else {
            tvCartCount.setVisibility(View.GONE);
            tvCartPrice.setVisibility(View.GONE);
            shopCarTV.setVisibility(View.VISIBLE);
        }
    }
}
