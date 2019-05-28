package com.yannis.common.thread.wrapper;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.util.LongSparseArray;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/16  15:55
 * @email : 923080261@qq.com
 * @description :
 */
public class ThreadUtils {
    static final String TAG = ThreadUtils.class.getSimpleName();
    private static ThreadUtils sThreadUtil;
    private final ExecutorService mExecutor;
    private final Handler mHandler;
    private LongSparseArray<Future> mFutureArray;

    private ThreadUtils() {
        mExecutor = Executors.newFixedThreadPool(5);
        mHandler = new Handler(Looper.getMainLooper());
        mFutureArray = new LongSparseArray<>();
    }

    private static ThreadUtils getInstance() {
        if (sThreadUtil == null) {
            synchronized (ThreadUtils.class) {
                if (sThreadUtil == null) sThreadUtil = new ThreadUtils();
            }
        }
        return sThreadUtil;
    }


    /**
     * @param thread 执行线程
     * @return 返回线程对应的key用于查看线程执行情况和取消线程执行
     */
    public static long execute(Runnable thread) {
        return getInstance().p_threadExecute(thread);
    }


    /**
     * 取消一条正在执行的线程
     *
     * @param key 执行时返回的线程执行Id
     * @return
     */
    public static boolean cancel(long key) {
        return getInstance().p_cancel(key, true);
    }

    public static boolean cancel(long key, boolean mayInterruptIfRunning) {
        return getInstance().p_cancel(key, true);
    }

    /**
     * 删除在集合中的引用
     *
     * @param key
     */
    public static void removeKey(long key) {
        getInstance().p_removeKey(key);
    }

    /**
     * 停止所有线程(理论上)
     */
    public static void shutdown() {
        getInstance().p_shutdown();
    }

    public static Handler getMainHandler() {
        return getInstance().p_getMainHandler();
    }

    public static ExecutorService getExecutors() {
        return getInstance().p_getExecutors();
    }

    private ExecutorService p_getExecutors() {
        return mExecutor;
    }

    private Handler p_getMainHandler() {
        return mHandler;
    }

    private void p_shutdown() {
        mExecutor.shutdown();
        mFutureArray.clear();
    }


    private long p_threadExecute(Runnable thread) {
        Future future = mExecutor.submit(thread);
        long key = getAutoIncrementKey();
        mFutureArray.put(key, future);
        return key;
    }

    private boolean p_cancel(long key, boolean mayInterruptIfRunning) {
        Future future = mFutureArray.get(key, null);
        if (future == null) {
            return false;
        }
        boolean success = future.cancel(mayInterruptIfRunning);
        if (success) p_removeKey(key);
        return success;
    }

    private void p_removeKey(long key) {
        mFutureArray.delete(key);
    }

    private long getAutoIncrementKey() {
        return mFutureArray.size();
    }

    public void removeAllTask() {
        if (mFutureArray == null) return;
        for (int i = 1; i <= mFutureArray.size(); i++) {
            Future future = mFutureArray.get(i, null);
            boolean success = future.cancel(true);
            if (success) p_removeKey(i);
        }
        mFutureArray.clear();
    }
}
