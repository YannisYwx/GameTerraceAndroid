package com.yuemai.game34999.presentaion.ui.test.download;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.orhanobut.logger.Logger;
import com.yannis.common.util.Preconditions;
import com.yannis.common.util.UIUtils;
import com.yuemai.game34999.data.bean.GameInfo;
import com.yuemai.game34999.presentaion.download.net.exception.StorageException;
import com.yuemai.game34999.presentaion.download.progress.DownloadListener;
import com.yuemai.game34999.presentaion.download.progress.Progress;
import com.yuemai.game34999.presentaion.widget.DownloadProgressButton;

import java.io.File;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/18  14:31
 * @email : 923080261@qq.com
 * @description : apk下载监听
 */
public abstract class ApkDownloadTestListener<T extends View> extends DownloadListener {
    private static final String TAG = ApkDownloadTestListener.class.getSimpleName();

    public DownloadProgressButton downloadButton;
    public ProgressBar progressBar;

    public ApkDownloadTestListener(Object tag, T view) {
        super(Preconditions.checkNotNull(tag, "tag = null"));
        Preconditions.checkNotNull(view, "DownloadProgressButton = null");
        if (view instanceof DownloadProgressButton) {
            downloadButton = (DownloadProgressButton) view;
            downloadButton.setMaxProgress(100);
        }
        if (view instanceof ProgressBar) {
            progressBar = (ProgressBar) view;
            progressBar.setMax(100);
        }
    }

    public ApkDownloadTestListener(T view, String... tagArgs) {
        this(createTag(tagArgs), view);
    }

    @Override
    public void onStart(Progress progress) {
        if (isCurrentItem(progress)) {
            downloadButton.setProgress(progress.fraction * 100);
            downloadStart(progress);
        }
    }

    @Override
    public void onProgress(Progress progress) {
        GameInfo gameInfo = (GameInfo) progress.extra1;
        Progress.log(TAG + "-" + gameInfo.getGname(), progress);
        String btnTag = (String) downloadButton.getTag();
        StringBuilder sb = new StringBuilder("下载Apk ->")
                .append(gameInfo.getGname()).append("\n")
                .append("按钮Tag = ").append(btnTag).append("\n")
                .append("监听Tag = ").append(tag).append("\n")
                .append("下载Tag = ").append(progress.tag).append("\n");
        String msg = sb.toString();
        Log.e(TAG, msg);
        if (isCurrentItem(progress)) {
            if (progress.status != Progress.PAUSE) {
                //设置按钮进度
                if (downloadButton != null) {
                    downloadButton.setProgress(progress.fraction * 100);
                }
                if (progressBar != null) {
                    progressBar.setProgress((int) (progress.fraction * 100));
                }
                Log.e(TAG, gameInfo.getGname() + " 下载进度 = " + downloadButton.getProgress() + " 状态 = " + Progress.status(progress.status));
//                Logger.e(gameInfo.getGname() + " 下载进度 = " + downloadButton.getProgress() + " 状态 = " + Progress.status(progress.status));
                downloading(progress);
            }
        }
    }

    @Override
    public void onError(Progress progress) {
        GameInfo gameInfo = (GameInfo) progress.extra1;
        StringBuilder sb = new StringBuilder("ApkDownloadTestListener 下载出错了 -->");
        sb.append("apk --> \n{\n").append("游戏名 = ").append(gameInfo.getGname()).append("\n")
                .append("下载地址 = ").append(gameInfo.getAndroidfileurl()).append("\n}\n");
        if (progress.exception != null) {
            sb.append(" 【 Exception = ").append(progress.exception.toString()).append("\n")
                    .append("ErrorMsg = ").append(progress.exception.getMessage()).append("】");
        } else {
            sb.append("未知错误");
        }
        Logger.e(sb.toString());
        if (isCurrentItem(progress)) {
            UIUtils.showToast("下载出错");
            downloadError(progress);
            if (progress.exception != null && progress.exception instanceof StorageException) {
                //6.0权限
            }
        }
    }

    @Override
    public void onFinish(File file, Progress progress) {
        if (isCurrentItem(progress)) {
            downloadButton.finish();
            UIUtils.showToast("下载完成 path = " + file.getAbsolutePath());
            downloadFinish(file, progress);
        }

    }

    @Override
    public void onRemove(Progress progress) {
        if (isCurrentItem(progress)) {
            downloadTaskRemove(progress);
        }
    }

    /**
     * 设置View 的tag标识
     *
     * @param tag
     */
    public void setViewTag(String tag) {
        if (downloadButton != null) {
            String oldTag = (String) downloadButton.getTag();
            Log.e(TAG, "更新Tag \n 旧 = " + oldTag + "\n 新 = " + tag);
            downloadButton.setTag(tag);
        } else {
            progressBar.setTag(tag);
        }
    }

    public boolean isCurrentItem(Progress progress) {
        String viewTag;
        if (downloadButton != null) {
            viewTag = (String) downloadButton.getTag();
        } else {
            viewTag = (String) progressBar.getTag();
        }
        Log.e(TAG, "isCurrentItem = {\n 按钮Tag = " + viewTag + "\n 下载监听Tag = " + progress.tag + "\n}");
        return viewTag.equals(progress.tag);
    }

    /**
     * 正在下载
     *
     * @param progress 下载详情
     */
    public abstract void downloading(Progress progress);

    /**
     * 下载出错
     *
     * @param progress 下载详情
     */
    public abstract void downloadError(Progress progress);

    /**
     * 正在下载
     *
     * @param file     下载完成的文件
     * @param progress 下载详情
     */
    public abstract void downloadFinish(File file, Progress progress);

    /**
     * 下载被移除
     *
     * @param progress 下载详情
     */
    public abstract void downloadTaskRemove(Progress progress);

    /**
     * 开始下载
     *
     * @param progress 下载详情
     */
    public abstract void downloadStart(Progress progress);
}
