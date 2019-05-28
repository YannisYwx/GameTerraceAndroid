package com.yuemai.game34999.presentaion.ui.main.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.widget.CheckBox;

import com.yuemai.game34999.data.bean.GameInfo;
import com.yuemai.game34999.presentaion.base.impl.BaseRecycleHolderImpl;
import com.yuemai.game34999.presentaion.download.progress.Progress;
import com.yuemai.game34999.presentaion.widget.CommonItemView;
import com.yuemai.game34999.presentaion.widget.DownloadProgressButton;

import butterknife.ButterKnife;

import static com.yuemai.game34999.core.Constants.EXTRA;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/27  14:17
 * @email : 923080261@qq.com
 * @description : 通用的item Holder
 */
public abstract class AbstractCommonItemHolder<T> extends RecyclerView.ViewHolder implements BaseRecycleHolderImpl<T> {
    protected DownloadProgressButton mProgressButton;
    private CheckBox mCheckBox;
    protected CommonItemView mItemView;
    protected T data;
    protected Context mContext;
    protected
    @CommonItemView.Type
    int itemType = CommonItemView.Type.normal;

    public AbstractCommonItemHolder(CommonItemView itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mItemView = itemView;
        mContext = itemView.getContext();
        itemType = itemView.getItemType();
        mProgressButton = itemView.getBtnDownload();
        mCheckBox = itemView.getCheckBox();
        itemView.setOnClickListener(view -> onCommonItemClick());
    }


    /**
     * 设置当前下载的大小对比
     *
     * @param progress 下载详情
     */
    private void setDownloadApkPercent(Progress progress) {
        String apkPercent = Progress.getApkDownloadedPercent(mContext, progress);
        mItemView.setMiddleText(apkPercent);
    }

    /**
     * 设置进度条进度
     *
     * @param progress 当前的进度
     */
    public void setProgress(int progress) {
        progress = Math.max(0, progress);
        progress = Math.min(100, progress);
        mItemView.setProgress(progress);
    }

    /**
     * 刷新正在下载状态下的控件UI
     *
     * @param progress 下载详情
     */
    protected void refreshDownloadingInfo(Progress progress) {
        mItemView.setNormalButtonText(CommonItemView.Notice.PAUSE.getMsg());
        setDownloadApkPercent(progress);
        mItemView.setSpeed(progress.speed);
        setProgress(progress.percent);
    }

    /**
     * 刷新等待状态下的控件UI
     *
     * @param progress 下载详情
     */
    protected void refreshWaitingInfo(Progress progress) {
        mItemView.setNormalButtonText(CommonItemView.Notice.DOWNLOAD.getMsg());
        setDownloadApkPercent(progress);
        mItemView.setNoticeMsg(CommonItemView.Notice.WAITING.getMsg(), false);
        setProgress(progress.percent);
    }

    /**
     * 刷新暂停状态下的控件UI
     *
     * @param progress 下载详情
     */
    protected void refreshPauseInfo(Progress progress) {
        mItemView.setNormalButtonText(CommonItemView.Notice.CONTINUE);
        setDownloadApkPercent(progress);
        mItemView.setNoticeMsg(CommonItemView.Notice.PAUSED.getMsg(), false);
        setProgress(progress.percent);
    }

    /**
     * 刷新下载出错状态下的控件UI
     *
     * @param progress 下载详情
     */
    protected void refreshErrorInfo(Progress progress) {
        mItemView.setNormalButtonText(CommonItemView.Notice.ERROR);
        setDownloadApkPercent(progress);
        mItemView.setNoticeMsg(CommonItemView.Notice.NO_RESOURCE.getMsg());
        setProgress(progress.percent);
    }

    /**
     * 刷新下载完成状态下的控件UI
     *
     * @param progress 下载详情
     */
    protected void refreshFinishInfo(Progress progress) {
        mItemView.setNormalButtonText(CommonItemView.Notice.INSTALL);
        GameInfo gameInfo = (GameInfo) progress.extra1;
        String versionName = "版本：" + gameInfo.getVersionname();
        mItemView.setMiddleText(versionName);
        String apkSize = Formatter.formatFileSize(mContext, progress.totalSize);
        mItemView.setBottomText(apkSize);
    }


    private void onCommonItemClick() {
        if (mItemView != null) {
            if (mItemView.getForwardActivity() != null) {
                startActivity(mItemView.getForwardActivity());
            } else {
                onItemClick();
            }
        }
    }

    /**
     * item 点击事件
     */
    public abstract void onItemClick();

    /**
     * 设置数据
     *
     * @param data 绑定的数据
     */
    @Override
    public void setDataAndRefreshHolderView(T data) {
        this.data = data;
        refreshViewHolder(data);
    }

    /**
     * 刷新控件
     *
     * @param data 绑定的数据
     */
    protected abstract void refreshViewHolder(T data);

    /**
     * 根据需求刷新控件
     *
     * @param key key
     */
    protected void refreshViewHolderByKey(Object key) {

    }

    public DownloadProgressButton getProgressButton() {
        return mProgressButton;
    }

    /**
     * 获得数据
     *
     * @return 当前绑定的数据
     */
    public T getData() {
        return data;
    }

    protected void startActivity(Class<? extends Activity> cls, Bundle bundle) {
        if (itemView == null) {
            throw new NullPointerException("The itemView can not be null");
        }
        if (cls == null) {
            throw new NullPointerException("The Class can not be null");
        }
        Intent intent = new Intent(itemView.getContext(), cls);
        if (bundle != null) {
            intent.putExtra(EXTRA, bundle);
        }
        itemView.getContext().startActivity(intent);
    }

    protected void startActivity(Class<? extends Activity> clazz) {
        startActivity(clazz, null);
    }

}
