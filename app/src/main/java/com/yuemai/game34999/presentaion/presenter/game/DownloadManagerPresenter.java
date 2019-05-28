package com.yuemai.game34999.presentaion.presenter.game;

import com.yannis.common.rx.RxBus;
import com.yuemai.game34999.core.mvp.RxPresenter;
import com.yuemai.game34999.data.DataManager;
import com.yuemai.game34999.data.event.DownloadTaskEvent;
import com.yuemai.game34999.presentaion.contract.DownloadManagerContract;
import com.yuemai.game34999.presentaion.download.DownloadManager;
import com.yuemai.game34999.presentaion.download.progress.Progress;
import com.yuemai.game34999.presentaion.download.task.DownloadTask;

import java.util.List;

import javax.inject.Inject;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/24  16:09
 * @email : 923080261@qq.com
 * @description :
 */
public class DownloadManagerPresenter extends RxPresenter<DownloadManagerContract.View> implements DownloadManagerContract.Presenter {
    DataManager mDataManager;

    @Inject
    DownloadManagerPresenter(DataManager dataManager) {
        mDataManager = dataManager;
        addSubscribe(RxBus.getInstance().register(DownloadTaskEvent.class, downloadTaskEvent -> {
            if (downloadTaskEvent.event == DownloadTaskEvent.EVENT_TASK_DELETE) {
                //删除任务
                Progress.log("------删除任务 = ", downloadTaskEvent.task.progress);
                downloadTaskEvent.task.remove(true);
                if (mView != null) {
                    mView.deleteTask(downloadTaskEvent.task);
                }
            }

            if (downloadTaskEvent.event == DownloadTaskEvent.EVENT_TASK_FINISH) {
                //任务完成
                Progress.log("------任务完成 = ", downloadTaskEvent.task.progress);
                if (mView != null) {
                    mView.downloadFinish(downloadTaskEvent.task);
                }
            }
        }));
    }

    @Override
    public List<DownloadTask> getDownloadingTask() {
        return DownloadManager.getDownloadingTask();
    }

    @Override
    public List<DownloadTask> getFinishedTask() {
        return DownloadManager.getFinishTask();
    }
}