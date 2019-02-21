package com.app.mlm.bms.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.app.mlm.R;
import com.app.mlm.dialog.BaseDialog;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.bms.dialog
 * @fileName : CommonDialog
 * @date : 2019/1/9  14:31
 * @describe : TODO
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public class CouponDialog extends BaseDialog {

    @Bind(R.id.ivClose)
    ImageView ivClose;
    private View.OnClickListener commitListener;
    private View.OnClickListener cancelListener;

    public CouponDialog(Context context) {
        super(context, R.layout.dialog_coupon, Gravity.CENTER);
    }

    @Override
    public void initView() {

    }

    public CouponDialog setCommitClickListener(View.OnClickListener commitListener) {
        this.commitListener = commitListener;
        return this;
    }

    public CouponDialog setCancelClickListener(View.OnClickListener cancelListener) {
        this.cancelListener = cancelListener;
        return this;
    }

    @OnClick({R.id.ivClose})
    public void onViewClicked(View view) {
        dismiss();
        switch (view.getId()) {
            case R.id.ivClose:
                dismiss();
                break;
        }
    }
}
