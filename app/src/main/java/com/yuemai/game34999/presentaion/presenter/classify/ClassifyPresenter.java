package com.yuemai.game34999.presentaion.presenter.classify;

import com.yannis.common.rx.RxUtils;
import com.yuemai.game34999.core.mvp.CommonSubscriber;
import com.yuemai.game34999.core.mvp.RxPresenter;
import com.yuemai.game34999.data.DataManager;
import com.yuemai.game34999.data.bean.GameType;
import com.yuemai.game34999.presentaion.contract.ClassifyContract;
import com.yuemai.game34999.util.AppFormatUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/9  17:33
 * @email : 923080261@qq.com
 * @description :
 */
public class ClassifyPresenter extends RxPresenter<ClassifyContract.View> implements ClassifyContract.Presenter {
    DataManager mDataManager;

    @Inject
    public ClassifyPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void getGameClassify() {
        addSubscribe(mDataManager.getAllGameType()
                .compose(RxUtils.getDefaultSchedulers())
                .compose(RxUtils.handleResult()).subscribeWith(new CommonSubscriber<List<GameType>>(mView) {
                    @Override
                    public void onNext(List<GameType> gameTypes) {
                        AppFormatUtils.log(gameTypes);
                        mView.showGameClassify(gameTypes);
                    }
                }));
    }

}
