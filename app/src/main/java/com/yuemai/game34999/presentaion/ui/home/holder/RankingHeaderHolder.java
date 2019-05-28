package com.yuemai.game34999.presentaion.ui.home.holder;

import android.view.View;
import android.widget.ImageView;

import com.yuemai.game34999.R;
import com.yuemai.game34999.presentaion.base.BaseRecycleHolder;
import com.yuemai.game34999.presentaion.ui.game.activity.GameInfoActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author : Yannis.Ywx
 * @createTime : 2017/9/25  14:55
 * @email : 923080261@qq.com
 * @description :
 */
public class RankingHeaderHolder extends BaseRecycleHolder {
    @BindView(R.id.iv_ranking1)
    ImageView iv_ranking1;
    @BindView(R.id.iv_ranking2)
    ImageView iv_ranking2;
    @BindView(R.id.iv_ranking3)
    ImageView iv_ranking3;

    public RankingHeaderHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void refreshViewHolder(Object data) {

    }

    @OnClick({R.id.iv_ranking1, R.id.iv_ranking2, R.id.iv_ranking3})
    public void onRankingIconClick(View view) {
        switch (view.getId()) {
            case R.id.iv_ranking1:
                startActivity(GameInfoActivity.class);
                break;
            case R.id.iv_ranking2:
                startActivity(GameInfoActivity.class);
                break;
            case R.id.iv_ranking3:
                startActivity(GameInfoActivity.class);
                break;
        }
    }
}
