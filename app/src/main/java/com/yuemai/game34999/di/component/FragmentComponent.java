package com.yuemai.game34999.di.component;

import com.yuemai.game34999.di.module.FragmentModule;
import com.yuemai.game34999.di.scope.FragmentScope;
import com.yuemai.game34999.presentaion.ui.classify.fragment.ClassifyFragment;
import com.yuemai.game34999.presentaion.ui.classify.fragment.GameListFragment;
import com.yuemai.game34999.presentaion.ui.game.fragment.DownloadManageFragment;
import com.yuemai.game34999.presentaion.ui.home.fragment.HomeFragment;
import com.yuemai.game34999.presentaion.ui.news.fragment.NewsFragment;
import com.yuemai.game34999.presentaion.ui.news.fragment.NewsListFragment;

import dagger.Subcomponent;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/12  14:57
 * @email : 923080261@qq.com
 * @description :
 */
@FragmentScope
@Subcomponent(modules = {FragmentModule.class})
public interface FragmentComponent {

    void inject(HomeFragment homeFragment);

    void inject(ClassifyFragment classifyFragment);

    void inject(GameListFragment gameListFragment);

    void inject(DownloadManageFragment downloadManageFragment);

    void inject(NewsListFragment newsListFragment);

    void inject(NewsFragment newsFragment);

}