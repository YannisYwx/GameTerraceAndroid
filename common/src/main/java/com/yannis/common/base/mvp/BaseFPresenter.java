package com.yannis.common.base.mvp;

import com.yannis.common.base.mvp.inter.IFragmentView;
import com.yannis.common.base.mvp.inter.IMode;
import com.yannis.common.base.mvp.inter.IPresenter;
import com.yannis.common.util.CheckUtil;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/23  17:30
 * @email : 923080261@qq.com
 * @description :
 */
public abstract class BaseFPresenter<V extends IFragmentView, M extends IMode> implements IPresenter {
    protected V mView;
    protected M mMode;

    private BaseFPresenter() {
    }

    public BaseFPresenter(V view) {
        CheckUtil.checkNotNull(view);
        this.mView = view;
        this.mView.setPresenter(this);
        this.mMode = createMode();
    }

    public abstract M createMode();

    @Override
    public void onDetach() {
        mView = null;
        mMode = null;
    }

    public V getView() {
        return mView;
    }

    public M getMode() {
        return mMode;
    }
}
