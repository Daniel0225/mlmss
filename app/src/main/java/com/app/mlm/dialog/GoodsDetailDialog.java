package com.app.mlm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mlm.Constants;
import com.app.mlm.R;
import com.app.mlm.activity.order.OrderPayActivity;
import com.app.mlm.application.MainApp;
import com.app.mlm.bean.AddShopCarEvent;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.utils.ToastUtil;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.dialog
 * @fileName : GoodsDetailDialog
 * @date : 2019/1/4  10:11
 * @describe : TODO
 * @email : xing.luo@taojiji.com
 */
public class GoodsDetailDialog extends Dialog {
    protected Context mContext;
    protected View mRoot;
    @Bind(R.id.ivGoodsImg)
    ImageView ivGoodsImg;
    @Bind(R.id.tvGoodsPrice)
    TextView tvGoodsPrice;
    @Bind(R.id.tvGoodsName)
    TextView tvGoodsName;
    @Bind(R.id.ivClose)
    View closeView;
    private Window mDialogWindow;
    private GoodsDetailDialog mInstance;
    private GoodsInfo mGoodsInfo;

    public GoodsDetailDialog(Context context, GoodsInfo goodsInfo) {
        super(context, R.style.NoBGDialog);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.mContext = context;
        this.mGoodsInfo = goodsInfo;
        mDialogWindow = this.getWindow();
        mDialogWindow.setGravity(Gravity.CENTER);
        mDialogWindow.setBackgroundDrawableResource(R.color.transparent);
//        mDialogWindow.setWindowAnimations(R.style.BottomAnimation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRoot = LayoutInflater.from(mContext).inflate(R.layout.dialog_goods_detail, null);
        this.setContentView(mRoot);
        ButterKnife.bind(this, mRoot);
        mInstance = this;
        WindowManager.LayoutParams lp = mDialogWindow.getAttributes();
        WindowManager windowManager = mDialogWindow.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        lp.width = display.getWidth() * 2 / 3;
        mDialogWindow.setAttributes(lp);
        initView();
    }

    private void initView() {
        Glide.with(getContext()).load(mGoodsInfo.getMdseUrl()).into(ivGoodsImg);
        tvGoodsName.setText(mGoodsInfo.getMdseName());
        tvGoodsPrice.setText("¥" + mGoodsInfo.getRealPrice());
    }

    @OnClick({R.id.tvBuyImm, R.id.tvAddCart, R.id.ivClose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvBuyImm:
                if(mGoodsInfo.getClcCapacity() <= 0){
                    ToastUtil.showLongCenterToast("该商品库存不足");
                    return;
                }
                Intent intent = new Intent(getContext(), OrderPayActivity.class);
                ArrayList<GoodsInfo> list = new ArrayList<>();
                list.add(mGoodsInfo);
                intent.putExtra(Constants.TOTAL_NUM, 1);
                intent.putExtra(Constants.TOTAL_PRICE, String.valueOf(mGoodsInfo.getRealPrice()));
                intent.putExtra("goods", list);
                getContext().startActivity(intent);
                dismiss();
                break;
            case R.id.tvAddCart:
                if(mGoodsInfo.getClcCapacity() <= 0){
                    ToastUtil.showLongCenterToast("该商品库存不足");
                    return;
                }
                MainApp.addShopCar(mGoodsInfo);
                EventBus.getDefault().post(new AddShopCarEvent());
                dismiss();
                break;
            case R.id.ivClose:
                dismiss();
                break;
        }
    }
}
