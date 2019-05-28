package com.yuemai.game34999.presentaion.contract;

import com.yuemai.game34999.core.mvp.inter.BaseContract;
import com.yuemai.game34999.data.bean.GameNewsLabel;
import com.yuemai.game34999.data.bean.News;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/25  10:38
 * @email : 923080261@qq.com
 * @description :
 */
public interface NewsContract {

    interface View extends BaseContract.BaseView {

        /**
         * 加载所有游戏新闻列表
         *
         * @param news 所有数据
         */
        void onLoadAllNewsList(List<News> news);

        /**
         * 加载游戏新闻列表
         *
         * @param news
         */
        void onLoadMoreNewsList(List<News> news);

        /**
         * 没有更多数据了
         */
        void onNoMoreData();

    }

    interface Presenter extends BaseContract.BasePresenter<NewsContract.View> {

        /**
         * 获取游戏新闻列表
         *
         * @param label 游戏类型
         */
        void getGameNewsList(GameNewsLabel label);


        /**
         * 加载更多
         *
         * @param label 新闻类型
         */
        void loadMoreGameNews(GameNewsLabel label);

    }
}
