package com.app.mlm.widget.dragsort;

public interface CallbackItemTouch {

    /**
     * 拖拽排序回调
     * @param oldPosition 初始位置
     * @param newPosition 结束位置
     */
    void itemTouchOnMove(int oldPosition, int newPosition);
}
