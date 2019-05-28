package com.yannis.common;

import android.app.Application;
import android.os.Environment;

import com.yannis.common.util.UIUtils;

import java.io.File;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/18  16:09
 * @email : 923080261@qq.com
 * @description :
 */
public class YannisApp extends Application{

    private static YannisApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        UIUtils.init(this);
    }

    public static YannisApp getInstance(){
        return instance;
    }

    @Override
    public File getCacheDir() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File cacheDir = getExternalCacheDir();
            if (cacheDir != null && (cacheDir.exists() || cacheDir.mkdirs())) {
                return cacheDir;
            }
        }
        return super.getCacheDir();
    }
}
