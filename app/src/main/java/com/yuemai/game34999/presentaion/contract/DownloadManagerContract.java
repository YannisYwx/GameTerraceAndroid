package com.yuemai.game34999.presentaion.contract;

import com.yuemai.game34999.core.mvp.inter.BaseContract;
import com.yuemai.game34999.presentaion.download.task.DownloadTask;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/24  16:07
 * @email : 923080261@qq.com
 * @description :
 */
public interface DownloadManagerContract {

    interface View extends BaseContract.BaseView {
        /**
         * 删除任务
         *
         * @param task 任务
         */
        void deleteTask(DownloadTask task);

        /**
         * 下载完成
         *
         * @param task 任务
         */
        void downloadFinish(DownloadTask task);

    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        /**
         * 获取下载中的任务
         *
         * @return 下载中的任务列表 not null
         */
        List<DownloadTask> getDownloadingTask();


        /**
         * 获取下载完成的任务
         *
         * @return 下载完成的任务列表 not null
         */
        List<DownloadTask> getFinishedTask();
    }

}
