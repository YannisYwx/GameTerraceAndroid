package com.yuemai.game34999.presentaion.download;

import android.util.Log;

import com.orhanobut.logger.Logger;
import com.yannis.common.rx.RxBus;
import com.yannis.common.util.Preconditions;
import com.yannis.common.util.UIUtils;
import com.yuemai.game34999.data.bean.GameInfo;
import com.yuemai.game34999.data.event.DownloadTaskEvent;
import com.yuemai.game34999.presentaion.download.net.exception.StorageException;
import com.yuemai.game34999.presentaion.download.progress.DownloadListener;
import com.yuemai.game34999.presentaion.download.progress.Progress;
import com.yuemai.game34999.presentaion.widget.CommonItemView;
import com.yuemai.game34999.presentaion.widget.DownloadProgressButton;
import com.yuemai.game34999.util.AppFormatUtils;

import java.io.File;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/18  14:31
 * @email : 923080261@qq.com
 * @description : apk下载监听
 */
public abstract class ApkDownloadListener extends DownloadListener {
    private static final String TAG = ApkDownloadListener.class.getSimpleName();
    public CommonItemView mCommonItemView;
    private @CommonItemView.Type
    int type;

    public ApkDownloadListener(Object tag, CommonItemView view) {
        super(Preconditions.checkNotNull(tag, "tag = null"));
        mCommonItemView = Preconditions.checkNotNull(view, "DownloadProgressButton = null");
        type = mCommonItemView.getType();
    }

    public ApkDownloadListener(CommonItemView view, String... tagArgs) {
        this(createTag(tagArgs), view);
    }

    @Override
    public void onStart(Progress progress) {
        if (isCurrentItem(progress)) {
            refreshProgress(progress);
            downloadStart(progress);
        }
    }

    @Override
    public void onProgress(Progress progress) {
        GameInfo gameInfo = (GameInfo) progress.extra1;
        Progress.log(TAG + "-" + gameInfo.getGname(), progress);
        String btnTag = (String) mCommonItemView.getTag();
        StringBuilder sb = new StringBuilder("下载Apk ->")
                .append(gameInfo.getGname()).append("\n")
                .append("按钮Tag = ").append(btnTag).append("\n")
                .append("监听Tag = ").append(tag).append("\n")
                .append("下载Tag = ").append(progress.tag).append("\n");
        String msg = sb.toString();
        Log.e(TAG, msg);
        if (isCurrentItem(progress)) {
            refreshProgress(progress);
            downloading(progress);
            Log.e(TAG, gameInfo.getGname() + " 下载进度 = " + progress.fraction + " 状态 = " + Progress.status(progress.status));
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
            refreshProgress(progress);
            UIUtils.showToast("下载出错");
            downloadError(progress);
            if (progress.exception != null && progress.exception instanceof StorageException) {
                //6.0权限
            }
        }
    }

    @Override
    public void onFinish(File file, Progress progress) {
        RxBus.getInstance().send(new DownloadTaskEvent(DownloadTaskEvent.EVENT_TASK_FINISH, DownloadManager.getInstance().getTask(progress.tag)));
        if (isCurrentItem(progress)) {
            refreshProgress(progress);
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
     * 刷新视图控件
     *
     * @param progress 下载的详情
     */
    private void refreshProgress(Progress progress) {
        GameInfo gameInfo = (GameInfo) progress.extra1;
        int percent = progress.percent;
        switch (progress.status) {
            case Progress.NONE:
                //无状态
                break;
            case Progress.WAITING:
                //等待
                if (type == CommonItemView.Type.downloadButton) {
                    //TO-DO
                    mCommonItemView.getBtnDownload().waiting();
                } else if (type == CommonItemView.Type.downloading) {
                    mCommonItemView.setNoticeMsg(CommonItemView.Notice.WAITING.getMsg(), false);
                }
                mCommonItemView.setNoticeMsg(CommonItemView.Notice.WAITING.getMsg(), false);
                Progress.log("等待中...", progress);
                Logger.e("-------------等待中.....type = " + type);
                break;
            case Progress.LOADING:
                //下载中
                if (type == CommonItemView.Type.downloadButton) {
                    //------------------------------下载按钮状态
                    //设置按钮进度
                    mCommonItemView.setBtnProgress(percent);
                } else if (type == CommonItemView.Type.downloading) {
                    //------------------------------下载进度条状态
                    //设置进度条进度
                    mCommonItemView.setProgress(percent);
                    mCommonItemView.setNormalButtonText(CommonItemView.Notice.PAUSE);
                }
                //设置下载速度
                mCommonItemView.setSpeed(progress.speed);
                //设置当前下载与总大小占比
                mCommonItemView.setApkDownloadPercent(progress.currentSize, progress.totalSize);
                break;
            case Progress.PAUSE:
                //暂停
                if (type == CommonItemView.Type.downloadButton) {
                    mCommonItemView.setNoticeMsg(CommonItemView.Notice.PAUSED.getMsg(), false);
                } else if (type == CommonItemView.Type.downloading) {
                    mCommonItemView.setProgress(percent);
                    mCommonItemView.setNormalButtonText(CommonItemView.Notice.CONTINUE);
                }
                break;
            case Progress.ERROR:
                //下载出错
                if (type == CommonItemView.Type.downloadButton) {
                    //按钮设置为完成状态
                    mCommonItemView.setDownloadButtonStatus(DownloadProgressButton.ButtonStatus.NORMAL);
                } else if (type == CommonItemView.Type.downloading) {
                    mCommonItemView.setProgress(0);
                    mCommonItemView.setMiddleText(AppFormatUtils.formatDownloadInfo(gameInfo));
                    mCommonItemView.setNormalButtonText(CommonItemView.Notice.DOWNLOAD);
                }
                mCommonItemView.setNoticeMsg(Progress.errorMsg(progress.errorCode));
                //从任务列表中移除任务
                DownloadManager.getInstance().removeTask(progress.tag);
                break;
            case Progress.FINISH:
                //下载完成
                if (type == CommonItemView.Type.downloadButton) {
                    //按钮设置为完成状态
                    mCommonItemView.getBtnDownload().finish();
                    mCommonItemView.setNoticeMsg(CommonItemView.Notice.DOWNLOAD_FINISHED.getMsg(), false);
                    //设置当前下载与总大小占比
                    mCommonItemView.setApkDownloadPercent(progress.totalSize, progress.totalSize);
                } else if (type == CommonItemView.Type.downloading) {
                    mCommonItemView.setProgress(percent);
                    mCommonItemView.setNormalButtonText(CommonItemView.Notice.INSTALL);
                }
                break;
            default:
                break;
        }
    }


    /**
     * 根据tag标识是否是当前的Item需要更新状态
     *
     * @param progress 下载状态
     * @return true 是当前的可见可以更新的item
     */
    private boolean isCurrentItem(Progress progress) {
        String viewTag = (String) mCommonItemView.getTag();
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
