package com.yuemai.game34999.presentaion.ui.classify.holder;

import android.text.TextUtils;
import android.text.format.Formatter;

import com.orhanobut.logger.Logger;
import com.yannis.common.util.UIUtils;
import com.yuemai.game34999.data.bean.GameInfo;
import com.yuemai.game34999.presentaion.download.ApkDownloadListener;
import com.yuemai.game34999.presentaion.download.DownloadManager;
import com.yuemai.game34999.presentaion.download.progress.DownloadListener;
import com.yuemai.game34999.presentaion.download.progress.Progress;
import com.yuemai.game34999.presentaion.download.task.DownloadTask;
import com.yuemai.game34999.presentaion.image.ImageLoader;
import com.yuemai.game34999.presentaion.ui.game.activity.GameInfoActivity;
import com.yuemai.game34999.presentaion.ui.main.adapter.AbstractCommonItemHolder;
import com.yuemai.game34999.presentaion.widget.CommonItemView;
import com.yuemai.game34999.presentaion.widget.DownloadProgressButton;
import com.yuemai.game34999.util.ApkUtils;
import com.yuemai.game34999.util.AppFormatUtils;

import java.io.File;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/18  17:46
 * @email : 923080261@qq.com
 * @description :游戏Item holder
 */
public class DownloadApkHolder extends AbstractCommonItemHolder<GameInfo> {
    public static final String TAG = DownloadApkHolder.class.getSimpleName();

    public DownloadApkHolder(CommonItemView itemView) {
        super(itemView);
    }

    @Override
    public void onItemClick() {
        GameInfoActivity.start(mItemView.getContext());
    }

    @Override
    protected void refreshViewHolder(GameInfo data) {
        Logger.e("refreshViewHolder --- " + data.toString());
        String downloadInfo;
        //设置游戏名
        mItemView.setTopText(data.getGname());
        //设置游戏简介
        mItemView.setBottomText(data.getGtitle());
        mItemView.setTag(data.getAndroidfileurl());
        ImageLoader.loadGameIcon(mItemView.getContext(), data.getGicon(), mItemView.getIvIcon());
        mProgressButton.setOnDownLoadClickListener(new DownloadButtonListener(mItemView, data));
        String apkUrl = data.getAndroidfileurl();
        DownloadTask task = null;
        if (!TextUtils.isEmpty(apkUrl)) {
            task = DownloadManager.getInstance().getTask(data.getAndroidfileurl());
        }
        if (task != null) {
            Logger.e("refreshViewHolder-----------Progress = " + task.progress.toString());
            downloadInfo = String.format("%s/%s", Formatter.formatFileSize(mContext, task.progress.currentSize), Formatter.formatFileSize(mContext, Integer.valueOf(data.getAndroidfilesize())));
            //任务进行中
            switch (task.progress.status) {
                case Progress.NONE:
                    //下载
                    if (task.progress.fraction > Progress.MIN_PROGRESS && task.progress.fraction < Progress.MAX_PROGRESS) {
                        mItemView.setBtnProgress(task.progress.percent);
                    }
                    mItemView.getBtnDownload().pause();
                    break;
                case Progress.PAUSE:
                    mItemView.setNoticeMsg(CommonItemView.Notice.PAUSED.getMsg(), false);
                    mItemView.getBtnDownload().setProgress(task.progress.percent);
                    mItemView.getBtnDownload().pause();
                    break;
                case Progress.ERROR:
                    mItemView.getBtnDownload().reset();
                    downloadInfo = AppFormatUtils.formatDownloadInfo(data);
                    mItemView.setNoticeMsg(Progress.errorMsg(task.progress.errorCode));
                    break;
                case Progress.WAITING:
                    mItemView.setNoticeMsg(CommonItemView.Notice.WAITING.getMsg(), false);
                    mItemView.getBtnDownload().waiting();
                    break;
                case Progress.FINISH:
                    mItemView.getBtnDownload().finish();
                    mItemView.setNoticeMsg(CommonItemView.Notice.DOWNLOAD_FINISHED.getMsg(), false);
                    break;
                case Progress.LOADING:
                    mItemView.getBtnDownload().setProgress(task.progress.percent);
                    mItemView.setSpeed(task.progress.speed);
                    break;
                default:
                    break;
            }
            //添加下载监听
            ApkDownloadListener listener = (ApkDownloadListener) task.listeners.get(DownloadListener.createTag(TAG, task.progress.tag));
            if (listener == null) {
                listener = new ApkDownloadingListener(DownloadListener.createTag(TAG, data.getAndroidfileurl()), mItemView);
                task.register(listener);
            } else {
                listener.mCommonItemView = mItemView;
            }
        } else {
            if (!TextUtils.isEmpty(apkUrl)) {
                mItemView.getBtnDownload().reset();
            } else {
                mItemView.getBtnDownload().disenable();
            }
            downloadInfo = AppFormatUtils.formatDownloadInfo(data);
            mItemView.setNoticeMsg("");
        }
        //设置下载详情
        mItemView.setMiddleText(downloadInfo);
    }

    private class DownloadButtonListener implements DownloadProgressButton.OnDownLoadClickListener {

        private CommonItemView commonItemView;
        private GameInfo gameInfo;

        DownloadButtonListener(CommonItemView commonItemView, GameInfo gameInfo) {
            this.commonItemView = commonItemView;
            this.gameInfo = gameInfo;
        }

        @Override
        public void clickDownload() {
            startDownload();
        }

        @Override
        public void clickPause() {
            DownloadManager.getInstance().getTask(gameInfo.getAndroidfileurl()).pause();
        }

        @Override
        public void clickResume() {
            startDownload();
        }

        @Override
        public void clickFinish() {

            String filePath = DownloadManager.getInstance().getTask(gameInfo.getAndroidfileurl()).progress.filePath;
            UIUtils.showToast(filePath);

            if (ApkUtils.isAvailable(mItemView.getContext(), new File(filePath))) {
                ApkUtils.uninstall(mItemView.getContext(), ApkUtils.getPackageName(mItemView.getContext(), filePath));
            } else {
                ApkUtils.install(mItemView.getContext(), new File(filePath));
            }

        }

        @Override
        public void clickDisenable() {

        }

        /**
         * 开始下载
         * 开始一个新的下载任务 或者恢复一个下载
         * 1:等待状态从0开始下载
         * 2:断点下载
         */
        private void startDownload() {
            if (TextUtils.isEmpty(gameInfo.getAndroidfileurl())) {
                commonItemView.getTvDownloadSpeed().setText("文件不存在");
                commonItemView.getBtnDownload().reset();
                return;
            }

            DownloadTask task = DownloadManager.request(gameInfo.getAndroidfileurl());
            if (task.listeners.get(DownloadListener.createTag(TAG, gameInfo.getAndroidfileurl())) == null) {
                task.register(new ApkDownloadingListener(DownloadListener.createTag(TAG, gameInfo.getAndroidfileurl()), commonItemView));
            }
            task.extra1(gameInfo).save().start();
        }
    }

    public class ApkDownloadingListener extends ApkDownloadListener {


        ApkDownloadingListener(Object tag, CommonItemView view) {
            super(tag, view);
        }

        @Override
        public void downloading(Progress progress) {

        }

        @Override
        public void downloadError(Progress progress) {

        }

        @Override
        public void downloadFinish(File file, Progress progress) {

        }

        @Override
        public void downloadTaskRemove(Progress progress) {

        }

        @Override
        public void downloadStart(Progress progress) {

        }
    }
}
