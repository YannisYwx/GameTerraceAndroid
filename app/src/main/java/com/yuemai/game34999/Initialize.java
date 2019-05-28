package com.yuemai.game34999;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.yannis.common.util.SPUtils;
import com.yannis.common.util.UIUtils;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/9  14:17
 * @email : 923080261@qq.com
 * @description : app的初始化都在这里
 */
public class Initialize {

    public static void init(Application application){
        /*-------------------------------下载管理--------------------------------------*/

        /*----------------------------init logger-------------------------------------*/
        LogcatLogStrategy logStrategy = new LogcatLogStrategy();

        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                // (Optional) Whether to show thread info or not. Default true
                .showThreadInfo(true)
                // (Optional) How many method line to show. Default 2
                .methodCount(2)
                // (Optional) Hides internal method calls up to offset. Default 5
                .methodOffset(2)
                // (Optional) Changes the log strategy to print out. Default LogCat
                .logStrategy(logStrategy)
                // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .tag("GameApp")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy){

        });
        /*--------------------------------------------------------------------------*/
        UIUtils.init(application);
        SPUtils.init(application);
        //---网络调试工具
        Stetho.initialize(
                Stetho.newInitializerBuilder(application)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(application))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(application))
                        .build());
    }
}
