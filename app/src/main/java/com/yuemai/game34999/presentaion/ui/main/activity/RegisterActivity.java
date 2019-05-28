package com.yuemai.game34999.presentaion.ui.main.activity;

import android.view.View;

import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.di.component.ActivityComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerActivity;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/1  18:52
 * @email : 923080261@qq.com
 * @description : 注册
 */
public class RegisterActivity extends AbstractMvpLoadPagerActivity {
    @Override
    public int setContentLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected String initTitle() {
        return null;
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
