package com.yannis.common.base.mvp;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.yannis.common.base.mvp.inter.IMode;
import com.yannis.common.base.mvp.inter.IPresenter;
import com.yannis.common.base.mvp.inter.IView;
import com.yannis.common.util.CheckUtil;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/7  16:34
 * @email : 923080261@qq.com
 * @description :
 */
public abstract class RxPresenter<V extends IView, M extends IMode> implements IPresenter {
    /**
     * UI 视图逻辑
     */
    private V mView;
    /**
     * Data 数据逻辑
     */
    private M mMode;

    protected CompositeDisposable mCompositeDisposable;

    /**
     * 初始化presenter
     *
     * @param view 需要绑定的view
     */
    public RxPresenter(@NonNull V view) {
        CheckUtil.checkNotNull(view);
        this.mView = view;
        this.mMode = createMode();
    }

    /**
     * 创建mode
     *
     * @return
     */
    public abstract M createMode();

    @Override
    public void onDetach() {
        unSubscribe();
        this.mView = null;
    }

    public V getView() {
        return mView;
    }

    public M getMode() {
        return mMode;
    }

    /**
     * 获取绑定的act
     *
     * @return act or null
     */
    public Activity getActivity() {
        if (mView instanceof Activity) {
            return (Activity) mView;
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

}
