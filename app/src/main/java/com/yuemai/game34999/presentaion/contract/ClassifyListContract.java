package com.yuemai.game34999.presentaion.contract;

import com.yuemai.game34999.core.mvp.inter.BaseContract;
import com.yuemai.game34999.data.bean.GameInfo;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/9  18:45
 * @email : 923080261@qq.com
 * @description :
 */
public interface ClassifyListContract {
    interface View extends BaseContract.BaseView {

        /**
         * 加载分类的游戏列表
         *
         * @param gameInfoList 所有数据
         */
        void onLoadAllClassifySuccess(List<GameInfo> gameInfoList);

        /**
         *  分页加载数据
         * @param gameInfoList 当前查询页的数据
         */
        void onLoadGameClassifySuccess(List<GameInfo> gameInfoList);

        /**
         * 没有更多数据了
         */
        void onNoMoreData();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        /**
         * 获取游戏分类列表
         *
         * @param tag 类型
         */
        void loadAllClassifyByTag(String tag);


        /**
         * 加载更多
         * @param tag 游戏类型
         */
        void loadMoreGameInfo(String tag);

    }
}
