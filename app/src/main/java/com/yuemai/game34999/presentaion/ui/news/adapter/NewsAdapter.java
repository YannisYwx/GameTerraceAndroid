package com.yuemai.game34999.presentaion.ui.news.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.yuemai.game34999.R;
import com.yuemai.game34999.data.bean.News;
import com.yuemai.game34999.presentaion.base.BaseRecyclerAdapter;
import com.yuemai.game34999.presentaion.ui.news.holder.InfoAppraisalHolder;
import com.yuemai.game34999.presentaion.ui.news.holder.InfoCommonHolder;
import com.yuemai.game34999.presentaion.ui.news.holder.InfoSimpleHolder;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/11/24  16:25
 * @email : 923080261@qq.com
 * @description :
 */
public class NewsAdapter extends BaseRecyclerAdapter<News> {

    private static final int VIEW_TYPE_SIMPLE = 1;
    private static final int VIEW_TYPE_APPRAISAL = 2;
    private boolean isShowType = false;

    public NewsAdapter(List<News> dataList, boolean isShowType) {
        super(dataList);
        this.isShowType = isShowType;
    }

    @Override
    protected boolean isCommonHolderPosition(int position) {
        News news = getDataList().get(position);
        int type = news.getID();
        switch (type) {
            case 2:
            case 5:
            case 6:
                //动态\数据\评测
                return false;
            default:
                return true;


        }
    }

    @Override
    protected int getOtherItemType(int position) {
        News news = getDataList().get(position);
        if (news.getID() == 6) {
            //测评
            return VIEW_TYPE_APPRAISAL;
        } else {
            return VIEW_TYPE_SIMPLE;
        }
    }

    @Override
    protected RecyclerView.ViewHolder getCommonHolder(ViewGroup parent, int viewType) {
        return new InfoCommonHolder(loadItemLayout(R.layout.item_information_common, parent), isShowType);
    }

    @Override
    protected RecyclerView.ViewHolder getSpecialHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_APPRAISAL) {
            return new InfoAppraisalHolder(loadItemLayout(R.layout.item_information_appraisal, parent), isShowType);
        } else {
            return new InfoSimpleHolder(loadItemLayout(R.layout.item_information_data, parent), isShowType);
        }
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
    public boolean isLoadFromSelf() {
        return false;
    }

    @Override
    protected List<News> onLoadMoreData() throws Exception {
        return null;
    }

    @Override
    protected Object getOtherData(int position) {
        return mDataList.get(position);
    }
}