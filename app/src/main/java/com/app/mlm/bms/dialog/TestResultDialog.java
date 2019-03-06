package com.app.mlm.bms.dialog;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.app.mlm.R;
import com.app.mlm.bms.bean.TestResult;
import com.app.mlm.dialog.BaseDialog;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.bms.dialog
 * @fileName : TestResultDialog
 * @date : 2019/1/19  14:42
 * @describe : 出货测试 -- 展示测试结果
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public class TestResultDialog extends BaseDialog {
    @Bind(R.id.huodaoName)
    TextView huodaoName;
    @Bind(R.id.state)
    TextView state;
    @Bind(R.id.desc)
    TextView desc;
    @Bind(R.id.button)
    TextView button;
    private TestResult testResult;
    public TestResultDialog(Context context, TestResult testResult) {
        super(context, R.layout.dialog_test_result, Gravity.CENTER);
        this.testResult = testResult;
    }

    @Override
    public void initView() {
        huodaoName.setText(testResult.getHuodaoName());
        state.setText(testResult.getState());
        desc.setText(testResult.getDesc());
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        dismiss();
    }
}
