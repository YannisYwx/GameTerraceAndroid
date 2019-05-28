package com.yuemai.game34999.presentaion.ui.news.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.yannis.common.util.UIUtils;
import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.data.bean.GameNewsLabel;
import com.yuemai.game34999.di.component.FragmentComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerFragment;
import com.yuemai.game34999.presentaion.contract.NewsLabelContract;
import com.yuemai.game34999.presentaion.presenter.news.NewsLabelPresenter;
import com.yuemai.game34999.presentaion.ui.news.adapter.InformationPagerAdapter;
import com.yuemai.game34999.util.MagicIndicatorUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/25  10:44
 * @email : 923080261@qq.com
 * @description :
 */
public class NewsFragment extends AbstractMvpLoadPagerFragment<NewsLabelPresenter> implements NewsLabelContract.View {
    @BindView(R.id.vp_information)
    ViewPager mViewPager;
    @BindView(R.id.mi_information)
    MagicIndicator mMagicIndicator;

    private List<Fragment> list;

    private String[] titles;

    @Override
    public View initContentView() {
        return View.inflate(UIUtils.getContext(), R.layout.fragment_information, null);
    }

    @Override
    public int initDataFromCache() {
        presenter.getGameNewsLabels();
        return LoadingPager.LoadingState.stateLoading;
    }

    @Override
    public int initData() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public void refreshContentView(View contentView) {
        InformationPagerAdapter adapter = new InformationPagerAdapter(list, getChildFragmentManager());
        mViewPager.setAdapter(adapter);
        MagicIndicatorUtils.bindViewpager(mMagicIndicator, getContext(), mViewPager, titles);
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
        fragmentComponent.inject(this);
    }

    @Override
    public void loadGameNewsLabels(List<GameNewsLabel> labels) {
        titles = new String[labels.size()];
        list = new ArrayList<>();
        int i = 0;
        for (GameNewsLabel label : labels) {
            titles[i] = label.getCName();
            list.add(NewsListFragment.getInstance(label));
            i++;
        }
        mLoadingPager.showSuccessView();
    }
}
