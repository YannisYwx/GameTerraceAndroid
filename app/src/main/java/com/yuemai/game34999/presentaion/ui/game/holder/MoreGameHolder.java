package com.yuemai.game34999.presentaion.ui.game.holder;

import android.view.View;

import com.yuemai.game34999.presentaion.base.BaseRecycleHolder;
import com.yuemai.game34999.presentaion.ui.home.activity.GameListActivity;

import static com.yuemai.game34999.presentaion.ui.home.activity.GameListActivity.MORE_GAME;

/**
 * @author : Yannis.Ywx
 * @createTime : 2018/1/22  17:43
 * @email : 923080261@qq.com
 * @description :
 */
public class MoreGameHolder extends BaseRecycleHolder {
    public MoreGameHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(v -> {
            GameListActivity.start(itemView.getContext(), MORE_GAME);
        });
    }

    @Override
    protected void refreshViewHolder(Object data) {

    }
}
