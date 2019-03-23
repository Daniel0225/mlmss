package com.app.mlm.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.RemoteException;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mlm.Constants;
import com.app.mlm.R;
import com.app.mlm.activity.order.OrderPayActivity;
import com.app.mlm.application.MainApp;
import com.app.mlm.bean.AddShopCarEvent;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.utils.MyDialogUtil;
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
    AlertDialog myDialogUtil;
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

    public View initLogOutDialogView() {
        View verifyCodeView = LayoutInflater.from(mContext).inflate(R.layout.go_on_chuhuo, null);
        TextView chuhuo = verifyCodeView.findViewById(R.id.chuhuo);
        startTime(chuhuo);
        return verifyCodeView;
    }

    @OnClick({R.id.tvBuyImm, R.id.tvAddCart, R.id.ivClose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvBuyImm:
                try {
                    String[] status = MainApp.bvmAidlInterface.BVMGetFGFault(1);
                    int statusCode = 0;
                    for (int i = 0; i < status.length; i++) {
                        if (status[i].contains("0FA")) {
                            statusCode = 1;
                            break;
                        }
                    }
                    switch (statusCode) {
                        case 0:
                            if (mGoodsInfo.getClcCapacity() <= 0) {
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
                        case 1:
                            Toast.makeText(mContext, "您有未取走的商品", Toast.LENGTH_SHORT).show();
                            int salegood = MainApp.bvmAidlInterface.BVMReSaleGoods(1);
                            Log.e("开柜门返回值", String.valueOf(salegood));
                            if (salegood == 99) {
                                Log.e("开柜门返回值进入", String.valueOf(salegood));
                                myDialogUtil = MyDialogUtil.getDialog(mContext, initLogOutDialogView(), Gravity.CENTER);
                                myDialogUtil.setCanceledOnTouchOutside(false);
                                myDialogUtil.show();
                            } else {
                                Log.e("开柜门失败返回值", String.valueOf(salegood));
                            }
                            break;
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.tvAddCart:
                try {
                    String[] status = MainApp.bvmAidlInterface.BVMGetFGFault(1);
                    int statusCode = 0;
                    for (int i = 0; i < status.length; i++) {
                        Log.e("错误码", status[i]);
                        if (status[i].contains("0FA")) {
                            statusCode = 1;
                            break;
                        }
                    }
                    switch (statusCode) {
                        case 0:
                            if (mGoodsInfo.getClcCapacity() <= 0) {
                                ToastUtil.showLongCenterToast("该商品库存不足");
                                return;
                            }
                            MainApp.addShopCar(mGoodsInfo);
                            EventBus.getDefault().post(new AddShopCarEvent());
                            dismiss();
                            break;
                        case 1:
                            Toast.makeText(mContext, "您有未取走的商品", Toast.LENGTH_SHORT).show();
                            int salegood = MainApp.bvmAidlInterface.BVMReSaleGoods(1);
                            Log.e("开柜门返回值", String.valueOf(salegood));
                            if (salegood == 99) {
                                Log.e("开柜门返回值进入", String.valueOf(salegood));
                                myDialogUtil = MyDialogUtil.getDialog(mContext, initLogOutDialogView(), Gravity.CENTER);
                                myDialogUtil.setCanceledOnTouchOutside(false);
                                myDialogUtil.show();
                            } else {
                                Log.e("开柜门失败返回值", String.valueOf(salegood));
                            }
                            break;
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.ivClose:
                dismiss();
                break;
        }

    }

    /**
     * 开启倒计时
     */
    public void startTime(TextView chuhuo) {
        /** 倒计时60秒，一次1秒 */
        CountDownTimer timer = new CountDownTimer(25 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                chuhuo.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                myDialogUtil.dismiss();
                try {
                    int noMachineStatus = MainApp.bvmAidlInterface.BVMGetRunningState(1);
                    Log.e("code1", "机器状态" + noMachineStatus);
                    if (noMachineStatus == 2) {
                        int code = MainApp.bvmAidlInterface.BVMCleanSysFault(1);
                        Log.e("code", String.valueOf(code));
                    } else {
                        Log.e("code", "状态" + noMachineStatus);
                    }


                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.start();
    }
}
