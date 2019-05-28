package com.yuemai.game34999.presentaion.ui.test.download;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yannis.common.util.UIUtils;
import com.yuemai.game34999.R;
import com.yuemai.game34999.data.bean.GameInfo;
import com.yuemai.game34999.presentaion.base.BaseRecycleHolder;
import com.yuemai.game34999.presentaion.download.DownloadManager;
import com.yuemai.game34999.presentaion.download.progress.DownloadListener;
import com.yuemai.game34999.presentaion.download.progress.Progress;
import com.yuemai.game34999.presentaion.download.task.DownloadTask;
import com.yuemai.game34999.presentaion.image.ImageLoader;
import com.yuemai.game34999.presentaion.widget.DownloadProgressButton;
import com.yuemai.game34999.util.ApkUtils;

import java.io.File;

import butterknife.BindView;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/13  17:25
 * @email : 923080261@qq.com
 * @description :
 */
public class DownloadHolder extends BaseRecycleHolder<DownloadTask> {
    public static String TAG = DownloadHolder.class.getSimpleName();
    @BindView(R.id.iv_icon)
    ImageView mImageView;
    @BindView(R.id.tv_gameName)
    TextView tvGameName;
    @BindView(R.id.tv_gameInfo)
    TextView tvGameInfo;
    @BindView(R.id.btn_download)
    DownloadProgressButton btnDownload;

    DownloadHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void refreshViewHolder(DownloadTask task) {
        Progress progress = task.progress;
        GameInfo gameInfo = (GameInfo) progress.extra1;
        tvGameName.setText(gameInfo.getGname());
        ImageLoader.loadGameIcon(mContext, gameInfo.getGicon(), mImageView);
        btnDownload.setTag(task.progress.tag);
        btnDownload.setOnDownLoadClickListener(new DownloadButtonListener(btnDownload, gameInfo));
        int percent = (int) (task.progress.fraction * 100);
        //注册下载监听 如果为空则表示还没有注册
        ApkDownloadTestListener listener = (ApkDownloadTestListener) task.listeners.get(DownloadListener.createTag(TAG, gameInfo.getAndroidfileurl()));
        if (listener == null) {
            listener = new ApkDownloadTestListener<DownloadProgressButton>(btnDownload, TAG, gameInfo.getAndroidfileurl()) {
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
            };
            task.register(listener);
        }
        switch (task.progress.status) {
            case Progress.NONE:
                //下载
                if (task.progress.fraction > Progress.MIN_PROGRESS && task.progress.fraction < Progress.MAX_PROGRESS) {
                    btnDownload.setProgress(percent);
                }
                btnDownload.pause();
                tvGameInfo.setText("停止");
                break;
            case Progress.PAUSE:
                btnDownload.setProgress(percent);
                btnDownload.pause();
                tvGameInfo.setText("暂停");
                break;
            case Progress.ERROR:
                btnDownload.reset();
                tvGameInfo.setText("下载出错");
                break;
            case Progress.WAITING:
                btnDownload.waiting();
                tvGameInfo.setText("等待中");
                break;
            case Progress.FINISH:
                btnDownload.finish();
                tvGameInfo.setText("下载完成");
                break;
            case Progress.LOADING:
                btnDownload.setProgress(percent);
                String speed = Formatter.formatFileSize(mContext, progress.speed);
                tvGameInfo.setText("正在下载" + " - " + String.format("%s/s", speed));
                break;
            default:
                break;
        }

    }

    private class DownloadButtonListener implements DownloadProgressButton.OnDownLoadClickListener {

        private DownloadProgressButton btn;
        private GameInfo gameInfo;

        DownloadButtonListener(DownloadProgressButton btn, GameInfo gameInfo) {
            this.btn = btn;
            this.gameInfo = gameInfo;
        }

        @Override
        public void clickDownload() {
            //点击下载
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

            if (ApkUtils.isAvailable(mContext, new File(filePath))) {
                ApkUtils.uninstall(mContext, ApkUtils.getPackageName(mContext, filePath));
            } else {
                ApkUtils.install(mContext, new File(filePath));
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
            DownloadManager.request(gameInfo.getAndroidfileurl())
                    .extra1(gameInfo)
                    .save()
                    .register(new ApkDownloadTestListener<DownloadProgressButton>(btn, TAG, gameInfo.getAndroidfileurl()) {
                        @Override
                        public void downloadError(Progress progress) {
                            tvGameInfo.setText("下载出错");
                        }

                        @Override
                        public void downloadFinish(File file, Progress progress) {
                            tvGameInfo.setText("下载完成");
                        }

                        @Override
                        public void downloadTaskRemove(Progress progress) {

                        }

                        @Override
                        public void downloadStart(Progress progress) {
                            tvGameInfo.setText("开始下载");
                        }

                        @Override
                        public void downloading(Progress progress) {
                            String speed = Formatter.formatFileSize(mContext, progress.speed);
                            tvGameInfo.setText("正在下载" + " - " + String.format("%s/s", speed));
                        }
                    })
                    .start();
        }
    }
}