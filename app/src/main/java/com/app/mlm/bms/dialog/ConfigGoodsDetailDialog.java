package com.app.mlm.bms.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mlm.R;
import com.app.mlm.application.MainApp;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.dialog.BaseDialog;
import com.app.mlm.http.bean.ProductInfo;
import com.app.mlm.utils.ToastUtil;
import com.bumptech.glide.Glide;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.bms.dialog
 * @fileName : ConfigGoodsDetailDialog
 * @date : 2019/1/19  15:45
 * @describe : 货道配置 -- 配置商品信息
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public class ConfigGoodsDetailDialog extends BaseDialog {
    @Bind(R.id.cancel)
    TextView cancel;
    @Bind(R.id.commit)
    TextView commit;
    @Bind(R.id.tvKucun)
    TextView tvKucun;
    @Bind(R.id.tvGoodsName)
    TextView tvGoodsName;
    @Bind(R.id.ivGoodsImg)
    ImageView ivGoodsImg;
    @Bind(R.id.tvChangeGoods)
    TextView tvChangeGoods;
    @Bind(R.id.etLong)
    EditText etLong;
    @Bind(R.id.etWidth)
    EditText etWidth;
    @Bind(R.id.etHight)
    EditText etHight;
    @Bind(R.id.etPrice)
    TextView etPrice;
    @Bind(R.id.etCapcity)
    EditText etCapcity;
    @Bind(R.id.etAddCount)
    EditText etAddCount;
    @Bind(R.id.etLessCount)
    EditText etLessCount;
    @Bind(R.id.etSerialNo)
    EditText etSerialNo;
    @Bind(R.id.tvClear)
    TextView tvClear;
    @Bind(R.id.tvFillAll)
    TextView tvFillAll;
    private ProductInfo mProductInfo;
    private ProductConfigListener productConfigListener;
    private GoodsInfo goodsInfo;

    public ConfigGoodsDetailDialog(Context context, GoodsInfo goodsInfo) {
        super(context, R.layout.dialog_config_goods_detail, true, Gravity.CENTER, 900, 1280);
        this.goodsInfo = goodsInfo;
    }

    public void showMyDialog(ProductConfigListener listener) {
        this.productConfigListener = listener;
        show();
    }

    @Override
    public void initView() {
        tvGoodsName.setText(goodsInfo.getMdseName());
        tvKucun.setText("库存：" + goodsInfo.getClcCapacity());
        if (goodsInfo.getMdseUrl().equals("empty")) {
            ivGoodsImg.setImageResource(R.drawable.empty);
        } else {
            Glide.with(MainApp.getAppInstance()).load(goodsInfo.getMdseUrl()).into(ivGoodsImg);
        }
        etPrice.setText(String.valueOf(goodsInfo.getMdsePrice()));
        etCapcity.setText(String.valueOf(goodsInfo.getClcCapacity()));

    }

    @OnClick({R.id.cancel, R.id.commit, R.id.tvChangeGoods, R.id.tvClear, R.id.tvFillAll, R.id.ivGoodsImg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.commit:
                if (mProductInfo == null) {
                    ToastUtil.showLongCenterToast("请选择商品");
                    return;
                }
                if (TextUtils.isEmpty(etHight.getText().toString()) || TextUtils.isEmpty(etWidth.getText().toString()) ||
                        TextUtils.isEmpty(etLong.getText().toString())) {
                    ToastUtil.showLongCenterToast("请输入长宽高");
                    return;
                }
                if (TextUtils.isEmpty(etPrice.getText().toString())) {
                    ToastUtil.showLongCenterToast("请输入价格");
                    return;
                }
                if (TextUtils.isEmpty(etCapcity.getText().toString())) {
                    ToastUtil.showLongCenterToast("请输入商品容量");
                    return;
                }
                if (TextUtils.isEmpty(etAddCount.getText().toString())) {
                    ToastUtil.showLongCenterToast("请输入补货数量");
                    return;
                }
                if (TextUtils.isEmpty(etLessCount.getText().toString())) {
                    ToastUtil.showLongCenterToast("请输入最低库存数");
                    return;
                }
                if (TextUtils.isEmpty(etSerialNo.getText().toString())) {
                    ToastUtil.showLongCenterToast("请输入产品批次");
                    return;
                }
                GoodsInfo goodsInfo = new GoodsInfo();
                goodsInfo.setVmCode("0000051");
                goodsInfo.setVmId(1);
                goodsInfo.setCheight(Double.valueOf(etHight.getText().toString().trim()));
                goodsInfo.setClong(Double.valueOf(etLong.getText().toString().trim()));
                goodsInfo.setCwidth(Double.valueOf(etWidth.getText().toString().trim()));
                goodsInfo.setClcCapacity(Integer.valueOf(etAddCount.getText().toString().trim()));
                goodsInfo.setRealPrice(Integer.valueOf(etPrice.getText().toString().trim().replace("¥", "")));
                goodsInfo.setClCapacity(Integer.valueOf(etCapcity.getText().toString().trim()));
                goodsInfo.setPriductBatch(etSerialNo.getText().toString().trim());
                goodsInfo.setThreshold(etLessCount.getText().toString().trim());
                goodsInfo.setMdseId(mProductInfo.getMdseId());
                goodsInfo.setMdseUrl(mProductInfo.getMdseUrl());
                goodsInfo.setMdseName(mProductInfo.getMdseName());
                goodsInfo.setMdseBrand(mProductInfo.getMdseBrand());
                goodsInfo.setMdsePack(mProductInfo.getMdsePack());
//                goodsInfo.setMerchantType(mProductInfo.getMer);
                goodsInfo.setMdsePrice(String.valueOf(mProductInfo.getMdsePrice()));
                productConfigListener.confirm(goodsInfo);
                dismiss();
                break;
            case R.id.tvChangeGoods:
            case R.id.ivGoodsImg:
                showGoodsListDialog();
                break;
            case R.id.tvClear:
                break;
            case R.id.tvFillAll:
                break;
        }
    }

    /**
     * 更换商品之后刷新UI
     */
    private void refreshUi() {
        tvGoodsName.setText(mProductInfo.getMdseName());
        tvKucun.setText("库存：0");
        Glide.with(getContext()).load(mProductInfo.getMdseUrl()).into(ivGoodsImg);
        etPrice.setText("¥" + String.valueOf(mProductInfo.getMdsePrice()));
    }

    private void showGoodsListDialog() {
        ChooseGoodsDialog dialog = new ChooseGoodsDialog(mContext);
        dialog.showDialog(new ChooseGoodsDialog.SelectProductListener() {
            @Override
            public void select(ProductInfo productInfo) {
                mProductInfo = productInfo;
                refreshUi();
            }
        });
    }

    public interface ProductConfigListener {
        void confirm(GoodsInfo goodsInfo);
    }
}
