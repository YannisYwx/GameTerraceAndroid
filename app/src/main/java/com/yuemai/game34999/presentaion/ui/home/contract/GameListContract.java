package com.yuemai.game34999.presentaion.ui.home.contract;

import com.yannis.common.base.mvp.inter.IMode;
import com.yuemai.game34999.core.mvp.inter.BaseContract;

/**
 * Author : Yannis.Ywx
 * CreateTime : 2017/11/22  19:54
 * Email : 923080261@qq.com
 * Description :
 */
public interface GameListContract {

    interface View extends BaseContract.BaseView {

    }

    interface Presenter {
        void startDownload(String msg);
    }

    interface Mode extends IMode {

    }
}
