package com.yannis.common.thread.wrapper;

import android.support.annotation.CheckResult;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/16  16:09
 * @email : 923080261@qq.com
 * @description : 一个有返回值的线程
 */
public abstract class ReturnThread<T> implements ThreadDelegate {

    private final Runnable mThread;
    private long mKey;

    public ReturnThread() {
        mThread = new Runnable() {
            @Override
            public void run() {
                T result = doInBackground();
                deliverEnd(result);
            }
        };
    }

    /**
     * 执行结束
     * @param result 结果
     */
    private void deliverEnd(final T result) {
        ThreadUtils.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                onPostExecute(result);
            }
        });
    }

    /**
     * 线程需要执行的内容
     */
    @CheckResult
    @WorkerThread
    public abstract T doInBackground();

    /**
     * 这个方法执行在UI线程
     *
     * @param result 执行结果
     */
    @UiThread
    public abstract void onPostExecute(T result);

    @Override
    public long start() {
        mKey = ThreadUtils.execute(mThread);
        return mKey;
    }

    @Override
    public boolean cancel(long key) {
        return ThreadUtils.cancel(key);
    }
}
