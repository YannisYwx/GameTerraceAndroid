package com.yannis.common.widget.banner.view;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/26  18:27
 * @email : 923080261@qq.com
 * @description :
 */
public abstract class LoopViewPagerAdapter<T> extends PagerAdapter implements ViewPager.OnPageChangeListener {
    private int currentPosition = 0;
    private ViewPager mViewPager;
    private ArrayList<View> views;

    public LoopViewPagerAdapter(ArrayList<T> datas, ViewPager viewPager) {
        views = new ArrayList<>();
        //如果数据大于一条
        if (datas.size() > 1) {
            //添加最后一页到第一页
            datas.add(0, datas.get(datas.size() - 1));
            //添加第一页(经过上行的添加已经是第二页了)到最后一页
            datas.add(datas.get(1));
        }
        for (int i = 0; i < datas.size(); i++) {
            T data = datas.get(i);
            views.add(getItemView(i, data));
        }
        mViewPager = viewPager;
        viewPager.setAdapter(this);
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(1, false);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    protected abstract View getItemView(int position, T data);

    protected abstract void onLoopPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    protected abstract void onLoopPageSelected(int position);

    protected abstract void onLoopPageScrollStateChanged(int state);

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        this.onLoopPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
        this.onLoopPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        this.onLoopPageScrollStateChanged(state);
        //若viewpager滑动未停止，直接返回
        if (state != ViewPager.SCROLL_STATE_IDLE) return;
        //若当前为第一张，设置页面为倒数第二张
        if (currentPosition == 0) {
            mViewPager.setCurrentItem(views.size() - 2, false);
        } else if (currentPosition == views.size() - 1) {
            //若当前为倒数第一张，设置页面为第二张
            mViewPager.setCurrentItem(1, false);
        }
    }


}
