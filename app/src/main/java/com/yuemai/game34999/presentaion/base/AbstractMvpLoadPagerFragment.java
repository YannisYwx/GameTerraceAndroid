package com.yuemai.game34999.presentaion.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yannis.common.base.mvp.BaseLoadPagerFragment;
import com.yuemai.game34999.core.Constants;
import com.yuemai.game34999.core.mvp.RxPresenter;
import com.yuemai.game34999.core.mvp.inter.BaseContract;
import com.yuemai.game34999.di.component.AppComponent;
import com.yuemai.game34999.di.component.FragmentComponent;
import com.yuemai.game34999.di.module.FragmentModule;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/11/24  16:59
 * @email : 923080261@qq.com
 * @description :
 */
public abstract class AbstractMvpLoadPagerFragment<P extends RxPresenter> extends BaseLoadPagerFragment implements BaseContract.BaseView {
    @Inject
    protected P presenter;
    @LayoutRes
    static int loadingPager;
    @LayoutRes
    static int errorPager;
    @LayoutRes
    static int emptyPager;
    @IdRes
    static int btnRetry;

    private Unbinder mUnbinder;

    static {
        loadingPager = Constants.LoadPagerRes.loadingPager;
        errorPager = Constants.LoadPagerRes.errorPager;
        emptyPager = Constants.LoadPagerRes.emptyPager;
        emptyPager = Constants.LoadPagerRes.emptyPager;
        btnRetry = Constants.LoadPagerRes.btnRetry;
    }

    public AbstractMvpLoadPagerFragment() {
        super(loadingPager, errorPager, emptyPager, btnRetry);
    }

    @Override
    public void initView(View rootView, @Nullable Bundle savedInstanceState) {
        mUnbinder = ButterKnife.bind(this, rootView);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //开始注入目标
        injectThis(getFragmentComponent());
        if (presenter != null) {
            presenter.onAttachView(this);
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (presenter != null) {
            presenter.onDetachView();
        }
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * 注入目标
     * @param fragmentComponent
     */
    public abstract void injectThis(FragmentComponent fragmentComponent);

    /**
     * 获取注入组件
     * @return FragmentComponent
     */
    private FragmentComponent getFragmentComponent() {
        return AppComponent.getInstance().addSub(new FragmentModule(this));
    }
}
