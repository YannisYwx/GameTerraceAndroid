package com.yuemai.game34999.presentaion.ui.classify.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.di.component.ActivityComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerActivity;
import com.yuemai.game34999.presentaion.ui.classify.adapter.GameListPagerAdapter;
import com.yuemai.game34999.presentaion.ui.classify.fragment.ClassifyFragment;
import com.yuemai.game34999.util.MagicIndicatorUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;

import butterknife.BindView;

import static com.yuemai.game34999.presentaion.ui.classify.fragment.ClassifyFragment.KEY_GAME_CLASSIFY;
import static com.yuemai.game34999.presentaion.ui.classify.fragment.ClassifyFragment.KEY_TAGS;
import static com.yuemai.game34999.presentaion.ui.classify.fragment.ClassifyFragment.KEY_TITLE;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/12  15:51
 * @email : 923080261@qq.com
 * @description :游戏分类
 */
public class GameGenreActivity extends AbstractMvpLoadPagerActivity {
    private int gameType;
    @BindView(R.id.magicIndicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    GameListPagerAdapter mAdapter;

    private String[] gameClassifyTitle;
    private String title;

    @Override
    protected String initTitle() {
        return title;
    }

    @Override
    protected void refreshContentView(View contentView) {

    }

    @Override
    public void init() {
        super.init();
        Bundle opt = getIntent().getExtras();
        gameType = opt.getInt(KEY_GAME_CLASSIFY);
        gameClassifyTitle = opt.getStringArray(KEY_TAGS);
        title = opt.getString(KEY_TITLE);
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
    public int setContentLayout() {
        return R.layout.activity_indicator_viewpager;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mAdapter = new GameListPagerAdapter(gameClassifyTitle, getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
        MagicIndicatorUtils.bindViewpager(mMagicIndicator, this, mViewPager, gameClassifyTitle);
    }

    @Override
    public void injectThis(ActivityComponent component) {

    }

    /**-------------------------------------
     * 获得标题
     *
     * @param type
     * @return
     */
    public String[] getTitleWithType(int type) {
        switch (type) {
            case ClassifyFragment.GameClassify.stg:
                //飞行射击
                break;
            case ClassifyFragment.GameClassify.leisurePuzzle:
                //休闲益智
                break;
            case ClassifyFragment.GameClassify.sportsCompetition:
                //体育竞技
                break;
            case ClassifyFragment.GameClassify.rolePlay:
                //角色扮演
                break;
            case ClassifyFragment.GameClassify.act:
                //格斗动作
                break;
            case ClassifyFragment.GameClassify.chess:
                //棋牌天地
                break;
            case ClassifyFragment.GameClassify.sim:
                //经营模拟
                break;
            case ClassifyFragment.GameClassify.towerDefense:
                //塔防策略
                break;
            default:
                break;
        }

        return getResources().getStringArray(R.array.classify_STG);
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void showMsg(String msg) {

    }
}
