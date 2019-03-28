package com.app.mlm.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mlm.R;
import com.app.mlm.adapter.ColumnGoodsAdapter;
import com.app.mlm.application.MainApp;
import com.app.mlm.bean.AddShopCarEvent;
import com.app.mlm.bean.ChuhuoSuccessBean;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.bms.dialog.ShopCarDialog;
import com.app.mlm.http.bean.HuodaoBean;
import com.app.mlm.utils.FastJsonUtil;
import com.app.mlm.utils.PreferencesUtil;
import com.app.mlm.widget.SpacesItemDecoration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

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
        Log.e("Tag","init shujv");
        String huodaoString = PreferencesUtil.getString("huodao");
        if (TextUtils.isEmpty(huodaoString)) {
            dataList.addAll(getData());
        } else {
            HuodaoBean huodaoBean = FastJsonUtil.getObject(huodaoString, HuodaoBean.class);
            dataList.addAll(huodaoBean.getAllDataList());
            //    Log.e("Tag","kucunC " + dataList.get(0).get(0).getClcCapacity());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
        adapter.notifyDataSetChanged();
        refreshShopCar();
    }

    @Override
    protected void initData() {

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
        if (shopCarDialog != null && shopCarDialog.isShowing()) {
            shopCarDialog.dismiss();
        }
    }

    @OnClick({R.id.quhuo, R.id.gouwuche, R.id.huodong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.quhuo:
                mActivity.addFragment(new QuhuoFragment());
                break;
            case R.id.gouwuche:
                shopCarDialog = null;
                shopCarDialog = new ShopCarDialog(getContext());
                shopCarDialog.show();
//                mActivity.addFragment(new ShopCartFragment());
                break;
            case R.id.huodong:
                Toast.makeText(getActivity(), "敬请期待", Toast.LENGTH_SHORT).show();
//                CommonDialog commonDialog = new CommonDialog(getActivity(), "系统维护", "请确认是否维护售货机", "确定", "取消");
//                commonDialog.setCommitClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        startActivity(new Intent(getActivity(), BackgroundManangerSystemActivity.class));
//                    }
//                });
//                commonDialog.show();
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
            goodsInfo.setMdsePrice(0);
            goodsInfoList.add(goodsInfo);
        }
        return goodsInfoList;
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void onMainThread(Object object) {
        if(object instanceof AddShopCarEvent){
            refreshShopCar();
        }else if(object instanceof ChuhuoSuccessBean){
            ChuhuoSuccessBean chuhuoSuccessBean = (ChuhuoSuccessBean)object;
            Log.e("Tag","kucunA " + dataList.get(0).get(0).getClcCapacity());
            for (List<GoodsInfo> goodsInfoList : dataList){
                for (GoodsInfo goodsInfo : goodsInfoList){
                    if(!TextUtils.isEmpty(goodsInfo.getClCode()) && goodsInfo.getClCode().equals(chuhuoSuccessBean.getHdCode())){
                        goodsInfo.setClcCapacity(goodsInfo.getClcCapacity() - 1);
                        break;
                    }
                }
            }
            Log.e("Tag","kucunB " + dataList.get(0).get(0).getClcCapacity());
            HuodaoBean huodaoBean = new HuodaoBean(dataList);
            PreferencesUtil.putString("huodao", FastJsonUtil.createJsonString(huodaoBean));
        }

    }

    /**
     * 刷新购物车TAB UI
     */
    public void refreshShopCar() {
        int totalNum = 0;
        double totalPrice = 0;
        for (GoodsInfo goodsInfo : MainApp.shopCarList) {
            totalNum += goodsInfo.getShopCarNum();
            totalPrice = new BigDecimal(totalPrice).add(new BigDecimal(goodsInfo.getRealPrice()).multiply(new BigDecimal(goodsInfo.getShopCarNum())))
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
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
