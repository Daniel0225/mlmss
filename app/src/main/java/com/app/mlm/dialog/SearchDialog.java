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
import android.widget.TextView;

import com.app.mlm.Constants;
import com.app.mlm.R;
import com.app.mlm.adapter.InputGridAdapter;
import com.app.mlm.adapter.SearchResultAdapter;
import com.app.mlm.bean.GoodsInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.dialog
 * @fileName : SearchDialog
 * @date : 2019/1/4  15:22
 * @describe : TODO
 * @email : xing.luo@taojiji.com
 */
public class SearchDialog extends BaseDialog {
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

    public SearchDialog(Context context) {
        super(context, R.layout.dialog_search_layout, true);
    }

    @Override
    public void initView() {
        inputGridAdapter = new InputGridAdapter(mContext);
        inputGridView.setAdapter(inputGridAdapter);

        searchResultAdapter = new SearchResultAdapter(mContext, data);
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
                GoodsDetailDialog dialog = new GoodsDetailDialog(mContext, null);
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
                getData();
            }
        });
    }

    private void getData() {

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
                clearKeywords();
                break;
            case R.id.rlTop:
                dismiss();
                break;
        }
    }

    private void clearKeywords(){
        etSearch.setText("");
    }

}
