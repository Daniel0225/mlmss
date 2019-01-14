package com.app.mlm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mlm.R;
import com.app.mlm.bean.GoodsInfo;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    @Bind(R.id.ivGoodsImg)
    ImageView ivGoodsImg;
    @Bind(R.id.tvGoodsPrice)
    TextView tvGoodsPrice;
    @Bind(R.id.tvGoodsName)
    TextView tvGoodsName;
    private Window mDialogWindow;
    protected Context mContext;
    protected View mRoot;
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
        mDialogWindow.setWindowAnimations(R.style.BottomAnimation);
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

    }

    @OnClick({R.id.tvBuyImm, R.id.tvAddCart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvBuyImm:
                dismiss();
                break;
            case R.id.tvAddCart:
                dismiss();
                break;
        }
    }
}
