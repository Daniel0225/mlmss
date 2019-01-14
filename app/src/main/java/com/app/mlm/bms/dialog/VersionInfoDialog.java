package com.app.mlm.bms.dialog;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.app.mlm.R;
import com.app.mlm.dialog.BaseDialog;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.bms.dialog
 * @fileName : VersionInfoDialog
 * @date : 2019/1/9  14:08
 * @describe : TODO
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public class VersionInfoDialog extends BaseDialog {
    @Bind(R.id.peizhixitong)
    TextView peizhixitong;
    @Bind(R.id.shoumaixitong)
    TextView shoumaixitong;
    @Bind(R.id.shengjichengxu)
    TextView shengjichengxu;
    @Bind(R.id.jiankongchengxu)
    TextView jiankongchengxu;
    @Bind(R.id.zhongjiancengchengxu)
    TextView zhongjiancengchengxu;
    @Bind(R.id.ceshichengxu)
    TextView ceshichengxu;
    @Bind(R.id.pandianchengxu)
    TextView pandianchengxu;
    @Bind(R.id.button)
    TextView button;

    public VersionInfoDialog(Context context) {
        super(context, R.layout.dialog_version_info, Gravity.CENTER);
    }

    @Override
    public void initView() {

    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        dismiss();
    }
}
