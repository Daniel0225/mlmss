package com.app.mlm.bms.dialog;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.app.mlm.Constants;
import com.app.mlm.R;
import com.app.mlm.activity.order.OrderPayActivity;
import com.app.mlm.adapter.ShopCartListAdapter;
import com.app.mlm.application.MainApp;
import com.app.mlm.bean.AddShopCarEvent;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.dialog.BaseDialog;
import com.app.mlm.utils.ToastUtil;

import org.litepal.util.Const;

import java.math.BigDecimal;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.bms.dialog
 * @fileName : ChooseGoodsDialog
 * @date : 2019/1/19  18:23
 * @describe : 购物车
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public class ShopCarDialog extends BaseDialog implements ShopCartListAdapter.ShopCarHandleListener {
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

    private ShopCartListAdapter adapter;
    private double totalPrice = 0;
    private int totalNum = 0;
    private double originPrice = 0;


    public ShopCarDialog(Context context) {
        super(context, R.layout.fragment_shop_cart, true, Gravity.CENTER);
    }

    @Override
    public void initView() {
        adapter = new ShopCartListAdapter(getContext(), MainApp.shopCarList, this);
        listView.setAdapter(adapter);

        if (MainApp.shopCarList.size() == 0) {
            emptyContain.setVisibility(View.VISIBLE);
            noEmptyContain.setVisibility(View.GONE);
        } else {
            emptyContain.setVisibility(View.GONE);
            noEmptyContain.setVisibility(View.VISIBLE);
            refreshShopCarInfo();
        }
    }

    public void showShopCarDialog() {
        adapter.notifyDataSetChanged();
        refreshShopCarInfo();
        show();
    }

    private void refreshShopCarInfo() {
        totalNum = 0;
        totalPrice = 0;
        for (GoodsInfo goodsInfo : MainApp.shopCarList) {
            totalNum += goodsInfo.getShopCarNum();
            originPrice += new BigDecimal(goodsInfo.getMdsePrice()).multiply(new BigDecimal(goodsInfo.getShopCarNum())).doubleValue();
            totalPrice = new BigDecimal(totalPrice).add(new BigDecimal(goodsInfo.getRealPrice()).multiply(new BigDecimal(goodsInfo.getShopCarNum())))
                    .setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }

        tvPrice.setText("¥ " + totalPrice);
        tvCount.setText(totalNum + "件商品");
    }

    @Override
    protected void onStop() {
        super.onStop();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.close, R.id.tvPay, R.id.to_pick, R.id.root_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.close:
                break;
            case R.id.tvPay:
                dismiss();
                Intent intent = new Intent(getContext(), OrderPayActivity.class);
                intent.putExtra(Constants.TOTAL_NUM, totalNum);
                intent.putExtra(Constants.TOTAL_PRICE, String.valueOf(totalPrice));
                intent.putExtra(Constants.ORIGIN_PRICE,String.valueOf(originPrice));
                getContext().startActivity(intent);
                break;
            case R.id.to_pick:
                dismiss();
                break;
            case R.id.root_view:
                dismiss();
                break;
        }
    }


    @Override
    public void addOne(int position) {
        int originNum = MainApp.shopCarList.get(position).getShopCarNum();
        if(originNum >=  MainApp.shopCarList.get(position).getClcCapacity()){
            ToastUtil.showLongCenterToast(String.format("该商品当前库存仅有%d个",originNum));
            return;
        }
        MainApp.shopCarList.get(position).setShopCarNum(originNum + 1);
        adapter.notifyDataSetChanged();
        refreshShopCarInfo();
        EventBus.getDefault().post(new AddShopCarEvent());
    }

    @Override
    public void reduceOne(int position) {
        int originNum = MainApp.shopCarList.get(position).getShopCarNum();
        MainApp.shopCarList.get(position).setShopCarNum(originNum - 1);
        adapter.notifyDataSetChanged();
        refreshShopCarInfo();
        EventBus.getDefault().post(new AddShopCarEvent());
    }

    @Override
    public void deleteOne(int position) {
        MainApp.shopCarList.get(position).setShopCarNum(1);
        MainApp.shopCarList.remove(position);
        adapter.notifyDataSetChanged();
        refreshShopCarInfo();
        if (MainApp.shopCarList.size() == 0) {
            dismiss();
        }
        EventBus.getDefault().post(new AddShopCarEvent());
    }
}
