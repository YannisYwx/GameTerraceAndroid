package com.yuemai.game34999.presentaion.ui.home.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.di.component.FragmentComponent;
import com.yuemai.game34999.presentaion.base.BaseListMvpFragment;
import com.yuemai.game34999.presentaion.ui.home.adapter.RankingClassifyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/25  14:44
 * @email : 923080261@qq.com
 * @description :
 */
public class RankingClassifyFragment extends BaseListMvpFragment {
    @BindView(R.id.rv_data_list)
    RecyclerView mRecyclerView;
    private List list;

    @Override
    public int initDataFromCache() {
        list = new ArrayList();
        for (int i = 0; i < 6; i++) {
            list.add("");
        }
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public int initData() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public void refreshContentView(View contentView) {
        RankingClassifyAdapter adapter = new RankingClassifyAdapter(list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
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
