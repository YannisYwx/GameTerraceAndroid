package com.yuemai.game34999.presentaion.ui.game.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.yannis.common.util.UIUtils;
import com.yuemai.game34999.R;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/25  14:45
 * @email : 923080261@qq.com
 * @description :
 */
public class GameInfoPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mList;
    private CharSequence[] mTitles;

    public GameInfoPagerAdapter(List<Fragment> list, FragmentManager fm) {
        super(fm);
        mList = list;
        mTitles = UIUtils.getContext().getResources().getStringArray(R.array.gameZoom);
    }

    @Override
    public Fragment getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    /**
     * 需要覆写getPageTitle
     * @param position
     * @return
     */

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        try {
            super.finishUpdate(container);
        } catch (NullPointerException nullPointerException) {
        }
    }
}
