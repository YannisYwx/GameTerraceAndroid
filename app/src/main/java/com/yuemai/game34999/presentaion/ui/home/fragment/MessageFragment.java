package com.yuemai.game34999.presentaion.ui.home.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.yannis.common.widget.LoadingPager;
import com.yuemai.game34999.R;
import com.yuemai.game34999.di.component.FragmentComponent;
import com.yuemai.game34999.presentaion.base.BaseListMvpFragment;
import com.yuemai.game34999.presentaion.ui.home.activity.MessageActivity;
import com.yuemai.game34999.presentaion.ui.home.holder.MessageHolder;
import com.yuemai.game34999.presentaion.ui.main.adapter.AbstractSingleItemAdapter;
import com.yuemai.game34999.util.TestDataUtils;

import java.util.List;

import butterknife.BindView;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/19  14:43
 * @email : 923080261@qq.com
 * @description :
 */
public class MessageFragment extends BaseListMvpFragment {
    /**
     * 系统消息
     */
    public static final int MSG_SYSTEM = 0;
    /**
     * 活动消息
     */
    public static final int MSG_ACT = 1;

    public static final String KEY_MSG_TYPE = "KEY_MSG_TYPE";

    @BindView(R.id.rv_data_list)
    RecyclerView mRecyclerView;

    private int type = MSG_SYSTEM;

    private List datas;

    public static MessageFragment getInstance(int type) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_MSG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int initDataFromCache() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public int initData() {
        return LoadingPager.LoadingState.stateSuccess;
    }

    @Override
    public void refreshContentView(View contentView) {
        datas = TestDataUtils.getTestDatas(5);
        type = getArguments().getInt(KEY_MSG_TYPE);

        AbstractSingleItemAdapter adapter = new AbstractSingleItemAdapter(datas) {
            @NonNull
            @Override
            public RecyclerView.ViewHolder createViewHolder(ViewGroup parent) {
                return new MessageHolder(loadItemLayout(R.layout.item_msg, parent));
            }

            @Override
            protected boolean hasLoadMore() {
                return false;
            }

            @Override
            protected List onLoadMoreData() throws Exception {
                return null;
            }
        };
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(position -> {
            Bundle bundle = new Bundle();
            bundle.putInt(KEY_MSG_TYPE, type);
            forward(MessageActivity.class, bundle);
        });
    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void showMsg(String msg) {

    }

    @Override
    public void injectThis(FragmentComponent fragmentComponent) {

    }
}
