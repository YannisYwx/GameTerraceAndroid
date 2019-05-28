package com.yuemai.game34999.presentaion.ui.home.activity;

import android.view.View;

import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.di.component.ActivityComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerActivity;

import static com.yuemai.game34999.presentaion.ui.home.fragment.MessageFragment.KEY_MSG_TYPE;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/11/29  15:46
 * @email : 923080261@qq.com
 * @description : 消息详情页面
 */
public class MessageActivity extends AbstractMvpLoadPagerActivity {
    private int type;

    @Override
    public void init() {
        super.init();
        type = getIntent().getBundleExtra(EXTRA).getInt(KEY_MSG_TYPE);
    }

    @Override
    public void injectThis(ActivityComponent component) {

    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_msg;
    }

    @Override
    protected String initTitle() {
        return getResources().getStringArray(R.array.msg)[type];
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
}
