package com.yuemai.game34999.presentaion.ui.game.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.di.component.FragmentComponent;
import com.yuemai.game34999.presentaion.base.BaseListMvpFragment;
import com.yuemai.game34999.presentaion.ui.game.adapter.GameDetailAdapter;
import com.yuemai.game34999.util.TestDataUtils;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/29  17:56
 * @email : 923080261@qq.com
 * @description :
 */
public class GameDetailFragment extends BaseListMvpFragment {
    GameDetailAdapter mAdapter;
    List datas;

    @Override
    public int initDataFromCache() {
        datas = TestDataUtils.getTestDatas();
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public int initData() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public void refreshContentView(View contentView) {
        mAdapter = new GameDetailAdapter(datas);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void lazyLoad() {

    }


    @Override
    public void onComplete() {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {

    }
}
