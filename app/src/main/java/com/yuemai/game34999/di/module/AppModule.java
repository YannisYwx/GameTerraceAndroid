package com.yuemai.game34999.di.module;


import com.yuemai.game34999.GameApp;
import com.yuemai.game34999.data.DataManager;
import com.yuemai.game34999.data.db.DBHelper;
import com.yuemai.game34999.data.db.RealmHelper;
import com.yuemai.game34999.data.http.HttpHelper;
import com.yuemai.game34999.data.http.RetrofitHelper;
import com.yuemai.game34999.data.prefs.AppPreferencesHelper;
import com.yuemai.game34999.data.prefs.PreferencesHelper;
import com.yuemai.game34999.di.qualifier.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/11  17:59
 * @email : 923080261@qq.com
 * @description :依赖提供 负责app网络请求、数据库等依赖提供
 */
@Module
public class AppModule {
    private final GameApp application;

    public AppModule(GameApp application) {
        this.application = application;
    }

    /**
     * 提供全局上下文依赖
     *
     * @return Context
     */
    @Singleton
    @Provides
    @ApplicationContext
    GameApp provideApplicationContext() {
        return application;
    }

    /**
     * 提供全局的网络操作依赖
     *
     * @param retrofitHelper HttpHelper
     * @return HttpHelper
     */
    @Singleton
    @Provides
    HttpHelper provideHttpHelper(RetrofitHelper retrofitHelper) {
        return retrofitHelper;
    }

    /**
     * 提供全局的数据库操作依赖
     *
     * @param realmHelper DBHelper
     * @return DBHelper
     */
    @Singleton
    @Provides
    DBHelper provideDBHelper(RealmHelper realmHelper) {
        return realmHelper;
    }

    /**
     * 提供全局的PreferencesHelper依赖
     *
     * @param appPreferencesHelper PreferencesHelper
     * @return PreferencesHelper
     */
    @Singleton
    @Provides
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    /**
     * 提供一个全局的数据中心操作依赖
     * @param preferencesHelper Preferences
     * @param dbHelper 数据库
     * @param httpHelper 网络
     * @return 数据中心
     */
    @Singleton
    @Provides
    DataManager provideDataManager(PreferencesHelper preferencesHelper, DBHelper dbHelper, HttpHelper httpHelper) {
        return new DataManager(preferencesHelper, dbHelper, httpHelper);
    }

}
