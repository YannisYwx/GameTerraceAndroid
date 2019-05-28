package com.yuemai.game34999.core.mvp.inter;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/12  18:46
 * @email : 923080261@qq.com
 * @description :
 */
public interface IPresenter {
    /**
     * 绑定view
     */
    void onAttach();

    /**
     * 解绑view
     */
    void onDetach();
}