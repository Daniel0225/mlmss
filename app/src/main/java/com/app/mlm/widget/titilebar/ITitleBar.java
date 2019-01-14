package com.app.mlm.widget.titilebar;

/**
 * @author :  luo.xing
 * @version : 1.0.0
 * @package : com.app.mlm.widget.titilebar
 * @fileName : ITitleBar
 * @date : 2019/1/8  19:51
 * @describe : TODO
 * @org : www.taojiji.com
 * @email : xing.luo@taojiji.com
 */
public interface ITitleBar {
    /**
     * TitleBar右侧icon点击事件
     */
    void onRightClicked();

    /**
     * TitleBar左侧icon点击事件
     */
    void onLeftClicked();

    /**
     * TitleBar右侧text点击事件
     */
    void onActionClicked();
}
