package com.yuemai.game34999.presentaion.ui.me.activity;

import android.view.View;
import android.widget.TextView;

import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.di.component.ActivityComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerActivity;
import com.yuemai.game34999.presentaion.ui.home.activity.MakeMoneyActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/11/30  10:12
 * @email : 923080261@qq.com
 * @description : 我的积分
 */
public class CreditsActivity extends AbstractMvpLoadPagerActivity {
    @BindView(R.id.tv_credits)
    TextView tvCredits;

    @Override
    public int setContentLayout() {
        return R.layout.activity_credits;
    }

    @Override
    protected String initTitle() {
        return getString(R.string.title_myCredits);
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

    @OnClick(R.id.tv_getCreditsExchange)
    public void getCredits(View view) {
        if (view.getId() == R.id.tv_getCreditsExchange) {
            forward(MakeMoneyActivity.class);
        }
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
