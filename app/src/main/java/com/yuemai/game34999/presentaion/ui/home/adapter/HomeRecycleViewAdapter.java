package com.yuemai.game34999.presentaion.ui.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuemai.game34999.R;
import com.yuemai.game34999.presentaion.base.BaseRecyclerAdapter;
import com.yuemai.game34999.presentaion.ui.game.activity.GameInfoActivity;
import com.yuemai.game34999.presentaion.ui.home.holder.HomeCommonHeaderHolder;
import com.yuemai.game34999.presentaion.ui.main.adapter.common.DownloadGameHolder;
import com.yuemai.game34999.presentaion.ui.home.holder.HomeRecommendHolder;
import com.yuemai.game34999.presentaion.widget.CommonItemView;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/25  9:34
 * @email : 923080261@qq.com
 * @description :
 */
public class HomeRecycleViewAdapter extends BaseRecyclerAdapter {
    private static final int VIEW_HOME_RECOMMEND = 100;
    private static final int VIEW_HOME_COMMON_HEADER = 200;

    public HomeRecycleViewAdapter(List dataList) {
        super(dataList);
    }

    @Override
    protected int getOtherItemType(int position) {
        if (position == 0) {
            return VIEW_HOME_RECOMMEND;
        } else {
            return VIEW_HOME_COMMON_HEADER;
        }
    }

    @Override
    protected boolean isCommonHolderPosition(int position) {
        return position != 0 && !((position - 1) % 6 == 0);
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
    protected RecyclerView.ViewHolder getSpecialHolder(ViewGroup parent, int viewType) {
       if (viewType == VIEW_HOME_RECOMMEND) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_home_recommend_holder, parent, false);
            return new HomeRecommendHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_common_header, parent, false);
            return new HomeCommonHeaderHolder(view);
        }
    }

    @Override
    protected int initOtherItemCount() {
        return 2;
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
