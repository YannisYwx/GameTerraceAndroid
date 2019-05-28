package com.yannis.common.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/20  16:48
 * @email : 923080261@qq.com
 * @description : 倒计时的TextView
 */
public class CountDownTextView extends android.support.v7.widget.AppCompatTextView implements Runnable {
    private boolean run = false;
    private int time;
    private String format = "%d";

    public CountDownTextView(Context context) {
        super(context);
    }

    public CountDownTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CountDownTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTime(int time) {  //设定初始值
        this.time = time;
    }

    public boolean isRun() {
        return run;
    }

    public void beginRun() {
        this.run = true;
        run();
    }

    public void stopRun() {
        this.run = false;
    }

    public void setTimeFormat(@NonNull String format) {
        this.format = format;
    }

    @Override
    public void run() {
        if (run) {
            computeTime();
            this.setText(String.format(format, time));
            postDelayed(this, 1000);
        } else {
            removeCallbacks(this);
        }
    }

    private void computeTime() {
        time--;
        if (time == 0) {
            stopRun();
        }
    }


}
