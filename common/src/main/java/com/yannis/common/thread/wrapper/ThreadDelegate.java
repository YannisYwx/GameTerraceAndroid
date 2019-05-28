package com.yannis.common.thread.wrapper;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/16  15:54
 * @email : 923080261@qq.com
 * @description :
 */
public interface ThreadDelegate {

    long start();

    boolean cancel(long key);

}
