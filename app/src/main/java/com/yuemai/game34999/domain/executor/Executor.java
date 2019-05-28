package com.yuemai.game34999.domain.executor;

import com.yuemai.game34999.domain.interactors.abs.ReturnTask;
import com.yuemai.game34999.domain.interactors.abs.VoidTask;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/11/21  18:36
 * @email : 923080261@qq.com
 * @description :
 */
public interface Executor<T> {

    /**
     * execute the task
     *
     * @param voidTask task
     * @return the key of task
     */
    long execute(final VoidTask voidTask);

    /**
     * execute the task
     *
     * @param returnTask task
     * @return the key of task
     */
    long execute(final ReturnTask<T> returnTask);

    /**
     * cancel the task by key
     *
     * @param key key
     * @return true:success false:fail
     */
    boolean cancel(long key);

}
