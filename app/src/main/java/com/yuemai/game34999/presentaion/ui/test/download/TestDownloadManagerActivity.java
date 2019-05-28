package com.yuemai.game34999.presentaion.ui.test.download;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.data.bean.GameInfo;
import com.yuemai.game34999.di.component.ActivityComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerActivity;
import com.yuemai.game34999.presentaion.download.DownloadManager;
import com.yuemai.game34999.presentaion.download.db.DownloadDao;
import com.yuemai.game34999.presentaion.download.task.DownloadTask;
import com.yuemai.game34999.presentaion.ui.test.inter.TestNetInterfaceContract;
import com.yuemai.game34999.presentaion.ui.test.inter.TestNetInterfacePresenter;

import java.util.List;

import butterknife.BindView;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/12/28  17:50
 * @email : 923080261@qq.com
 * @description :
 */
public class TestDownloadManagerActivity extends AbstractMvpLoadPagerActivity<TestNetInterfacePresenter> implements TestNetInterfaceContract.View {

    @BindView(R.id.rv_downloadList)
    RecyclerView mRecyclerView;
    private ApkDownloadManagerAdapter adapter;
    private LayoutAnimationController controller;
    private List<DownloadTask> values;

    public static final int TYPE_ALL = 0;
    public static final int TYPE_FINISH = 1;
    public static final int TYPE_ING = 2;

    private int type;

    private List<GameInfo> apks;

    public static void start(android.content.Context context) {
        context.startActivity(new Intent(context, TestDownloadManagerActivity.class));
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void showMsg(String msg) {
        showResult(msg);
    }

    @Override
    public void injectThis(ActivityComponent component) {
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_test_download;
    }

    @Override
    protected String initTitle() {
        return "下载管理";
    }

    @Override
    public void initEvent() {
        super.initEvent();

    }

    private void getAllTask() {
        updateData(TYPE_ALL);
    }

    public void updateData(int type) {
        //这里是将数据库的数据恢复
        this.type = type;
        if (type == TYPE_ALL) {
            values = DownloadManager.restore(DownloadDao.getInstance().getAll());
        }
        if (type == TYPE_FINISH) {
            values = DownloadManager.restore(DownloadDao.getInstance().getFinished());
        }
        if (type == TYPE_ING) {
            values = DownloadManager.restore(DownloadDao.getInstance().getDownloading());
        }
    }

    @Override
    protected void refreshContentView(View contentView) {
        controller =
                AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down);
        mRecyclerView.setLayoutAnimation(controller);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected int loadDataFromCache() {
        getAllTask();
        adapter = new ApkDownloadManagerAdapter(values);
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public int loadData() {
        return LoadingPager.LoadingState.stateSuccess;
    }


    @Override
    public void showResult(String result) {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //将本页面的监听全部移除
        DownloadManager.getInstance().removeDownloadListenerByKeyTag(DownloadHolder.TAG);
    }
}
