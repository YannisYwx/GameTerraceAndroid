package com.yuemai.game34999.di.component;

import com.yuemai.game34999.di.module.ActivityModule;
import com.yuemai.game34999.di.scope.ActivityScope;
import com.yuemai.game34999.presentaion.ui.game.activity.GameInfoActivity;
import com.yuemai.game34999.presentaion.ui.game.activity.UserCommentActivity;
import com.yuemai.game34999.presentaion.ui.main.activity.LoginActivity;
import com.yuemai.game34999.presentaion.ui.news.activity.ArticleActivity;
import com.yuemai.game34999.presentaion.ui.test.download.TestDownloadActivity;
import com.yuemai.game34999.presentaion.ui.test.download.TestDownloadXiaoMiActivity;
import com.yuemai.game34999.presentaion.ui.test.inter.TestNetInterfaceActivity;

import dagger.Subcomponent;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/12  14:57
 * @email : 923080261@qq.com
 * @description : 提供注入target
 */
@ActivityScope
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {

    void inject(GameInfoActivity gameInfoActivity);

    void inject(UserCommentActivity userCommentActivity);

    void inject(LoginActivity loginActivity);

    void inject(ArticleActivity articleActivity);



    void inject(TestNetInterfaceActivity interfaceActivity);

    void inject(TestDownloadActivity downloadActivity);

    void inject(TestDownloadXiaoMiActivity downloadActivity);

}
