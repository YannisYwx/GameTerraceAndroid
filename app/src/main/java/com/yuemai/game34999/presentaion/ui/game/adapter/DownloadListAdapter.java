package com.yuemai.game34999.presentaion.ui.game.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.yuemai.game34999.presentaion.base.BaseRecyclerAdapter;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/28  17:58
 * @email : 923080261@qq.com
 * @description :
 */
public class DownloadListAdapter extends BaseRecyclerAdapter {
    private static final int VIEW_GAME_DETAIL_INFO = 3;

    public DownloadListAdapter(List dataList) {
        super(dataList);
    }

    @Override
    protected int getOtherItemType(int position) {
        return 0;
    }

    @Override
    protected RecyclerView.ViewHolder getCommonHolder(ViewGroup parent, int viewType) {
        return null;
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
    protected List onLoadMoreData() throws Exception {
        return null;
    }
}
