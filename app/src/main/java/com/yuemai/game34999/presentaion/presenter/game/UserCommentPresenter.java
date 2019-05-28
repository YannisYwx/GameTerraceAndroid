package com.yuemai.game34999.presentaion.presenter.game;

import com.orhanobut.logger.Logger;
import com.yannis.common.rx.RxUtils;
import com.yuemai.game34999.core.mvp.RxPresenter;
import com.yuemai.game34999.data.DataManager;
import com.yuemai.game34999.presentaion.contract.UserCommentContract;

import javax.inject.Inject;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/14  16:14
 * @email : 923080261@qq.com
 * @description :
 */
public class UserCommentPresenter extends RxPresenter<UserCommentContract.View> implements UserCommentContract.Presenter {

    DataManager mDataManager;

    private int pagerIndex = 1;

    @Inject
    UserCommentPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }

    @Override
    public void getCommentList(long topic_id) {
        addSubscribe(mDataManager.getArticleComments(topic_id, pagerIndex)
                .compose(RxUtils.getDefaultSchedulers())
                .subscribe(articleComment -> {
                    if (articleComment == null || articleComment.getComments().size() == 0) {
                        mView.onNoMoreData();
                    } else {
                        pagerIndex++;
                        Logger.e("---------------------->" + articleComment.toString());
                        mView.loadComments(articleComment);
                    }
                }, throwable -> {
                    Logger.e("---------------------->" + throwable.toString());
                    mView.showMsg("网络异常出错了");
                }));
    }

}
