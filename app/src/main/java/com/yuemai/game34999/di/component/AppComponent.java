package com.yuemai.game34999.di.component;

import com.yuemai.game34999.GameApp;
import com.yuemai.game34999.di.module.ActivityModule;
import com.yuemai.game34999.di.module.AppModule;
import com.yuemai.game34999.di.module.FragmentModule;
import com.yuemai.game34999.di.module.HttpModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/11  17:10
 * @email : 923080261@qq.com
 * @description :提供全局的管理
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public abstract class AppComponent {
    private static AppComponent instance;

    public abstract ActivityComponent addSub(ActivityModule activityModule);

    public abstract FragmentComponent addSub(FragmentModule fragmentModule);

    /**
     * 获取依赖组件单例
     *
     * @return
     */
    public static AppComponent getInstance() {
        if (instance == null) {
            instance = DaggerAppComponent.builder().appModule(new AppModule(GameApp.getInstance())).httpModule(new HttpModule()).build();
        }
        return instance;
    }

//    /**
//     * 提供全局的上下文
//     *
//     * @return context
//     */
//    GameApp getContext();
//
//    /**
//     * 对其他依赖的Component提供数据中心
//     *
//     * @return DataManager
//     */
//    DataManager getDataManager();
//
//    /**
//     * 对其他依赖的Component提供网络操作
//     *
//     * @return RetrofitHelper
//     */
//    RetrofitHelper retrofitHelper();
//
//    /**
//     * 对其他依赖的Component提供数据库操作
//     *
//     * @return RealmHelper
//     */
//    RealmHelper realmHelper();
//
//    /**
//     * 对其他依赖的Component提供Preferences操作
//     *
//     * @return AppPreferencesHelper
//     */
//    AppPreferencesHelper gamePreferencesHelper();


}
