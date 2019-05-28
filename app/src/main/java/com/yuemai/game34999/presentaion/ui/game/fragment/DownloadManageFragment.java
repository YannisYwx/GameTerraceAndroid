package com.yuemai.game34999.presentaion.ui.game.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.data.bean.GameInfo;
import com.yuemai.game34999.di.component.FragmentComponent;
import com.yuemai.game34999.presentaion.base.AbstractMvpLoadPagerFragment;
import com.yuemai.game34999.presentaion.contract.DownloadManagerContract;
import com.yuemai.game34999.presentaion.download.DownloadManager;
import com.yuemai.game34999.presentaion.download.task.DownloadTask;
import com.yuemai.game34999.presentaion.presenter.game.DownloadManagerPresenter;
import com.yuemai.game34999.presentaion.ui.game.adapter.ApkDownloadManagerAdapter;
import com.yuemai.game34999.util.TestDataUtils;

import java.util.List;

import butterknife.BindView;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/28  16:06
 * @email : 923080261@qq.com
 * @description :
 */
public class DownloadManageFragment extends AbstractMvpLoadPagerFragment<DownloadManagerPresenter> implements DownloadManagerContract.View {

    private static final String KEY_DOWNLOAD_STATUS = "DOWNLOAD_STATUS";

    @BindView(R.id.rv_download)
    RecyclerView rvDownload;
    List<DownloadTask> downloadDataList;
    List<GameInfo> remindDataList;
    ApkDownloadManagerAdapter mAdapter;
    /**
     * 所有任务
     */
    public static final int ALL_TASK = 0;

    /**
     * 已完成的下载
     */
    public static final int DOWNLOAD_FINISHED_TASK = 1;

    /**
     * 正在继续的下载
     */
    public static final int DOWNLOADING_TASK = 2;


    public int status = DOWNLOADING_TASK;

    public static DownloadManageFragment newInstance(int status) {
        DownloadManageFragment fragment = new DownloadManageFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_DOWNLOAD_STATUS, status);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        status = getArguments().getInt(KEY_DOWNLOAD_STATUS);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {
        fragmentComponent.inject(this);
    }

    @Override
    public void initEvent() {

    }

    /**
     * 根据状态获取不同的任务集合
     *
     * @param status 任务状态 正在进行 以及 已完成的任务
     */
    private void updateData(int status) {
        //这里是将数据库的数据恢复
        this.status = status;
        if (status == ALL_TASK) {
            downloadDataList = DownloadManager.getAllTask();
        }
        if (status == DOWNLOAD_FINISHED_TASK) {
            downloadDataList = DownloadManager.getFinishTask();
        }
        if (status == DOWNLOADING_TASK) {
            downloadDataList = DownloadManager.getDownloadingTask();
            remindDataList = TestDataUtils.initApkData();
        }
    }

    @Override
    public View initContentView() {
        RecyclerView recyclerView = new RecyclerView(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        recyclerView.setLayoutParams(params);
        return View.inflate(getContext(), R.layout.fragment_download_manage, null);
    }

    @Override
    public int initDataFromCache() {
        updateData(status);
        if (downloadDataList.size() == 0 && status == DOWNLOAD_FINISHED_TASK) {
            return LoadingPager.LoadingState.stateEmpty;
        }
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public int initData() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public void refreshContentView(View contentView) {
        if (status == DOWNLOAD_FINISHED_TASK) {
            mAdapter = new ApkDownloadManagerAdapter(downloadDataList);
        } else if (status == DOWNLOADING_TASK) {
            mAdapter = new ApkDownloadManagerAdapter(downloadDataList, remindDataList);
        }
        rvDownload.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDownload.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void deleteTask(DownloadTask task) {
        int index = getTaskIndex(task);
        Logger.e("deleteTask index = " + index + " adapter = " + mAdapter);
        if (index >= 0) {
            downloadDataList.remove(task);
            mAdapter.notifyItemRemoved(index);
        }
        if (downloadDataList.size() == 0 && mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void downloadFinish(DownloadTask task) {

    }

    private int getTaskIndex(DownloadTask task) {
        for (int i = 0; i < downloadDataList.size(); i++) {
            if (downloadDataList.get(i).progress.tag.equals(task.progress.tag)) {
                return i;
            }
        }
        return -1;
    }
}
