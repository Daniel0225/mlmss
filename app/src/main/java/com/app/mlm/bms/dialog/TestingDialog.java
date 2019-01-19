package com.app.mlm.bms.dialog;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.widget.ImageView;

import com.app.mlm.R;
import com.app.mlm.bms.bean.TestResult;
import com.app.mlm.dialog.BaseDialog;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.bms.dialog
 * @fileName : TestingDialog
 * @date : 2019/1/19  14:29
 * @describe : 出货测试，测试中弹窗
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public class TestingDialog extends BaseDialog {
    @Bind(R.id.ivGoodsImg)
    ImageView ivGoodsImg;
    @Bind(R.id.ivClose)
    ImageView ivClose;

    public TestingDialog(Context context) {
        super(context, R.layout.dialog_testing, Gravity.CENTER);
    }

    @Override
    public void initView() {
        doTest();
    }

    /**
     * 执行测试操作
     */
    private void doTest() {
        //模拟测试耗时操作
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //执行完成后，关闭dialog
                dismiss();
                //展示测试结果
                showTestResutDialog(new TestResult()); //此处应传入实际从测试结果
            }
        }, 2000);
    }

    private void showTestResutDialog(TestResult result) {
        TestResultDialog dialog = new TestResultDialog(mContext, result);
        dialog.show();
    }

    @OnClick(R.id.ivClose)
    public void onViewClicked() {
        dismiss();
    }
}
