package com.yuemai.game34999.presentaion.contract;

import com.yuemai.game34999.core.mvp.inter.BaseContract;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/25  10:38
 * @email : 923080261@qq.com
 * @description :
 */
public interface MainContract {

    interface View extends BaseContract.BaseView {


    }

    interface Presenter extends BaseContract.BasePresenter<MainContract.View> {

        /**
         * 获取游戏分类
         */
        void getGameClassify();

        /**
         * 获取新闻标签
         */
        void getGameNewsLabels();
    }
}
