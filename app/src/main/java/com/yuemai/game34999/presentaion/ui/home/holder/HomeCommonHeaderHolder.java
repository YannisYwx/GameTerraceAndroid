package com.yuemai.game34999.presentaion.ui.home.holder;

import android.os.Bundle;
import android.view.View;

import com.yuemai.game34999.R;
import com.yuemai.game34999.presentaion.base.BaseRecycleHolder;
import com.yuemai.game34999.presentaion.ui.home.activity.GameListActivity;
import com.yuemai.game34999.presentaion.widget.ClassifyView;

import butterknife.BindView;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/25  10:10
 * @email : 923080261@qq.com
 * @description :
 */
public class HomeCommonHeaderHolder extends BaseRecycleHolder {
    @BindView(R.id.cv_more)
    ClassifyView mClassifyView;

    public HomeCommonHeaderHolder(View itemView) {
        super(itemView);
        mClassifyView.setOnClassifyClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt(GameListActivity.KEY_GAME_LIST_TYPE, GameListActivity.MORE_GAME);
            startActivity(GameListActivity.class, bundle);

        });
    }

    @Override
    protected void refreshViewHolder(Object data) {

    }
}
