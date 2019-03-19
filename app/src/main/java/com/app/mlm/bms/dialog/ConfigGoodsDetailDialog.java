package com.app.mlm.bms.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mlm.Constants;
import com.app.mlm.R;
import com.app.mlm.application.MainApp;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.dialog.BaseDialog;
import com.app.mlm.http.bean.ProductInfo;
import com.app.mlm.utils.FastJsonUtil;
import com.app.mlm.utils.PreferencesUtil;
import com.app.mlm.utils.ToastUtil;
import com.bumptech.glide.Glide;

import java.util.List;

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
    @Bind(R.id.capacity_jian)
    ImageView capacityJian;
    @Bind(R.id.buhuo_jian)
    ImageView buhuoJian;
    @Bind(R.id.zuidi_jian)
    ImageView zuidiJian;
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
            etLong.setText(String.valueOf(goodsInfo.getClong()));
            etWidth.setText(String.valueOf(goodsInfo.getCwidth()));
            etHight.setText(String.valueOf(goodsInfo.getCheight()));
            etPrice.setText(String.valueOf(goodsInfo.getRealPrice()));
            etCapcity.setText(String.valueOf(goodsInfo.getClCapacity()));
            etSerialNo.setText(goodsInfo.getPriductBatch());
            etLessCount.setText(goodsInfo.getThreshold());

            String cacheString = PreferencesUtil.getString(Constants.LOCK_IDS);
            if (!TextUtils.isEmpty(cacheString)) {
                List<Integer> lockIds = FastJsonUtil.getObjects(PreferencesUtil.getString(Constants.LOCK_IDS), Integer.class);
                for (Integer lockId : lockIds) {
                    if (goodsInfo.getMdseId() == lockId) {
                        etPrice.setEnabled(false);
                    }
                }
            }

        }

    }

    @OnClick({R.id.cancel, R.id.commit, R.id.tvChangeGoods, R.id.tvClear, R.id.tvFillAll, R.id.ivGoodsImg,
            R.id.capacity_jian, R.id.capacity_add, R.id.buhuo_add, R.id.buhuo_jian, R.id.zuidi_add, R.id.zuidi_jian})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.commit:
                if (goodsInfo.getMdseUrl().equals("empty") && mProductInfo == null) {
                    ToastUtil.showLongCenterToast("请选择商品");
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
                if(goodsInfo.getClcCapacity() + Integer.valueOf(etAddCount.getText().toString().trim()) > Integer.valueOf(etCapcity.getText().toString())){
                    ToastUtil.showLongCenterToast("补货数量超出最大可补货数");
                    return;
                }
                if (TextUtils.isEmpty(etLessCount.getText().toString())) {
                    ToastUtil.showLongCenterToast("请输入最低库存数");
                    return;
                }
                if (goodsInfo.getMdseUrl().equals("empty")) {
                    goodsInfo = new GoodsInfo();
                    goodsInfo.setVmCode(PreferencesUtil.getString(Constants.VMCODE));
                    goodsInfo.setVmId(PreferencesUtil.getInt(Constants.VMID));
                    goodsInfo.setMdseId(mProductInfo.getMdseId());
                    goodsInfo.setMdseUrl(mProductInfo.getMdseUrl());
                    goodsInfo.setMdseName(mProductInfo.getMdseName());
                    goodsInfo.setMdseBrand(mProductInfo.getMdseBrand());
                    goodsInfo.setMdsePack(mProductInfo.getMdsePack());
                    goodsInfo.setMdsePrice(mProductInfo.getMdsePrice());
//                goodsInfo.setMerchantType(mProductInfo.getMer);
                }
                goodsInfo.setCheight(TextUtils.isEmpty(etHight.getText().toString().trim()) ? 0 : Double.valueOf(etHight.getText().toString().trim()));
                goodsInfo.setClong(TextUtils.isEmpty(etLong.getText().toString().trim()) ? 0 : Double.valueOf(etLong.getText().toString().trim()));
                goodsInfo.setCwidth(TextUtils.isEmpty(etWidth.getText().toString().trim()) ? 0 : Double.valueOf(etWidth.getText().toString().trim()));
                goodsInfo.setClcCapacity(goodsInfo.getClcCapacity() + Integer.valueOf(etAddCount.getText().toString().trim()));
                goodsInfo.setRealPrice(Double.valueOf(etPrice.getText().toString().trim().replace("¥", "")));
                goodsInfo.setClCapacity(Integer.valueOf(etCapcity.getText().toString().trim()));
                goodsInfo.setPriductBatch(etSerialNo.getText().toString().trim());
                goodsInfo.setThreshold(etLessCount.getText().toString().trim());

                productConfigListener.confirm(goodsInfo);
                dismiss();
                break;
            case R.id.tvChangeGoods:
            case R.id.ivGoodsImg:
                showGoodsListDialog();
                break;
            case R.id.tvClear:
                clearHuoDaoInfo();
                break;
            case R.id.tvFillAll:
                if (TextUtils.isEmpty(etCapcity.getText().toString()) || Integer.valueOf(etCapcity.getText().toString()) == 0) {
                    ToastUtil.showLongCenterToast("请设置货道容量");
                } else {
                    int capcity = Integer.valueOf(etCapcity.getText().toString());
                    etAddCount.setText(String.valueOf(capcity - goodsInfo.getClcCapacity()));
                }
                break;
            case R.id.capacity_jian:
                int capacity = TextUtils.isEmpty(etCapcity.getText().toString()) ? 0 :Integer.valueOf(etCapcity.getText().toString());
                if (capacity > 0) {
                    etCapcity.setText(String.valueOf(capacity - 1));
                }
                if (capacity == 1) {
                    capacityJian.setImageResource(R.drawable.jian_goods_nor);
                }

                int buhuoNum3 = TextUtils.isEmpty(etAddCount.getText().toString()) ? 0 : Integer.valueOf(etAddCount.getText().toString());
                int keBuhuoShu = Integer.valueOf(etCapcity.getText().toString()) - goodsInfo.getClcCapacity() - buhuoNum3;
                if ( keBuhuoShu < 0 ) {
                    etAddCount.setText(String.valueOf(buhuoNum3 + keBuhuoShu));
                }
                break;
            case R.id.capacity_add:
                int capacity2 = TextUtils.isEmpty(etCapcity.getText().toString()) ? 0 : Integer.valueOf(etCapcity.getText().toString());
                etCapcity.setText(String.valueOf(capacity2 + 1));
                capacityJian.setImageResource(R.drawable.jian_goods);
                break;
            case R.id.buhuo_jian:
                int buhuoNum = TextUtils.isEmpty(etAddCount.getText().toString()) ? 0 : Integer.valueOf(etAddCount.getText().toString());
                etAddCount.setText(String.valueOf(buhuoNum - 1));
                break;
            case R.id.buhuo_add:
                int buhuoNum2 = TextUtils.isEmpty(etAddCount.getText().toString()) ? 0 : Integer.valueOf(etAddCount.getText().toString());
                if (buhuoNum2 <  Integer.valueOf(etCapcity.getText().toString()) - goodsInfo.getClcCapacity()) {
                    etAddCount.setText(String.valueOf(buhuoNum2 + 1));
                    buhuoJian.setImageResource(R.drawable.jian_goods);
                } else {
                    ToastUtil.showLongCenterToast("补货数不能超过货道容量");
                }
                break;
            case R.id.zuidi_jian:
                int zuidiNum = TextUtils.isEmpty(etLessCount.getText().toString()) ? 0 : Integer.valueOf(etLessCount.getText().toString());
                if (zuidiNum > 0) {
                    etLessCount.setText(String.valueOf(zuidiNum - 1));
                }
                if (zuidiNum == 1) {
                    zuidiJian.setImageResource(R.drawable.jian_goods_nor);
                }
                break;
            case R.id.zuidi_add:
                int zuidiNum2 = TextUtils.isEmpty(etLessCount.getText().toString()) ? 0 : Integer.valueOf(etLessCount.getText().toString());
                etLessCount.setText(String.valueOf(zuidiNum2 + 1));
                zuidiJian.setImageResource(R.drawable.jian_goods);
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

        String cacheString = PreferencesUtil.getString(Constants.LOCK_IDS);
        if (!TextUtils.isEmpty(cacheString)) {
            List<Integer> lockIds = FastJsonUtil.getObjects(cacheString, Integer.class);
            for (Integer lockId : lockIds) {
                if (mProductInfo.getMdseId() == lockId) {
                    etPrice.setEnabled(false);
                }
            }
        }

    }

    private void showGoodsListDialog() {
        ChooseGoodsDialog dialog = new ChooseGoodsDialog(mContext);
        dialog.showDialog(new ChooseGoodsDialog.SelectProductListener() {
            @Override
            public void select(ProductInfo productInfo) {
                goodsInfo.setMdseUrl("empty");
                mProductInfo = productInfo;
                refreshUi();
            }
        });
    }

    private void clearHuoDaoInfo(){
        CommonDialog dialog = new CommonDialog(getContext(), "提示", "清空当前货道所有数据，是否清空", "清空", "取消");
        dialog.setCommitClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearGoodsInfo();
            }
        }).setCancelClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void clearGoodsInfo(){
//        mProductInfo = null;
//        goodsInfo.setMdseUrl("empty");
//        tvKucun.setText("0");
//        tvGoodsName.setText("请选择商品");
//        ivGoodsImg.setImageResource(R.drawable.empty);
//        etLong.setText("");
//        etWidth.setText("");
//        etHight.setText("");
//        etPrice.setText("");
//        etCapcity.setText("");
//        etAddCount.setText("");
//        etLessCount.setText("");
//        etSerialNo.setText("");
        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setMdseName("请补货");
        goodsInfo.setMdseUrl("empty");
        goodsInfo.setMdsePrice(0);
        productConfigListener.confirm(goodsInfo);
        dismiss();
    }

    public interface ProductConfigListener {
        void confirm(GoodsInfo goodsInfo);
    }
}
