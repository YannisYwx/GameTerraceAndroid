package com.yuemai.game34999.presentaion.presenter.news;

import com.orhanobut.logger.Logger;
import com.yannis.common.rx.RxUtils;
import com.yuemai.game34999.core.mvp.CommonSubscriber;
import com.yuemai.game34999.core.mvp.RxPresenter;
import com.yuemai.game34999.data.DataManager;
import com.yuemai.game34999.data.bean.Article;
import com.yuemai.game34999.presentaion.contract.ArticleContract;

import java.util.List;

import javax.inject.Inject;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/26  16:35
 * @email : 923080261@qq.com
 * @description :
 */
public class ArticlePresenter extends RxPresenter<ArticleContract.View> implements ArticleContract.Presenter {

    DataManager mDataManager;

    @Inject
    ArticlePresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void loadArticle(int articleId) {
        addSubscribe(mDataManager.getArticle(articleId)
                .compose(RxUtils.getDefaultSchedulers())
                .compose(RxUtils.handleResult())
                .subscribeWith(new CommonSubscriber<List<Article>>(mView) {
                    @Override
                    public void onNext(List<Article> articleList) {
                        mView.showArticle(articleList);
                    }
                }));
    }

    @Override
    public void loadArticleCommentDetails(int articleId) {
        addSubscribe(mDataManager.getArticleComment(123456, 5, 5)
                .compose(RxUtils.getDefaultSchedulers())
                .subscribe(articleCommentDetails -> {
                    Logger.e("articleCommentDetails---------------------loadArticleComments");
                    Logger.e(articleCommentDetails.toString());
                    mView.showArticleCommentDetails(articleCommentDetails);
                }, throwable -> {
                    Logger.e("throwable---------------------loadArticleComments msg = " + throwable.getMessage());
                    Logger.e(throwable.getMessage());
                }));
    }
}
