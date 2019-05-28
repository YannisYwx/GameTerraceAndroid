package com.yuemai.game34999.presentaion.ui.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.yuemai.game34999.presentaion.base.BaseRecyclerAdapter;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/11/29  14:16
 * @email : 923080261@qq.com
 * @description :
 */
public abstract class AbstractSingleItemAdapter extends BaseRecyclerAdapter {
    public AbstractSingleItemAdapter(List dataList) {
        super(dataList);
    }

    /**
     * 创建viewHolder
     * @param parent
     * @return
     */
    public abstract RecyclerView.ViewHolder createViewHolder(ViewGroup parent);

    @Override
    protected int getOtherItemType(int position) {
        return 0;
    }

    @Override
    protected RecyclerView.ViewHolder getCommonHolder(ViewGroup parent, int viewType) {
        return createViewHolder(parent);
    }

    @Override
    protected RecyclerView.ViewHolder getSpecialHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected int initOtherItemCount() {
        return 0;
    }

}