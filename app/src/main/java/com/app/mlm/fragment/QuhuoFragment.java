package com.app.mlm.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.mlm.Constants;
import com.app.mlm.R;
import com.app.mlm.activity.ChuhuoActivity;
import com.app.mlm.adapter.InputGridAdapter;
import com.app.mlm.countdown.CountDownManager;
import com.app.mlm.utils.TimeCountUtilsFinishFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuhuoFragment extends BaseFragment implements CountDownManager.OnCountDownListener {
    TimeCountUtilsFinishFragment timeCount;
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.tvCountDown)
    TextView tvCountDown;
    @Bind(R.id.ivCode)
    ImageView ivCode;
    @Bind(R.id.inputGridView)
    GridView inputGridView;
    @Bind(R.id.etQuhuoCode)
    EditText etQuhuoCode;
    @Bind(R.id.tvQuhuo)
    TextView tvQuhuo;
    private InputGridAdapter adapter;

    public QuhuoFragment() {
    }

    @Override
    protected int provideLayoutResId() {
        return R.layout.fragment_quhuo;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        adapter = new InputGridAdapter(context, false);
        inputGridView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        inputGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 11) {
                    deleteLastChar();
                } else if (position == 9) {
                    etQuhuoCode.setText("");
                } else {
                    etQuhuoCode.setText(etQuhuoCode.getText() + Constants.BUTTONS[position]);
                }
            }
        });
    }

    @Override
    protected void initData() {
        startTime();
    }

    private void deleteLastChar() {
        if(!TextUtils.isEmpty(etQuhuoCode.getText().toString())){
            StringBuffer sb = new StringBuffer(etQuhuoCode.getText().toString());
            sb.deleteCharAt(sb.length() - 1);
            etQuhuoCode.setText(sb.toString());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.setSearchLayoutVisible(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        mActivity.setSearchLayoutVisible(View.VISIBLE);
    }

   /* @Override
    public void onTick(int seconds) {
        tvCountDown.setText(seconds + "s");
    }*/

    @Override
    public void onFinish() {
        ivBack.performClick();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        countDownManager.cancelTimer();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ivBack, R.id.tvQuhuo})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.ivBack:
                mActivity.removeFragment();
                break;
            case R.id.tvQuhuo:
//                mActivity.addFragment(new ChuhuoFragment());
                ChuhuoActivity.start(getContext());
                break;
        }
    }

    @Override
    protected boolean isNeedCountDown() {
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    /**
     * 开启倒计时
     */
    public void startTime() {
        if (timeCount == null) {
            timeCount = new TimeCountUtilsFinishFragment(mActivity, 60000, 1000, tvCountDown);
        }
        timeCount.start(); //倒计时后重新获取
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timeCount != null) {
            timeCount.cancel();
        }
    }
}
