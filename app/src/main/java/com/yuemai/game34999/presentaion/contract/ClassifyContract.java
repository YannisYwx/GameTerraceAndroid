package com.yuemai.game34999.presentaion.contract;

import com.yuemai.game34999.core.mvp.inter.BaseContract;
import com.yuemai.game34999.data.bean.GameType;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/9  17:34
 * @email : 923080261@qq.com
 * @description :分类
 */
public interface ClassifyContract {
    interface View extends BaseContract.BaseView {

        /**
         * 加载游戏类型数据
         *
         * @param gameTypeList
         */
        void showGameClassify(List<GameType> gameTypeList);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        /**
         * 获取游戏分类
         */
        void getGameClassify();

    }
}
