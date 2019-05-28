package com.yuemai.game34999.presentaion.ui.home.presenter;

import com.yuemai.game34999.core.mvp.RxPresenter;
import com.yuemai.game34999.presentaion.ui.home.contract.GameListContract;

import javax.inject.Inject;

/**
 * Author : Yannis.Ywx
 * CreateTime : 2017/11/22  19:56
 * Email : 923080261@qq.com
 * Description :
 */
public class GameListPresenter extends RxPresenter<GameListContract.View> implements GameListContract.Presenter {

    @Inject
    public GameListPresenter(){

    }

    @Override
    public void startDownload(String msg) {

    }

}
