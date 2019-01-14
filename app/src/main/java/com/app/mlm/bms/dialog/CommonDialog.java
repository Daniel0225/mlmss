package com.app.mlm.bms.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
public class CommonDialog extends BaseDialog {
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.tvDesc)
    TextView tvDesc;
    @Bind(R.id.tvCancel)
    TextView tvCancel;
    @Bind(R.id.tvCommit)
    TextView tvCommit;
    @Bind(R.id.ivClose)
    ImageView ivClose;
    private String mTitle, mDesc, mCommit, mCancel;
    private View.OnClickListener commitListener;
    private View.OnClickListener cancelListener;

    public CommonDialog(Context context, String title, String desc, String commitBtn, String cancleBtn) {
        super(context, R.layout.dialog_common, Gravity.CENTER);
        this.mTitle = title;
        this.mDesc = desc;
        this.mCommit = commitBtn;
        this.mCancel = cancleBtn;
    }

    @Override
    public void initView() {
        if(!TextUtils.isEmpty(mTitle)) tvTitle.setText(mTitle);
        if(!TextUtils.isEmpty(mDesc)) tvDesc.setText(mDesc);
        if(!TextUtils.isEmpty(mCommit)) {
            tvCommit.setText(mCommit);
            tvCommit.setVisibility(View.VISIBLE);
        }
        if(!TextUtils.isEmpty(mCancel)) {
            tvCancel.setText(mCancel);
            tvCancel.setVisibility(View.VISIBLE);
        }
    }

    public CommonDialog setCommitClickListener(View.OnClickListener commitListener){
        this.commitListener = commitListener;
        return this;
    }

    public CommonDialog setCancelClickListener(View.OnClickListener cancelListener){
        this.cancelListener = cancelListener;
        return this;
    }

    @OnClick({R.id.tvCancel, R.id.tvCommit, R.id.ivClose})
    public void onViewClicked(View view) {
        dismiss();
        switch (view.getId()) {
            case R.id.tvCancel:
                if(cancelListener != null) cancelListener.onClick(view);
                break;
            case R.id.tvCommit:
                if(commitListener != null) commitListener.onClick(view);
                break;
            case R.id.ivClose:
                break;
        }
    }
}
