package com.yuemai.game34999.presentaion.ui.home.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.di.component.ActivityComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerActivity;
import com.yuemai.game34999.presentaion.ui.home.adapter.RankListPagerAdapter;
import com.yuemai.game34999.presentaion.ui.home.fragment.RankingClassifyFragment;
import com.yuemai.game34999.util.MagicIndicatorUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/11/23  16:31
 * @email : 923080261@qq.com
 * @description :排行榜
 */
public class RanklistActivity extends AbstractMvpLoadPagerActivity{
    @BindView(R.id.vp_ranking_list)
    ViewPager mViewPager;
    @BindView(R.id.mi_rankingList)
    MagicIndicator mMagicIndicator;
    private List<Fragment> list;

    private CharSequence[] titles;


    @Override
    public int setContentLayout() {
        return R.layout.activity_ranklist;
    }

    @Override
    protected String initTitle() {
        return getString(R.string.title_ranklist);
    }

    @Override
    protected void refreshContentView(View contentView) {
        list = new ArrayList<>();
        list.add(new RankingClassifyFragment());
        list.add(new RankingClassifyFragment());
        list.add(new RankingClassifyFragment());
        list.add(new RankingClassifyFragment());

        titles = getResources().getStringArray(R.array.ranking_list_title);

        RankListPagerAdapter adapter = new RankListPagerAdapter(list, getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

        MagicIndicatorUtils.bindViewpager(mMagicIndicator, this, mViewPager, titles);
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
