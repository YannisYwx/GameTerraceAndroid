package com.yuemai.game34999.presentaion.download.task.queue;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/4  11:49
 * @email : 923080261@qq.com
 * @description : 具有优先级对象的公共类
 */
public class PriorityObject<E> {

    public final int priority;
    public final E obj;

    public PriorityObject(int priority, E obj) {
        this.priority = priority;
        this.obj = obj;
    }
}
