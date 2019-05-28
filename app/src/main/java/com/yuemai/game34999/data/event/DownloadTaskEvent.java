package com.yuemai.game34999.data.event;

import com.yuemai.game34999.presentaion.download.task.DownloadTask;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/24  16:27
 * @email : 923080261@qq.com
 * @description : 下载任务的事件--> 0:删除 1：完成
 */
public class DownloadTaskEvent extends RxEventBean {
    /**
     * 事件移除
     */
    public static final int EVENT_TASK_DELETE = 0;
    /**
     * 事件完成
     */
    public static final int EVENT_TASK_FINISH = 1;
    
    public DownloadTask task;

    public DownloadTaskEvent(int event, DownloadTask task) {
        this.event = event;
        this.task = task;
    }
}
