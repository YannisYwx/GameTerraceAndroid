package com.yuemai.game34999.presentaion.ui.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.yuemai.game34999.presentaion.base.BaseRecyclerAdapter;
import com.yuemai.game34999.presentaion.widget.CommonItemView;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/27  14:15
 * @email : 923080261@qq.com
 * @description :
 */
public abstract class AbstractCommonItemAdapter extends BaseRecyclerAdapter {
    public AbstractCommonItemAdapter(List dataList) {
        super(dataList);
    }

    @Override
    protected int getOtherItemType(int position) {
        return 0;
    }

    @Override
    protected RecyclerView.ViewHolder getCommonHolder(ViewGroup parent, int viewType) {
        return new AbstractCommonItemHolder(createCommonItem()) {
            @Override
            public void onItemClick() {

            }

            @Override
            protected void refreshViewHolder(Object data) {
                switch (itemType) {
                    case CommonItemView.Type.copy:
                        break;
                    case CommonItemView.Type.downloadButton:
                        break;
                    default:
                        break;
                }
            }
        };
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

    public abstract CommonItemView createCommonItem();
}
