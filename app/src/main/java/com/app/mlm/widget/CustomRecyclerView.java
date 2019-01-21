package com.app.mlm.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.widget
 * @fileName : CustomRecyclerView
 * @date : 2019/1/19  13:30
 * @describe : TODO
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public class CustomRecyclerView extends RecyclerView {
    private float downX, downY;
    public CustomRecyclerView(@NonNull Context context) {
        super(context);
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        interuptParentTouch(e);
        return super.onTouchEvent(e);
    }

    /**
     * 当横向滑动时，阻塞父布局抢夺焦点
     *
     * @param event
     */
    private void interuptParentTouch(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = x;
                downY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = x - downX;
                float dy = y - downY;
                if (Math.abs(dx) > 8 && Math.abs(dy) > 8) {
                    int orientation = getOrientation(dx, dy);
                    switch (orientation) {
                        case 'b':
                            getParent().requestDisallowInterceptTouchEvent(true);
                            break;
                        case 't':
                            getParent().requestDisallowInterceptTouchEvent(true);
                            break;
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
    }

    /**
     * 根据距离差判断 滑动方向
     *
     * @param dx X轴的距离差
     * @param dy Y轴的距离差
     * @return 滑动的方向
     */
    private int getOrientation(float dx, float dy) {
        if (Math.abs(dx) > Math.abs(dy)) {
            return dx > 0 ? 'r' : 'l';
        } else {
            return dy > 0 ? 'b' : 't';
        }
    }
}
