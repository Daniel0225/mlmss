package com.app.mlm.bms.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mlm.R;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.dialog.BaseDialog;
import com.app.mlm.dialog.GoodsDetailDialog;

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
    EditText etPrice;
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
    private GoodsInfo goodsInfo;

    public ConfigGoodsDetailDialog(Context context, GoodsInfo goodsInfo) {
        super(context, R.layout.dialog_config_goods_detail, true, Gravity.CENTER);
    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.cancel, R.id.commit, R.id.tvChangeGoods, R.id.tvClear, R.id.tvFillAll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();
                break;
            case R.id.commit:
                dismiss();
                break;
            case R.id.tvChangeGoods:
                showGoodsListDialog();
                break;
            case R.id.tvClear:
                break;
            case R.id.tvFillAll:
                break;
        }
    }

    private void showGoodsListDialog() {
        ChooseGoodsDialog dialog = new ChooseGoodsDialog(mContext);
        dialog.show();
    }
}
