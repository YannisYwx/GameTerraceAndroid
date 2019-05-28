package com.yannis.common.base.mvp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yannis.common.util.ToastUtils;
import com.yannis.common.widget.LoadingPager;
import com.yannis.common.widget.LoadingPager.LoadingState;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/16  18:51
 * @email : 923080261@qq.com
 * @description : 带有加载页面的fragment
 */
public abstract class BaseLoadPagerFragment extends Fragment {
    public static final String EXTRA = "_extra";

    protected View mRootView;
    protected Context mContext;
    protected AppCompatActivity mAppCompatActivity;
    protected boolean isVisible;
    protected boolean isPrepared = false;
    protected LoadingPager mLoadingPager;
    @LayoutRes
    private int loadingPager;
    @LayoutRes
    private int errorPager;
    @LayoutRes
    private int emptyPager;
    @IdRes
    private int btnRetry;


    public BaseLoadPagerFragment(@LayoutRes int loadingPager, @LayoutRes int errorPager, @LayoutRes int emptyPager, @IdRes int btnRetry) {
        this.loadingPager = loadingPager;
        this.errorPager = errorPager;
        this.emptyPager = emptyPager;
        this.btnRetry = btnRetry;
    }

    public BaseLoadPagerFragment() {

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mAppCompatActivity = (AppCompatActivity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = createView();
            initView(mRootView, savedInstanceState);
            isPrepared = true;
            checkIsVisibleToLoad();
        } else {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (null != parent) {
                parent.removeView(mRootView);
            }
        }
        return mRootView;
    }

    /**
     * 创建视图
     *
     * @return
     */
    public View createView() {
        mLoadingPager = new LoadingPager(mContext) {
            @Override
            protected View initContentView() {
                return BaseLoadPagerFragment.this.initContentView();
            }

            @Override
            protected int loadDataFromCache() {
                return BaseLoadPagerFragment.this.initDataFromCache();
            }

            @Override
            public int loadData() {
                return BaseLoadPagerFragment.this.initData();
            }

            @Override
            protected void refreshContentView(View contentView) {
                BaseLoadPagerFragment.this.refreshContentView(contentView);
            }
        };
        return mLoadingPager;
    }

    /**
     * 初始化内容视图 交由子类完成
     *
     * @return view
     */
    public abstract View initContentView();

    /**
     * 后台加载缓存数据
     *
     * @return
     */
    public abstract
    @LoadingState
    int initDataFromCache();

    /**
     * 后台加载网络数据
     *
     * @return
     */
    public abstract
    @LoadingState
    int initData();

    /**
     * 得到数据后刷新视图
     *
     * @param contentView
     */
    public abstract void refreshContentView(View contentView);


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEvent();
    }

//    /**
//     * 绑定
//     */
//    public abstract void bindMvp();


    /**
     * 初始化视图
     *
     * @param rootView           view
     * @param savedInstanceState savedInstanceState
     */
    public abstract void initView(View rootView, @Nullable Bundle savedInstanceState);


    /**
     * 绑定事件
     */
    public abstract void initEvent();


    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    /**
     * 视图可见时操作
     */
    public void onVisible() {
        checkIsVisibleToLoad();
    }

    /**
     * 视图不可见时操作
     */
    public void onInvisible() {
    }

    /**
     * 只有当绑定了视图和视图可见的时候才去加载数据
     */
    protected void checkIsVisibleToLoad() {
        if (isPrepared && isVisible) {
            lazyLoad();
        }

    }

    /**
     * 懒加载(可见时加载,不可见时隐藏等),在此时填充数据或者更新view
     */
    protected abstract void lazyLoad();


    /**
     * 跳转到下一个Activity，不销毁当前Activity
     *
     * @param cls 下一个Activity的Class
     */
    public void forward(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(mContext, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (bundle != null) {
            intent.putExtra(EXTRA, bundle);
        }
        startActivity(intent);
    }

    /**
     * 跳转到下一个Activity，不销毁当前Activity
     *
     * @param cls 下一个Activity的Class
     */
    public void forward(Class<? extends Activity> cls) {
        Intent intent = new Intent(mContext, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }


    public void showToast(@StringRes int msgId) {
        ToastUtils.showShortToast(mContext, msgId);
    }

    public void showToast(String msg) {
        ToastUtils.showShortToast(mContext, msg);
    }

    /**
     * 显示正在加载的View
     */
    public void showLoadingView(){
        mLoadingPager.showLoadingView();
    }

    /**
     * 显示加载成功的view
     */
    public void showSuccessView(){
        mLoadingPager.showSuccessView();
    }

}
