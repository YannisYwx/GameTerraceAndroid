package com.yuemai.game34999.presentaion.download.progress;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/4  14:36
 * @email : 923080261@qq.com
 * @description : 下载进度监听
 */
public interface ProgressListener<T> {
    /**
     * 成功添加任务的回调
     *
     * @param progress 下载详情
     */
    void onStart(Progress progress);

    /**
     * 下载进行时回调
     *
     * @param progress 下载详情
     */
    void onProgress(Progress progress);

    /**
     * 下载出错时回调
     *
     * @param progress 下载详情
     */
    void onError(Progress progress);

    /**
     * 下载完成时回调
     *
     * @param progress 下载详情
     */
    void onFinish(T t, Progress progress);

    /**
     * 被移除时回调
     *
     * @param progress 下载详情
     */
    void onRemove(Progress progress);
}
