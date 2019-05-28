package com.yuemai.game34999.presentaion.ui.test.download;

import com.orhanobut.logger.Logger;
import com.yannis.common.rx.RxUtils;
import com.yuemai.game34999.core.mvp.CommonSubscriber;
import com.yuemai.game34999.core.mvp.RxPresenter;
import com.yuemai.game34999.data.DataManager;
import com.yuemai.game34999.data.bean.GameInfo;

import java.util.List;

import javax.inject.Inject;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/28  17:42
 * @email : 923080261@qq.com
 * @description :
 */
public class TestDownloadPresenter extends RxPresenter<TestDownloadContract.View> implements TestDownloadContract.Presenter {

    DataManager mDataManager;
    StringBuilder sb = new StringBuilder();

    @Inject
    public TestDownloadPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    private <T> void showInfo(List<T> objects) {
        sb.delete(0, sb.length());
        assert objects != null;
        for (T obj : objects) {
            sb.append(obj.toString()).append("\n");
        }
        Logger.e(sb.toString());
    }

    @Override
    public void getDownloadGameInfoList() {
        addSubscribe(mDataManager.getGameList("3D", 1)
                .compose(RxUtils.getDefaultSchedulers())
                .compose(RxUtils.handleResult()).subscribeWith(new CommonSubscriber<List<GameInfo>>(mView) {
                    @Override
                    public void onNext(List<GameInfo> gameInfos) {
                        showInfo(gameInfos);
                        mView.loadDownloadGameInfoList(gameInfos);
                    }
                }));
    }
}
