package com.yuemai.game34999.presentaion.ui.home.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.di.component.ActivityComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerActivity;
import com.yuemai.game34999.presentaion.ui.home.adapter.OpenServicePagerAdapter;
import com.yuemai.game34999.util.MagicIndicatorUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;

import butterknife.BindView;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/19  11:32
 * @email : 923080261@qq.com
 * @description :开服开测页面
 */
public class OpenServiceActivity extends AbstractMvpLoadPagerActivity {
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    OpenServicePagerAdapter mAdapter;
    @BindView(R.id.magicIndicator)
    MagicIndicator mMagicIndicator;

    @Override
    protected String initTitle() {
        mTitleBar.setRightText(getString(R.string.setting_remind));
        return getString(R.string.title_openService);
    }

    @Override
    public void onRightTitleBarClick(@NonNull View view) {
        super.onRightTitleBarClick(view);
        forward(MyRemindActivity.class);
    }

    @Override
    protected void refreshContentView(View contentView) {

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
        mAdapter = new OpenServicePagerAdapter(getResources().getStringArray(R.array.openService_openTest), getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

        MagicIndicatorUtils.bindViewpager(mMagicIndicator, this, mViewPager, getResources().getStringArray(R.array.openService_openTest));
    }

    @Override
    public void injectThis(ActivityComponent component) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void showMsg(String msg) {

    }
}
