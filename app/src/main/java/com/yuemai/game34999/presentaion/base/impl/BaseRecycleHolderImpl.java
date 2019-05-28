package com.yuemai.game34999.presentaion.base.impl;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/11/2  16:22
 * @email : 923080261@qq.com
 * @description :
 */
public interface BaseRecycleHolderImpl<T> {
    /**
     * 刷新数据接口
     * @param data 数据
     */
    void setDataAndRefreshHolderView(T data);
}
