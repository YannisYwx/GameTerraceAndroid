package com.yuemai.game34999.presentaion.ui.game.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/12  17:58
 * @email : 923080261@qq.com
 * @description :
 */
public class DownloadManagePagerAdapter extends FragmentStatePagerAdapter {
    List<Fragment> fragments;
    public DownloadManagePagerAdapter(@NonNull List<Fragment> fragments, FragmentManager fm) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

}
