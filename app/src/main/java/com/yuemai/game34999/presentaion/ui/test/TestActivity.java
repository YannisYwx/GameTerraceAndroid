package com.yuemai.game34999.presentaion.ui.test;

import android.content.Intent;
import android.view.View;

import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.di.component.ActivityComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerActivity;
import com.yuemai.game34999.presentaion.ui.test.download.TestDownloadActivity;
import com.yuemai.game34999.presentaion.ui.test.download.TestDownloadXiaoMiActivity;
import com.yuemai.game34999.presentaion.ui.test.inter.TestNetInterfaceActivity;

import butterknife.OnClick;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/13  18:14
 * @email : 923080261@qq.com
 * @description :
 */
public class TestActivity extends AbstractMvpLoadPagerActivity {

    public static void start(android.content.Context context) {
        context.startActivity(new Intent(context, TestActivity.class));
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
    public int setContentLayout() {
        return R.layout.activity_test;
    }

    @Override
    protected String initTitle() {
        return "测试页面";
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

    @OnClick({R.id.btn_test_interface, R.id.btn_test_download, R.id.btn_test_download_xiaomi})
    public void onBtnClick(View view) {
        if (view.getId() == R.id.btn_test_interface) {
            TestNetInterfaceActivity.start(this);
        }
        if (view.getId() == R.id.btn_test_download) {
            TestDownloadActivity.start(this);
        }

        if (view.getId() == R.id.btn_test_download_xiaomi) {
            TestDownloadXiaoMiActivity.start(this);
        }
    }
}
