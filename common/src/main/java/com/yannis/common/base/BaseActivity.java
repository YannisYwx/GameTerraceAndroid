package com.yannis.common.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.yannis.common.util.ActivityManager;
import com.yannis.common.util.StatusBarCompat;
import com.yannis.common.util.StatusBarUtils;
import com.yannis.common.util.ToastUtils;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/16  17:07
 * @email : 923080261@qq.com
 * @description : Activity基类 统一初始化数据 绑定事件 跳转等方法
 */
public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA = "_extra";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置activity 为竖直方向
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActivityManager.getInstance().popOneActivity(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        init();
        //是否沉浸式状态栏
        if (isSteepStatusBar()) {
            steepStatusBar();
        }
        //确定是否重写跳转动画
        overrideAnimation();

        //默认状态栏 黑色文字
        if (!isWhiteTextStatus()) {
            StatusBarUtils.statusBarLightMode(this);
        }
        if (isLoadLayoutRes()) {
            setContentView(setContentLayout());
        } else {
            setContentView(loadContentView());
        }
        initView(savedInstanceState);
        initData();
        initEvent();
    }

    /**
     * 初始化
     */
    public abstract void init();

    /**
     * 设置布局文件
     *
     * @return
     */
    public abstract @LayoutRes
    int setContentLayout();

    /**
     * 初始化视图
     *
     * @param savedInstanceState
     */
    public void initView(@Nullable Bundle savedInstanceState) {

    }

    /**
     * 初始化绑定事件
     */
    public abstract void initEvent();

    /**
     * 初始化数据
     */
    public abstract void initData();


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

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected void setStatusBarColor(int color){
        StatusBarCompat.setStatusBarColor(this,color);
    }
    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void setImmerseBar(){
        StatusBarCompat.translucentStatusBar(this);
    }

    /**
     * 查找View
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return
     */
    protected <VT extends View> VT getViewById(@IdRes int id) {
        return (VT) findViewById(id);
    }

    /**
     * 跳转到下一个Activity，并且销毁当前Activity
     *
     * @param cls 下一个Activity的Class
     */
    public void forwardAndFinish(Class<? extends Activity> cls) {
        forward(cls);
        finish();
    }


    /**
     * 跳转到下一个Activity，不销毁当前Activity
     *
     * @param cls 下一个Activity的Class
     */
    public void forward(Class<? extends Activity> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (bundle != null) {
            intent.putExtra(EXTRA, bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转到下一个Activity，不销毁当前Activity
     *
     * @param cls 下一个Activity的Class
     */
    public void forward(Class<? extends Activity> cls) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }


    /**
     * 注册点击事件
     *
     * @param views 需要绑定的view
     */
    protected void registerOnClickListener(@NonNull View... views) {
        for (View view : views) {
            view.setOnClickListener(this);
        }
    }


    /**
     * 在主线程弹出一个toast
     *
     * @param msg 内容
     */
    protected void showToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showShortToast(BaseActivity.this, msg);
            }
        });
    }

    /**
     * 在主线程弹出一个toast
     *
     * @param msgRes 内容
     */
    protected void showToast(final @StringRes int msgRes) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showShortToast(BaseActivity.this, msgRes);
            }
        });
    }

    @Override
    protected void onDestroy() {
        ActivityManager.getInstance().popOneActivity(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {

    }

    public boolean isLoadLayoutRes() {
        return true;
    }

    public View loadContentView() {
        return null;
    }

    protected boolean isWhiteTextStatus() {
        return false;
    }

    /**
     * 是否需要沉浸式状态栏 默认有 子类选择性复写
     *
     * @return
     */
    protected boolean isSteepStatusBar() {
        return true;
    }

    /**
     * 重写act切换动画
     */
    private void overrideAnimation() {
        if (overrideActivityAnimation()) {
            //TODO
        }
    }

    protected boolean overrideActivityAnimation() {
        return false;
    }
}
