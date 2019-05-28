package com.yuemai.game34999.presentaion.ui.game.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yannis.common.util.UIUtils;
import com.yannis.common.widget.LoadingPager;
import com.yannis.common.widget.TitleBar;
import com.yuemai.game34999.R;
import com.yuemai.game34999.di.component.ActivityComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerActivity;
import com.yuemai.game34999.presentaion.contract.GameInfoContract;
import com.yuemai.game34999.presentaion.presenter.game.GameInfoPresenter;
import com.yuemai.game34999.presentaion.ui.game.adapter.GameInfoPagerAdapter;
import com.yuemai.game34999.presentaion.ui.game.fragment.GameDetailFragment;
import com.yuemai.game34999.presentaion.ui.game.fragment.GameGiftBagFragment;
import com.yuemai.game34999.presentaion.ui.game.fragment.GameInformationFragment;
import com.yuemai.game34999.presentaion.ui.game.fragment.GameStrategyFragment;
import com.yuemai.game34999.util.MagicIndicatorUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/28  15:55
 * @email : 923080261@qq.com
 * @description :
 */
public class GameInfoActivity extends AbstractMvpLoadPagerActivity<GameInfoPresenter> implements GameInfoContract.View {
    @BindView(R.id.mi_gameZoomTab)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.vp_gameZoom)
    ViewPager mViewPager;
    private List<Fragment> list;
    private boolean fixedFlag = false, resetFlag = false;

    public static void start(Context context) {
        context.startActivity(new Intent(context, GameInfoActivity.class));
    }

    @Override
    protected String initTitle() {
        return getString(R.string.title_gameZone);
    }

    @Override
    protected void refreshContentView(View contentView) {

    }

    @Override
    public void initTitleBar() {
        mTitleBar.setRightDrawable(getResources().getDrawable(R.drawable.share_selector));
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_game_info;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        list = new ArrayList<>();
        list.add(new GameDetailFragment());
        list.add(new GameGiftBagFragment());
        list.add(new GameStrategyFragment());
        list.add(new GameInformationFragment());
        GameInfoPagerAdapter adapter = new GameInfoPagerAdapter(list, getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
        MagicIndicatorUtils.bindViewpager(mMagicIndicator, this, mViewPager, UIUtils.getStringArray(R.array.gameZoom));
    }

    @Override
    public void injectThis(ActivityComponent component) {
        component.inject(this);
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }

    @Override
    protected int loadDataFromCache() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public int loadData() {
        presenter.getGameInfo();
        return LoadingPager.LoadingState.stateLoading;
    }

    @Override
    public void onEventTrigger(@TitleBar.Event int type) {
        super.onEventTrigger(type);
        if (type == TitleBar.Event.imageRight) {
            showToast("分享个球球");
        }
    }

    //Enable nested scrolling of recyclerView in ScrollView
    private void enableNestedScrolling(RecyclerView recyclerView) {
        if (recyclerView != null) {
            if (!fixedFlag) {
                setFixedFlag();
                recyclerView.setNestedScrollingEnabled(true);
            }
        }
    }

    //Disable nested scrolling of recyclerView in ScrollView
    private void disableNestedScrolling(RecyclerView recyclerView) {
        if (recyclerView != null) {
            if (!resetFlag) {
                setResetFlag();
                recyclerView.setNestedScrollingEnabled(false);
            }
        }
    }

    private void setFixedFlag() {
        setFlag(false);
    }

    private void setResetFlag() {
        setFlag(true);
    }

    //True:reset;false:fix
    private void setFlag(boolean reset) {
        if (reset) {
            resetFlag = true;
            fixedFlag = false;
        } else {
            fixedFlag = true;
            resetFlag = false;
        }
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void showMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void showInfo() {

    }

    @Override
    public void downloadSuccess() {
        showToast("下载成功");
    }

    @Override
    public void downloadFailed() {

    }

    @OnClick(R.id.btn_download)
    public void startDownloadGame() {
        presenter.startDownload();
    }
}
