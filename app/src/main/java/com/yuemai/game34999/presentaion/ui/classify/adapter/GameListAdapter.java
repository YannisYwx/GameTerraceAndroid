package com.yuemai.game34999.presentaion.ui.classify.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.yuemai.game34999.data.bean.GameInfo;
import com.yuemai.game34999.presentaion.base.BaseRecyclerAdapter;
import com.yuemai.game34999.presentaion.ui.classify.holder.DownloadApkHolder;
import com.yuemai.game34999.presentaion.widget.CommonItemView;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/12  16:55
 * @email : 923080261@qq.com
 * @description :
 */
public class GameListAdapter extends BaseRecyclerAdapter<GameInfo> {
    public GameListAdapter(List<GameInfo> dataList) {
        super(dataList);
    }

    @Override
    protected int getOtherItemType(int position) {
        return 0;
    }

    @Override
    protected RecyclerView.ViewHolder getCommonHolder(ViewGroup parent, int viewType) {
        CommonItemView commonItemView = new CommonItemView(parent.getContext());
        commonItemView.setItemType(CommonItemView.Type.downloadButton);
        commonItemView.setDividerPaddingDp(10, 10);
        return new DownloadApkHolder(commonItemView);
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
        return true;
    }

    @Override
    protected List<GameInfo> onLoadMoreData() throws Exception {
        return null;
    }

    @Override
    public boolean isLoadFromSelf() {
        return false;
    }
}
