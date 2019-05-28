package com.yuemai.game34999;

import android.app.Application;
import android.os.Environment;

import com.umeng.socialize.PlatformConfig;
import com.yannis.common.util.ACache;
import com.yuemai.game34999.core.Constants;
import com.yuemai.game34999.di.component.AppComponent;
import com.yuemai.game34999.presentaion.download.DownloadManager;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/23  19:27
 * @email : 923080261@qq.com
 * @description :
 */
public class GameApp extends Application {
    String locationAddress;

    static {
        PlatformConfig.setWeixin("11111", "1111");
        PlatformConfig.setQQZone("1111", "1111");
        PlatformConfig.setSinaWeibo("1111", "1111", "11111");
    }

    public static GameApp instance;
    private static AppComponent sAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        Initialize.init(this);
        instance = this;
        locationAddress = ACache.get(this).getAsString("LocationAddress");
        DownloadManager.getInstance().setFolder(Environment.getExternalStorageDirectory().getAbsolutePath() + Constants.DOWNLOAD_FOLDER);
        DownloadManager.getInstance().getThreadPool().setCorePoolSize(3);
        //从数据库中恢复所有任务
        DownloadManager.restoreAllTask();

    }

    public static GameApp getInstance() {
        return instance;
    }




}
