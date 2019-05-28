package com.yuemai.game34999.presentaion.ui.home.holder;

import android.view.View;
import android.widget.LinearLayout;

import com.yuemai.game34999.presentaion.base.BaseRecycleHolder;
import com.yuemai.game34999.data.bean.OpenServiceTestGame;
import com.yuemai.game34999.presentaion.widget.DrawableCenterTextView;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/10/19  16:41
 * @email : 923080261@qq.com
 * @description :
 */
public class OpenServiceTestHolder extends BaseRecycleHolder<OpenServiceTestGame> {
    DrawableCenterTextView mTextView;


    public OpenServiceTestHolder(LinearLayout itemView) {
        super(itemView);
        mTextView = (DrawableCenterTextView) itemView.getChildAt(0);
    }

    @Override
    protected void refreshViewHolder(OpenServiceTestGame data) {
        mTextView.setVisibility(data.isHasLable ? View.VISIBLE : View.GONE);
        mTextView.setText(data.isHasLable ? data.lable : "");
    }
}
