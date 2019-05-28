package com.yuemai.game34999.core.mvp.inter;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/13  15:22
 * @email : 923080261@qq.com
 * @description : MVP底层接口
 */
public interface BaseContract {
    /**
     * View的基类接口
     */
    interface BaseView {

        /**
         * 数据加载完成的回调
         */
        void onComplete();

        /**
         * 提示信息
         *
         * @param msg
         */
        void showMsg(String msg);
    }

    /**
     * Presenter的基类接口
     *
     * @param <T>
     */
    interface BasePresenter<T> {

        /**
         * 绑定视图层
         *
         * @param view UI
         */
        void onAttachView(T view);

        /**
         * 解绑视图层
         */
        void onDetachView();
    }

    /**
     * Mode层基类接口
     */
    interface BaseMode {

        /**
         * 开始加载数据
         */
        void startLoad();

        /**
         * 加载成功
         */
        void loadSuccess();

        /**
         * 加载失败
         */
        void loadFailed();
    }
}
