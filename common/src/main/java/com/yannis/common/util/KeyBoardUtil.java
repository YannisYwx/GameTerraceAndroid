package com.yannis.common.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.ref.WeakReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/16  14:35
 * @email : 923080261@qq.com
 * @description : 输入键盘工具类
 */
public class KeyBoardUtil {
    private static KeyBoardUtil instance;
    private static InputMethodManager mInputMethodManager;
    private static WeakReference<Activity> mActivity;
    private static Lock sLock = new ReentrantLock();

    private KeyBoardUtil(Activity activity) {
        mActivity = new WeakReference<Activity>(activity);
        mInputMethodManager = (InputMethodManager) mActivity.get().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    public static KeyBoardUtil getInstance(Activity activity) {
        if (instance == null) {
            sLock.lock();
            if(instance == null){
                instance = new KeyBoardUtil(activity);
            }
            sLock.unlock();
        }
        return instance;
    }

    /**
     * 强制显示输入法
     */
    public void show() {
        Activity activity = mActivity.get();
        if (activity != null) {
            show(activity.getWindow().getCurrentFocus());
        }
    }

    public void show(View view) {
        mInputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 强制关闭输入法
     */
    public void hide() {
        Activity activity = mActivity.get();
        if (activity != null) {
            hide(activity.getWindow().getCurrentFocus());
        }
    }

    public void hide(View view) {
        mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 如果输入法已经显示，那么就隐藏它；如果输入法现在没显示，那么就显示它
     */
    public void showOrHide() {
        mInputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
