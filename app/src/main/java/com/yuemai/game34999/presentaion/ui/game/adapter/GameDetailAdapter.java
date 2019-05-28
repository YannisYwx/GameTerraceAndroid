package com.yuemai.game34999.presentaion.ui.game.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.yuemai.game34999.R;
import com.yuemai.game34999.presentaion.base.BaseRecyclerAdapter;
import com.yuemai.game34999.presentaion.ui.game.holder.GameCommentHolder;
import com.yuemai.game34999.presentaion.ui.game.holder.GameDetailHolder;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/29  18:41
 * @email : 923080261@qq.com
 * @description :
 */
public class GameDetailAdapter extends BaseRecyclerAdapter {
    private static final int VIEW_GAME_DETAIL_INFO = 3;

    public GameDetailAdapter(List dataList) {
        super(dataList);
    }

    @Override
    protected int getOtherItemType(int position) {
        if (position == 0) {
            return VIEW_GAME_DETAIL_INFO;
        }
        return 0;
    }

    @Override
    protected RecyclerView.ViewHolder getCommonHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext().getApplicationContext(), R.layout.item_game_comment, null);
        return new GameCommentHolder(view);
    }

    @Override
    protected RecyclerView.ViewHolder getSpecialHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext().getApplicationContext(), R.layout.item_game_detail, null);
        return new GameDetailHolder(view);
    }

    @Override
    protected int initOtherItemCount() {
        return 1;
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
