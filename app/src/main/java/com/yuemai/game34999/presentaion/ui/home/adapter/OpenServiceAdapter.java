package com.yuemai.game34999.presentaion.ui.home.adapter;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yuemai.game34999.R;
import com.yuemai.game34999.data.bean.OpenServiceTestGame;
import com.yuemai.game34999.presentaion.base.BaseRecyclerAdapter;
import com.yuemai.game34999.presentaion.ui.home.holder.OpenServiceTestHolder;
import com.yuemai.game34999.presentaion.widget.CommonItemView;
import com.yuemai.game34999.presentaion.widget.DrawableCenterTextView;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/19  11:44
 * @email : 923080261@qq.com
 * @description :
 */
public class OpenServiceAdapter extends BaseRecyclerAdapter<OpenServiceTestGame> {

    public OpenServiceAdapter(List<OpenServiceTestGame> dataList) {
        super(dataList);
    }

    @Override
    protected int getOtherItemType(int position) {
        return 0;
    }

    @Override
    protected RecyclerView.ViewHolder getCommonHolder(ViewGroup parent, int viewType) {
        LinearLayout layout = new LinearLayout(parent.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        DrawableCenterTextView textView = new DrawableCenterTextView(layout.getContext());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Drawable drawable = parent.getContext().getResources().getDrawable(R.drawable.icon_clock);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

        textView.setCompoundDrawables(drawable, null, null, null);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setCompoundDrawablePadding(10);
        textView.setPadding(0, 20, 0, 20);
        textView.setBackgroundColor(parent.getContext().getResources().getColor(R.color.gapBg));
        textView.setLayoutParams(params);
        layout.addView(textView);

        CommonItemView commonItemView = new CommonItemView(parent.getContext(),CommonItemView.Type.remind);
        commonItemView.setDividerPaddingDp(10, 10);
        commonItemView.setLayoutParams(params);
        layout.addView(commonItemView);
        return new OpenServiceTestHolder(layout);
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
