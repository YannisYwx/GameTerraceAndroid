package com.yuemai.game34999.presentaion.presenter.classify;

import com.orhanobut.logger.Logger;
import com.yannis.common.rx.RxUtils;
import com.yuemai.game34999.data.AppCacheDataManager;
import com.yuemai.game34999.core.mvp.CommonSubscriber;
import com.yuemai.game34999.core.mvp.RxPresenter;
import com.yuemai.game34999.data.DataManager;
import com.yuemai.game34999.data.bean.GameInfo;
import com.yuemai.game34999.presentaion.contract.ClassifyListContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/9  17:33
 * @email : 923080261@qq.com
 * @description :
 */
public class ClassifyListPresenter extends RxPresenter<ClassifyListContract.View> implements ClassifyListContract.Presenter {
    private DataManager mDataManager;
    private int pagerIndex = 1;
    private boolean isLoadAll = false;

    @Inject
    ClassifyListPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }


    @Override
    public void loadAllClassifyByTag(String tag) {
        Logger.e("loadAllClassifyByTag --> 加载全部数据 tag = " + tag + "----pagerIndex = " + pagerIndex);
        List<GameInfo> gameInfoList = AppCacheDataManager.getClassify(tag, pagerIndex);
        if (gameInfoList != null && gameInfoList.size() >= 0) {
            StringBuilder sb = new StringBuilder("游戏详情数据条数 = ").append(gameInfoList.size()).append("\n");
            for (GameInfo info : gameInfoList) {
                sb.append(info.toString() + "\n");
            }
            Logger.e(sb.toString());
            mView.onLoadAllClassifySuccess(gameInfoList);
            return;
        }
        addSubscribe(mDataManager.getGameList(tag, pagerIndex)
                .compose(RxUtils.getIoSchedulers())
                .compose(RxUtils.handleResult())
                .doOnNext(gameList -> AppCacheDataManager.putClassify(tag, gameList))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CommonSubscriber<List<GameInfo>>(mView) {
                    @Override
                    public void onNext(List<GameInfo> gameInfoList) {
                        if (gameInfoList != null && gameInfoList.size() > 0) {
                            mView.onLoadAllClassifySuccess(AppCacheDataManager.getAllClassify(tag));
                        } else {
                            mView.onNoMoreData();
                        }
                    }
                }));
    }

    @Override
    public void loadMoreGameInfo(String tag) {
        if (isLoadAll) {
            mView.onNoMoreData();
            return;
        }
        pagerIndex++;
        Logger.e("loadMoreGameInfo --> 加载分页数据 tag = " + tag + "----pagerIndex = " + pagerIndex);
        addSubscribe(mDataManager.getGameList(tag, pagerIndex)
                .compose(RxUtils.getIoSchedulers())
                .compose(RxUtils.handleResult())
                .doOnNext(gameList -> AppCacheDataManager.putClassify(tag, gameList))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CommonSubscriber<List<GameInfo>>(mView) {
                    @Override
                    public void onNext(List<GameInfo> gameInfoList) {
                        if (gameInfoList != null && gameInfoList.size() > 0) {
                            mView.onLoadGameClassifySuccess(gameInfoList);
                        } else {
                            isLoadAll = true;
                            mView.onNoMoreData();
                        }
                    }
                }));
    }
}
