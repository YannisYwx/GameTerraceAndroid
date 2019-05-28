package com.yuemai.game34999.presentaion.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/*
 *  @项目名：  RenRenLeXiang
 *  @创建者:   liucong
 *  @创建时间:  2017/4/27 12:47
 *  @邮箱：    liucong2012and@163.com 
 *  @描述：    
 */
public class MyScrollView extends ScrollView {
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(onSizeChangedListener!=null){
            onSizeChangedListener.sizeChanged(h);
        }
    }
    private OnSizeChangedListener onSizeChangedListener;

    public interface OnSizeChangedListener{
        void sizeChanged(int h);
    }
    public void setOnSizeChangedListener(OnSizeChangedListener onSizeChangedListener){
        this.onSizeChangedListener = onSizeChangedListener;
    }
}
