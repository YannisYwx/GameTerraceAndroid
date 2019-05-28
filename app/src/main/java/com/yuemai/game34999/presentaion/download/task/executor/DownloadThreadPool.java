package com.yuemai.game34999.presentaion.download.task.executor;

import android.support.annotation.NonNull;

import com.yuemai.game34999.presentaion.download.task.queue.PriorityBlockingQueue;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/4  15:06
 * @email : 923080261@qq.com
 * @description : 自定义线程池 五个工作线程同时工作3个
 */
public class DownloadThreadPool {
    /**
     * 最大线程池的数量
     */
    private static final int MAX_POOL_SIZE = 5;
    /**
     * 存活的时间
     */
    private static final int KEEP_ALIVE_TIME = 1;
    /**
     * 时间单位
     */
    private static final TimeUnit UNIT = TimeUnit.HOURS;
    /**
     * 核心线程池的数量，同时能执行的线程数量，默认3个
     */
    private int corePoolSize = 3;
    /**
     * 线程池执行器
     */
    private ObserveExecutor executor;

    private DownloadThreadPool() {
    }



    public ObserveExecutor getExecutor() {
        if (executor == null) {
            synchronized (DownloadThreadPool.class) {
                if (executor == null) {
                    executor = new ObserveExecutor(
                            corePoolSize,
                            MAX_POOL_SIZE,
                            KEEP_ALIVE_TIME,
                            UNIT,
                            //无限容量的缓冲队列
                            new PriorityBlockingQueue<>(),
                            //线程创建工厂
                            new NameThreadFactory(),
                            //继续超出上限的策略，阻止
                            new ThreadPoolExecutor.AbortPolicy());
                }
            }
        }
        return executor;
    }

    /**
     * 必须在首次执行前设置，否者无效 ,范围1-5之间
     */
    public void setCorePoolSize(int corePoolSize) {
        if (corePoolSize <= 0) {
            corePoolSize = 1;
        }
        if (corePoolSize > MAX_POOL_SIZE) {
            corePoolSize = MAX_POOL_SIZE;
        }
        this.corePoolSize = corePoolSize;
    }

    /**
     * 执行任务
     */
    public void execute(Runnable runnable) {
        if (runnable != null) {
            getExecutor().execute(runnable);
        }
    }

    /**
     * 移除线程
     */
    public void remove(Runnable runnable) {
        if (runnable != null) {
            getExecutor().remove(runnable);
        }
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

    public static DownloadThreadPool getInstance() {
        return DownloadThreadPoolHolder.INSTANCE;
    }

    private static class DownloadThreadPoolHolder {
        private static final DownloadThreadPool INSTANCE = new DownloadThreadPool();
    }
}
