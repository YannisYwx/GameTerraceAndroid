package com.yuemai.game34999.presentaion.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.yannis.common.base.mvp.BaseLoadPagerFragment;
import com.yuemai.game34999.R;
import com.yuemai.game34999.core.Constants;
import com.yuemai.game34999.core.mvp.RxPresenter;
import com.yuemai.game34999.core.mvp.inter.BaseContract;
import com.yuemai.game34999.di.component.AppComponent;
import com.yuemai.game34999.di.component.FragmentComponent;
import com.yuemai.game34999.di.module.FragmentModule;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/11/24  16:59
 * @email : 923080261@qq.com
 * @description :
 */
public abstract class BaseListMvpFragment<P extends RxPresenter> extends BaseLoadPagerFragment implements BaseContract.BaseView {
    @BindView(R.id.rv_data_list)
    protected RecyclerView mRecyclerView;
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

    protected View contentView;

    protected LayoutAnimationController controller;

    static {
        loadingPager = Constants.LoadPagerRes.loadingPager;
        errorPager = Constants.LoadPagerRes.errorPager;
        emptyPager = Constants.LoadPagerRes.emptyPager;
        btnRetry = Constants.LoadPagerRes.btnRetry;
    }

    @Override
    public View initContentView() {
        contentView = View.inflate(mContext, R.layout.fragment_list, null);
        return contentView;
    }

    @Override
    public void initView(View rootView, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this, rootView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        controller =
                AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_fall_down);
        mRecyclerView.setLayoutAnimation(controller);

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
    public void onInvisible() {
        super.onInvisible();
    }

    /**
     * 注入目标
     *
     * @param fragmentComponent component
     */
    public abstract void injectThis(FragmentComponent fragmentComponent);

    /**
     * 获取注入组件
     *
     * @return FragmentComponent
     */
    private FragmentComponent getFragmentComponent() {
        return AppComponent.getInstance().addSub(new FragmentModule(this));
    }
}
