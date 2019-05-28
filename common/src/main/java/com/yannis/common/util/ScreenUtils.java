package com.yannis.common.util;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/16  14:45
 * @email : 923080261@qq.com
 * @description : 屏幕信息工具类
 * 获取屏幕宽高 以及截屏
 */
public class ScreenUtils {

    private ScreenUtils(){

    }

    /**
     * 获取屏幕高度
     * @param activity 当前屏幕
     * @return 高度值
     */
    public static int  getScreenHeight(Activity activity){
        DisplayMetrics displayMetrics = getDisplayMetrics(activity);
        return displayMetrics.heightPixels;
    }


    /**
     * 获取屏幕宽
     * @param activity 当前屏幕
     * @return 宽值
     */
    public static int  getScreenWidth(Activity activity){
        DisplayMetrics displayMetrics = getDisplayMetrics(activity);
        return displayMetrics.widthPixels;
    }


    public static DisplayMetrics getDisplayMetrics(Activity activity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }
}
