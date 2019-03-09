package com.app.mlm.bms.dialog;

import android.content.Context;
import android.view.Gravity;

import com.app.mlm.R;
import com.app.mlm.dialog.BaseDialog;
import com.dinuscxj.progressbar.CircleProgressBar;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.bms.dialog
 * @fileName : SyncProgressDialog
 * @date : 2019/1/9  9:18
 * @describe : TODO
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public class SyncProgressDialog extends BaseDialog {
    @Bind(R.id.cpbProgress)
    CircleProgressBar cpbProgress;

    public SyncProgressDialog(Context context) {
        super(context, R.layout.dialog_sync_progress, Gravity.CENTER);
    }

    @Override
    public void initView() {

    }

    @OnClick(R.id.ivClose)
    public void onViewClicked() {
        dismiss();
    }

    public int getProgress() {
        return cpbProgress.getProgress();
    }

    public void setProgress(int progress) {
        cpbProgress.setProgress(progress);
    }
}
