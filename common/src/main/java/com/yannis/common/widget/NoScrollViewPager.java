package com.yannis.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/23  10:27
 * @email : 923080261@qq.com
 * @description : 禁止滑动的viewpager
 */
public class NoScrollViewPager extends LazyViewPager{
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
