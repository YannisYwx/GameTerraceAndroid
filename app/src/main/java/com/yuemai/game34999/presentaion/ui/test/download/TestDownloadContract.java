package com.yuemai.game34999.presentaion.ui.test.download;

import com.yuemai.game34999.core.mvp.inter.BaseContract;
import com.yuemai.game34999.data.bean.GameInfo;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/28  17:43
 * @email : 923080261@qq.com
 * @description :
 */
public interface TestDownloadContract {

    interface View extends BaseContract.BaseView {

        void loadDownloadGameInfoList(List<GameInfo> gameInfoList);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void getDownloadGameInfoList();
    }
}
