package com.yuemai.game34999.presentaion.presenter.news;

import com.yannis.common.rx.RxUtils;
import com.yuemai.game34999.core.mvp.CommonSubscriber;
import com.yuemai.game34999.core.mvp.RxPresenter;
import com.yuemai.game34999.data.DataManager;
import com.yuemai.game34999.data.bean.GameNewsLabel;
import com.yuemai.game34999.presentaion.contract.NewsLabelContract;
import com.yuemai.game34999.util.AppFormatUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/25  10:50
 * @email : 923080261@qq.com
 * @description :
 */
public class NewsLabelPresenter extends RxPresenter<NewsLabelContract.View> implements NewsLabelContract.Presenter {

    DataManager mDataManager;

    @Inject
    NewsLabelPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void getGameNewsLabels() {
        addSubscribe(mDataManager.getGameNewsLabel()
                .compose(RxUtils.getDefaultSchedulers())
                .compose(RxUtils.handleResult())
                .subscribeWith(new CommonSubscriber<List<GameNewsLabel>>(mView) {
                    @Override
                    public void onNext(List<GameNewsLabel> gameNewsLabels) {
                        AppFormatUtils.log(gameNewsLabels);
                        mView.loadGameNewsLabels(gameNewsLabels);
                    }
                }));
    }


}
