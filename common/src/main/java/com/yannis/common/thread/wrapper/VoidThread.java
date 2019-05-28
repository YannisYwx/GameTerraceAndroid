package com.yannis.common.thread.wrapper;

import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/16  16:09
 * @email : 923080261@qq.com
 * @description : 一个没有返回的线程
 */
public abstract class VoidThread implements ThreadDelegate {

    private final Runnable mThread;
    private long mKey;

    public VoidThread() {
        mThread = new Runnable() {
            @Override
            public void run() {
                doInBackground();
                deliverEnd();
            }
        };
    }

    /**
     * 执行结束
     */
    private void deliverEnd() {
        ThreadUtils.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                onPostExecute();
            }
        });
    }

    /**
     * 线程需要执行的内容
     */
    @WorkerThread
    public abstract void doInBackground();

    /**
     * 这个方法执行在UI线程
     */
    @UiThread
    public abstract void onPostExecute();

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
