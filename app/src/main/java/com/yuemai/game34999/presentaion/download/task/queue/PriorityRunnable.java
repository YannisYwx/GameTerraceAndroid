package com.yuemai.game34999.presentaion.download.task.queue;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/4  11:58
 * @email : 923080261@qq.com
 * @description : Runnable优先级别封装
 */
public class PriorityRunnable extends PriorityObject<Runnable> implements Runnable {

    public PriorityRunnable(int priority, Runnable obj) {
        super(priority, obj);
    }

    @Override
    public void run() {
        obj.run();
    }
}
