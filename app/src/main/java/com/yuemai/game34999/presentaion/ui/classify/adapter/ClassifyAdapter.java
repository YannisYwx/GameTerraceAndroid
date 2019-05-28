package com.yuemai.game34999.presentaion.ui.classify.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.yannis.common.adapter.CommonAdapter;
import com.yannis.common.adapter.ViewHolder;
import com.yuemai.game34999.R;
import com.yuemai.game34999.data.bean.GameType;

import java.util.List;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/25  11:54
 * @email : 923080261@qq.com
 * @description :
 */
public class ClassifyAdapter extends CommonAdapter<GameType> {
    public ClassifyAdapter(Context context, List dataList) {
        super(context, R.layout.item_sort_gridview, dataList);
    }

    @Override
    public void convert(ViewHolder holder, GameType gameType) {
        ImageView iv_bg = holder.getView(R.id.iv_sort_bg);
        ImageView iv_icon = holder.getView(R.id.iv_sort_icon);
        TextView tv_title = holder.getView(R.id.tv_sort_title);
        iv_bg.setImageResource(gameType.getBgRes());
        iv_icon.setImageResource(gameType.getIconRes());
//        ImageLoader.loadImage(getContext(),gameType.getBgRes(),iv_bg);
//        ImageLoader.loadImage(getContext(),gameType.getIconRes(),iv_icon);
        tv_title.setText(gameType.getCname());
    }
}
