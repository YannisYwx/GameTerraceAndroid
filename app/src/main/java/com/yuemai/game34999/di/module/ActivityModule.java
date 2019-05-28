package com.yuemai.game34999.di.module;

import android.app.Activity;

import com.yuemai.game34999.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/12  15:30
 * @email : 923080261@qq.com
 * @description : 提供activity依赖 view层
 */
@Module
public class ActivityModule {
    Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return activity;
    }
}
