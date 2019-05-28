package com.yannis.common.util;

import android.content.Context;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/25  10:54
 * @email : 923080261@qq.com
 * @description :
 */
public class UtilInit {

    public static void init(Context context){
        CheckUtil.checkNotNull(context);
        UIUtils.init(context.getApplicationContext());
        ToastUtils.init();
        SPUtils.init(context.getApplicationContext());
    }
}
