package com.yuemai.game34999.presentaion.ui.home.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.di.component.ActivityComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerActivity;
import com.yuemai.game34999.presentaion.ui.main.adapter.common.DownloadGameAdapter;
import com.yuemai.game34999.util.TestDataUtils;

import java.util.List;

import butterknife.BindView;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/11/28  9:46
 * @email : 923080261@qq.com
 * @description : 游戏精选详情
 */
public class GameSelectionDetailActivity extends AbstractMvpLoadPagerActivity {
    @BindView(R.id.rv_gameSelectionDetail)
    RecyclerView mRecyclerView;

    @Override
    public int setContentLayout() {
        return R.layout.activity_game_selection_detail;
    }

    @Override
    protected String initTitle() {
        return getString(R.string.title_gameSelectionDetail);
    }

    @Override
    protected void refreshContentView(View contentView) {
        List data = TestDataUtils.getTestDatas(6);
        DownloadGameAdapter adapter = new DownloadGameAdapter(data);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL,false));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected int loadDataFromCache() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public int loadData() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void injectThis(ActivityComponent component) {

    }
}
