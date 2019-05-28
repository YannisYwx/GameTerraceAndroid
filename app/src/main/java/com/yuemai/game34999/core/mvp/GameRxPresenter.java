package com.yuemai.game34999.core.mvp;

import com.yuemai.game34999.core.mvp.inter.BaseContract;
import com.yuemai.game34999.data.DataManager;

import javax.inject.Inject;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/9  17:41
 * @email : 923080261@qq.com
 * @description :
 */
public class GameRxPresenter<T extends BaseContract.BaseView> extends RxPresenter<T>{
    protected DataManager mDataManager;

    @Inject
    public GameRxPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }
}
