package com.yannis.common.base.mvp;


import com.yannis.common.base.BaseActivity;
import com.yannis.common.base.mvp.inter.IPresenter;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/16  18:15
 * @email : 923080261@qq.com
 * @description :
 */
public abstract class BaseMvpActivity<P extends IPresenter> extends BaseActivity {
    protected P presenter;

    @Override
    public void init() {
        bindMVP();
    }


    @Override
    protected void onDestroy() {
        unbindMVP();
        super.onDestroy();
    }

    /**
     * 绑定presenter
     */
    private void bindMVP() {
        if (presenter == null) {
            presenter = createPresenter();
        }
        presenter.onAttach();
    }

    /**
     * 解绑presenter
     */
    private void unbindMVP() {
        if (presenter != null) {
            presenter.onDetach();
            presenter = null;
        }
    }

    /**
     * 创建Presenter
     * @return p
     */
    public abstract P createPresenter();
}
