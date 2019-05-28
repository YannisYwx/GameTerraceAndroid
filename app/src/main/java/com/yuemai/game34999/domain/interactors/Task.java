package com.yuemai.game34999.domain.interactors;

import android.support.annotation.WorkerThread;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/11/21  18:36
 * @email : 923080261@qq.com
 * @description :
 */
public interface Task {
    /**
     * 开始执行任务
     *
     * @return 任务对应的key
     */
    @WorkerThread
    long execute();

    /**
     * 任务取消
     *
     * @return true:成功 false:失败
     */
    boolean cancel();



}
