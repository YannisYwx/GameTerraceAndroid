package com.yuemai.game34999.presentaion.ui.home.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.data.bean.OpenServiceTestGame;
import com.yuemai.game34999.di.component.FragmentComponent;
import com.yuemai.game34999.presentaion.base.BaseListMvpFragment;
import com.yuemai.game34999.presentaion.ui.home.adapter.OpenServiceAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/19  14:43
 * @email : 923080261@qq.com
 * @description :
 */
public class OpenServiceFragment extends BaseListMvpFragment {
    /**
     * 开服表
     */
    public static final int OPEN_SERVICE = 0;
    /**
     * 开测表
     */
    public static final int OPEN_TEST = 1;

    private static final String KEY_OPEN_SERVICE_OR_TEST = "OPEN_SERVICE_OR_TEST";

    @BindView(R.id.rv_data_list)
    RecyclerView mRecyclerView;

    OpenServiceAdapter mAdapter;

    private int type = OPEN_SERVICE;

    private List<OpenServiceTestGame> datas;

    public static OpenServiceFragment getInstance(int type) {
        OpenServiceFragment fragment = new OpenServiceFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_OPEN_SERVICE_OR_TEST, type);
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
        type = getArguments().getInt(KEY_OPEN_SERVICE_OR_TEST);
        datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            OpenServiceTestGame game = new OpenServiceTestGame();
            if (i == 0) {
                game.lable = "今天";
                game.isHasLable = true;
            }

            if (i == 4) {
                game.lable = "未来三天";
                game.isHasLable = true;
            }

            if (i == 10) {
                game.lable = "历史";
                game.isHasLable = true;
            }
            datas.add(game);
        }
        mAdapter = new OpenServiceAdapter(datas);
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
