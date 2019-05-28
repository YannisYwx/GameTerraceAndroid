package com.yuemai.game34999.presentaion.ui.classify.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.yuemai.game34999.presentaion.ui.classify.fragment.GameListFragment;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/12  17:58
 * @email : 923080261@qq.com
 * @description :
 */
public class GameListPagerAdapter extends FragmentStatePagerAdapter {
    private String[] titles;

    public GameListPagerAdapter(@NonNull String[] titles, FragmentManager fm) {
        super(fm);
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return GameListFragment.newInstance(titles[position]);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null) {
            return titles[position];
        }
        return super.getPageTitle(position);
    }
}
