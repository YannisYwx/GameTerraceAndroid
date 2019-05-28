package com.yuemai.game34999.presentaion.ui.classify.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.orhanobut.logger.Logger;
import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.data.bean.GameInfo;
import com.yuemai.game34999.di.component.FragmentComponent;
import com.yuemai.game34999.presentaion.base.BaseListMvpFragment;
import com.yuemai.game34999.presentaion.contract.ClassifyListContract;
import com.yuemai.game34999.presentaion.presenter.classify.ClassifyListPresenter;
import com.yuemai.game34999.presentaion.ui.classify.adapter.GameListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/12  16:08
 * @email : 923080261@qq.com
 * @description : 游戏列表
 */
public class GameListFragment extends BaseListMvpFragment<ClassifyListPresenter> implements ClassifyListContract.View {
    private static final String KEY_CLASSIFY = "CLASSIFY";
    private GameListAdapter mAdapter;
    private List<GameInfo> mGameInfos = new ArrayList<>();
    private String tag;

    public static GameListFragment newInstance(String classify) {
        GameListFragment fragment = new GameListFragment();
        Bundle args = new Bundle();
        args.putString(KEY_CLASSIFY, classify);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int initDataFromCache() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public int initData() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public void refreshContentView(View contentView) {
    }

    @Override
    public void initView(View rootView, @Nullable Bundle savedInstanceState) {
        super.initView(rootView, savedInstanceState);
        controller =
                AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_fall_down);
        mAdapter = new GameListAdapter(mGameInfos);
        mRecyclerView.setLayoutAnimation(controller);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initEvent() {
        mAdapter.setOnLoadMoreDataListener(() -> presenter.loadMoreGameInfo(tag));
    }

    @Override
    protected void lazyLoad() {
        if (mGameInfos.size() == 0) {
            tag = getArguments().getString(KEY_CLASSIFY);
            showLoadingView();
            Logger.e("------------------lazyLoad tag = " + tag);
            presenter.loadAllClassifyByTag(tag);
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

    @Override
    public void onLoadAllClassifySuccess(List<GameInfo> gameInfoList) {
        mGameInfos.clear();
        mGameInfos.addAll(gameInfoList);
        mRecyclerView.setLayoutAnimation(controller);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        showSuccessView();
    }

    @Override
    public void onLoadGameClassifySuccess(List<GameInfo> gameInfoList) {
        mAdapter.loadMoreData(gameInfoList);
    }

    @Override
    public void onNoMoreData() {
        showToast("没有更多数据了");
        mAdapter.noMoreData();
    }
}
