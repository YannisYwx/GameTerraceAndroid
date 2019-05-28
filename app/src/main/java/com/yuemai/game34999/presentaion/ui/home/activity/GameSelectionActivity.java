package com.yuemai.game34999.presentaion.ui.home.activity;

import android.view.View;

import com.yannis.common.widget.LoadingPager;
import com.yannis.common.widget.NestedGridView;
import com.yuemai.game34999.R;
import com.yuemai.game34999.di.component.ActivityComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerActivity;
import com.yuemai.game34999.presentaion.ui.home.adapter.GameSelectionAdapter;
import com.yuemai.game34999.util.TestDataUtils;

import java.util.List;

import butterknife.BindView;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/11/28  9:46
 * @email : 923080261@qq.com
 * @description : 游戏精选
 */
public class GameSelectionActivity extends AbstractMvpLoadPagerActivity {
    @BindView(R.id.gv_hot_recommend)
    NestedGridView mGridView;

    @Override
    public int setContentLayout() {
        return R.layout.activity_game_selection;
    }

    @Override
    protected String initTitle() {
        return getString(R.string.title_gameSelection);
    }

    @Override
    protected void refreshContentView(View contentView) {
        List data = TestDataUtils.getTestDatas(6);
        GameSelectionAdapter adapter = new GameSelectionAdapter(this, R.layout.item_game_selection, data);
        mGridView.setAdapter(adapter);
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
    public void initEvent() {
        super.initEvent();
        mGridView.setOnItemClickListener((parent, view, position, id) -> {
            forward(GameSelectionDetailActivity.class);
        });
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
