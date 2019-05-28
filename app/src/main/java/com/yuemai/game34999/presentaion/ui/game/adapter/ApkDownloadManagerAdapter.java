package com.yuemai.game34999.presentaion.ui.game.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.orhanobut.logger.Logger;
import com.yuemai.game34999.R;
import com.yuemai.game34999.data.bean.GameInfo;
import com.yuemai.game34999.presentaion.base.BaseRecyclerAdapter;
import com.yuemai.game34999.presentaion.download.task.DownloadTask;
import com.yuemai.game34999.presentaion.ui.classify.holder.DownloadApkHolder;
import com.yuemai.game34999.presentaion.ui.game.holder.DownloadingApkHolder;
import com.yuemai.game34999.presentaion.ui.game.holder.EmptyHolder;
import com.yuemai.game34999.presentaion.ui.game.holder.MoreGameHolder;
import com.yuemai.game34999.presentaion.widget.ClassifyView;
import com.yuemai.game34999.presentaion.widget.CommonItemView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/13  17:23
 * @email : 923080261@qq.com
 * @description :
 */
public class ApkDownloadManagerAdapter extends BaseRecyclerAdapter<DownloadTask> {
    /**
     * 推荐更多
     */
    private static final int TYPE_RECOMMEND_MORE = 1;
    /**
     * 推荐的游戏
     */
    private static final int TYPE_RECOMMEND = 2;
    private static final int TYPE_EMPTY = 3;


    private List<GameInfo> recommendGameList = new ArrayList<>();

    public ApkDownloadManagerAdapter(List<DownloadTask> dataList) {
        super(dataList);
    }

    public ApkDownloadManagerAdapter(List<DownloadTask> dataList, List<GameInfo> recommendGameList) {
        super(dataList);
        this.recommendGameList.addAll(recommendGameList);
    }

    @Override
    public int getItemCount() {
        int itemCount = initOtherItemCount();
        if (mDataList != null) {
            itemCount = itemCount + mDataList.size();
        } else {
            itemCount++;
        }
        if (recommendGameList.size() != 0) {
            itemCount = itemCount + recommendGameList.size() + 1;
        }
        return itemCount;
    }

    @Override
    protected int getOtherItemType(int position) {
        if (mDataList.size() == 0) {
            if (position == 0) {
                return TYPE_EMPTY;
            } else if (position == 1) {
                return TYPE_RECOMMEND_MORE;
            } else {
                return TYPE_RECOMMEND;
            }
        } else {
            if (position == mDataList.size()) {
                return TYPE_RECOMMEND_MORE;
            } else {
                return TYPE_RECOMMEND;
            }
        }
    }

    @Override
    protected RecyclerView.ViewHolder getCommonHolder(ViewGroup parent, int viewType) {
        //下载任务的Item
        CommonItemView commonItemView = CommonItemView.createNormalItemView(parent.getContext(), CommonItemView.Type.downloading, parent.getContext().getString(R.string.pause));
        commonItemView.setDividerPaddingDp(10, 10);
        return new DownloadingApkHolder(commonItemView);
    }

    @Override
    protected RecyclerView.ViewHolder getSpecialHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_RECOMMEND_MORE) {
            ClassifyView classifyView = new ClassifyView(parent.getContext());
            classifyView.setLeftText("相关游戏推荐");
            return new MoreGameHolder(classifyView);
        } else if (viewType == TYPE_RECOMMEND) {
            CommonItemView commonItemView = new CommonItemView(parent.getContext());
            commonItemView.setItemType(CommonItemView.Type.downloadButton);
            commonItemView.setDividerPaddingDp(10, 10);
            return new DownloadApkHolder(commonItemView);
        } else if (viewType == TYPE_EMPTY) {
            return new EmptyHolder(loadItemLayout(R.layout.layout_empty, parent));
        }
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
    protected List<DownloadTask> onLoadMoreData() throws Exception {
        return null;
    }

    @Override
    protected Object getOtherData(int position) {
        Logger.e("getOtherData-------------- position = " + position + "\n任务总数 = " + mDataList.size() + "\n推荐总数 = " + recommendGameList.size());
        if (position > mDataList.size() && position < (mDataList.size() + recommendGameList.size() + 2)) {
            return recommendGameList.get(position - mDataList.size() - 1);
        }
        return null;
    }

    @Override
    protected boolean isLoadMoreHolderPosition(int position) {
        return position == mDataList.size() + initOtherItemCount() + recommendGameList.size() + 1;
    }

    @Override
    protected boolean isCommonHolderPosition(int position) {
        return !(mDataList == null || mDataList.size() == 0) && position < mDataList.size();
    }

    @Override
    public boolean isShowLoadMoreView() {
        return false;
    }
}
