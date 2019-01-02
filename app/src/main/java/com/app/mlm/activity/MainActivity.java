package com.app.mlm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.app.mlm.R;
import com.app.mlm.activity.order.OrderPayActivity;

public class MainActivity extends AppCompatActivity {
    Button tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init() {
        tv = (Button) findViewById(R.id.pay);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OrderPayActivity.class));
            }
        });
    }
}
