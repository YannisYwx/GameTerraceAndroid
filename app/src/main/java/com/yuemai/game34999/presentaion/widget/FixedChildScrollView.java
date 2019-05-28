package com.yuemai.game34999.presentaion.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/11  14:41
 * @email : 923080261@qq.com
 * @description :设置子节点view FIXED_TAG设置其固定位置
 */
public class FixedChildScrollView extends ScrollView {
    private static final String FIXED_TAG = "fixed";

    public FixedChildScrollView(Context context) {
        super(context);
    }

    public FixedChildScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedChildScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    View view;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(changed){
            ViewGroup v = (ViewGroup) getChildAt(0);
            if(v != null){
                for(int i=0;i<v.getChildCount();i++){
                    if(v.getChildAt(i).getTag() != null && v.getChildAt(i).getTag().equals(FIXED_TAG)){
                        view = v.getChildAt(i);
                        break;
                    }
                }
            }
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if(getScrollY() >= view.getTop()){
            fixHead();
//            canvas.save();
//            canvas.translate(0,getScrollY());
//            canvas.clipRect(0,0,view.getWidth(),view.getHeight());
//            view.draw(canvas);
//            canvas.restore();
        }else {
            resetHead();
        }
    }




    private OnFixHeadListener listener;

    private void fixHead() {
        if (listener != null) {
            listener.onFix();
        }
    }

    private void resetHead() {
        if (listener != null) {
            listener.onReset();
        }
    }

    public void setFixHeadListener(OnFixHeadListener listener) {
        this.listener = listener;
    }

    public interface OnFixHeadListener {
        void onFix();
        void onReset();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }
}
