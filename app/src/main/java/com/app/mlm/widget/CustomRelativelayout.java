package com.app.mlm.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.app.mlm.utils.PhoneUtil;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.widget
 * @fileName : CustomRelativelayout
 * @date : 2019/1/4  17:52
 * @describe : 四分之一屏幕高度的RelativeLayout
 * @email : xing.luo@taojiji.com
 */
public class CustomRelativelayout extends RelativeLayout {
    private Context mContext;
    public CustomRelativelayout(Context context) {
        super(context);
        this.mContext = context;
    }

    public CustomRelativelayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public CustomRelativelayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        //设置控件高度为四分之一屏幕高度
        int height = PhoneUtil.getDisplayHeight(mContext) / 4;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
