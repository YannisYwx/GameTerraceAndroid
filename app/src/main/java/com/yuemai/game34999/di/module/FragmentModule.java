package com.yuemai.game34999.di.module;

import android.support.v4.app.Fragment;

import com.yuemai.game34999.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/12  15:30
 * @email : 923080261@qq.com
 * @description : 提供fragment依赖 view层
 */
@Module
public class FragmentModule {
    Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    Fragment provideFragment() {
        return fragment;
    }
}
