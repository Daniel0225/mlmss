package com.app.mlm.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.widget
 * @fileName : SpacesItemDecoration
 * @date : 2019/1/3  20:51
 * @describe : RecycleView Item 之间的间距
 * @email : xing.luo@taojiji.com
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int top;
    private int bottom;
    private int left;
    private int right;
    public SpacesItemDecoration(int top, int bottom, int left, int right){
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = left;
        outRect.right = right;
        outRect.bottom = bottom;
        outRect.top = top;
    }
}
