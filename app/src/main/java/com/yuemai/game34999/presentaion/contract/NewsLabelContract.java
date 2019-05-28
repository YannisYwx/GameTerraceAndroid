package com.yuemai.game34999.presentaion.contract;

import com.yuemai.game34999.core.mvp.inter.BaseContract;
import com.yuemai.game34999.data.bean.GameNewsLabel;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/25  10:38
 * @email : 923080261@qq.com
 * @description : 游戏标签
 */
public interface NewsLabelContract {

    interface View extends BaseContract.BaseView {

        /**
         * 加载游戏标签
         *
         * @param labels 游戏标签
         */
        void loadGameNewsLabels(List<GameNewsLabel> labels);
    }

    interface Presenter extends BaseContract.BasePresenter<NewsLabelContract.View> {

        /**
         * 获取新闻标签
         */
        void getGameNewsLabels();
    }
}
