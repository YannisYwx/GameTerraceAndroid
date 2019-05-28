package com.yuemai.game34999.presentaion.ui.test.inter;

import com.yuemai.game34999.core.mvp.inter.BaseContract;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/28  17:43
 * @email : 923080261@qq.com
 * @description :
 */
public interface TestNetInterfaceContract {

    interface View extends BaseContract.BaseView {
        void showResult(String result);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void request(long position);
    }
}
