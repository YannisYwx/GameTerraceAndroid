package com.yuemai.game34999.presentaion.ui.main.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yannis.common.base.BaseActivity;
import com.yannis.common.util.ActivityManager;
import com.yannis.common.util.StatusBarUtils;
import com.yannis.common.util.UIUtils;
import com.yannis.common.widget.NoScrollViewPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.presentaion.ui.classify.fragment.ClassifyFragment;
import com.yuemai.game34999.presentaion.ui.game.activity.DownloadManageActivity;
import com.yuemai.game34999.presentaion.ui.gift.fragment.GiftFragment;
import com.yuemai.game34999.presentaion.ui.home.activity.MessagePagerActivity;
import com.yuemai.game34999.presentaion.ui.home.activity.SearchActivity;
import com.yuemai.game34999.presentaion.ui.home.fragment.HomeFragment;
import com.yuemai.game34999.presentaion.ui.main.adapter.MainTabPagerAdapter;
import com.yuemai.game34999.presentaion.ui.me.fragment.MeFragment;
import com.yuemai.game34999.presentaion.ui.news.fragment.NewsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * @author Yannis.ywx
 */
public class MainActivity extends BaseActivity{

    @BindView(R.id.rg_main_tab)
    RadioGroup rgMainTab;
    @BindView(R.id.vp_main)
    NoScrollViewPager mViewPager;
    @BindView(R.id.iv_download)
    ImageView ivDownload;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    private HomeFragment mHomeFragment;
    private Unbinder unbinder;

    public static void start(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    public void init() {

    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void initEvent() {
        rgMainTab.setOnCheckedChangeListener((radioGroup, i) -> {
            switch (i) {
                case R.id.rb_main_home:
                    StatusBarUtils.statusBarLightMode(this);
                    llTop.setVisibility(View.VISIBLE);
                    mViewPager.setCurrentItem(0, false);
                    break;
                case R.id.rb_main_classify:
                    StatusBarUtils.statusBarLightMode(this);
                    llTop.setVisibility(View.VISIBLE);
                    mViewPager.setCurrentItem(1, false);
                    break;
                case R.id.rb_main_news:
                    StatusBarUtils.statusBarLightMode(this);
                    llTop.setVisibility(View.GONE);
                    mViewPager.setCurrentItem(2, false);
                    break;
                case R.id.rb_main_gift:
                    StatusBarUtils.statusBarLightMode(this);
                    llTop.setVisibility(View.GONE);
                    mViewPager.setCurrentItem(3, false);
                    break;
                case R.id.rb_main_me:
                    StatusBarUtils.statusBarDarkMode(this);
                    llTop.setVisibility(View.GONE);
                    mViewPager.setCurrentItem(4, false);
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public void initData() {
        rgMainTab.check(R.id.rb_main_home);
        List<Fragment> list = new ArrayList<>();
        if (mHomeFragment == null) {
            mHomeFragment = HomeFragment.getInstance();
        }
        list.add(mHomeFragment);
        list.add(new ClassifyFragment());
        list.add(new NewsFragment());
        list.add(new GiftFragment());
        list.add(new MeFragment());

        MainTabPagerAdapter adapter = new MainTabPagerAdapter(list, getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

        //设置默认加载多少页 屏幕左右之外，保留多少页面， 其实就是默认加载几页。
        mViewPager.setOffscreenPageLimit(0);

        RxPermissions rxPermissions = new RxPermissions(this);

        rxPermissions.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(permission -> {
            if (permission.granted) {
                // 用户已经同意该权限
            } else if (permission.shouldShowRequestPermissionRationale) {
                // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
            } else {
                // 用户拒绝了该权限，并且选中『不再询问』
            }
        });

    }

    @OnClick({R.id.tv_search, R.id.iv_message, R.id.iv_download})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                forward(SearchActivity.class);
                break;
            case R.id.iv_download:
                forward(DownloadManageActivity.class);
                break;
            case R.id.iv_message:
                forward(MessagePagerActivity.class);
                break;
            default:
                break;
        }
    }

    private long firstTime = 0;
    private static final long INTERVAL_TIME = 1_500;

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > INTERVAL_TIME) {
                UIUtils.showInfo(this, "再按一次退出");
                firstTime = secondTime;
                return true;
            } else {
                ActivityManager.getInstance().appExit();
            }
        }
        return false;
    }


}
