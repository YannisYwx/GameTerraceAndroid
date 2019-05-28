package com.yuemai.game34999.presentaion.ui.game.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.yuemai.game34999.R;
import com.yuemai.game34999.presentaion.base.BaseRecyclerAdapter;
import com.yuemai.game34999.presentaion.ui.game.holder.GameCommentHolder;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/29  18:41
 * @email : 923080261@qq.com
 * @description :
 */
public class GamePicAdapter extends BaseRecyclerAdapter {
    public GamePicAdapter(List dataList) {
        super(dataList);
    }

    @Override
    protected int getOtherItemType(int position) {
        return 0;
    }

    @Override
    protected RecyclerView.ViewHolder getCommonHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext().getApplicationContext(), R.layout.item_game_pic, null);
        return new GameCommentHolder(view);
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
