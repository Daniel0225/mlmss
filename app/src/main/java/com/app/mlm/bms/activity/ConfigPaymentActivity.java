package com.app.mlm.bms.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.app.mlm.R;
import com.app.mlm.bms.adapter.PaymentListAdapter;
import com.app.mlm.bms.bean.PaymentInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 支付配置页
 */
public class ConfigPaymentActivity extends BaseActivity {
    @Bind(R.id.lvPayment)
    ListView lvPayment;
    private PaymentListAdapter adapter;
    private List<PaymentInfo> data = new ArrayList<>();
    @Override
    protected int provideLayoutResId() {
        return R.layout.activity_config_payment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        data = PaymentInfo.getPayments();
        adapter = new PaymentListAdapter(this, data);
        lvPayment.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        lvPayment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                data.get(position).setChecked(!data.get(position).isChecked());
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void initData() {

    }

}
