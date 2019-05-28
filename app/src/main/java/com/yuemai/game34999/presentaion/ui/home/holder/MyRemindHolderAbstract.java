package com.yuemai.game34999.presentaion.ui.home.holder;

import com.yuemai.game34999.data.bean.MyRemindBean;
import com.yuemai.game34999.presentaion.ui.main.adapter.AbstractCommonItemHolder;
import com.yuemai.game34999.presentaion.widget.CommonItemView;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/11/1  17:48
 * @email : 923080261@qq.com
 * @description :
 */
public class MyRemindHolderAbstract extends AbstractCommonItemHolder<MyRemindBean> {

    public MyRemindHolderAbstract(CommonItemView itemView) {
        super(itemView);
    }

    @Override
    protected void refreshViewHolder(MyRemindBean data) {
        mItemView.setCheckBoxVisibility(data.isShowCheckBox);
        mItemView.getCheckBox().setChecked(data.isChecked);
    }

    @Override
    public void onItemClick() {
        mItemView.getCheckBox().setChecked(!mItemView.isChecked());
        data.isChecked = !mItemView.isChecked();
    }

}
