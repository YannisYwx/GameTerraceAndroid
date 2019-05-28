package com.yuemai.game34999.presentaion.download.net.callback;


import android.support.annotation.UiThread;

import com.yuemai.game34999.presentaion.download.net.request.GetRequest;
import com.yuemai.game34999.presentaion.download.progress.Progress;
import com.yuemai.game34999.presentaion.download.mode.Response;
import com.yuemai.game34999.presentaion.download.net.callback.convert.Converter;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/4  18:19
 * @email : 923080261@qq.com
 * @description : 网络回调
 */
public interface Callback<T> extends Converter<T> {

    /**
     * 请求网络开始前
     * @param getRequest 请求包装体
     */
    @UiThread
    void onStart(GetRequest<T> getRequest);

    /**
     * 网络处理成功
     * @param response 请求响应体
     */
    @UiThread
    void onSuccess(Response<T> response);

    /**
     * 请求失败，响应错误，数据解析错误等，都会回调该方法
     * @param response 请求响应体
     */
    @UiThread
    void onError(Response<T> response);

    /**
     * 请求网络结束后，UI线程
     */
    @UiThread
    void onFinish();

    /**
     *  下载过程中的进度回调
     * @param progress 下载的进度
     */
    @UiThread
    void downloadProgress(Progress progress);
}
