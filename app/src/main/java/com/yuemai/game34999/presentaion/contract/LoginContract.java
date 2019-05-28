package com.yuemai.game34999.presentaion.contract;

import com.yuemai.game34999.core.mvp.inter.BaseContract;
import com.yuemai.game34999.data.bean.AccountLoginBean;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/21  11:55
 * @email : 923080261@qq.com
 * @description :
 */
public interface LoginContract {

    interface View extends BaseContract.BaseView {

        /**
         * 登录成功
         */
        void loginSuccess();

        /**
         * 登录失败
         */
        void loginFailed();

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        /**
         * 普通登录
         *
         * @param accountLoginBean 账号登录bean
         */
        void doLogin(AccountLoginBean accountLoginBean);

        /**
         * qq登录
         */
        void loginByQQ();

        /**
         * 微信登录
         */
        void loginByWeChat();

        /**
         * 微博登录
         */
        void loginByWeiBo();

       /* *//**
         * 初始化位置信息
         *//*
        void initLocationAddress();*/
    }
}
