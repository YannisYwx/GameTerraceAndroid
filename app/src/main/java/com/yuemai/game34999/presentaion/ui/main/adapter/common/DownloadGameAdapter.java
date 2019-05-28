package com.yuemai.game34999.presentaion.ui.main.adapter.common;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.yuemai.game34999.presentaion.ui.game.activity.GameInfoActivity;
import com.yuemai.game34999.presentaion.ui.main.adapter.AbstractCommonItemAdapter;
import com.yuemai.game34999.presentaion.widget.CommonItemView;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/25  9:34
 * @email : 923080261@qq.com
 * @description : 游戏下载适配器
 */
public class DownloadGameAdapter extends AbstractCommonItemAdapter {

    public DownloadGameAdapter(List dataList) {
        super(dataList);
    }

    @Override
    protected RecyclerView.ViewHolder getCommonHolder(ViewGroup parent, int viewType) {
        CommonItemView commonItemView = new CommonItemView(parent.getContext());
        commonItemView.setItemType(CommonItemView.Type.downloadButton);
        commonItemView.setDividerPaddingDp(10, 10);
        commonItemView.setForwardActivity(GameInfoActivity.class);
        return new DownloadGameHolder(commonItemView);
    }

    @Override
    protected boolean hasLoadMore() {
        return false;
    }

    @Override
    protected List onLoadMoreData() throws Exception {
        return null;
    }

    @Override
    public CommonItemView createCommonItem() {
        return null;
    }
}
