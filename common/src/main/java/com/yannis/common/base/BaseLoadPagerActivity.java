package com.yannis.common.base;

import android.support.annotation.NonNull;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.yannis.common.R;
import com.yannis.common.widget.LoadingPager;
import com.yannis.common.widget.TitleBar;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/25  19:59
 * @email : 923080261@qq.com
 * @description :
 */
public abstract class BaseLoadPagerActivity extends BaseActivity implements TitleBar.OnEventTriggerListener {
    protected TitleBar mTitleBar;

    /**
     * 主视图
     */
    protected View contentView;

    private LoadingPager mLoadingPager;

    public View getContentView() {
        return contentView;
    }

    @Override
    public void onEventTrigger(@TitleBar.Event int type) {
        switch (type) {
            case TitleBar.Event.buttonLeft:

                break;
            case TitleBar.Event.buttonRight:

                break;
            case TitleBar.Event.imageLeft:
                onBackClick();
                break;
            case TitleBar.Event.imageRight:
                onRightTitleBarClick(mTitleBar.getRightImageView());
                break;
            case TitleBar.Event.imageRight2:

                break;
            case TitleBar.Event.textLeft:
                onBackClick();
                break;
            case TitleBar.Event.textRight:
                onRightTitleBarClick(mTitleBar.getRightTextView());
                break;
            case TitleBar.Event.textTitle:

                break;
            default:
                break;
        }
    }

    @Override
    public void initData() {
        mTitleBar.setTitle(initTitle());
        mTitleBar.setVisibility(isShowToolbar() && !TextUtils.isEmpty(initTitle()) ? View.VISIBLE : View.GONE);
        mTitleBar.setOnEventTriggerListener(this);
        initTitleBar();
    }

    /**
     * 初始化titleBar
     */
    public void initTitleBar() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public TitleBar getTitleBar() {
        return mTitleBar;
    }

    /**
     * 返回按钮
     */
    public void onBackClick() {
        finish();
    }

    /**
     * titleBar右侧点击
     */
    public void onRightTitleBarClick(@NonNull View view) {

    }

    /**
     * 初始化标题
     *
     * @return title
     */
    protected abstract String initTitle();

    /**
     * 刷新主界面
     *
     * @param contentView
     */
    protected abstract void refreshContentView(View contentView);

    @Override
    public boolean isLoadLayoutRes() {
        return false;
    }

    public boolean isShowToolbar() {
        return true;
    }

    @Override
    public View loadContentView() {
        mLoadingPager = new LoadingPager(this) {
            @Override
            protected View initContentView() {
                return BaseLoadPagerActivity.this.initContentView();
            }

            @Override
            protected int loadDataFromCache() {
                return BaseLoadPagerActivity.this.loadDataFromCache();
            }

            @Override
            public int loadData() {
                return BaseLoadPagerActivity.this.loadData();
            }

            @Override
            protected void refreshContentView(View contentView) {
                BaseLoadPagerActivity.this.refreshContentView(contentView);
            }
        };
        View view = View.inflate(this, R.layout.activity_base_title, null);
        mTitleBar = view.findViewById(R.id.view_titleBar);
        FrameLayout container = view.findViewById(R.id.fl_container);
        container.addView(mLoadingPager);
        return view;
    }


    /**
     * 初始化内容视图 交由子类完成
     *
     * @return view
     */
    @UiThread
    private View initContentView() {
        contentView = View.inflate(this, setContentLayout(), null);
        return contentView;
    }


    /**
     * 从缓存中加载数据 子线程工作
     *
     * @return 加载状态
     */
    @WorkerThread
    protected abstract
    @LoadingPager.LoadingState
    int loadDataFromCache();

    /**
     * 加载数据
     *
     * @return 加载状态
     */
    @WorkerThread
    public abstract
    @LoadingPager.LoadingState
    int loadData();

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
