package com.yuemai.game34999.presentaion.ui.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.di.component.ActivityComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerActivity;
import com.yuemai.game34999.presentaion.ui.home.adapter.MessagePagerAdapter;
import com.yuemai.game34999.util.MagicIndicatorUtils;

import net.lucode.hackware.magicindicator.MagicIndicator;

import butterknife.BindView;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/11/29  11:37
 * @email : 923080261@qq.com
 * @description : 系统消息 or 活动消息
 */
public class MessagePagerActivity extends AbstractMvpLoadPagerActivity{
    @BindView(R.id.magicIndicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    @Override
    protected String initTitle() {
        return getString(R.string.title_message);
    }

    @Override
    protected void refreshContentView(View contentView) {

    }

    @Override
    public void init() {
        super.init();
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
    public int setContentLayout() {
        return R.layout.activity_indicator_viewpager;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        String[] messageTypes = getResources().getStringArray(R.array.msg);
        MessagePagerAdapter adapter = new MessagePagerAdapter(messageTypes, getSupportFragmentManager());
        mViewPager.setAdapter(adapter);

        MagicIndicatorUtils.bindViewpager(mMagicIndicator, this, mViewPager, messageTypes);
    }

    @Override
    public void injectThis(ActivityComponent component) {

    }


    @Override
    public void onComplete() {

    }

    @Override
    public void showMsg(String msg) {

    }
}
