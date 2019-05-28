package com.yuemai.game34999.presentaion.presenter.home;

import com.orhanobut.logger.Logger;
import com.yannis.common.rx.RxUtils;
import com.yuemai.game34999.core.mvp.CommonSubscriber;
import com.yuemai.game34999.core.mvp.RxPresenter;
import com.yuemai.game34999.data.DataManager;
import com.yuemai.game34999.data.bean.BannerInfo;
import com.yuemai.game34999.presentaion.contract.HomeContract;

import java.util.List;

import javax.inject.Inject;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/28  14:51
 * @email : 923080261@qq.com
 * @description :
 */
public class HomePresenter extends RxPresenter<HomeContract.View> implements HomeContract.Presenter {

    DataManager mDataManager;

    @Inject
    HomePresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void getBannerInfo() {
        addSubscribe(mDataManager.getBannerInfo()
                .compose(RxUtils.getDefaultSchedulers())
                .compose(RxUtils.handleResult())
                .subscribeWith(new CommonSubscriber<List<BannerInfo>>(mView) {
                    @Override
                    public void onNext(List<BannerInfo> bannerInfos) {
                        if (bannerInfos != null && bannerInfos.size() > 0) {
                             mView.loadBannerInfoList(bannerInfos);
                             for(BannerInfo bannerInfo:bannerInfos){
                                 Logger.e("getBannerInfo------------------"+bannerInfo.toString());
                             }
                        } else {
                            mView.showMsg("数据加载出错了！");
                        }
                    }
                }));
    }
}
