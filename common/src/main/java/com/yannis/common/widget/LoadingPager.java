package com.yannis.common.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.yannis.common.R;
import com.yannis.common.thread.ThreadPoolManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

import static com.yannis.common.widget.LoadingPager.LoadingState.stateCacheEmpty;
import static com.yannis.common.widget.LoadingPager.LoadingState.stateCacheSuccess;
import static com.yannis.common.widget.LoadingPager.LoadingState.stateEmpty;
import static com.yannis.common.widget.LoadingPager.LoadingState.stateError;
import static com.yannis.common.widget.LoadingPager.LoadingState.stateLoading;
import static com.yannis.common.widget.LoadingPager.LoadingState.stateSuccess;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/21  17:24
 * @email : 923080261@qq.com
 * @description : 加载页面
 */
public abstract class LoadingPager extends FrameLayout {

    public static int[] IDS = {
            R.drawable.num1, R.drawable.num2, R.drawable.num3, R.drawable.num4, R.drawable.num5
    };

    @LayoutRes
    private int loadingPager;
    @LayoutRes
    private int errorPager;
    @LayoutRes
    private int emptyPager;
    @IdRes
    private int btnRetry;

    /**
     * 加载中
     */
    private View mErrorView;
    /**
     * 错误的布局
     */
    private View mEmptyView;
    /**
     * 空数据视图
     */
    private View mContentView;
    /**
     * 内容视图
     */
    private View mLoadingView;

    private boolean cacheIsEmpty = true;

    private LoadDataTask mLoadDataTask = null;

    private GetDataFromCacheTask mCacheTask = null;

    @LoadingPager.LoadingState
    private int mCurrentState = stateLoading;

    @IntDef({stateLoading, stateError, stateSuccess, stateEmpty, stateCacheEmpty, stateCacheSuccess})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoadingState {
        /**
         * 加载中
         */
        int stateLoading = 0;
        /**
         * 成功
         */
        int stateError = 1;
        /**
         * 成功
         */
        int stateSuccess = 2;
        /**
         * 空（没有数据）
         */
        int stateEmpty = 3;
        /**
         * 缓存中没有数据
         */
        int stateCacheEmpty = 4;
        /**
         * 缓存中有数据
         */
        int stateCacheSuccess = 5;
    }

    public LoadingPager(@NonNull Context context) {
        super(context);
        loadingPager = R.layout.pager_loading;
        errorPager = R.layout.pager_error;
        emptyPager = R.layout.pager_empty;
        btnRetry = R.id.btn_error_retry;
        initLoadingPager(context);
    }

    public LoadingPager(@NonNull Context context, @LayoutRes int loadingPager, @LayoutRes int errorPager, @LayoutRes int emptyPager, @IdRes int btnRetry) {
        this(context, null, loadingPager, errorPager, emptyPager, btnRetry);
    }

    public LoadingPager(@NonNull Context context, @Nullable AttributeSet attrs, @LayoutRes int loadingPager, @LayoutRes int errorPager, @LayoutRes int emptyPager, @IdRes int btnRetry) {
        this(context, attrs, 0, loadingPager, errorPager, emptyPager, btnRetry);
    }

    public LoadingPager(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @LayoutRes int loadingPager, @LayoutRes int errorPager, @LayoutRes int emptyPager, @IdRes int btnRetry) {
        super(context, attrs, defStyleAttr);
        this.loadingPager = loadingPager;
        this.errorPager = errorPager;
        this.emptyPager = emptyPager;
        this.btnRetry = btnRetry;
        initLoadingPager(context);
    }

    /**
     * 初始化控件
     *
     * @param context 上下文
     */
    public void initLoadingPager(@NonNull Context context) {
        //1.初始化3种 常规视图
        mLoadingView = View.inflate(context, loadingPager, null);
        mEmptyView = View.inflate(context, emptyPager, null);
        mErrorView = View.inflate(context, errorPager, null);
        GifImageView gifImageView = mLoadingView.findViewById(R.id.gifImageView);
        gifImageView.setImageResource(IDS[new Random().nextInt(IDS.length)]);
        /*添加空内容视图*/
        if (mEmptyView != null) {
            addView(mEmptyView, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
        }
        /*添加错误视图*/
        if (mErrorView != null) {
            addView(mErrorView, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
            try {
                assert mEmptyView != null;
                mErrorView.findViewById(btnRetry).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        triggerLoadData();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /*添加加载中视图*/
        if (mLoadingView != null) {
            addView(mLoadingView, new LayoutParams(LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT));
        }
        //2.初始化内容布局
        mContentView = initContentView();
        addView(mContentView, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        //3.隐藏3种常规视图
        refreshUIByState();

        //4.从缓存中读取数据
        getDataFromCache();
    }

    public void showLoadingView(){
        mCurrentState = stateLoading;
        refreshUIByState();
    }

    public void showSuccessView(){
        mCurrentState = stateSuccess;
        refreshUIByState();
    }

    public void showEmptyView(){
        mCurrentState = stateEmpty;
        refreshUIByState();
    }

    public boolean isCacheIsEmpty() {
        return cacheIsEmpty;
    }

    public void refreshUIByState() {
        //同一时刻,只能对外提供一个视图
        mLoadingView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mContentView.setVisibility(View.GONE);
        switch (mCurrentState) {
            case LoadingState.stateLoading:
                mLoadingView.setVisibility(VISIBLE);
                break;
            case LoadingState.stateError:
                mErrorView.setVisibility(VISIBLE);
                break;
            case LoadingState.stateEmpty:
                mEmptyView.setVisibility(VISIBLE);
                break;
            case LoadingState.stateSuccess:
                mContentView.setVisibility(VISIBLE);
                refreshContentView(mContentView);
                break;
            case LoadingState.stateCacheEmpty:
                //缓存中数据为空
                cacheIsEmpty = true;
                triggerLoadData();
                break;
            case LoadingState.stateCacheSuccess:
                //缓存中有数据  ->添加成功视图 ->初始化界面
                cacheIsEmpty = false;
                mContentView.setVisibility(VISIBLE);
                refreshContentView(mContentView);
                triggerLoadData();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化内容视图 交由子类完成
     *
     * @return view
     */
    @UiThread
    protected abstract View initContentView();

    /**
     * 从缓存中加载数据 子线程工作
     *
     * @return 加载状态
     */
    @WorkerThread
    protected abstract
    @LoadingState
    int loadDataFromCache();

    /**
     * 加载数据
     *
     * @return 加载状态
     */
    @WorkerThread
    public abstract
    @LoadingState
    int loadData();

    /**
     * 加载数据成功刷新主视图
     *
     * @param contentView 主视图
     */
    protected abstract void refreshContentView(View contentView);

    public void triggerLoadData() {
        boolean isLoadData = (mCurrentState == LoadingState.stateError && mLoadDataTask == null) ||
                (mCurrentState == LoadingState.stateCacheEmpty && mLoadDataTask == null);
        if (isLoadData) {
            // 缓存中没有数据 or 加载错误
            //异步加载前,重置状态
            mCurrentState = stateLoading;
            refreshUIByState();
            //异步加载
            mLoadDataTask = new LoadDataTask();
            ThreadPoolManager.getInstance().execute(mLoadDataTask);
        } else if (mCurrentState == stateCacheSuccess && mLoadDataTask == null) {
            //缓存中有数据
            //异步加载
            mLoadDataTask = new LoadDataTask();
            ThreadPoolManager.getInstance().execute(mLoadDataTask);
        }
    }

    private void getDataFromCache() {
        mCacheTask = new LoadingPager.GetDataFromCacheTask();
        ThreadPoolManager.getInstance().execute(mCacheTask);
    }

    /**
     * 加载数据任务
     */
    private class LoadDataTask implements Runnable {

        @Override
        public void run() {
            //在子线程中,得到具体数据
            mCurrentState = loadData();
            ThreadPoolManager.getInstance().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //刷新ui-->mCurState-->refreshUIByState()方法
                    refreshUIByState();
                }
            });
            //走到run方法体的最后相当于任务执行完成了.置空任务
            mLoadDataTask = null;
        }
    }

    private class GetDataFromCacheTask implements Runnable {
        @Override
        public void run() {
            //处理数据
            mCurrentState = loadDataFromCache();
            ThreadPoolManager.getInstance().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    refreshUIByState();
                }
            });
            mCacheTask = null;
        }
    }
}
