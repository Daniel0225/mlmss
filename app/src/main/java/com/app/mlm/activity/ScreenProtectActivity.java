package com.app.mlm.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.app.imageloader.glide.GlideApp;
import com.app.mlm.R;
import com.app.mlm.widget.CoustomTopView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.countdown
 * @fileName : CountDownManager
 * @date : 2019/1/2  13:14
 * @describe : 屏保页
 * @email : xing.luo@taojiji.com
 */
public class ScreenProtectActivity extends AppCompatActivity {
    @Bind(R.id.topView)
    CoustomTopView topView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_protect);
        ButterKnife.bind(this);

        topView.setData(CoustomTopView.TYPE_JPG, "http://b-ssl.duitang.com/uploads/item/201505/13/20150513225953_QJCa8.thumb.700_0.gif");
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        finish();
        return super.dispatchTouchEvent(ev);
    }
}
