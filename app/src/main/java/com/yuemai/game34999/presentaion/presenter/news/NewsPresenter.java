package com.yuemai.game34999.presentaion.presenter.news;

import com.orhanobut.logger.Logger;
import com.yannis.common.rx.RxUtils;
import com.yuemai.game34999.core.mvp.CommonSubscriber;
import com.yuemai.game34999.core.mvp.RxPresenter;
import com.yuemai.game34999.data.AppCacheDataManager;
import com.yuemai.game34999.data.DataManager;
import com.yuemai.game34999.data.bean.GameNewsLabel;
import com.yuemai.game34999.data.bean.News;
import com.yuemai.game34999.presentaion.contract.NewsContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/25  10:50
 * @email : 923080261@qq.com
 * @description :
 */
public class NewsPresenter extends RxPresenter<NewsContract.View> implements NewsContract.Presenter {
    private boolean isLoadAll = false;
    DataManager mDataManager;
    private int pagerIndex = 1;

    @Inject
    NewsPresenter(DataManager dataManager) {
        this.mDataManager = dataManager;
    }


    @Override
    public void getGameNewsList(final GameNewsLabel label) {
        Logger.e("getGameNewsList --> 加载全部数据 type = " + label.getCName() + "----pagerIndex = " + pagerIndex);
        List<News> newsList = AppCacheDataManager.getNews(label.getID(), pagerIndex);
        if (newsList != null && newsList.size() >= 0) {
            StringBuilder sb = new StringBuilder("新闻列表 = ").append(newsList.size()).append("\n");
            for (News news : newsList) {
                sb.append(news.toString() + "\n");
            }
            Logger.e(sb.toString());
            mView.onLoadAllNewsList(newsList);
            return;
        }
        addSubscribe(mDataManager.getNewsList(label.getID(), pagerIndex)
                .compose(RxUtils.getIoSchedulers())
                .compose(RxUtils.handleResult())
                .doOnNext(newsList1 -> AppCacheDataManager.putNews(label.getID(), newsList1))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CommonSubscriber<List<News>>(mView) {
                    @Override
                    public void onNext(List<News> gameInfoList) {
                        if (gameInfoList != null && gameInfoList.size() > 0) {
                            mView.onLoadAllNewsList(AppCacheDataManager.getAllNews(label.getID()));
                        } else {
                            mView.onNoMoreData();
                        }
                    }
                }));
    }

    @Override
    public void loadMoreGameNews(GameNewsLabel label) {
        if (isLoadAll) {
            mView.onNoMoreData();
            return;
        }
        pagerIndex++;
        Logger.e("loadMoreGameNews --> 加载分页数据 tag = " + label.getCName() + "----pagerIndex = " + pagerIndex);
        addSubscribe(mDataManager.getNewsList(label.getID(), pagerIndex)
                .compose(RxUtils.getIoSchedulers())
                .compose(RxUtils.handleResult())
                .doOnNext(newsList -> AppCacheDataManager.putNews(label.getID(), newsList))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CommonSubscriber<List<News>>(mView) {
                    @Override
                    public void onNext(List<News> news) {
                        if (news != null && news.size() > 0) {
                            mView.onLoadMoreNewsList(news);
                        } else {
                            isLoadAll = true;
                            mView.onNoMoreData();
                        }
                    }
                }));
    }
}
