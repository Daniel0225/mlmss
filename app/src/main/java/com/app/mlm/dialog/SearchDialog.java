package com.app.mlm.dialog;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.mlm.Constants;
import com.app.mlm.R;
import com.app.mlm.adapter.InputGridAdapter;
import com.app.mlm.adapter.SearchResultAdapter;
import com.app.mlm.application.MainApp;
import com.app.mlm.bean.AddShopCarEvent;
import com.app.mlm.bean.GoodsInfo;
import com.app.mlm.http.bean.HuodaoBean;
import com.app.mlm.utils.FastJsonUtil;
import com.app.mlm.utils.PreferencesUtil;
import com.app.mlm.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.dialog
 * @fileName : SearchDialog
 * @date : 2019/1/4  15:22
 * @describe : TODO
 * @email : xing.luo@taojiji.com
 */
public class SearchDialog extends BaseDialog implements SearchResultAdapter.SearchResultAddShopCarListener {
    @Bind(R.id.etSearch)
    EditText etSearch;
    @Bind(R.id.ivClear)
    ImageView ivClear;
    @Bind(R.id.rlSearch)
    RelativeLayout rlSearch;
    @Bind(R.id.rlTop)
    RelativeLayout rlTop;
    @Bind(R.id.gridView)
    GridView gridView;
    @Bind(R.id.inputGridView)
    GridView inputGridView;
    private InputGridAdapter inputGridAdapter;
    private SearchResultAdapter searchResultAdapter;
    private List<GoodsInfo> data = new ArrayList<>();
    private List<List<GoodsInfo>> originData = new ArrayList<>();

    public SearchDialog(Context context) {
        super(context, R.layout.dialog_search_layout, true);
    }

    @Override
    public void initView() {
        inputGridAdapter = new InputGridAdapter(mContext);
        inputGridView.setAdapter(inputGridAdapter);
        initList();
        searchResultAdapter = new SearchResultAdapter(mContext, data, this);
        gridView.setAdapter(searchResultAdapter);
        initListener();
    }

    private void initListener() {
        inputGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 11){
                    deleteLastChar();
                }else if(position == 9){
                    clearKeywords();
                }else {
                    etSearch.setText(etSearch.getText() + Constants.BUTTONS[position]);
                }
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (data.get(position).getMdseUrl().equals("empty")) {
                    ToastUtil.showLongToast("该货道暂无商品");
                    return;
                }
                GoodsDetailDialog dialog = new GoodsDetailDialog(mContext, data.get(position));
                dialog.show();
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getData(s.toString());
            }
        });
    }

    private void getData(String keyWords) {
        searchResult(keyWords);
    }

    private void deleteLastChar() {
        if(!TextUtils.isEmpty(etSearch.getText().toString())){
            StringBuffer sb = new StringBuffer(etSearch.getText().toString());
            sb.deleteCharAt(sb.length() - 1);
            etSearch.setText(sb.toString());
        }
    }

    @OnClick({R.id.ivClear, R.id.rlTop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivClear:
                dismiss();
                break;
            case R.id.rlTop:
                dismiss();
                break;
        }
    }

    private void clearKeywords(){
        etSearch.setText(String.valueOf(1));
        deleteLastChar();

    }

    private void searchResult(String clCode) {
        data.clear();
        if(!TextUtils.isEmpty(clCode)){
            int huodao = Integer.valueOf(clCode);
            if(huodao != 0 && huodao <= originData.size()){
                for (GoodsInfo goods: originData.get(huodao - 1)) {
                    if(!goods.getMdseUrl().equals("empty")){
                        data.add(goods);
                    }
                }
            }
            for (int i = 0; i < originData.size(); i++) {
                List<GoodsInfo> list = originData.get(i);
                for (int j = 0; j < list.size();j++){
                    GoodsInfo goodsInfo = list.get(j);
                    if (goodsInfo.getClCode() != null && goodsInfo.getClCode().equals(clCode)) {
                        data.add(goodsInfo);
                    }
                }
            }
        }

        searchResultAdapter.notifyDataSetChanged();
    }
    /**
     * 造数据
     */
    private void initList() {
        originData.clear();
        String huodaoString = PreferencesUtil.getString("huodao");
        if (TextUtils.isEmpty(huodaoString)) {
//            originData.addAll(getDefaultData());
        } else {
            HuodaoBean huodaoBean = FastJsonUtil.getObject(huodaoString, HuodaoBean.class);
            List<List<GoodsInfo>> dataList = huodaoBean.getAllDataList();
            originData.addAll(dataList);
        }
    }


    private List<GoodsInfo> getDefaultData() {
        List<GoodsInfo> goodsInfoList = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            GoodsInfo goodsInfo = new GoodsInfo();
            goodsInfo.setMdseName("请补货");
            goodsInfo.setMdseUrl("empty");
            goodsInfo.setMdsePrice(0);
            goodsInfoList.add(goodsInfo);
        }
        return goodsInfoList;
    }

    @Override
    public void addCar(int position) {
        if (data.get(position).getMdseUrl().equals("empty")) {
            ToastUtil.showLongToast("该货道暂无商品");
            return;
        }
        MainApp.addShopCar(data.get(position));
        EventBus.getDefault().post(new AddShopCarEvent());//发送消息到首页 更新购物车TAB角标数据
    }
}
