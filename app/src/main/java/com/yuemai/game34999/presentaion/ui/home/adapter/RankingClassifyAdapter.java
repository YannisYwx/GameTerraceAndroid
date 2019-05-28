package com.yuemai.game34999.presentaion.ui.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuemai.game34999.R;
import com.yuemai.game34999.presentaion.base.BaseRecyclerAdapter;
import com.yuemai.game34999.presentaion.ui.home.holder.RankingCommonHolder;
import com.yuemai.game34999.presentaion.ui.home.holder.RankingHeaderHolder;
import com.yuemai.game34999.presentaion.widget.CommonItemView;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/25  14:52
 * @email : 923080261@qq.com
 * @description :
 */
public class RankingClassifyAdapter extends BaseRecyclerAdapter{
    private static final int VIEW_RANK = 100;

    public RankingClassifyAdapter(List dataList) {
        super(dataList);
    }

    @Override
    protected int getOtherItemType(int position) {
        if (position == 0){
            return VIEW_RANK;
        }
        return 0;
    }

    @Override
    protected RecyclerView.ViewHolder getCommonHolder(ViewGroup parent, int viewType) {
        CommonItemView view = new CommonItemView(parent.getContext());
        view.setItemType(CommonItemView.Type.downloadButton);
        view.setDividerPaddingDp(10, 10);
        view.setHasIndex(true);
        view.setBackground(R.drawable.ranklist_systhesize_common);
        return new RankingCommonHolder(view);
    }

    @Override
    protected RecyclerView.ViewHolder getSpecialHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_RANK){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking_header, parent, false);
            return new RankingHeaderHolder(view);
        }
        return null;
    }

    @Override
    protected int initOtherItemCount() {
        return 1;
    }

    @Override
    protected boolean hasLoadMore() {
        return true;
    }

    @Override
    public List onLoadMoreData() throws Exception {
        return null;
    }

}