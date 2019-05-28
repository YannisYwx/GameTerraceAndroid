package com.yuemai.game34999.presentaion.presenter.game;

import com.orhanobut.logger.Logger;
import com.yuemai.game34999.core.mvp.RxPresenter;
import com.yuemai.game34999.data.DataManager;
import com.yuemai.game34999.presentaion.contract.GameInfoContract;

import javax.inject.Inject;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/14  16:14
 * @email : 923080261@qq.com
 * @description :
 */
public class GameInfoPresenter extends RxPresenter<GameInfoContract.View> implements GameInfoContract.Presenter {

    DataManager mDataManager;

    @Inject
    GameInfoPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void getGameInfo() {
        mView.showInfo();
        Logger.e("GameInfoPresenter-getGameInfo----------------GameInfoPresenter = startDownload");
        Logger.e("GameInfoPresenter-getGameInfo----------------mDataManager = " + mDataManager.toString());
        Logger.e("GameInfoPresenter-getGameInfo----------------mDataManager DBHelper = " + mDataManager.getDBHelper().toString());
        Logger.e("GameInfoPresenter-getGameInfo----------------mDataManager HttpHelper = " + mDataManager.getHttpHelper().toString());
        Logger.e("GameInfoPresenter-getGameInfo----------------mDataManager Preferences = " + mDataManager.getPreferencesHelper().toString());
    }

    @Override
    public void startDownload() {
        mDataManager.insert();
        mDataManager.delete();
        mDataManager.query();
        mDataManager.isFirstIn();
        Logger.e("GameInfoPresenter-startDownload----------------GameInfoPresenter = startDownload");
        Logger.e("GameInfoPresenter-startDownload----------------mDataManager = " + mDataManager.toString());
        Logger.e("GameInfoPresenter-startDownload----------------mDataManager DBHelper = " + mDataManager.getDBHelper().toString());
        Logger.e("GameInfoPresenter-startDownload----------------mDataManager HttpHelper = " + mDataManager.getHttpHelper().toString());
        Logger.e("GameInfoPresenter-startDownload----------------mDataManager Preferences = " + mDataManager.getPreferencesHelper().toString());
        mView.showMsg("开始下载");

    }
}
