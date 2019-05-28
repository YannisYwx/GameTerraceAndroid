package com.yannis.common.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/25  11:30
 * @email : 923080261@qq.com
 * @description :
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    private List<T> dataList;
    private Context mContext;
    private int mItemLayoutId;

    public Context getContext() {
        return mContext;
    }

    public CommonAdapter(Context context, int layoutId, List<T> dataList) {
        this.dataList = dataList;
        this.mContext = context;
        this.mItemLayoutId = layoutId;
    }

    @Override
    public int getCount() {
        if (dataList != null) {
            return dataList.size();
        }
        return 0;
    }

    @Override
    public T getItem(int i) {
        if (dataList != null) {
            return dataList.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position, convertView,
                parent);
        convert(viewHolder, getItem(position));
        return viewHolder.getConvertView();
    }

    private ViewHolder getViewHolder(int position, View convertView,
                                     ViewGroup parent) {
        return ViewHolder.getViewHolder(mContext, convertView, parent, mItemLayoutId,
                position);
    }

    public abstract void convert(ViewHolder holder, T object);

}
