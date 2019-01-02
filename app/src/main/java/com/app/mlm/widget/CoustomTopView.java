package com.app.mlm.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.app.mlm.utils.PhoneUtil;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.widget
 * @fileName : CoustomTopView
 * @date : 2019/1/2  14:20
 * @describe : 顶部可以放静态图片，动态图片，视频的控件
 * @email : xing.luo@taojiji.com
 */
public class CoustomTopView extends RelativeLayout {
    private Context mContext;
    public CoustomTopView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public CoustomTopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public CoustomTopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr){
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
