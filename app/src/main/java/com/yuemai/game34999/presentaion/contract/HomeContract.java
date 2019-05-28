package com.yuemai.game34999.presentaion.contract;

import com.yuemai.game34999.core.mvp.inter.BaseContract;
import com.yuemai.game34999.data.bean.BannerInfo;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/28  14:52
 * @email : 923080261@qq.com
 * @description :
 */
public interface HomeContract {

    interface View extends BaseContract.BaseView {

        /**
         * 加载头部广告信息
         * @param bannerInfoList
         */
        void loadBannerInfoList(List<BannerInfo> bannerInfoList);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        /**
         * 获取广告数据
         */
        void getBannerInfo();
    }
}
