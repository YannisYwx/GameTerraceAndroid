package com.yannis.common.base.mvp;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.yannis.common.base.mvp.inter.IMode;
import com.yannis.common.base.mvp.inter.IPresenter;
import com.yannis.common.base.mvp.inter.IView;
import com.yannis.common.util.CheckUtil;


/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/16  18:16
 * @email : 923080261@qq.com
 * @description :
 */
public abstract class BasePresenter<V extends IView, M extends IMode> implements IPresenter {
    private V mView;
    private M mMode;

    /**
     * 初始化presenter
     *
     * @param view 需要绑定的view
     */
    public BasePresenter(@NonNull V view) {
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
}
