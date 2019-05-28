package com.yannis.common.util;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.yannis.common.R;

import static android.R.id.message;


public class ToastUtils {
    private static Toast mToast;

    private static Handler sHandler = new Handler(Looper.getMainLooper());

    private static Runnable r = new Runnable() {
        public void run() {
            mToast.cancel();
            mToast = null;//toast隐藏后，将其置为null
        }
    };

    /**
     * 展示一个自定义的toast
     *
     * @param context 上下文
     * @param msg     消息
     * @return toast
     */
    public static Toast showCustomToast(Context context, String msg, @DrawableRes int res) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            mToast.getView().setBackgroundResource(res);
            TextView v = mToast.getView().findViewById(message);
            v.setTextColor(Color.BLACK);
            mToast.setGravity(Gravity.CENTER, 0, 0);
        }
        mToast.setText(msg);
        mToast.show();
        return mToast;
    }

    public static Toast showCustomToast(Context context, String msg) {
        return showCustomToast(context, msg, R.drawable.toast_black_background);
    }

    /**
     * 展示一个不会重复弹窗的toast
     *
     * @param context    上下文
     * @param message    消息
     * @param showLength 展示时长
     */
    public static void showToast(Context context, String message, int showLength) {
        sHandler.removeCallbacks(r);
        if (mToast == null) {//只有mToast==null时才重新创建，否则只需更改提示文字
            mToast = Toast.makeText(context, message, showLength);
            mToast.setGravity(Gravity.BOTTOM, 0, 150);
        }
        mToast.setText(message);
        sHandler.postDelayed(r, 1000);//延迟1秒隐藏toast
        mToast.show();
    }

    public static void showShortToast(Context context, String message) {
        showToast(context, message, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(Context context, String message) {
        showToast(context, message, Toast.LENGTH_LONG);
    }

    /**
     * 展示一个不会重复弹窗的toast
     *
     * @param context    上下文
     * @param resId     消息
     * @param showLength 展示时长
     */
    public static void showToast(Context context, @StringRes int resId, int showLength) {
        sHandler.removeCallbacks(r);
        if (mToast == null) {//只有mToast==null时才重新创建，否则只需更改提示文字
            mToast = Toast.makeText(context, resId, showLength);
            mToast.setGravity(Gravity.BOTTOM, 0, 150);
        }
        mToast.setText(resId);
        sHandler.postDelayed(r, 1000);//延迟1秒隐藏toast
        mToast.show();
    }

    public static void showShortToast(Context context, @StringRes int resId) {
        showToast(context, resId, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(Context context, @StringRes int resId) {
        showToast(context, resId, Toast.LENGTH_LONG);
    }

    public static void init(){

    }

}
