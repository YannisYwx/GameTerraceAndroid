package com.yuemai.game34999.presentaion.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yuemai.game34999.presentaion.base.impl.BaseRecycleHolderImpl;

import butterknife.ButterKnife;

import static com.yuemai.game34999.core.Constants.EXTRA;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/22  16:30
 * @email : 923080261@qq.com
 * @description :
 */
public abstract class BaseRecycleHolder<T> extends RecyclerView.ViewHolder implements BaseRecycleHolderImpl<T> {
    private T data;
    protected Context mContext;
    public BaseRecycleHolder(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }
    /**
     * 设置数据
     *
     * @param data 绑定的数据
     */
    @Override
    public void setDataAndRefreshHolderView(T data) {
        this.data = data;
        refreshViewHolder(data);
    }

    /**
     * 刷新控件
     *
     * @param data 绑定的数据
     */
    protected abstract void refreshViewHolder(T data);

    /**
     * 根据需求刷新控件
     *
     * @param key key
     */
    protected void refreshViewHolderByKey(Object key) {
    }

    /**
     * 获得数据
     *
     * @return 当前绑定的数据
     */
    public T getData() {
        return data;
    }

    protected void startActivity(Class<? extends Activity> cls, Bundle bundle) {
        if (itemView == null) {
            throw new NullPointerException("The itemView can not be null");
        }
        if (cls == null) {
            throw new NullPointerException("The Class can not be null");
        }
        Intent intent = new Intent(itemView.getContext(), cls);
        if (bundle != null) {
            intent.putExtra(EXTRA, bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        itemView.getContext().startActivity(intent);
    }

    public int getColor(@ColorRes int colorRes) {
        return itemView.getContext().getResources().getColor(colorRes);
    }

    public String getString(@StringRes int stringRes) {
        return itemView.getContext().getResources().getString(stringRes);
    }

    protected void startActivity(Class<? extends Activity> clazz) {
        startActivity(clazz, null);
    }
}
