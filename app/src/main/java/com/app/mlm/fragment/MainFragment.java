package com.app.mlm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.RxHttpUtils;
import com.allen.library.base.BaseObserver;
import com.app.mlm.R;
import com.app.mlm.activity.MainActivity;
import com.app.mlm.adapter.ColumnGoodsAdapter;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.bms.activity.BackgroundManangerSystemActivity;
import com.app.mlm.bms.dialog.CommonDialog;
import com.app.mlm.http.ApiService;
import com.app.mlm.http.bean.BaseBean;
import com.app.mlm.widget.SpacesItemDecoration;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends BaseFragment {
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.quhuo)
    LinearLayout quhuo;
    @Bind(R.id.llCart)
    LinearLayout llCart;
    @Bind(R.id.tvCartPrice)
    TextView tvCartPrice;
    @Bind(R.id.tvCartCount)
    TextView tvCartCount;
    @Bind(R.id.gouwuche)
    RelativeLayout gouwuche;
    @Bind(R.id.huodong)
    LinearLayout huodong;

    public MainFragment() {
    }

    @Override
    protected int provideLayoutResId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        LinearLayoutManager ms = new LinearLayoutManager(getActivity());
        ms.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(ms);
        recyclerView.addItemDecoration(new SpacesItemDecoration(8, 8, 0, 0));
        ColumnGoodsAdapter adapter = new ColumnGoodsAdapter(getActivity(), null);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        RxHttpUtils.createApi(ApiService.class)
            .getHomeGoodsList("0000003")
            .subscribeOn(Schedulers.io())
            .subscribe(new BaseObserver<BaseBean>(getActivity()) {
                @Override
                public void doOnSubscribe(Disposable d) {

                }

                @Override
                public void doOnError(String errorMsg) {

                }

                @Override
                public void doOnNext(BaseBean baseBean) {
                    List<GoodsInfo> data = (List<GoodsInfo>) baseBean.getData();
                }

                @Override
                public void doOnCompleted() {

                }
            });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.quhuo, R.id.gouwuche, R.id.huodong})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.quhuo:
                mActivity.addFragment(new QuhuoFragment());
                break;
            case R.id.gouwuche:
                mActivity.addFragment(new ShopCartFragment());
                break;
            case R.id.huodong:
//                Toast.makeText(getActivity(), "敬请期待", Toast.LENGTH_SHORT).show();
                CommonDialog commonDialog = new CommonDialog(getActivity(), "系统维护", "请确认是否维护售货机", "确定", "取消");
                commonDialog.setCommitClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getActivity(), BackgroundManangerSystemActivity.class));
                    }
                });
                commonDialog.show();
                break;
        }
    }
}
