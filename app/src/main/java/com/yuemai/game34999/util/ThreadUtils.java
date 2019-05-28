package com.yuemai.game34999.util;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.util.LongSparseArray;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/9  15:20
 * @email : 923080261@qq.com
 * @description : 线程工具类
 */
public class ThreadUtils {

    private static final int CORE_POOL_SIZE = 3;
    private static final int MAX_POOL_SIZE = 5;
    private static final int KEEP_ALIVE_TIME = 120;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private static final BlockingQueue<Runnable> WORK_QUEUE;
    private static final LongSparseArray<Future> FUTURE_ARRAY;
    private static final AtomicInteger COUNT;
    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR;

    /**
     * 主线程调度
     */
    private static final android.os.Handler DELIVERY;

    static {
        DELIVERY = new Handler(Looper.getMainLooper());
        FUTURE_ARRAY = new LongSparseArray<>();
        COUNT = new AtomicInteger(0);
        WORK_QUEUE = new LinkedBlockingQueue<>();
        THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TIME_UNIT,
                WORK_QUEUE, new NameThreadFactory());
    }

    public static void runOnUiThread(Runnable run) {
        DELIVERY.post(run);
    }

    public static void runOnUiThread(Runnable run, long delay) {
        DELIVERY.postDelayed(run, delay);
    }

    public static long runOnBackgroundThread(Runnable run) {
        Future future = THREAD_POOL_EXECUTOR.submit(run);
        long key = COUNT.addAndGet(1);
        FUTURE_ARRAY.put(key, future);
        return key;
    }


    public static long runOnBackgroundThread(Runnable run, long delay) {
        Future future = THREAD_POOL_EXECUTOR.submit(() -> {
            SystemClock.sleep(delay);
            run.run();

        });
        long key = COUNT.addAndGet(1);
        FUTURE_ARRAY.put(key, future);
        return key;
    }

    public static boolean cancel(long key){
        Future future = FUTURE_ARRAY.get(key);
        if (future == null) {
            return false;
        }

        boolean isSuccess = future.cancel(true);
        if (isSuccess) {
            FUTURE_ARRAY.remove(key);
        }
        return isSuccess;
    }

    public static void shutdown(){
        THREAD_POOL_EXECUTOR.shutdown();
    }



    /**
     * 自定义名字的线程工厂
     */
    private static class NameThreadFactory implements ThreadFactory {
        private AtomicInteger count = new AtomicInteger(0);

        @Override
        public Thread newThread(@NonNull Runnable r) {
            Thread thread = new Thread(r);
            String threadName = "ThreadUtil-" + count.addAndGet(1);
            thread.setName(threadName);
            return thread;
        }
    }
}
