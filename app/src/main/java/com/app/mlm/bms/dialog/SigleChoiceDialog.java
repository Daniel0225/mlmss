package com.app.mlm.bms.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.mlm.R;
import com.app.mlm.dialog.BaseDialog;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.bms.dialog
 * @fileName : SigleChoiceDialog
 * @date : 2019/1/9  16:12
 * @describe : TODO
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public class SigleChoiceDialog extends BaseDialog {
    @Bind(R.id.yunxu)
    LinearLayout yunxu;
    @Bind(R.id.jujue)
    LinearLayout jujue;
    @Bind(R.id.tishi)
    LinearLayout tishi;
    @Bind(R.id.ivClose)
    ImageView ivClose;
    private OnItemClickListener listener;

    public SigleChoiceDialog(Context context, OnItemClickListener listener) {
        super(context, R.layout.dialog_single_choice, Gravity.CENTER);
        this.listener = listener;
    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.yunxu, R.id.jujue, R.id.tishi, R.id.ivClose})
    public void onViewClicked(View view) {
        dismiss();
        switch (view.getId()) {
            case R.id.yunxu:
                listener.onClick("允许");
                break;
            case R.id.jujue:
                listener.onClick("拒绝");
                break;
            case R.id.tishi:
                listener.onClick("提示");
                break;
            case R.id.ivClose:
                break;
        }
    }

    public interface OnItemClickListener {
        void onClick(String value);
    }
}
