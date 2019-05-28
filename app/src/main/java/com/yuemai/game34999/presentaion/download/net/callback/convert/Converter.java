package com.yuemai.game34999.presentaion.download.net.callback.convert;

import android.support.annotation.WorkerThread;

import okhttp3.Response;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/4  18:20
 * @email : 923080261@qq.com
 * @description :网络数据转换器
 */
public interface Converter<T> {

    /**
     * 拿到响应后，将数据转换成需要的格式，子线程中执行，可以是耗时操作
     *
     * @param response 需要转换的对象
     * @return 转换后的结果
     * @throws Throwable 转换过程发生的异常
     */
    @WorkerThread
    T convertResponse(Response response) throws Throwable;
}
