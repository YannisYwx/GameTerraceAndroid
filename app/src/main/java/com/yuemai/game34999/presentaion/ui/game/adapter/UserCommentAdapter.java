package com.yuemai.game34999.presentaion.ui.game.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.yuemai.game34999.R;
import com.yuemai.game34999.data.bean.Comment;
import com.yuemai.game34999.presentaion.base.BaseRecyclerAdapter;
import com.yuemai.game34999.presentaion.ui.news.holder.CommentHolder;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/12  14:57
 * @email : 923080261@qq.com
 * @description :
 */
public class UserCommentAdapter extends BaseRecyclerAdapter<Comment> {
    public UserCommentAdapter(List<Comment> dataList) {
        super(dataList);
    }

    @Override
    protected int getOtherItemType(int position) {
        return 0;
    }

    @Override
    protected RecyclerView.ViewHolder getCommonHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_game_comment, null);
        return new CommentHolder(view);
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
    protected List<Comment> onLoadMoreData() throws Exception {
        return null;
    }

    @Override
    public boolean isLoadFromSelf() {
        return false;
    }
}
