package com.yuemai.game34999.presentaion.ui.test.download;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuemai.game34999.R;
import com.yuemai.game34999.presentaion.base.BaseRecyclerAdapter;
import com.yuemai.game34999.presentaion.download.task.DownloadTask;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/13  17:23
 * @email : 923080261@qq.com
 * @description :
 */
public class ApkDownloadManagerAdapter extends BaseRecyclerAdapter<DownloadTask> {
    public ApkDownloadManagerAdapter(List<DownloadTask> dataList) {
        super(dataList);
    }

    @Override
    protected int getOtherItemType(int position) {
        return 0;
    }

    @Override
    protected RecyclerView.ViewHolder getCommonHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_download,null);
        return new DownloadHolder(view);
    }

    @Override
    protected RecyclerView.ViewHolder getSpecialHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected int initOtherItemCount() {
        return 0;
    }

    @Override
    protected boolean hasLoadMore() {
        return false;
    }

    @Override
    protected List<DownloadTask> onLoadMoreData() throws Exception {
        return null;
    }
}
