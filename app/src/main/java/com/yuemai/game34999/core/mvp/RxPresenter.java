package com.yuemai.game34999.core.mvp;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.yuemai.game34999.core.mvp.inter.BaseContract;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/12  18:43
 * @email : 923080261@qq.com
 * @description :基于Rx的Presenter封装,控制订阅的生命周期
 * unSubscribe() 这个方法很重要，
 * 因为在 subscribe() 之后， Observable 会持有 Subscriber 的引用，
 * 这个引用如果不能及时被释放，将有内存泄露的风险。
 */
public class RxPresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter<T> {
    /**
     * UI 视图逻辑
     */
    protected T mView;

    protected CompositeDisposable mCompositeDisposable;


    /**
     * 获取绑定的act
     *
     * @return act or null
     */
    public Activity getActivity() {
        if (mView instanceof Activity) {
            return (Activity) mView;
        }
        if (mView instanceof Fragment) {
            return ((Fragment) mView).getActivity();
        }
        return null;
    }

    /**
     * 切断所有操作 防止退出界面继续操作UI
     */
    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    /**
     * 加入队列
     *
     * @param subscription
     */
    protected void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    @Override
    public void onAttachView(T view) {
        this.mView = view;
    }

    @Override
    public void onDetachView() {
        unSubscribe();
        this.mView = null;
    }
}
