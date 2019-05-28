package com.yuemai.game34999.domain.executor.impl;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.util.LongSparseArray;

import com.yuemai.game34999.domain.executor.Executor;
import com.yuemai.game34999.domain.interactors.abs.ReturnTask;
import com.yuemai.game34999.domain.interactors.abs.VoidTask;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/11/21  18:36
 * @email : 923080261@qq.com
 * @description :
 */
public class ThreadExecutor<T> implements Executor<T> {
    private static final int CORE_POOL_SIZE = 3;
    private static final int MAX_POOL_SIZE = 5;
    private static final int KEEP_ALIVE_TIME = 120;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private static final BlockingQueue<Runnable> WORK_QUEUE = new LinkedBlockingQueue<>();
    private LongSparseArray<Future> mFutureArray;
    private AtomicInteger count = new AtomicInteger(0);

    private Handler mHandler;

    private ThreadPoolExecutor mThreadPoolExecutor;

    private ThreadExecutor() {
        mThreadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TIME_UNIT,
                WORK_QUEUE, new NameThreadFactory());
        mHandler = new Handler(Looper.getMainLooper());
    }

    private void post(@NonNull final VoidTask task) {
        mHandler.post(task::onExecuteFinished);
    }


    private void post(@NonNull final ReturnTask<T> task, final T obj) {
        mHandler.post(() -> task.onExecuteFinished(obj));
    }

    @Override
    public long execute(final VoidTask voidTask) {
        Future future = mThreadPoolExecutor.submit(() -> {
            // run the main logic
            voidTask.run();
            voidTask.onFinished();
            // notify execute finished
            post(voidTask);

        });
        long key = count.addAndGet(1);
        mFutureArray.put(key, future);
        return key;
    }

    @Override
    public long execute(ReturnTask<T> returnTask) {
        Future future = mThreadPoolExecutor.submit(() -> {
            // run the main logic
            T result = returnTask.run();
            returnTask.setResponse(result);
            returnTask.onFinished();
            // notify execute finished
            post(returnTask, result);

        });
        long key = count.addAndGet(1);
        mFutureArray.put(key, future);
        return key;
    }

    @Override
    public boolean cancel(long key) {
        Future future = mFutureArray.get(key);
        if (future == null) {
            return false;
        }

        boolean isSuccess = future.cancel(true);
        if (isSuccess) {
            mFutureArray.remove(key);
        }
        return isSuccess;
    }

    /**
     * 自定义名字的线程工厂
     */
    private class NameThreadFactory implements ThreadFactory {
        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            Thread thread = new Thread(r);
            String threadName = "Yannis-" + count.addAndGet(1);
            thread.setName(threadName);
            return thread;
        }
    }

    public static ThreadExecutor getInstance() {
        return ThreadExecutorHolder.INSTANCE;
    }

    private static class ThreadExecutorHolder {
        private static final ThreadExecutor INSTANCE = new ThreadExecutor();
    }


}
