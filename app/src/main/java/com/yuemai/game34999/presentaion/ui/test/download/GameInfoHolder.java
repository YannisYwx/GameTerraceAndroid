package com.yuemai.game34999.presentaion.ui.test.download;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.yannis.common.util.UIUtils;
import com.yuemai.game34999.R;
import com.yuemai.game34999.data.bean.GameInfo;
import com.yuemai.game34999.presentaion.base.BaseRecycleHolder;
import com.yuemai.game34999.presentaion.download.DownloadManager;
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
public class GameInfoHolder extends BaseRecycleHolder<GameInfo> {
    public static final String TAG = GameInfoHolder.class.getSimpleName();
    @BindView(R.id.iv_icon)
    ImageView mImageView;
    @BindView(R.id.tv_gameName)
    TextView tvGameName;
    @BindView(R.id.tv_gameInfo)
    TextView tvGameInfo;
    @BindView(R.id.btn_download)
    DownloadProgressButton btnDownload;

    GameInfoHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void refreshViewHolder(GameInfo data) {
        tvGameName.setText(data.getGname());
        ImageLoader.loadGameIcon(mContext, data.getGicon(), mImageView);
        btnDownload.setTag(data.getAndroidfileurl());
        btnDownload.setOnDownLoadClickListener(new DownloadButtonListener(btnDownload, data));
        DownloadTask task = DownloadManager.getInstance().getTask(data.getAndroidfileurl());
        if (task != null) {
            Logger.e("refreshViewHolder---Progress = " + task.progress.toString());
            //任务进行中
            int percent = task.progress.percent;
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
                    tvGameInfo.setText("正在下载" + task.progress.speed + "/s");
                    break;
                default:
                    break;
            }
        } else {
            btnDownload.reset();
            tvGameInfo.setText("");
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
                            tvGameInfo.setText("下载出错 msg = " + progress.exception.getMessage());
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
