package com.yuemai.game34999.presentaion.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.yannis.common.base.BaseLoadPagerActivity;
import com.yannis.common.rx.RxBus;
import com.yuemai.game34999.core.mvp.RxPresenter;
import com.yuemai.game34999.core.mvp.inter.BaseContract;
import com.yuemai.game34999.di.component.ActivityComponent;
import com.yuemai.game34999.di.component.AppComponent;
import com.yuemai.game34999.di.module.ActivityModule;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/11/23  14:49
 * @email : 923080261@qq.com
 * @description :
 */
public abstract class AbstractMvpLoadPagerActivity<P extends RxPresenter> extends BaseLoadPagerActivity implements BaseContract.BaseView {
    @Inject
    protected P presenter;
    private Unbinder mUnbinder;
    private Disposable disposable;

    @Override
    public void init() {
        bindMVP();
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        unbindMVP();
        mUnbinder.unbind();
        if (disposable != null) {
            RxBus.getInstance().unregister(disposable);
        }
        super.onDestroy();
    }


    /**
     * 绑定presenter
     */
    private void bindMVP() {
        injectThis(getActivityComponent());
        if (presenter != null) {
            presenter.onAttachView(this);
        }
    }

    /**
     * 解绑presenter
     */
    private void unbindMVP() {
        if (presenter != null) {
            presenter.onDetachView();
            presenter = null;
        }
    }

    public ActivityComponent getActivityComponent(){
        return AppComponent.getInstance().addSub(new ActivityModule(this));
    }

    public abstract void injectThis(ActivityComponent component);

}
