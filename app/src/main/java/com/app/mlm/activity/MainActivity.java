package com.app.mlm.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.app.mlm.R;
import com.app.mlm.activity.base.BaseActivity;
import com.app.mlm.widget.CoustomTopView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.countdown
 * @fileName : CountDownManager
 * @date : 2019/1/2  13:14
 * @describe : 首页
 * @email : xing.luo@taojiji.com
 */
public class MainActivity extends BaseActivity {
    @Bind(R.id.topView)
    CoustomTopView topView;
    @Bind(R.id.quhuo)
    LinearLayout quhuo;
    @Bind(R.id.gouwuche)
    LinearLayout gouwuche;
    @Bind(R.id.huodong)
    LinearLayout huodong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.quhuo, R.id.gouwuche, R.id.huodong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.quhuo:
                break;
            case R.id.gouwuche:
                break;
            case R.id.huodong:
                break;
        }
    }
}
