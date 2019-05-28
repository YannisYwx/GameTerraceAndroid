package com.yuemai.game34999.presentaion.ui.news.fragment;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.data.bean.GameNewsLabel;
import com.yuemai.game34999.data.bean.News;
import com.yuemai.game34999.di.component.FragmentComponent;
import com.yuemai.game34999.presentaion.base.BaseListMvpFragment;
import com.yuemai.game34999.presentaion.contract.NewsContract;
import com.yuemai.game34999.presentaion.presenter.news.NewsPresenter;
import com.yuemai.game34999.presentaion.ui.news.activity.ArticleActivity;
import com.yuemai.game34999.presentaion.ui.news.adapter.NewsAdapter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/26  11:18
 * @email : 923080261@qq.com
 * @description :
 */
public class NewsListFragment extends BaseListMvpFragment<NewsPresenter> implements NewsContract.View {
    NewsAdapter mAdapter;
    private static final String KEY_NEWS_LABEL = "NEWS_LABEL";
    private GameNewsLabel label;
    private List<News> mList = new ArrayList<>();

    public static NewsListFragment getInstance(GameNewsLabel label) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_NEWS_LABEL, label);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initView(View rootView, @Nullable Bundle savedInstanceState) {
        super.initView(rootView, savedInstanceState);
        label = (GameNewsLabel) getArguments().getSerializable(KEY_NEWS_LABEL);
    }

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public int initDataFromCache() {
        return LoadingPager.LoadingState.stateLoading;
    }

    @Override
    public int initData() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public void refreshContentView(View contentView) {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void initEvent() {
        mAdapter = new NewsAdapter(mList, label.getID() == 0);
        mAdapter.setOnLoadMoreDataListener(() -> {
            Logger.e("-----------------加载更多");
            presenter.loadMoreGameNews(label);
        });
        mAdapter.setOnItemClickListener(position -> {
            ArticleActivity.start(getContext(), mList.get(position).getID(), mList.get(position).getNewsIcon());
//            TestActivity.start(getContext());
        });
//        mAdapter.setOnItemClickListener(position -> {
//            RecyclerView.LayoutManager manager = mRecyclerView.getLayoutManager();
//            View view = manager.getChildAt(position);
//            ImageView imageView = view.findViewById(R.id.iv_information);
//            ArticleActivity.startAction(getActivity(), imageView, mList.get(position).getID(), mList.get(position).getNewsIcon());
//        });
    }

    @Override
    protected void lazyLoad() {
        if (label != null && mList.size() == 0) {
            //开始加载新闻列表
            presenter.getGameNewsList(label);
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void onLoadAllNewsList(List<News> news) {
        mList.clear();
        mList.addAll(news);
        if (mAdapter == null) {
            mAdapter = new NewsAdapter(mList, label.getID() == 0);
        }
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        showSuccessView();
    }

    @Override
    public void onLoadMoreNewsList(List<News> news) {
        mAdapter.loadMoreData(news);
    }

    @Override
    public void onNoMoreData() {
        mAdapter.noMoreData();
        if (mList.size() == 0) {
            mLoadingPager.showEmptyView();
        }
    }

    @IntDef({com.yuemai.game34999.presentaion.ui.news.fragment.NewsListFragment.InformationType.home, com.yuemai.game34999.presentaion.ui.news.fragment.NewsListFragment.InformationType.news, com.yuemai.game34999.presentaion.ui.news.fragment.NewsListFragment.InformationType.dynamic, com.yuemai.game34999.presentaion.ui.news.fragment.NewsListFragment.InformationType.movie, com.yuemai.game34999.presentaion.ui.news.fragment.NewsListFragment.InformationType.strategy, com.yuemai.game34999.presentaion.ui.news.fragment.NewsListFragment.InformationType.data, com.yuemai.game34999.presentaion.ui.news.fragment.NewsListFragment.InformationType.appraisal, com.yuemai.game34999.presentaion.ui.news.fragment.NewsListFragment.InformationType.interview})
    @Retention(RetentionPolicy.SOURCE)
    public @interface InformationType {
        /**
         * 主页
         */
        int home = 0;
        /**
         * 新闻
         */
        int news = 1;
        /**
         * 动态
         */
        int dynamic = 2;
        /**
         * 视频
         */
        int movie = 3;
        /**
         * 攻略
         */
        int strategy = 4;
        /**
         * 数据
         */
        int data = 5;
        /**
         * 评测
         */
        int appraisal = 6;
        /**
         * 专访
         */
        int interview = 7;

    }
}

