package com.yuemai.game34999.presentaion.ui.me.activity;

import android.view.View;

import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.di.component.ActivityComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerActivity;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/11/30  15:32
 * @email : 923080261@qq.com
 * @description : 意见反馈
 */
public class FeedbackActivity extends AbstractMvpLoadPagerActivity {

    @Override
    public int setContentLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    protected String initTitle() {
        return getString(R.string.title_feedback);
    }

    @Override
    protected void refreshContentView(View contentView) {

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
}
