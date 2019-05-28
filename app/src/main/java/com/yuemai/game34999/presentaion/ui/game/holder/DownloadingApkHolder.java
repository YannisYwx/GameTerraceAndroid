package com.yuemai.game34999.presentaion.ui.game.holder;

import android.view.View;

import com.orhanobut.logger.Logger;
import com.yannis.common.rx.RxBus;
import com.yannis.common.util.UIUtils;
import com.yuemai.game34999.data.bean.GameInfo;
import com.yuemai.game34999.data.event.DownloadTaskEvent;
import com.yuemai.game34999.presentaion.download.ApkDownloadListener;
import com.yuemai.game34999.presentaion.download.DownloadManager;
import com.yuemai.game34999.presentaion.download.progress.DownloadListener;
import com.yuemai.game34999.presentaion.download.progress.Progress;
import com.yuemai.game34999.presentaion.download.task.DownloadTask;
import com.yuemai.game34999.presentaion.image.ImageLoader;
import com.yuemai.game34999.presentaion.ui.main.adapter.AbstractCommonItemHolder;
import com.yuemai.game34999.presentaion.widget.CommonItemView;
import com.yuemai.game34999.util.ApkUtils;

import java.io.File;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/18  18:37
 * @email : 923080261@qq.com
 * @description : 下载管理界面的正下载下载的apk viewHolder
 */
public class DownloadingApkHolder extends AbstractCommonItemHolder<DownloadTask> implements View.OnClickListener {
    public static String TAG = DownloadingApkHolder.class.getSimpleName();

    public DownloadingApkHolder(CommonItemView itemView) {
        super(itemView);
    }

    @Override
    public void onItemClick() {

    }

    @Override
    protected void refreshViewHolder(DownloadTask task) {
        GameInfo gameInfo = (GameInfo) task.progress.extra1;
        //设置游戏名
        mItemView.setTopText(gameInfo.getGname());
        ImageLoader.loadGameIcon(mContext, gameInfo.getGicon(), mItemView.getIvIcon());
        mItemView.setTag(gameInfo.getAndroidfileurl());
        switch (task.progress.status) {
            case Progress.NONE:
                //无状态
            case Progress.PAUSE:
                //暂停
                refreshPauseInfo(task.progress);
                break;
            case Progress.ERROR:
                //出错
                refreshErrorInfo(task.progress);
                break;
            case Progress.WAITING:
                //等待
                refreshWaitingInfo(task.progress);
                break;
            case Progress.FINISH:
                //下载完成
                mItemView.setItemType(CommonItemView.Type.downloadFinished);
                refreshFinishInfo(task.progress);
                break;
            case Progress.LOADING:
                //正在下载
                refreshDownloadingInfo(task.progress);
                break;
            default:
                break;
        }
        //注册下载监听 如果为空则表示还没有注册

        //添加下载监听
        ApkDownloadListener listener = (ApkDownloadListener) task.listeners.get(DownloadListener.createTag(TAG, task.progress.tag));
        if (listener == null) {
            listener = new ApkDownloadingListener(DownloadListener.createTag(TAG, gameInfo.getAndroidfileurl()), mItemView);
            task.register(listener);
        } else {
            listener.mCommonItemView = mItemView;
        }
        mItemView.getNormalBtn().setOnClickListener(this);
        mItemView.getTvRight().setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //按钮点击
        if (v == mItemView.getNormalBtn()) {
            switch (data.progress.status) {
                case Progress.NONE:
                case Progress.PAUSE:
                    //继续下载
                    data.start();
                    break;
                case Progress.ERROR:
                    //重新下载
                    data.restart();
                    break;
                case Progress.WAITING:
                    //暂停
                    data.pause();
                    break;
                case Progress.FINISH:
                    //安装
                    installAPK();
                    break;
                case Progress.LOADING:
                    //暂停
                    data.pause();
                    break;
                default:
                    break;
            }
        }
        //删除
        if (v == mItemView.getTvRight()) {
            //通知刷新界面
            Logger.e("----------------通知删除");
            RxBus.getInstance().send(new DownloadTaskEvent(DownloadTaskEvent.EVENT_TASK_DELETE, data));
        }

    }

    /**
     * 安装apk
     */
    private void installAPK() {
        GameInfo gameInfo = (GameInfo) data.progress.extra1;
        String filePath = DownloadManager.getInstance().getTask(gameInfo.getAndroidfileurl()).progress.filePath;
        UIUtils.showToast(filePath);

        if (ApkUtils.isAvailable(mContext, new File(filePath))) {
            ApkUtils.uninstall(mContext, ApkUtils.getPackageName(mContext, filePath));
        } else {
            ApkUtils.install(mContext, new File(filePath));
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
