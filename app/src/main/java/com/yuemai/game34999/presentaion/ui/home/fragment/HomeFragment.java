package com.yuemai.game34999.presentaion.ui.home.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.yannis.common.widget.LoadingPager;
import com.yannis.common.widget.banner.BannerConfig;
import com.yannis.common.widget.banner.transformer.CoverModeTransformer;
import com.yannis.common.widget.banner.view.Banner;
import com.yuemai.game34999.R;
import com.yuemai.game34999.data.net.GlideImageLoader;
import com.yuemai.game34999.di.component.FragmentComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerFragment;
import com.yuemai.game34999.presentaion.presenter.home.HomePresenter;
import com.yuemai.game34999.presentaion.ui.home.activity.GameListActivity;
import com.yuemai.game34999.presentaion.ui.home.activity.GameSelectionActivity;
import com.yuemai.game34999.presentaion.ui.home.activity.MakeMoneyActivity;
import com.yuemai.game34999.presentaion.ui.home.activity.OpenServiceActivity;
import com.yuemai.game34999.presentaion.ui.home.activity.RanklistActivity;
import com.yuemai.game34999.presentaion.ui.home.adapter.HomeRecycleViewAdapter;
import com.yuemai.game34999.presentaion.ui.test.TestActivity;
import com.yuemai.game34999.presentaion.widget.FixedChildScrollView;
import com.yuemai.game34999.util.TestDataUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/23  15:35
 * @email : 923080261@qq.com
 * @description : 主页
 */
public class HomeFragment extends AbstractMvpLoadPagerFragment<HomePresenter> implements FixedChildScrollView.OnFixHeadListener {
    @BindView(R.id.rv_home)
    RecyclerView mRecyclerView;
    @BindView(R.id.banner)
    Banner mBanner;
    @BindView(R.id.sv_home)
    FixedChildScrollView mFixedChildScrollView;
    @BindView(R.id.tv_newGame)
    TextView tvNewGame;
    @BindView(R.id.tv_openService)
    TextView tvOpenService;
    @BindView(R.id.tv_gameSelection)
    TextView tvGameSelection;
    @BindView(R.id.tv_makeMoney)
    TextView tvMakeMoney;

    @BindView(R.id.ll_mod)
    LinearLayout ll;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private List list;

    @NonNull
    public static HomeFragment getInstance() {
        return new HomeFragment();
    }

    @Override
    public View initContentView() {
        return View.inflate(mContext, R.layout.fragment_home, null);
    }

    @Override
    public int initDataFromCache() {
        list = TestDataUtils.getTestDatas(17);
        presenter.getBannerInfo();
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public int initData() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public void refreshContentView(View contentView) {
        HomeRecycleViewAdapter adapter = new HomeRecycleViewAdapter(list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setNestedScrollingEnabled(true);
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.default_banner);
        images.add(R.drawable.default_banner);
        images.add(R.drawable.default_banner);
        images.add(R.drawable.default_banner);
        mBanner.setImages(images)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(position -> {
                }).setDelayTime(5 * 1000)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .start();
        mBanner.setPageTransformer(true, new CoverModeTransformer(mBanner.getViewPager()));
        mBanner.setViewPagerIsScroll(false);
        mBanner.setOnBannerListener(position -> TestActivity.start(getContext()));
        refreshLayout.setEnableLoadmore(false);
        refreshLayout.setDragRate(1.0f);
        refreshLayout.setOnRefreshListener(refreshLayout -> refreshLayout.finishRefresh(2000));
        refreshLayout.setOnLoadmoreListener(refreshLayout -> refreshLayout.finishLoadmore(2000));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initEvent() {
        mFixedChildScrollView.setFixHeadListener(this);
        refreshLayout.setNestedScrollingEnabled(true);
    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    public void onFix() {
        ll.setVisibility(View.VISIBLE);
        mRecyclerView.setNestedScrollingEnabled(true);
    }

    @Override
    public void onReset() {
        ll.setVisibility(View.INVISIBLE);
    }

    @OnClick({R.id.tv_newGame, R.id.tv_openService, R.id.tv_gameSelection, R.id.tv_makeMoney, R.id.tv_rankingList,
            R.id.tv_topNewGame, R.id.tv_topOpenService, R.id.tv_topGameSelection, R.id.tv_topMakeMoney, R.id.tv_topRankingList})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_newGame:
            case R.id.tv_topNewGame:
                Bundle bundle = new Bundle();
                bundle.putInt(GameListActivity.KEY_GAME_LIST_TYPE, GameListActivity.NEW_GAME);
                forward(GameListActivity.class, bundle);
                break;
            case R.id.tv_openService:
            case R.id.tv_topOpenService:
                forward(OpenServiceActivity.class);
                break;
            case R.id.tv_gameSelection:
            case R.id.tv_topGameSelection:
                forward(GameSelectionActivity.class);
                break;
            case R.id.tv_makeMoney:
            case R.id.tv_topMakeMoney:
                forward(MakeMoneyActivity.class);
                break;
            case R.id.tv_rankingList:
            case R.id.tv_topRankingList:
                forward(RanklistActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }
}
