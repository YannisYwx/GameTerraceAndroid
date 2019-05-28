package com.yannis.common.base.mvp.inter;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/16  17:39
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
