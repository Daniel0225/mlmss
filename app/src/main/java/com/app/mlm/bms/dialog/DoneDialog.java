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
 * @fileName : DoneDialog
 * @date : 2019/1/9  11:44
 * @describe : TODO
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public class DoneDialog extends BaseDialog {
    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.icon)
    ImageView icon;
    @Bind(R.id.discription)
    TextView discription;
    @Bind(R.id.button)
    TextView button;
    @Bind(R.id.ivClose)
    ImageView ivClose;
    private String mTitle, mDescription, mButton;
    private int iconId;

    public DoneDialog(Context context) {
        super(context, R.layout.dialog_done, Gravity.CENTER);
    }

    public DoneDialog(Context context, String title, String description, String button, int iconId) {
        super(context, R.layout.dialog_done, Gravity.CENTER);
        this.mTitle = title;
        this.mDescription = description;
        this.mButton = button;
        this.iconId = iconId;
    }

    @Override
    public void initView() {
        if(!TextUtils.isEmpty(mTitle)){
            title.setText(mTitle);
        }
        if(!TextUtils.isEmpty(mDescription)){
            discription.setText(mDescription);
        }
        if(!TextUtils.isEmpty(mButton)){
            button.setText(mButton);
        }
        if(iconId > 0){
            icon.setImageResource(iconId);
        }
    }

    @OnClick({R.id.button, R.id.ivClose})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
            case R.id.ivClose:
                dismiss();
                break;
        }
    }
}
