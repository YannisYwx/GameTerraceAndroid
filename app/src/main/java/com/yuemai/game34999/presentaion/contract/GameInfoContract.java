package com.yuemai.game34999.presentaion.contract;

import com.yuemai.game34999.core.mvp.inter.BaseContract;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/14  16:16
 * @email : 923080261@qq.com
 * @description :
 */
public interface GameInfoContract {

    interface View extends BaseContract.BaseView {
        void showInfo();

        void downloadSuccess();

        void downloadFailed();
    }

    interface Presenter extends BaseContract.BasePresenter<View> {
        void getGameInfo();

        void startDownload();
    }
}
