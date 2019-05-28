package com.yuemai.game34999.presentaion.download.task.executor;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/4  14:41
 * @email : 923080261@qq.com
 * @description :
 */
public class ObserveExecutor extends ThreadPoolExecutor {
    /**
     * UI线程切换
     */
    private Handler innerHandler = new Handler(Looper.getMainLooper());
    /**
     * 任务结束观察者
     */
    private List<OnTaskEndListener> taskEndListenerList;
    /**
     * 所有任务结束观察者
     */
    private List<OnAllTaskEndListener> allTaskEndListenerList;


    public ObserveExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public ObserveExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public ObserveExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public ObserveExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    /** 任务结束后回调 */
    @Override
    protected void afterExecute(final Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (taskEndListenerList != null && taskEndListenerList.size() > 0) {
            for (final OnTaskEndListener listener : taskEndListenerList) {
                innerHandler.post(() -> listener.onTaskEnd(r));
            }
        }
        //当前正在运行的数量为1 表示当前正在停止的任务，同时队列中没有任务，表示所有任务下载完毕
        if (getActiveCount() == 1 && getQueue().size() == 0) {
            if (allTaskEndListenerList != null && allTaskEndListenerList.size() > 0) {
                for (final OnAllTaskEndListener listener : allTaskEndListenerList) {
                    innerHandler.post(listener::onAllTaskEnd);
                }
            }
        }
    }

    /**
     * 添加任务结束观察者
     * @param taskEndListener 结束观察者
     */
    public void addOnTaskEndListener(OnTaskEndListener taskEndListener) {
        if (taskEndListenerList == null) {
            taskEndListenerList = new ArrayList<>();
        }
        taskEndListenerList.add(taskEndListener);
    }

    public void removeOnTaskEndListener(OnTaskEndListener taskEndListener) {
        taskEndListenerList.remove(taskEndListener);
    }


    public void addOnAllTaskEndListener(OnAllTaskEndListener allTaskEndListener) {
        if (allTaskEndListenerList == null) {
            allTaskEndListenerList = new ArrayList<>();
        }
        allTaskEndListenerList.add(allTaskEndListener);
    }

    public void removeOnAllTaskEndListener(OnAllTaskEndListener allTaskEndListener) {
        allTaskEndListenerList.remove(allTaskEndListener);
    }


    public interface OnTaskEndListener {
        /**
         * 任务结束的时候回调接口
         *
         * @param r task
         */
        void onTaskEnd(Runnable r);
    }

    public interface OnAllTaskEndListener {
        /**
         * 当所有任务都结束的时候回调接口
         */
        void onAllTaskEnd();
    }
}
