package com.yuemai.game34999.presentaion.ui.game.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.di.component.ActivityComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerActivity;
import com.yuemai.game34999.presentaion.download.DownloadManager;
import com.yuemai.game34999.presentaion.ui.game.adapter.DownloadManagePagerAdapter;
import com.yuemai.game34999.presentaion.ui.game.fragment.DownloadManageFragment;
import com.yuemai.game34999.presentaion.ui.game.holder.DownloadingApkHolder;
import com.yuemai.game34999.util.MagicIndicatorUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/28  15:44
 * @email : 923080261@qq.com
 * @description :游戏下载管理
 */
public class DownloadManageActivity extends AbstractMvpLoadPagerActivity {
    @BindView(R.id.mi_downloadManage)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.vp_downloadManage)
    ViewPager mViewPager;
    DownloadManagePagerAdapter mAdapter;
    List<Fragment> mFragments;

    @Override
    public int setContentLayout() {
        return R.layout.activity_download_manage;
    }

    @Override
    protected String initTitle() {
        return getString(R.string.title_downloadManage);
    }

    @Override
    protected void refreshContentView(View contentView) {
        mFragments = new ArrayList<>();
        mFragments.add(DownloadManageFragment.newInstance(DownloadManageFragment.DOWNLOADING_TASK));
        mFragments.add(DownloadManageFragment.newInstance(DownloadManageFragment.DOWNLOAD_FINISHED_TASK));
        mAdapter = new DownloadManagePagerAdapter(mFragments, getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        MagicIndicatorUtils.bindViewpager(mMagicIndicator, this, mViewPager, new String[]{"下载中", "已下载"});
    }

    @Override
    protected int loadDataFromCache() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public int loadData() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void injectThis(ActivityComponent component) {

    }

    @Override
    protected void onDestroy() {
        //移除正在下载中的listener
        DownloadManager.getInstance().removeTask(DownloadingApkHolder.TAG);
        super.onDestroy();
    }
}
