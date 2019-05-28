package com.yuemai.game34999.presentaion.ui.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/23  15:34
 * @email : 923080261@qq.com
 * @description : 主页面
 */
public class MainTabPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;

    public MainTabPagerAdapter(List<Fragment> fragments, FragmentManager fm) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

}
