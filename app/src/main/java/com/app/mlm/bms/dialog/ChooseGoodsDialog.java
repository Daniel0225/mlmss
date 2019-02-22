package com.app.mlm.bms.dialog;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.app.mlm.Constants;
import com.app.mlm.R;
import com.app.mlm.bean.AllTypeInfo;
import com.app.mlm.bms.adapter.ChooseGoodsAdapter;
import com.app.mlm.bms.adapter.ChooseGoodsTypeAdapter;
import com.app.mlm.dialog.BaseDialog;
import com.app.mlm.http.BaseResponse;
import com.app.mlm.http.JsonCallBack;
import com.app.mlm.http.bean.AllTypeInfoResponse;
import com.app.mlm.http.bean.GoodsTypeSelectBean;
import com.app.mlm.http.bean.ProductInfo;
import com.app.mlm.utils.FastJsonUtil;
import com.app.mlm.utils.PreferencesUtil;
import com.app.mlm.widget.SpacesItemDecoration;
import com.app.mlm.widget.titilebar.ITitleBar;
import com.app.mlm.widget.titilebar.TitleBar;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import org.litepal.LitePal;
import org.litepal.crud.callback.FindMultiCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.bms.dialog
 * @fileName : ChooseGoodsDialog
 * @date : 2019/1/19  18:23
 * @describe : 货道配置 --  更换商品 -- 商品列表
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public class ChooseGoodsDialog extends BaseDialog implements ITitleBar, View.OnClickListener, ChooseGoodsTypeAdapter.CustomClickListener {
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.titlebar)
    TitleBar titleBar;
    @Bind(R.id.rl_search)
    View searchView;
    @Bind(R.id.rl_type)
    View typeView;
    @Bind(R.id.select_view)
    View selectView;
    @Bind(R.id.brand)
    TextView brandView;
    @Bind(R.id.type)
    TextView typeTextView;
    @Bind(R.id.pack)
    TextView packTextView;
    @Bind(R.id.select_grid)
    RecyclerView selectGridView;
    private List<ProductInfo> originData = new ArrayList<>();
    private List<ProductInfo> data = new ArrayList<>();
    private ChooseGoodsAdapter adapter;
    private ChooseGoodsTypeAdapter goodsTypeAdapter;
    private SelectProductListener selectProductListener;
    private AllTypeInfoResponse allTypeInfoResponse;
    private ChooseGoodsTypeAdapter.CustomClickListener customClickListener;
    private GoodsTypeSelectBean goodsTypeSelectBean = new GoodsTypeSelectBean();
    private ChooseGoodsAdapter.CustomClickListener listener = new ChooseGoodsAdapter.CustomClickListener() {
        @Override
        public void onClick(int position) {
            selectProductListener.select(data.get(position));
            dismiss();
        }
    };

    public ChooseGoodsDialog(Context context) {
        super(context, R.layout.dialog_choose_goods, true, Gravity.CENTER);
    }

    public void showDialog(SelectProductListener listener) {
        this.selectProductListener = listener;
        show();
    }

    @Override
    public void initView() {
        titleBar.setTitlebarListener(this);
        titleBar.setRightViewVisibility(View.VISIBLE);
        mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 5));
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(4, 4, 4, 4));

        customClickListener = this;
        selectGridView.setLayoutManager(new GridLayoutManager(mContext, 5));
        selectGridView.addItemDecoration(new SpacesItemDecoration(8, 8, 5, 5));


        LitePal.findAllAsync(ProductInfo.class).listen(new FindMultiCallback() {
            @Override
            public <T> void onFinish(List<T> t) {
                data = (List<ProductInfo>) t;
                originData.addAll(data);
                adapter = new ChooseGoodsAdapter(data, listener);
                mRecyclerView.setAdapter(adapter);
            }
        });

        selectView.setOnClickListener(this);
        brandView.setOnClickListener(this);
        typeTextView.setOnClickListener(this);
        packTextView.setOnClickListener(this);

        getAllTypeInfo();

        allTypeInfoResponse = FastJsonUtil.getObject(PreferencesUtil.getString("allType"), AllTypeInfoResponse.class);
    }

    @Override
    public void onRightClicked() {
        if (searchView.getVisibility() == View.VISIBLE) {
            searchView.setVisibility(View.GONE);
            typeView.setVisibility(View.VISIBLE);
        } else {
            searchView.setVisibility(View.VISIBLE);
            typeView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLeftClicked() {
        dismiss();
    }

    @Override
    public void onActionClicked() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_view:
                selectView.setVisibility(View.GONE);
                break;
            case R.id.brand:
                if (selectView.getVisibility() == View.GONE) {
                    selectView.setVisibility(View.VISIBLE);
                }
                goodsTypeSelectBean.setType(0);
                goodsTypeAdapter = new ChooseGoodsTypeAdapter(mContext, allTypeInfoResponse.getBrand(), customClickListener, goodsTypeSelectBean);
                selectGridView.setAdapter(goodsTypeAdapter);
                break;
            case R.id.type:
                if (selectView.getVisibility() == View.GONE) {
                    selectView.setVisibility(View.VISIBLE);
                }
                goodsTypeSelectBean.setType(1);
                goodsTypeAdapter = new ChooseGoodsTypeAdapter(mContext, allTypeInfoResponse.getType(), customClickListener, goodsTypeSelectBean);
                selectGridView.setAdapter(goodsTypeAdapter);
                break;
            case R.id.pack:
                if (selectView.getVisibility() == View.GONE) {
                    selectView.setVisibility(View.VISIBLE);
                }
                goodsTypeSelectBean.setType(2);
                goodsTypeAdapter = new ChooseGoodsTypeAdapter(mContext, allTypeInfoResponse.getPack(), customClickListener, goodsTypeSelectBean);
                selectGridView.setAdapter(goodsTypeAdapter);
                break;
        }
    }

    @Override
    public void onClick(int position) {
        selectView.setVisibility(View.GONE);
        goodsTypeSelectBean.setSelectType(goodsTypeSelectBean.getType());
        goodsTypeSelectBean.setSelectPosition(position);
        goodsTypeAdapter.notifyDataSetChanged();
        if (position == 0) {
            data.clear();
            data.addAll(originData);
        } else {
            filterData();
        }

        adapter.notifyDataSetChanged();
    }

    private void filterData() {
        data.clear();
        for (int i = 0; i < originData.size(); i++) {
            ProductInfo productInfo = originData.get(i);
            if (goodsTypeSelectBean.getType() == 0) {
                int keyWord = allTypeInfoResponse.getBrand().get(goodsTypeSelectBean.getSelectPosition()).getMerchantId();
                if (productInfo.getMerchantId() == keyWord) {
                    data.add(productInfo);
                }
            } else if (goodsTypeSelectBean.getType() == 1) {
                String keyWord = allTypeInfoResponse.getType().get(goodsTypeSelectBean.getSelectPosition()).getBrandName();
                if (!TextUtils.isEmpty(productInfo.getMdseBrand()) && productInfo.getMdseBrand().equals(keyWord)) {
                    data.add(productInfo);
                }
            } else {
                String keyWord = allTypeInfoResponse.getPack().get(goodsTypeSelectBean.getSelectPosition()).getPackName();
                if (!TextUtils.isEmpty(productInfo.getMdsePack()) && productInfo.getMdsePack().equals(keyWord)) {
                    data.add(productInfo);
                }
            }
        }
    }

    private void getAllTypeInfo() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("vmCode", "0000051");
        OkGo.<BaseResponse<AllTypeInfoResponse>>get(Constants.GET_ALL_TYPE)
                .tag(this)
                .params(httpParams)
                .execute(new JsonCallBack<BaseResponse<AllTypeInfoResponse>>() {
                    @Override
                    public void onSuccess(Response<BaseResponse<AllTypeInfoResponse>> response) {
                        if (response.body().getCode() == 0) {
                            allTypeInfoResponse = response.body().getData();
                            AllTypeInfo allTypeInfo = new AllTypeInfo("不限");
                            allTypeInfoResponse.getBrand().add(0, allTypeInfo);
                            allTypeInfoResponse.getPack().add(0, allTypeInfo);
                            allTypeInfoResponse.getType().add(0, allTypeInfo);
                            PreferencesUtil.putString("allType", FastJsonUtil.createJsonString(allTypeInfoResponse));
                        }
                    }
                });
    }

    public interface SelectProductListener {
        void select(ProductInfo productInfo);
    }
}
