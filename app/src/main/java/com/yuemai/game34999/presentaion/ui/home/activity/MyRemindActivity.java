package com.yuemai.game34999.presentaion.ui.home.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.data.bean.MyRemindBean;
import com.yuemai.game34999.di.component.ActivityComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerActivity;
import com.yuemai.game34999.presentaion.ui.home.adapter.MyRemindAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yuemai.game34999.R.id.ll_bottom;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/30  18:01
 * @email : 923080261@qq.com
 * @description :我的提醒
 */
public class MyRemindActivity extends AbstractMvpLoadPagerActivity implements Animation.AnimationListener {
    @BindView(R.id.rv_myRemind)
    RecyclerView mRecyclerView;
    @BindView(ll_bottom)
    LinearLayout llBottom;
    private List<MyRemindBean> datas = new ArrayList<>();
    private Animation enter, exit;
    MyRemindAdapter adapter = null;
    @BindView(R.id.tv_checkAll)
    TextView tvCheckAll;
    @BindView(R.id.tv_delete)
    TextView tvDelete;

    boolean isCheckAll = false;

    @Override
    public int setContentLayout() {
        return R.layout.activity_settings_remind;
    }

    @Override
    protected String initTitle() {
        mTitleBar.setRightText(getString(R.string.edit));
        return getString(R.string.title_settingRemind);
    }

    @Override
    public void initData() {
        super.initData();
        enter = AnimationUtils.loadAnimation(this, R.anim.enter);
        exit = AnimationUtils.loadAnimation(this, R.anim.exit);
        enter.setAnimationListener(this);
        exit.setAnimationListener(this);
    }

    @Override
    public void onRightTitleBarClick(@NonNull View view) {
        super.onRightTitleBarClick(view);
        Logger.e("----------------------------------onRightTitleBarClick");
        llBottom.clearAnimation();
        if (llBottom.getVisibility() == View.VISIBLE) {
            //关闭
            llBottom.startAnimation(exit);
            mTitleBar.setRightText(getString(R.string.edit));
            adapter.notifyDataSetChanged();
            initBeans(false);
        } else {
            //打开
            initBeans(true);
            llBottom.startAnimation(enter);
            mTitleBar.setRightText(getString(R.string.cancel));
            adapter.notifyDataSetChanged();
        }

    }

    @Override
    protected void refreshContentView(final View contentView) {
        adapter = new MyRemindAdapter(datas);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected int loadDataFromCache() {
        initBeans(false);
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public int loadData() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == enter) {
            llBottom.setVisibility(View.VISIBLE);
        }
        if (animation == exit) {
            llBottom.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }


    public void initBeans(boolean isShow) {
        if (datas.size() == 0) {
            for (int i = 0; i < 5; i++) {
                MyRemindBean bean = new MyRemindBean();
                bean.isChecked = false;
                bean.isShowCheckBox = false;
                datas.add(bean);
            }
        }

        for (MyRemindBean bean : datas) {
            bean.isShowCheckBox = isShow;
        }
    }

    public void isCheckAll(boolean isShow, boolean isChecked) {
        if (datas.size() == 0) {
            for (int i = 0; i < 5; i++) {
                MyRemindBean bean = new MyRemindBean();
                bean.isChecked = false;
                bean.isShowCheckBox = false;
                datas.add(bean);
            }
        }

        for (MyRemindBean bean : datas) {
            bean.isShowCheckBox = isShow;
            bean.isChecked = isChecked;
        }
    }

    @OnClick({R.id.tv_delete, R.id.tv_checkAll})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_checkAll:
                isCheckAll = !isCheckAll;
                isCheckAll(true, isCheckAll);
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_delete:
                datas.clear();
                adapter.notifyDataSetChanged();
                break;
            default:
                break;
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
