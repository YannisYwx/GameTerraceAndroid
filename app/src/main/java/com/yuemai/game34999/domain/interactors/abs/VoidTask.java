package com.yuemai.game34999.domain.interactors.abs;

import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;

import com.yuemai.game34999.domain.executor.impl.ThreadExecutor;
import com.yuemai.game34999.domain.interactors.Task;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/11/21  18:36
 * @email : 923080261@qq.com
 * @description :
 */
public abstract class VoidTask implements Task {
    protected volatile boolean mIsCanceled;
    protected volatile boolean mIsRunning;
    protected long key;

    @WorkerThread
    public abstract void run();

    public boolean isRunning() {
        return mIsRunning;
    }

    public void onFinished() {
        mIsRunning = false;
        mIsCanceled = false;
    }

    @Override
    public long execute() {
        mIsRunning = true;
        key = ThreadExecutor.getInstance().execute(this);
        return key;
    }

    /**
     * 执行任务结束
     */
    @UiThread
    public abstract void onExecuteFinished();

    @Override
    public boolean cancel() {
        boolean isSuccess = ThreadExecutor.getInstance().cancel(key);
        mIsCanceled = true;
        mIsRunning = false;
        return isSuccess;
    }
}
