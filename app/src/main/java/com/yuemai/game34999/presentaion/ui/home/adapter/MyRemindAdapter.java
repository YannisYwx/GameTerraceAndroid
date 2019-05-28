package com.yuemai.game34999.presentaion.ui.home.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.yuemai.game34999.data.bean.MyRemindBean;
import com.yuemai.game34999.presentaion.base.BaseRecyclerAdapter;
import com.yuemai.game34999.presentaion.ui.home.holder.MyRemindHolderAbstract;
import com.yuemai.game34999.presentaion.widget.CommonItemView;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/11/2  15:40
 * @email : 923080261@qq.com
 * @description :
 */
public class MyRemindAdapter extends BaseRecyclerAdapter<MyRemindBean>{
    public MyRemindAdapter(List<MyRemindBean> dataList) {
        super(dataList);
    }

    @Override
    protected int getOtherItemType(int position) {
        return 0;
    }

    @Override
    protected RecyclerView.ViewHolder getCommonHolder(ViewGroup parent, int viewType) {
        CommonItemView commonItemView = CommonItemView.createNormalItemView(parent.getContext(), CommonItemView.Type.remind);
        return new MyRemindHolderAbstract(commonItemView);
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
    protected List<MyRemindBean> onLoadMoreData() throws Exception {
        return null;
    }

}
