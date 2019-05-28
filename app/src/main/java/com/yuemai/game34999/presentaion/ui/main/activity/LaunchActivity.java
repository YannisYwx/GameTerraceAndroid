package com.yuemai.game34999.presentaion.ui.main.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.yannis.common.util.ActivityManager;
import com.yannis.common.util.ViewUtils;
import com.yannis.common.widget.CountDownTextView;
import com.yuemai.game34999.R;
import com.yuemai.game34999.data.prefs.AppPreferencesHelper;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/19  17:11
 * @email : 923080261@qq.com
 * @description : 启动页面
 */
public class LaunchActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().popOneActivity(this);
        steepStatusBar();
        RelativeLayout rootView = new RelativeLayout(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.setLayoutParams(params);
        rootView.setBackgroundResource(R.drawable.start_page);
        CountDownTextView countDownTextView = new CountDownTextView(this);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(ViewUtils.dip2px(this, 60), ViewUtils.dip2px(this, 25));
        layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layoutParams1.setMargins(0, ViewUtils.dip2px(this, 30), ViewUtils.dip2px(this, 20), 0);
        countDownTextView.setTimeFormat("%ds跳过");
        countDownTextView.setLayoutParams(layoutParams1);
        countDownTextView.setTextColor(Color.WHITE);
        countDownTextView.setGravity(Gravity.CENTER);
        countDownTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        countDownTextView.setBackgroundResource(R.drawable.launch_btn);
        countDownTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        rootView.addView(countDownTextView);
        setContentView(rootView);
        countDownTextView.setTime(4);
        countDownTextView.beginRun();
        Observable.timer(3, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(aLong -> toNext());
        countDownTextView.setOnClickListener(v -> toNext());
    }

    private void toNext() {
        if (AppPreferencesHelper.getInstance().isFirstIn()) {
//            TestActivity.start(this);
            GuideActivity.start(this);
        } else {
            MainActivity.start(this);
        }

        finish();
    }

    /**
     * 沉浸状态栏
     */
    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(Color.TRANSPARENT);
                window.setNavigationBarColor(Color.TRANSPARENT);
            }

        }
    }

    @Override
    protected void onDestroy() {
        ActivityManager.getInstance().popOneActivity(this);
        super.onDestroy();
    }
}
